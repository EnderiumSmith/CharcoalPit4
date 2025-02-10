package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.DataComponents.BloomData;
import charcoalPit.DataComponents.DynamitePositions;
import charcoalPit.DataComponents.FluidData;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistry {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS=DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CharcoalPit.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<BloomData>> BLOOM_DATA =DATA_COMPONENTS.registerComponentType("bloom_data",
            builder->builder.persistent(BloomData.codec).networkSynchronized(BloomData.streamCodec));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<DynamitePositions>> DYNAMITE_POSITIONS=DATA_COMPONENTS.registerComponentType("dynamite_positions",
            builder->builder.persistent(DynamitePositions.CODEC));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Unit>> MUSKET_LOADED=DATA_COMPONENTS.registerComponentType("musket_loaded",
            builder->builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<SimpleFluidContent>> FLUID_DATA=DATA_COMPONENTS.registerComponentType("fluid_data",
            builder->builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> FRUIT_LEAVES_STATE=DATA_COMPONENTS.registerComponentType("fruit_leaves_state",
            builder->builder.persistent(ExtraCodecs.intRange(0,3)).networkSynchronized(ByteBufCodecs.VAR_INT));

}
