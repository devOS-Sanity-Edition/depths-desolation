package one.devos.nautical.depths_desolation.mixin;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import one.devos.nautical.depths_desolation.content.worldgen.chunkgen.DesolateChunkGenerator;
import one.devos.nautical.depths_desolation.duck.LevelExt;
import one.devos.nautical.depths_desolation.duck.ServerLevelExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements ServerLevelExt {
	@Unique
	private final AtomicBoolean needsSpawnSet = new AtomicBoolean(false);

	protected ServerLevelMixin(WritableLevelData worldProperties, ResourceKey<Level> registryKey, RegistryAccess registryManager, Holder<DimensionType> dimension, Supplier<ProfilerFiller> profiler, boolean client, boolean debug, long seed, int maxChainedNeighborUpdates) {
		super(worldProperties, registryKey, registryManager, dimension, profiler, client, debug, seed, maxChainedNeighborUpdates);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void setDesolate(MinecraftServer server, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess,
							 ServerLevelData worldProperties, ResourceKey<Level> registryKey, LevelStem levelStem,
							 ChunkProgressListener chunkProgressListener, boolean bl, long l, List<CustomSpawner> spawners,
							 boolean shouldTickTime, RandomSequences randomSequences, CallbackInfo ci) {
		if (levelStem.generator() instanceof DesolateChunkGenerator) {
			((LevelExt) this).dd$setDesolate(true);
		}
	}

	@Inject(method = "advanceWeatherCycle", at = @At("HEAD"), cancellable = true)
	private void constantWeather(CallbackInfo ci) {
		if (DepthsAndDesolation.isDesolate(this)) {
			ci.cancel();
		}
	}

	@ModifyExpressionValue(
			method = "tickChunk",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"
			)
	)
	private Precipitation alwaysSnow(Precipitation original) {
		return DepthsAndDesolation.isDesolate(this) ? Precipitation.SNOW : original;
	}

	@Override
	public AtomicBoolean dd$needsSpawnSet() {
		return needsSpawnSet;
	}
}
