package one.devos.nautical.depths_desolation.content.items;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;

public class FogglesItem extends Item implements Equipable {
	public FogglesItem(Properties settings) {
		super(settings);
	}

	@Override
	@NotNull
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}
