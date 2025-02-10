package charcoalPit.block;

import charcoalPit.core.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class BlockBlastFurnaceMiddle extends Block {
    public BlockBlastFurnaceMiddle(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(direction==Direction.DOWN && !(neighborState.getBlock() instanceof BlockBlastFurnace))
            return Blocks.AIR.defaultBlockState();
        if(direction==Direction.UP && !(neighborState.getBlock() instanceof BlockBloomeryChimney))
            return Blocks.AIR.defaultBlockState();
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), BlockRegistry.BLAST_FURNACE_CHIMNEY.get().defaultBlockState(),3);
    }
}
