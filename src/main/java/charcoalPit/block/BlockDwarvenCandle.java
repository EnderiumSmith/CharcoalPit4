package charcoalPit.block;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlockDwarvenCandle extends Block {

    public static final DirectionProperty FACING= BlockStateProperties.FACING;
    public static final BooleanProperty LIT=BlockStateProperties.LIT;

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D),
            Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
            Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D),
            Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D),
            Direction.UP,Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Direction.DOWN,Block.box(6.0D, 4.0D, 6.0D, 10.0D, 16.0D, 10.0D)));

    public BlockDwarvenCandle(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LIT,false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,LIT);
    }

    public void setPrimed(Level level, BlockPos pos,BlockState state){
        level.setBlock(pos,state.setValue(LIT,true),3);
        level.playSound(null,pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS,1F,1F);
        level.scheduleTick(pos,this,40);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(LIT)){
            level.removeBlock(pos,false);
            level.explode(null,pos.getX()+0.5D,pos.getY()+0.5D,pos.getZ()+0.5D,3, Level.ExplosionInteraction.TNT);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(level.hasNeighborSignal(pos)){
            setPrimed(level,pos,state);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(state.getValue(LIT)) {
            Direction direction = state.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.85D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.22D;
            double d4 = 0.27D;
            Direction direction1 = direction.getOpposite();
            double d5 = 0.17D * direction1.getStepX();
            double d6 = 0.17D * direction1.getStepZ();
            if (direction1.getAxis().isHorizontal()) {
                d5 = 0.17D * direction1.getStepX();
                d6 = 0.17D * direction1.getStepZ();
                d1 +=0.22D;
            }
            if(direction==Direction.DOWN)
                d1=pos.getY()+0.15D;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1, d2 + d6, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1, d2 + d6, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1, d2 + d6, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1, d2 + d6, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, direction);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            Direction direction1 = direction.getOpposite();
            blockstate = blockstate.setValue(FACING, direction1);
            if (blockstate.canSurvive(levelreader, blockpos)) {
                return blockstate;
            }
        }

        return null;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if(!level.isClientSide)
            level.explode(null,pos.getX()+0.5D,pos.getY()+0.5D,pos.getZ()+0.5D,3, Level.ExplosionInteraction.TNT);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(LIT)||(!stack.is(Items.FLINT_AND_STEEL)&&!stack.is(Items.FIRE_CHARGE))){
            return super.useItemOn(stack,state,level,pos,player,hand,hitResult);
        }else{
            setPrimed(level,pos,state);
            if(!player.isCreative()){
                if(stack.is(Items.FLINT_AND_STEEL)){
                    stack.hurtAndBreak(1,player,player.getEquipmentSlotForItem(stack));
                }else{
                    stack.shrink(1);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
    }
}
