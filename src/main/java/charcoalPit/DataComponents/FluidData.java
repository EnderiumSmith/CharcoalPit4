package charcoalPit.DataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;

public record FluidData(FluidStack fluid) {

    public static final Codec<FluidData> Codec= RecordCodecBuilder.create(instance->instance.group(
            FluidStack.CODEC.fieldOf("fluid").forGetter(FluidData::fluid)
    ).apply(instance,FluidData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidData> streamCodec= StreamCodec.composite(
            FluidStack.STREAM_CODEC,FluidData::fluid,
            FluidData::new
    );

    @Override
    public boolean equals(Object o) {
        return o instanceof FluidStack s && FluidStack.isSameFluidSameComponents(fluid,s);
    }

    @Override
    public int hashCode() {
        return fluid.hashCode();
    }
}
