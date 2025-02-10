package charcoalPit.dataMap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FuelTemperatureData(int temperature, boolean clean) {

    public static final Codec<FuelTemperatureData> CODEC= RecordCodecBuilder.create(instance->instance.group(
            Codec.INT.fieldOf("temperature").forGetter(FuelTemperatureData::temperature),
            Codec.BOOL.fieldOf("clean").forGetter(FuelTemperatureData::clean)
    ).apply(instance,FuelTemperatureData::new));
}
