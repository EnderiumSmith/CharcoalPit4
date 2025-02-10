package charcoalPit.entity;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES=DeferredRegister.create(Registries.ENTITY_TYPE, CharcoalPit.MODID);

    public static final Supplier<EntityType<ThrownJavelin>> JAVELIN=ENTITIES.register("javelin",
            ()-> EntityType.Builder.<ThrownJavelin>of(ThrownJavelin::new, MobCategory.MISC).sized(0.5F,0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20).build("charcoal_pit_javelin"));

}
