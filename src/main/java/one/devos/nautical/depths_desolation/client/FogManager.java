package one.devos.nautical.depths_desolation.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import one.devos.nautical.depths_desolation.content.DdItems;

import one.devos.nautical.depths_desolation.content.DdWorldgen;

public class FogManager {
	public static final int SNOW_FOG_END = 16 * 6; // 6 chunks
	public static final int SNOW_FOG_END_FOGGLES = 16 * 12; // 12 chunks
	public static final int TICKS_FOR_MAX_FOG = 60;
	public static final Vec3 SNOW_FOG_COLORS = new Vec3(1, 1, 1);

	private static float fogress; // fog progress, 0 - TICKS_FOR_MAX_FOG

	public static float getFogress(float partialTicks) {
		float ticks = fogress + partialTicks;
		float progress = ticks / TICKS_FOR_MAX_FOG;
		return smoothenProgress(progress);
	}

	public static boolean hideSky() {
		return fogress > (TICKS_FOR_MAX_FOG / 2f);
	}

	public static Vec3 modifyFogColors(Vec3 originalColors, float partialTicks) {
		if (isActive()) {
			return originalColors.lerp(SNOW_FOG_COLORS, getFogress(partialTicks));
		} else {
			return originalColors;
		}
	}

	public static float modifyFogStart(float originalStart, Camera camera, float viewDistance, float partialTicks) {
		System.out.println(getFogress(partialTicks));
		if (isActive()) {
			float end = getSnowFogEnd(camera, viewDistance);
			float snowStart = -end;
			return Mth.lerp(getFogress(partialTicks), originalStart, snowStart);
		} else {
			return originalStart;
		}
	}

	public static float modifyFogEnd(float originalEnd, Camera camera, float viewDistance, float partialTicks) {
		if (isActive()) {
			float snowEnd = getSnowFogEnd(camera, viewDistance);
			return Mth.lerp(getFogress(partialTicks), originalEnd, snowEnd);
		} else {
			return originalEnd;
		}
	}

	public static float getSnowFogEnd(Camera camera, float viewDistance) {
		boolean foggles = camera.getEntity() instanceof LivingEntity living
				&& living.getItemBySlot(EquipmentSlot.HEAD).is(DdItems.FOGGLES);
		float end = foggles ? SNOW_FOG_END_FOGGLES : SNOW_FOG_END;
		return Math.min(end, viewDistance);
	}

	public static boolean isActive() {
		return fogress > Mth.EPSILON;
	}

	public static void tick(Minecraft mc) {
		boolean inSnowFog = getSnowFogStatus(mc.level, mc.cameraEntity);
		if (inSnowFog && fogress < TICKS_FOR_MAX_FOG) {
			fogress += 1;
		} else if (!inSnowFog && fogress != 0) {
			fogress -= 1;
		}
	}

	private static boolean getSnowFogStatus(ClientLevel level, Entity entity) {
		if (DdWorldgen.isOverworld(level)) {
			BlockPos blockPos = entity.blockPosition();
			return level.canSeeSky(blockPos);
		}
		return false;
	}

	private static float smoothenProgress(float progress) {
		float inSineDomain = Mth.map(progress, 0f, 1f, -Mth.HALF_PI, Mth.HALF_PI);
		return (Mth.sin(inSineDomain) + 1) / 2;
	}
}
