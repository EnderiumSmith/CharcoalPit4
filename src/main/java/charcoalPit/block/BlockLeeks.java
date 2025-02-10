package charcoalPit.block;

import charcoalPit.core.ItemRegistry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class BlockLeeks extends CropBlock {
    public BlockLeeks(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.LEEKS.get();
    }
}
