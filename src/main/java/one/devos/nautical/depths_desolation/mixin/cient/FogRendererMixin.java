package one.devos.nautical.depths_desolation.mixin.cient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import one.devos.nautical.depths_desolation.content.DdItems;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CubicSampler.Vec3Fetcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
	@Unique
	private static final int TIME_FOR_MAX_FOG = 3000;
	@Unique
	private static final Vec3 SNOW_FOG_COLORS = new Vec3(1, 1, 1);
	@Unique
	private static final int SNOW_FOG_END = 16 * 6; // 6 chunks
	@Unique
	private static final int SNOW_FOG_END_FOGGLES = 16 * 12; // 12 chunks

	@Unique
	private static long timeOfTransitionEnd = -1;
	@Unique
	private static boolean inSnowFog = false;

	@Unique
	private static float fogress; // fog progress, 0-1

	@WrapOperation(
			method = "setupColor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/CubicSampler;gaussianSampleVec3(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/util/CubicSampler$Vec3Fetcher;)Lnet/minecraft/world/phys/Vec3;"
			)
	)
	private static Vec3 handleSnowFogColor(Vec3 position, Vec3Fetcher vec3dFetcher, Operation<Vec3> original,
										   Camera camera, float tickDelta, ClientLevel world, int viewDistance, float skyDarkness) {
		// color runs before distance, so it handles progress
		updateSnowFogProgress(world, camera);
		Vec3 normalColors = original.call(position, vec3dFetcher);
		return normalColors.lerp(SNOW_FOG_COLORS, fogress);
	}

	@ModifyExpressionValue(method = "setupColor", at = @At(value = "CONSTANT", args = "intValue=4"))
	private static int noSunsetInSnowFog(int thresholdForSunset) {
		return fogress > 0.5 ? Integer.MAX_VALUE : thresholdForSunset;
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
		float snowFogEnd = getSnowFogEnd(camera, viewDistance);
		float snowFogStart = -snowFogEnd;
		args.set(0, Mth.lerp(fogress, start, snowFogStart));
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
		float snowFogEnd = getSnowFogEnd(camera, viewDistance);
		args.set(0, Mth.lerp(fogress, end, snowFogEnd));
	}

	@Unique
	private static float getSnowFogEnd(Camera camera, float viewDistance) {
		boolean foggles = camera.getEntity() instanceof LivingEntity living
				&& living.getItemBySlot(EquipmentSlot.HEAD).is(DdItems.FOGGLES);
		float end = foggles ? SNOW_FOG_END_FOGGLES : SNOW_FOG_END;
		return Math.min(end, viewDistance);
	}

	@Unique
	private static void updateSnowFogProgress(ClientLevel level, Camera camera) {
		boolean lastInSnowFog = inSnowFog;
		inSnowFog = getSnowFogStatus(level, camera);
		if (lastInSnowFog != inSnowFog) {
			// changed, reset transition
			timeOfTransitionEnd = Util.getMillis() + TIME_FOR_MAX_FOG;
		}
		if (timeOfTransitionEnd != -1) {
			long currentTime = Util.getMillis();
			long diff = timeOfTransitionEnd - currentTime; // starts at max, counts down
			float progress = 1 - (diff / (float) TIME_FOR_MAX_FOG);
			float capped = Math.min(1, progress);
			float smooth = smoothenProgress(capped);
			fogress = inSnowFog ? smooth : 1 - smooth;
			if (currentTime > timeOfTransitionEnd) {
				timeOfTransitionEnd = -1;
			}
		}
	}

	@Unique
	private static boolean getSnowFogStatus(ClientLevel level, Camera camera) {
		if (DdWorldgen.isOverworld(level)) {
			BlockPos blockPos = camera.getBlockPosition();
			return level.canSeeSky(blockPos);
		}
		return false;
	}

	@Unique
	private static float smoothenProgress(float progress) {
		float inSineDomain = Mth.map(progress, 0f, 1f, -Mth.HALF_PI, Mth.HALF_PI);
		return (Mth.sin(inSineDomain) + 1) / 2;
	}
}
