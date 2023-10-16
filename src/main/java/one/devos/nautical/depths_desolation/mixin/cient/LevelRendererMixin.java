package one.devos.nautical.depths_desolation.mixin.cient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.depths_desolation.client.FogManager;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

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
			method = "renderSky",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;getSunriseColor(FF)[F"
			)
	)
	private float[] noSunsetInSnowFog(float[] original) {
		return FogManager.hideSky() ? null : original;
	}

	@ModifyExpressionValue(
			method = "renderSnowAndRain",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z"
			)
	)
	private boolean precipitationInOverworld(boolean hasPrecipitation) {
		return hasPrecipitation || DdWorldgen.isOverworld(minecraft.level);
	}

	@ModifyVariable(
			method = "renderSnowAndRain",
			slice = @Slice(
					from = @At(
							value = "INVOKE",
							target = "Lcom/mojang/blaze3d/systems/RenderSystem;depthMask(Z)V",
							remap = false
					)
			),
			at = @At("STORE"),
			ordinal = 2
	)
	private float intensifySnow(float progress) { // float g = ticks + tickDelta
		int multiplier = DdWorldgen.isOverworld(minecraft.level) ? 13 : 1;
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
		return DdWorldgen.isOverworld(minecraft.level) ? Precipitation.SNOW : precipitation;
	}
}
