package one.devos.nautical.depths_desolation.mixin.cient;

import java.util.Objects;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Redirect(
			method = "renderSnowAndRain",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z"
			)
	)
	private boolean precipitationInOverworld(Biome instance) {
		Level level = Objects.requireNonNull(minecraft.level);
		return DepthsAndDesolation.isOverworld(level);
	}

	@Redirect(
			method = { "renderSnowAndRain", "tickRain" },
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"
			)
	)
	private Precipitation snowInOverworld(Biome instance, BlockPos pos) {
		Level level = Objects.requireNonNull(minecraft.level);
		return DepthsAndDesolation.isOverworld(level) ? Precipitation.SNOW : instance.getPrecipitationAt(pos);
	}
}
