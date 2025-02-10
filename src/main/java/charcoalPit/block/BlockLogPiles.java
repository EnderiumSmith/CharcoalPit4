package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockLogPiles extends BlockActivePile{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public BlockLogPiles(boolean isRefractory, float tick_chance, Holder<Block> origin, Holder<Block> destination, Properties properties) {
        super(isRefractory, tick_chance, origin, destination, properties);
    }

    @Override
    public void igniteNeighbor(Level level, BlockPos pos, BlockState state) {
        level.setBlock(pos,this.defaultBlockState().setValue(AXIS,state.getValue(AXIS)),3);
        level.playSound(null,pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS,1F,1F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }
}
