package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.tile.TIleStill;
import charcoalPit.tile.TileNestingBox;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class BlockNestingBox extends Block implements EntityBlock {

    public static final IntegerProperty EGGS= IntegerProperty.create("eggs",0,4);

    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public BlockNestingBox(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(EGGS,0));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOTTOM_AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EGGS);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        TileNestingBox tile = (TileNestingBox) level.getBlockEntity(pos);
        if(!tile.inventory.getStackInSlot(0).isEmpty())
            return (int)Math.ceil(tile.inventory.getStackInSlot(0).getCount()*15F/tile.inventory.getStackInSlot(0).getMaxStackSize());
        return 0;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            ((TileNestingBox)level.getBlockEntity(pos)).dropInventory();
            level.removeBlockEntity(pos);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(state.getValue(EGGS)==0)
            return InteractionResult.PASS;
        if(!level.isClientSide){
            TileNestingBox tile=(TileNestingBox) level.getBlockEntity(pos);
            ItemHandlerHelper.giveItemToPlayer(player,tile.inventory.getStackInSlot(0),player.getInventory().selected);
            tile.inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        level.playSound(player,pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS,1F,1F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(EGGS)!=0)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(!level.isClientSide){
            TileNestingBox tile=(TileNestingBox) level.getBlockEntity(pos);
            if(tile.inventory.insertItem(0,stack,true).isEmpty()) {
                player.setItemInHand(hand, tile.inventory.insertItem(0, stack, false));
                level.playSound(null,pos,SoundEvents.CHICKEN_EGG,SoundSource.BLOCKS,1F,1F);
                return ItemInteractionResult.CONSUME;
            }else{
                return ItemInteractionResult.FAIL;
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileNestingBox(pos,state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.NESTING_BOX.get())?(l, p, s, e)->((TileNestingBox)e).tick():null;
    }
}
