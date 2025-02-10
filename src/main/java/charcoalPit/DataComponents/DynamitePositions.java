package charcoalPit.DataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.stream.Collectors;

public class DynamitePositions {
    public static final DynamitePositions EMPTY=new DynamitePositions(List.of());
    public static final Codec<DynamitePositions> CODEC=position.CODEC
            .sizeLimitedListOf(256)
            .xmap(DynamitePositions::fromSlots,DynamitePositions::asSlots);
    public final List<BlockPos> candles;
    private final int hashCode;
    public DynamitePositions(List<BlockPos> candles){
        this.candles=candles;
        this.hashCode=candles.hashCode();
    }

    static record position(BlockPos pos){
        public static final Codec<position> CODEC= RecordCodecBuilder.create(instance->instance.group(
                BlockPos.CODEC.fieldOf("position").forGetter(position::pos)
        ).apply(instance,position::new));
    }

    private static DynamitePositions fromSlots(List<position> list){
        if(list.isEmpty()){
            return EMPTY;
        }else{
            List<BlockPos> pos=list.stream().map(position::pos).collect(Collectors.toList());
            return new DynamitePositions(pos);
        }
    }

    private List<position> asSlots(){
        return candles.stream().map(position::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof DynamitePositions dynamitePositions && this.candles.equals(dynamitePositions.candles)) {
                return true;
            }

            return false;
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
