package one.devos.nautical.depths_desolation;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;

import one.devos.nautical.depths_desolation.client.FogManager;

import one.devos.nautical.depths_desolation.content.DdBlocks;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class DdClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientTickEvents.START.register(FogManager::tick);
		ColorProviderRegistry.BLOCK.register(
				(state, level, pos, tintIndex) -> level != null && pos != null
						? BiomeColors.getAverageGrassColor(level, pos)
						: GrassColor.getDefaultColor(),
				DdBlocks.FULL_GRASS
		);
	}
}
