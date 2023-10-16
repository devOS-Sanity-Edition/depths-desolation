package one.devos.nautical.depths_desolation.mixin.cient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import one.devos.nautical.depths_desolation.client.FogManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.CubicSampler.Vec3Fetcher;
import net.minecraft.world.phys.Vec3;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
	@Unique
	private static float partialTicks;

	@Inject(method = "setupColor", at = @At("HEAD"))
	private static void yoinkPartialTicks(Camera camera, float tickDelta, ClientLevel world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		partialTicks = tickDelta;
	}

	@ModifyExpressionValue(
			method = "method_24873", // gaussianSampleVec3 lambda
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/phys/Vec3;fromRGB24(I)Lnet/minecraft/world/phys/Vec3;"
			)
	)
	private static Vec3 handleSnowFogColor(Vec3 originalColors) {
		return FogManager.modifyFogColors(originalColors, partialTicks);
	}

	@ModifyExpressionValue(method = "setupColor", at = @At(value = "CONSTANT", args = "intValue=4"))
	private static int noSunsetInSnowFog(int thresholdForSunset) {
		return FogManager.hideSky() ? Integer.MAX_VALUE : thresholdForSunset;
	}

	@ModifyArgs(
			method = "setupFog",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V",
					remap = false
			)
	)
	private static void handleSnowFogDistanceStart(Args args,
												   Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta) {
		float start = args.get(0);
		float newStart = FogManager.modifyFogStart(start, camera, viewDistance, tickDelta);
		args.set(0, newStart);
	}

	@ModifyArgs(
			method = "setupFog",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V",
					remap = false
			)
	)
	private static void handleSnowFogDistanceEnd(Args args,
												 Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta) {
		float end = args.get(0);
		float newEnd = FogManager.modifyFogEnd(end, camera, viewDistance, tickDelta);
		args.set(0, newEnd);
	}
}
