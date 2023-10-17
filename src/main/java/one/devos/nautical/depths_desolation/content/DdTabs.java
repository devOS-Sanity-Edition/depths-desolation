package one.devos.nautical.depths_desolation.content;

import java.util.Map;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DdTabs {
	public static final Map<Item, Item[]> ASSOCIATIONS = Map.of(
			Items.COARSE_DIRT, new Item[] { DdItems.PERMAFROST },
			Items.GLOW_BERRIES, new Item[] { DdItems.LIGHT_BULB }
	);

	public static void init() {
		ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
			ASSOCIATIONS.forEach((dependency, items) -> {
				boolean hasDependency = entries.getDisplayStacks().stream().anyMatch(stack -> stack.is(dependency));
				if (hasDependency) {
					entries.addAfter(dependency, items);
				}
			});
		});
	}
}
