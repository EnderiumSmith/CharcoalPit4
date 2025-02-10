package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockMachineBase extends Block {

    public static final EnumProperty<Direction.Axis> AXIS= BlockStateProperties.AXIS;
    public static final BooleanProperty LIT=BlockStateProperties.LIT;

    protected static final VoxelShape BASE_TOP= Shapes.box(0D,0D,0D,1D,4D/16D,1D);
    protected static final VoxelShape BASE_BOTTOM=Shapes.box(0D,12D/16D,0D,1D,1D,1D);
    protected static final VoxelShape BASE_EAST=Shapes.box(0D,0D,0D,4D/16D,1D,1D);
    protected static final VoxelShape BASE_WEST=Shapes.box(12D/16D,0D,0D,1D,1D,1D);
    protected static final VoxelShape BASE_SOUTH=Shapes.box(0D,0D,0D,1D,1D,4D/16D);
    protected static final VoxelShape BASE_NORTH=Shapes.box(0D,0D,12D/16D,1D,1D,1D);
    protected static final VoxelShape MIDDLE=Shapes.box(2D/16D,2D/16D,2D/16D,14D/16D,14D/16D,14D/16D);
    protected static final VoxelShape Y=Shapes.join(MIDDLE,Shapes.join(BASE_TOP,BASE_BOTTOM, BooleanOp.OR),BooleanOp.OR);
    protected static final VoxelShape Z=Shapes.join(MIDDLE,Shapes.join(BASE_NORTH,BASE_SOUTH,BooleanOp.OR),BooleanOp.OR);
    protected static final VoxelShape X=Shapes.join(MIDDLE,Shapes.join(BASE_WEST,BASE_EAST,BooleanOp.OR),BooleanOp.OR);

    public BlockMachineBase(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LIT,false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)){
            case X -> {
                return X;
            }
            case Z -> {
                return Z;
            }
            default -> {
                return Y;
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT)?15:0;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getAxis());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS,LIT);
    }
}
