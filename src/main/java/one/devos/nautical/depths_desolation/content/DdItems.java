package one.devos.nautical.depths_desolation.content;

import net.minecraft.world.item.BlockItem;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.items.FogglesItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class DdItems {
	public static final Item FOGGLES = register("foggles", new FogglesItem(
			new Properties().stacksTo(1)
	));

	// block items

	public static final Item PERMAFROST = register("permafrost", new BlockItem(
			DdBlocks.PERMAFROST, new Properties()
	));

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, DepthsAndDesolation.id(name), item);
	}

	public static void init() {
	}
}
