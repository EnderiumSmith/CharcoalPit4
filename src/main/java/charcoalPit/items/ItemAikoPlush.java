package charcoalPit.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.Block;

public class ItemAikoPlush extends BlockItem implements Equipable {
	public ItemAikoPlush(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}
