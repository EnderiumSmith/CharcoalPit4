package charcoalPit.block;

import charcoalPit.core.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DoubleCropBlock extends DoublePlantBlock implements BonemealableBlock{

    public static final IntegerProperty AGE= BlockStateProperties.AGE_7;
    private static final VoxelShape[] LOWER_SHAPE_BY_AGE=new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Shapes.block(),
            Shapes.block(),
            Shapes.block()
    };
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE=new VoxelShape[]{
            Shapes.empty(),
            Shapes.empty(),
            Shapes.empty(),
            Shapes.empty(),
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };
    private static final int DOUBLE_PLANT_AGE_INTERSECTION = 5;

    public DoubleCropBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER
                ? UPPER_SHAPE_BY_AGE[state.getValue(AGE)]
                : LOWER_SHAPE_BY_AGE[state.getValue(AGE)];
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(
            BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos
    ) {
        if (isDouble(state.getValue(AGE))) {
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return state.canSurvive(level, currentPos) ? state : Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        net.neoforged.neoforge.common.util.TriState soilDecision = level.getBlockState(pos.below()).canSustainPlant(level, pos.below(), Direction.UP, state);
        return isLower(state) && !sufficientLight(level, pos) ? soilDecision.isTrue() : super.canSurvive(state, level, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof net.minecraft.world.level.block.FarmBlock;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Ravager && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            level.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && !this.isMaxAge(state);
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        float f = CropBlock.getGrowthSpeed(state, level, pos);
        boolean flag = random.nextInt((int)(25.0F / f) + 1) == 0;
        if (flag) {
            this.grow(level, state, pos, 1);
        }
    }

    private void grow(ServerLevel level, BlockState state, BlockPos pos, int ageIncrement) {
        int i = Math.min(state.getValue(AGE) + ageIncrement, 7);
        if (this.canGrow(level, pos, state, i)) {
            BlockState blockstate = state.setValue(AGE, Integer.valueOf(i));
            level.setBlock(pos, blockstate, 2);
            if (isDouble(i)) {
                level.setBlock(pos.above(), blockstate.setValue(HALF, DoubleBlockHalf.UPPER), 3);
            }
        }
    }

    private static boolean canGrowInto(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.isAir() || blockstate.is(BlockRegistry.SUNFLOWER.get());
    }

    private static boolean sufficientLight(LevelReader level, BlockPos pos) {
        return CropBlock.hasSufficientLight(level, pos);
    }

    private static boolean isLower(BlockState state) {
        return state.is(BlockRegistry.SUNFLOWER.get()) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private static boolean isDouble(int age) {
        return age >= 5;
    }

    private boolean canGrow(LevelReader reader, BlockPos pos, BlockState state, int age) {
        return !this.isMaxAge(state) && sufficientLight(reader, pos) && (!isDouble(age) || canGrowInto(reader, pos.above()));
    }

    private boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= 7;
    }

    @Nullable
    private DoubleCropBlock.PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state) {
        if (isLower(state)) {
            return new DoubleCropBlock.PosAndState(pos, state);
        } else {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            return isLower(blockstate) ? new DoubleCropBlock.PosAndState(blockpos, blockstate) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        DoubleCropBlock.PosAndState pitchercropblock$posandstate = this.getLowerHalf(level, pos, state);
        return pitchercropblock$posandstate == null
                ? false
                : this.canGrow(
                level, pitchercropblock$posandstate.pos, pitchercropblock$posandstate.state, pitchercropblock$posandstate.state.getValue(AGE) + 1
        );
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        DoubleCropBlock.PosAndState pitchercropblock$posandstate = this.getLowerHalf(level, pos, state);
        if (pitchercropblock$posandstate != null) {
            this.grow(level, pitchercropblock$posandstate.state, pitchercropblock$posandstate.pos, 1);
        }
    }

    static record PosAndState(BlockPos pos, BlockState state) {
    }
}
