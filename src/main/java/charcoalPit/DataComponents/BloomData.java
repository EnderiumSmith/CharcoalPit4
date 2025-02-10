package charcoalPit.DataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record BloomData(ItemStack stack, int work, float xp) {

    public static final Codec<BloomData> codec= RecordCodecBuilder.create(instance->instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(BloomData::stack),
            Codec.INT.fieldOf("work").forGetter(BloomData::work),
            Codec.FLOAT.fieldOf("xp").forGetter(BloomData::xp)
    ).apply(instance, BloomData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BloomData> streamCodec=StreamCodec.composite(
            ItemStack.STREAM_CODEC, BloomData::stack,
            ByteBufCodecs.INT, BloomData::work,
            ByteBufCodecs.FLOAT, BloomData::xp,
            BloomData::new
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloomData bloomData = (BloomData) o;
        return work == bloomData.work && Float.compare(xp, bloomData.xp) == 0 && ItemStack.matches(stack,bloomData.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ItemStack.hashItemAndComponents(stack), work, xp);
    }
}
