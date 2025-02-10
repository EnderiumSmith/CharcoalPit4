package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockBellows extends Block {

    public static final DirectionProperty FACING= BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED=BlockStateProperties.POWERED;

    public static final VoxelShape NORTH= Shapes.box(0D, 0D, 0D, 1D, 1D, 6D/16D);
    public static final VoxelShape SOUTH=Shapes.box(0D, 0D, 10D/16D, 1D, 1D, 1D);
    public static final VoxelShape WEST=Shapes.box(0D, 0D, 0D, 6D/16D, 1D, 1D);
    public static final VoxelShape EAST=Shapes.box(10D/16D, 0D, 0D, 1D, 1D, 1D);
    public static final VoxelShape UP=Shapes.box(0D, 10D/16D, 0D, 1D, 1D, 1D);
    public static final VoxelShape DOWN=Shapes.box(0D, 0D, 0D, 1D, 6D/16D, 1D);

    public BlockBellows(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(POWERED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(!state.getValue(POWERED))
            return Shapes.block();
        switch (state.getValue(FACING)) {
            case DOWN -> {
                return DOWN;
            }
            case UP -> {
                return UP;
            }
            case NORTH -> {
                return NORTH;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case WEST -> {
                return WEST;
            }
            case EAST -> {
                return EAST;
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!state.getValue(POWERED)) {
            if(!level.isClientSide) {
                level.scheduleTick(pos, this, 10);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(POWERED)) {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, false));
        }else {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, true));
            level.scheduleTick(pos, this, 20);
            level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.9F, 1F);
        }
    }
}
