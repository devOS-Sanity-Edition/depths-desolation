package one.devos.nautical.depths_desolation;

import net.minecraft.resources.ResourceLocation;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import one.devos.nautical.depths_desolation.content.DdItems;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthsAndDesolation implements ModInitializer {
	public static final String ID = "depths_desolation";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		DdItems.init();
		DdWorldgen.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
