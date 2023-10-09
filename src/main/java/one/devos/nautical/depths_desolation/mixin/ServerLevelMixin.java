package one.devos.nautical.depths_desolation.mixin;

import java.util.function.Supplier;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
	protected ServerLevelMixin(WritableLevelData worldProperties, ResourceKey<Level> registryKey, RegistryAccess registryManager, Holder<DimensionType> dimension, Supplier<ProfilerFiller> profiler, boolean client, boolean debug, long seed, int maxChainedNeighborUpdates) {
		super(worldProperties, registryKey, registryManager, dimension, profiler, client, debug, seed, maxChainedNeighborUpdates);
	}

	@Inject(method = "advanceWeatherCycle", at = @At("HEAD"), cancellable = true)
	private void constantWeather(CallbackInfo ci) {
		if (Level.OVERWORLD == this.dimension()) {
			ci.cancel();
		}
	}

	@Redirect(
			method = "tickChunk",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"
			)
	)
	private Precipitation alwaysSnow(Biome instance, BlockPos pos) {
		return DepthsAndDesolation.isOverworld(this) ? Precipitation.SNOW : instance.getPrecipitationAt(pos);
	}
}
