package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.tile.TilePress;
import charcoalPit.tile.TileQuern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class BlockQuern extends Block implements EntityBlock {

    public static final VoxelShape BASE= Shapes.box(0D, 0D, 0D, 1D, 7D/16D, 1D);
    public static final VoxelShape STONE= Shapes.box(2D/16D, 7D/16D, 2D/16D, 14D/16D, 13D/16D, 14D/16D);
    protected static final VoxelShape QUERN=Shapes.join(BASE,STONE,BooleanOp.OR);

    public BlockQuern(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return QUERN;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            ((TileQuern)level.getBlockEntity(pos)).dropInventory();
            level.removeBlockEntity(pos);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide)
            return InteractionResult.SUCCESS;
        TileQuern quern=(TileQuern) level.getBlockEntity(pos);
        if(quern!=null) {
            if (!player.isShiftKeyDown()) {
                if(player.getFoodData().getFoodLevel()>6 && quern.tryActivate()) {
                    level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1F, 1F);
                    player.causeFoodExhaustion(6F);
                }
                return InteractionResult.SUCCESS;
            }else{
                if(!quern.inventory.getStackInSlot(1).isEmpty()){
                    ItemHandlerHelper.giveItemToPlayer(player,quern.inventory.getStackInSlot(1),player.getInventory().selected);
                    quern.inventory.setStackInSlot(1,ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }else if(!quern.inventory.getStackInSlot(0).isEmpty()){
                    ItemHandlerHelper.giveItemToPlayer(player,quern.inventory.getStackInSlot(0),player.getInventory().selected);
                    quern.inventory.setStackInSlot(0,ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.isEmpty())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(level.isClientSide)
            return ItemInteractionResult.CONSUME;
        TileQuern quern=(TileQuern) level.getBlockEntity(pos);
        if(quern!=null) {
            player.setItemInHand(hand,quern.inventory.insertItem(0,stack,false));
            return ItemInteractionResult.CONSUME;
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileQuern(pos,state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (blockEntityType== TileEntityRegistry.QUERN.get())?(l, p, s, e)->((TileQuern)e).tick():null;
    }
}
