package one.devos.nautical.depths_desolation.mixin.cient;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CubicSampler.Vec3Fetcher;
import net.minecraft.util.Mth;
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
	private static long timeOfTransitionEnd = -1;
	@Unique
	private static boolean inSnowFog = false;

	@Unique
	private static double fogress; // fog progress, 0-1

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

	@ModifyArgs(
			method = "setupFog",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
			)
	)
	private static void handleSnowFogDistanceStart(Args args,
												   Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta) {
		float start = args.get(0);
		float snowFogEnd = Math.min(SNOW_FOG_END, viewDistance);
		float snowFogStart = -snowFogEnd / 2;
		args.set(0, (float) Mth.lerp(fogress, start, snowFogStart));
	}

	@ModifyArgs(
			method = "setupFog",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"
			)
	)
	private static void handleSnowFogDistanceEnd(Args args,
												 Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta) {
		float end = args.get(0);
		float snowFogEnd = Math.min(SNOW_FOG_END, viewDistance);
		args.set(0, (float) Mth.lerp(fogress, end, snowFogEnd));
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
			double progress = 1 - (diff / (double) TIME_FOR_MAX_FOG);
			double capped = Math.min(1, progress);
			double smooth = smoothenProgress(capped);
			fogress = inSnowFog ? smooth : 1 - smooth;
			if (currentTime > timeOfTransitionEnd) {
				timeOfTransitionEnd = -1;
			}
		}
	}

	@Unique
	private static boolean getSnowFogStatus(ClientLevel level, Camera camera) {
		if (DepthsAndDesolation.isOverworld(level)) {
			BlockPos blockPos = camera.getBlockPosition();
			return level.canSeeSky(blockPos);
		}
		return false;
	}

	@Unique
	private static double smoothenProgress(double progress) {
		double inSineDomain = Mth.map(progress, 0, 1, -Mth.HALF_PI, Mth.HALF_PI);
		return (Math.sin(inSineDomain) + 1) / 2;
	}
}