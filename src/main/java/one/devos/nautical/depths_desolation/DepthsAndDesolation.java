package one.devos.nautical.depths_desolation;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.ResetChunksCommand;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import one.devos.nautical.depths_desolation.content.DdBlocks;

import one.devos.nautical.depths_desolation.content.DdTabs;

import one.devos.nautical.depths_desolation.duck.LevelExt;
import one.devos.nautical.depths_desolation.net.DdNetworking;
import one.devos.nautical.depths_desolation.util.TagBlockStateProvider;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import one.devos.nautical.depths_desolation.content.DdItems;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static one.devos.nautical.depths_desolation.content.DdItems.FUR;

public class DepthsAndDesolation implements ModInitializer {
	public static final String ID = "depths_desolation";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final ResourceKey<CreativeModeTab> DEPTHSDESOLATION = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ID, "depths_desolation.tab"));
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(FUR))
			.title(Component.translatable("itemGroup.depths_desolation.depths_desolation"))
			.build();

	@Override
	public void onInitialize(ModContainer mod) {
		DdBlocks.init();
		DdItems.init();
		DdTabs.init();
		DdWorldgen.init();
		DdNetworking.init();
		TagBlockStateProvider.init();
		CommandRegistrationCallback.EVENT.register((dispatcher, ctx, env) -> ResetChunksCommand.register(dispatcher));
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DEPTHSDESOLATION, ITEM_GROUP);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static boolean isDesolate(LevelReader reader) {
		return reader instanceof LevelExt level && level.dd$isDesolate();
	}
}
