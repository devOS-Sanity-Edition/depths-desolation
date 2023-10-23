package one.devos.nautical.depths_desolation;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.ResetChunksCommand;

import one.devos.nautical.depths_desolation.content.DdBlocks;

import one.devos.nautical.depths_desolation.content.DdTabs;

import one.devos.nautical.depths_desolation.util.TagBlockStateProvider;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import one.devos.nautical.depths_desolation.content.DdItems;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.Registry;

public class DepthsAndDesolation implements ModInitializer {
	public static final String ID = "depths_desolation";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		DdBlocks.init();
		DdItems.init();
		DdTabs.init();
		DdWorldgen.init();
		TagBlockStateProvider.init();

		CommandRegistrationCallback.EVENT.register((dispatcher, ctx, env) -> ResetChunksCommand.register(dispatcher));
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
