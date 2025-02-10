package charcoalPit.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemAeternalisFuel extends Item {
    public ItemAeternalisFuel(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
