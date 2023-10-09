package one.devos.nautical.depths_desolation.mixin.cient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.biome.Biome.Precipitation;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyExpressionValue(
			method = "renderSnowAndRain",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z"
			)
	)
	private boolean precipitationInOverworld(boolean hasPrecipitation) {
		return hasPrecipitation || DepthsAndDesolation.isOverworld(minecraft.level);
	}

	@ModifyVariable(
			method = "renderSnowAndRain",
			slice = @Slice(
					from = @At(
							value = "INVOKE",
							target = "Lcom/mojang/blaze3d/systems/RenderSystem;depthMask(Z)V"
					)
			),
			at = @At("STORE"),
			ordinal = 2
	)
	private float intensifySnow(float progress) { // float g = ticks + tickDelta
		int multiplier = DepthsAndDesolation.isOverworld(minecraft.level) ? 13 : 1;
		return progress * multiplier;
	}

	@ModifyExpressionValue(
			method = { "renderSnowAndRain", "tickRain" },
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"
			)
	)
	private Precipitation snowInOverworld(Precipitation precipitation) {
		return DepthsAndDesolation.isOverworld(minecraft.level) ? Precipitation.SNOW : precipitation;
	}
}
