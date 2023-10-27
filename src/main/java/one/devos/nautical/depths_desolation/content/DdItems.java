package one.devos.nautical.depths_desolation.content;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.armor.DdMaterials;
import one.devos.nautical.depths_desolation.content.items.FogglesItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import static one.devos.nautical.depths_desolation.DepthsAndDesolation.DEPTHSDESOLATION;

public class DdItems {
	public static final Item FOGGLES = register("foggles", new FogglesItem(
			new Properties().stacksTo(1)
	));

	public static final Item FUR = register("fur", new Item(new Properties().stacksTo(32)));

	public static Item FUR_HELMET = register("fur_helmet", new ArmorItem(DdMaterials.FUR, ArmorItem.Type.HELMET, new Properties().stacksTo(1)));
	public static Item FUR_CHESTPLATE = register("fur_chestplate", new ArmorItem(DdMaterials.FUR, ArmorItem.Type.CHESTPLATE,  new Properties().stacksTo(1)));
	public static Item FUR_LEGGINGS = register("fur_leggings", new ArmorItem(DdMaterials.FUR, ArmorItem.Type.LEGGINGS,  new Properties().stacksTo(1)));
	public static Item FUR_BOOTS = register("fur_boots", new ArmorItem(DdMaterials.FUR, ArmorItem.Type.BOOTS, new Properties().stacksTo(1)));

	// block items

	public static final Item PERMAFROST = register("permafrost", new BlockItem(
			DdBlocks.PERMAFROST, new Properties()
	));
	public static final Item LIGHT_BULB = register("light_bulb", new BlockItem(
			DdBlocks.LIGHT_BULB, new Properties()
	));

	private static Item register(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(DEPTHSDESOLATION).register(content -> content.accept(item));
		return Registry.register(BuiltInRegistries.ITEM, DepthsAndDesolation.id(name), item);
	}

	public static void init() {
	}
}
