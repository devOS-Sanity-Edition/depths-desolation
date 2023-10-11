package one.devos.nautical.depths_desolation.mixin;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.ServerLevelData;

import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(method = "setInitialSpawn", at = @At("HEAD"), cancellable = true)
	private static void spawnInLushCave(ServerLevel world, ServerLevelData worldProperties,
										boolean bonusChest, boolean debugWorld, CallbackInfo ci) {
		if (debugWorld)
			return;

		Pair<BlockPos, Holder<Biome>> lushCave = world.findClosestBiome3d(
				biome -> biome.is(Biomes.LUSH_CAVES),
				BlockPos.ZERO,
				10_000,
				16,
				16
		);

		if (lushCave == null)
			return;

		ci.cancel();
		BlockPos spawnPos = lushCave.getFirst();
		worldProperties.setSpawn(spawnPos, 0);

		ResourceKey<ConfiguredFeature<?, ?>> spawnCave = SpawnCaveFeature.get(bonusChest);
		world.registryAccess().registry(Registries.CONFIGURED_FEATURE)
				.flatMap(registry -> registry.getHolder(spawnCave))
				.ifPresent(
						feature -> feature.value().place(
								world,
								world.getChunkSource().getGenerator(),
								world.random,
								spawnPos
						)
				);
	}
}
