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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ServerLevelData;

import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeature;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(method = "setInitialSpawn", at = @At("HEAD"), cancellable = true)
	private static void spawnInLushCave(ServerLevel world, ServerLevelData worldProperties,
										boolean bonusChest, boolean debugWorld, CallbackInfo ci) {
		if (debugWorld)
			return;

		BlockPos spawnPos = findValidSpawn(world);
		System.out.println("Found spawn: " + spawnPos);
		if (spawnPos == null)
			return; // good luck!

		ci.cancel();
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

	@Nullable
	@Unique
	private static BlockPos findValidSpawn(ServerLevel level) {
		Pair<BlockPos, Holder<Biome>> lushCave = level.findClosestBiome3d(
				biome -> biome.is(Biomes.LUSH_CAVES),
				BlockPos.ZERO,
				10_000,
				16,
				16
		);
		if (lushCave == null)
			return null;

		// find a lit open space.
		Optional<BlockPos> found = BlockPos.findClosestMatch(lushCave.getFirst(), 256, 256, pos -> {
			BlockState state = level.getBlockState(pos);
			if (!state.canBeReplaced() || !state.getFluidState().isEmpty())
				return false;
			if (level.getLightEmission(pos) == 0)
				return false;
			BlockState floor = level.getBlockState(pos.below());
			if (floor.canBeReplaced())
				return false;
			if (!level.getBiome(pos).is(Biomes.LUSH_CAVES))
				return false;

			return true;
		});

		return found.orElse(null);
	}
}
