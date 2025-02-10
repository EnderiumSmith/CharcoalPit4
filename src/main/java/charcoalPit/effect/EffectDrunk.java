package charcoalPit.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class EffectDrunk extends MobEffect {
    public EffectDrunk(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        amplifier-=3;
        if(amplifier>=1){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20*5));
        }
        if(amplifier>=2){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20*5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20*5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20*5));
        }
        if(amplifier>=3){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20*5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 20*5));
        }
        if(amplifier>=4){
            livingEntity.hurt(livingEntity.level().damageSources().generic(), (amplifier-3)*2);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {

    }
}
