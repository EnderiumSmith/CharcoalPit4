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
    //public static final TankOnlyFluid HONEY_DEWOIS=new TankOnlyFluid("honey_dewois", FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY,SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL,SoundEvents.BUCKET_FILL));




}
