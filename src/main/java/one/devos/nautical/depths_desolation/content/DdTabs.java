package one.devos.nautical.depths_desolation.content;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Items;

public class DdTabs {
	public static void init() {
		ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
			boolean hasCoarseDirt = entries.getDisplayStacks().stream()
					.anyMatch(stack -> stack.is(Items.COARSE_DIRT));
			if (hasCoarseDirt) {
				entries.addAfter(Items.COARSE_DIRT, DdItems.PERMAFROST);
			}
		});
	}
}
