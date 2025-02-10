package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.tile.TileFeedingThrough;
import charcoalPit.tile.TileNestingBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class BlockFeedingThrough extends Block implements EntityBlock {

    public static final BooleanProperty ROTATED= BooleanProperty.create("rotated");
    public static final BooleanProperty HAS_BAY= BooleanProperty.create("has_bay");

    public static final VoxelShape NORTH= Shapes.box(2D/16D, 0D, 0D, 14D/16D, 0.5D, 1D);
    public static final VoxelShape WEST= Shapes.box(0D, 0D, 2D/16D, 1D, 0.5D, 14D/16D);

    public BlockFeedingThrough(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(HAS_BAY,false));
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.FENCE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATED,HAS_BAY);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(ROTATED)?WEST:NORTH;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(ROTATED,context.getHorizontalDirection().getAxis()== Direction.Axis.Z);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if(state.getValue(HAS_BAY)){
            TileFeedingThrough tile= (TileFeedingThrough) level.getBlockEntity(pos);
            return (int)Math.ceil(tile.inventory.getStackInSlot(0).getCount()*15F/tile.inventory.getStackInSlot(0).getMaxStackSize());
        }
        return 0;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            ((TileFeedingThrough)level.getBlockEntity(pos)).dropInventory();
            level.removeBlockEntity(pos);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!state.getValue(HAS_BAY) || !player.isShiftKeyDown())
            return InteractionResult.PASS;
        if(!level.isClientSide){
            TileFeedingThrough tile=(TileFeedingThrough) level.getBlockEntity(pos);
            ItemHandlerHelper.giveItemToPlayer(player,tile.inventory.getStackInSlot(0),player.getInventory().selected);
            tile.inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        level.playSound(player,pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS,1F,1F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.isEmpty())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(!level.isClientSide){
            TileFeedingThrough tile=(TileFeedingThrough) level.getBlockEntity(pos);
            if(tile.inventory.insertItem(0,stack,true)!=stack) {
                player.setItemInHand(hand, tile.inventory.insertItem(0, stack, false));
                level.playSound(null,pos,SoundEvents.GRASS_PLACE,SoundSource.BLOCKS,1F,1F);
                return ItemInteractionResult.CONSUME;
            }else{
                return ItemInteractionResult.FAIL;
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileFeedingThrough(pos,state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.FEEDING_THROUGH.get())?(l, p, s, e)->((TileFeedingThrough)e).tick():null;
    }
}
