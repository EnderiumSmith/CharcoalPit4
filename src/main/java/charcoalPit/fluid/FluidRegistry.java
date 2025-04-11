package charcoalPit.fluid;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FluidRegistry {

    public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(Registries.FLUID, CharcoalPit.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES=DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES,CharcoalPit.MODID);

    public static final FluidComplex CREOSOTE=new FluidComplex("creosote", FluidType.Properties.create().density(800).viscosity(2000).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid ALCOHOL=new TankOnlyFluid("alcohol", FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid VINEGAR=new TankOnlyFluid("vinegar", FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid ETHANOL=new TankOnlyFluid("ethanol", FluidType.Properties.create().density(790).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid LIMEWATER=new TankOnlyFluid("limewater", FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid SEED_OIL=new TankOnlyFluid("seed_oil", FluidType.Properties.create().density(920).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid BIODIESEL=new TankOnlyFluid("biodiesel", FluidType.Properties.create().density(880).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid OIL_OF_VITRIOL=new TankOnlyFluid("oil_of_vitriol",FluidType.Properties.create().density(1830).viscosity(26000).sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid HYDROGEN_SULFIDE=new TankOnlyFluid("hydrogen_sulfide",FluidType.Properties.create().density(15).viscosity(1).canSwim(false).canPushEntity(false).fallDistanceModifier(1F).sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid MURIATIC_ACID=new TankOnlyFluid("muriatic_acid",FluidType.Properties.create().density(15).viscosity(1).sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid CHLORINE=new TankOnlyFluid("chlorine",FluidType.Properties.create().density(32).viscosity(1).canSwim(false).canPushEntity(false).fallDistanceModifier(1F).sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid ACETYLENE=new TankOnlyFluid("acetylene",FluidType.Properties.create().density(-11).viscosity(1).sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));
    public static final TankOnlyFluid NITERWATER=new TankOnlyFluid("niterwater",FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));




}
