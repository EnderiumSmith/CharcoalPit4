package charcoalPit.items;

import charcoalPit.core.BlockRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ItemTuyere extends Item {
    public ItemTuyere(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockState state=pContext.getLevel().getBlockState(pContext.getClickedPos());
        if(state.getBlock()== Blocks.BLAST_FURNACE){
            pContext.getLevel().setBlock(pContext.getClickedPos(),
                    BlockRegistry.BLAST_FURNACE.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,state.getValue(BlockStateProperties.HORIZONTAL_FACING)),
                    3);
            pContext.getItemInHand().shrink(1);
            pContext.getLevel().playSound(pContext.getPlayer(),pContext.getClickedPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS,1F,1F);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(pContext);
    }
}
