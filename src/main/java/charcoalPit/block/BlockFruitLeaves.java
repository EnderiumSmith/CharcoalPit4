package charcoalPit.block;

import charcoalPit.core.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class BlockFruitLeaves extends Block {

    public static final IntegerProperty DISTANCE= BlockStateProperties.DISTANCE;
    public static final BooleanProperty PERSISTENT=BlockStateProperties.PERSISTENT;
    public static final IntegerProperty AGE=BlockStateProperties.AGE_7;

    public Holder<Item> fruit;
    public final float tick_chance;

    public BlockFruitLeaves(Holder<Item> fruit,float tick_chance,Properties properties) {
        super(properties);
        this.fruit=fruit;
        this.tick_chance=tick_chance;
        this.registerDefaultState(defaultBlockState().setValue(DISTANCE,7).setValue(PERSISTENT,false).setValue(AGE,0));
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(context instanceof EntityCollisionContext){
            if(((EntityCollisionContext)context).getEntity() instanceof ItemEntity){
                return Shapes.empty();
            }
        }
        return super.getCollisionShape(state,level,pos,context);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7) {
            dropResources(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
        if(random.nextFloat()<tick_chance&&!state.getValue(PERSISTENT)){
            int stage=state.getValue(AGE);
            boolean empty_leaves=false;
            for(BlockPos mutable:BlockPos.MutableBlockPos.betweenClosed(pos.below(2).north(2).west(2),pos.above(2).south(2).east(2))){
                if(worldIn.getBlockState(mutable).getBlock()==this&&!worldIn.getBlockState(mutable).getValue(PERSISTENT)){
                    int stage2=worldIn.getBlockState(mutable).getValue(AGE);
                    if(stage2==0)
                        empty_leaves=true;
                    if(stage2+1==stage||(stage==0&&stage2==7))
                        return;
                }
            }
            if(stage<7)
                worldIn.setBlock(pos,state.setValue(AGE,stage+1),2);
            else{
                if(empty_leaves) {
                    //if some leaves are harvested start rotting
                    worldIn.setBlock(pos, state.setValue(AGE, 0), 2);
                    if(random.nextFloat()<0.2F)
                        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(fruit));
                }
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(state.getValue(AGE)==7&&!state.getValue(PERSISTENT)){
            ItemHandlerHelper.giveItemToPlayer(player,new ItemStack(fruit),player.getInventory().selected);
            level.setBlock(pos,state.setValue(AGE,0),2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || state.getValue(DISTANCE) != i) {
            level.scheduleTick(currentPos, this, 1);
        }

        return state;
    }

    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, updateDistance(state, level, pos), 3);
    }

    private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int i = 7;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        Direction[] var5 = Direction.values();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            blockpos$mutableblockpos.setWithOffset(pos, direction);
            i = Math.min(i, getDistanceAt(level.getBlockState(blockpos$mutableblockpos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.setValue(DISTANCE, i);
    }

    private static int getDistanceAt(BlockState neighbor) {
        return getOptionalDistanceAt(neighbor).orElse(7);
    }

    public static OptionalInt getOptionalDistanceAt(BlockState state) {
        if (state.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else {
            return state.hasProperty(DISTANCE) ? OptionalInt.of(state.getValue(DISTANCE)) : OptionalInt.empty();
        }
    }

    @Override
    protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isRainingAt(pos.above()) && random.nextInt(15) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, ParticleTypes.DRIPPING_WATER);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE,PERSISTENT,AGE);
    }

    private int getAge(ItemStack stack){
        if(stack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
            return switch (stack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                case 1 -> 3;
                case 2 -> 6;
                case 3 -> 7;
                default -> 0;
            };
        }
        return 0;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, true).setValue(AGE,getAge(context.getItemInHand()));
        return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
    }

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if(explosion.canTriggerBlocks()&&!state.getValue(PERSISTENT)&&state.getValue(AGE)==7){
            level.setBlock(pos,state.setValue(AGE,0),3);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(fruit));
        }
        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }
}
