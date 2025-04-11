package charcoalPit.effect;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {

    public static final DeferredRegister<MobEffect> EFFECTS=DeferredRegister.create(Registries.MOB_EFFECT, CharcoalPit.MODID);

    public static final DeferredHolder<MobEffect,EffectDrunk> DRUNK=EFFECTS.register("drunk",()->new EffectDrunk(MobEffectCategory.HARMFUL,0xFFFFFF));

    public static final DeferredRegister<Potion> POTIONS=DeferredRegister.create(Registries.POTION,CharcoalPit.MODID);

    public static final DeferredHolder<Potion,Potion> HEALTH_BOOST=POTIONS.register("charcoalpit_health_boost",
            ()->new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST,20*60*3),new MobEffectInstance(MobEffects.REGENERATION,100,1)));
    public static final DeferredHolder<Potion,Potion> HEALTH_BOOST_EXTENDED=POTIONS.register("charcoalpit_health_boost_extended",
            ()->new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST,20*60*8),new MobEffectInstance(MobEffects.REGENERATION,100,1)));
    public static final DeferredHolder<Potion,Potion> HEALTH_BOOST_ENHANCED=POTIONS.register("charcoalpit_health_boost_enhanced",
            ()->new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST,20*90,1),new MobEffectInstance(MobEffects.REGENERATION,200,1)));

    public static final DeferredHolder<Potion,Potion> ABSORPTION=POTIONS.register("charcoalpit_absorption",
            ()->new Potion(new MobEffectInstance(MobEffects.ABSORPTION,20*60*3,1)));
    public static final DeferredHolder<Potion,Potion> ABSORPTION_EXTENDED=POTIONS.register("charcoalpit_absorption_extended",
            ()->new Potion(new MobEffectInstance(MobEffects.ABSORPTION,20*60*8,1)));
}
