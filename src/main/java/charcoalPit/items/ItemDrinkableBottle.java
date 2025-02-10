package charcoalPit.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class ItemDrinkableBottle extends Item {

    final boolean type;

    public ItemDrinkableBottle(boolean type,Properties properties) {
        super(properties);
        this.type=type;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return type?SoundEvents.GENERIC_DRINK:SoundEvents.HONEY_DRINK;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
