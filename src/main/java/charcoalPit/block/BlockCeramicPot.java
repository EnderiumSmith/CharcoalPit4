package charcoalPit.block;

import charcoalPit.gui.menu.CeramicPotMenu;
import charcoalPit.gui.menu.StillMenu;
import charcoalPit.tile.TIleCeramicPot;
import charcoalPit.tile.TIleStill;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCeramicPot extends Block implements EntityBlock {

    public static final VoxelShape POT= Shapes.box(2D/16D, 0D, 2D/16D, 14D/16D, 1D, 14D/16D);

    public BlockCeramicPot(Properties properties) {
        super(properties);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        TIleCeramicPot tile= (TIleCeramicPot) level.getBlockEntity(pos);
        return new SimpleMenuProvider((id, inv, player)->new CeramicPotMenu(id,inv,tile, ContainerLevelAccess.create(level,pos)), Component.translatable("screen.charcoal_pit.ceramic_pot"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer){
            serverPlayer.openMenu(state.getMenuProvider(level,pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static List<ItemStack> serializeItems(TIleCeramicPot tile){
        return List.of(tile.inventory.getStackInSlot(0),tile.inventory.getStackInSlot(1),tile.inventory.getStackInSlot(2),
                tile.inventory.getStackInSlot(3),tile.inventory.getStackInSlot(4),tile.inventory.getStackInSlot(5),
                tile.inventory.getStackInSlot(6),tile.inventory.getStackInSlot(7),tile.inventory.getStackInSlot(8));
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        TIleCeramicPot tile=(TIleCeramicPot) params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack=new ItemStack(this);
        boolean empty=true;
        for(int i=0;i<9;i++){
            if(!tile.inventory.getStackInSlot(i).isEmpty()){
                empty=false;
                break;
            }
        }
        if(!empty){
            stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(serializeItems(tile)));
        }
        return List.of(stack);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(stack.has(DataComponents.CONTAINER)){
            ItemContainerContents items=stack.get(DataComponents.CONTAINER);
            TIleCeramicPot pot=(TIleCeramicPot) level.getBlockEntity(pos);
            for(int i=0;i<Math.min(9,items.getSlots());i++){
                pot.inventory.setStackInSlot(i,items.getStackInSlot(i));
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        level.updateNeighbourForOutputSignal(pos,newState.getBlock());
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(DataComponents.CONTAINER)){
            ItemContainerContents items=stack.get(DataComponents.CONTAINER);
            items.nonEmptyStream().forEach(stk->tooltipComponents.add(stk.getHoverName().plainCopy().append(" x"+stk.getCount()).withStyle(ChatFormatting.GRAY)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return POT;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public static int calcRedstoneFromInventory(IItemHandler inv) {
        if (inv == null) {
            return 0;
        } else {
            int i = 0;
            float f = 0.0F;

            for(int j = 0; j < inv.getSlots(); ++j) {
                ItemStack itemstack = inv.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)Math.min(inv.getSlotLimit(j), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f = f / (float)inv.getSlots();
            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return calcRedstoneFromInventory(((TIleCeramicPot)level.getBlockEntity(pos)).inventory);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        TIleCeramicPot tile=(TIleCeramicPot)level.getBlockEntity(pos);
        ItemStack stack=new ItemStack(this);
        boolean empty=true;
        for(int i=0;i<9;i++){
            if(!tile.inventory.getStackInSlot(i).isEmpty()){
                empty=false;
                break;
            }
        }
        if(!empty){
            stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(serializeItems(tile)));
        }
        return stack;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TIleCeramicPot(pos,state);
    }
}
