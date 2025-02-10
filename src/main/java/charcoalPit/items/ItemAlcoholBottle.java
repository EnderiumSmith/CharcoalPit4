package charcoalPit.items;

import charcoalPit.effect.ModPotions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemAlcoholBottle extends Item {
    final int alcohol;
    public ItemAlcoholBottle(int alcohol,Properties properties) {
        super(properties);
        this.alcohol=alcohol;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        int amp;
        if(livingEntity.hasEffect(ModPotions.DRUNK)){
            amp=livingEntity.getEffect(ModPotions.DRUNK).getAmplifier()+alcohol;
        }else{
            amp=alcohol-1;
        }
        livingEntity.addEffect(new MobEffectInstance(ModPotions.DRUNK,20*60*3,amp));
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
