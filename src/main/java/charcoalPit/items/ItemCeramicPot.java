package charcoalPit.items;

import charcoalPit.core.ItemRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class ItemCeramicPot extends BlockItem {
    public ItemCeramicPot(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if(stack.has(DataComponents.CONTAINER)&&stack.get(DataComponents.CONTAINER)!=ItemContainerContents.EMPTY){
            return 1;
        }
        return super.getMaxStackSize(stack);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
        ItemStack other=slot.getItem();
        if(other.isEmpty()){
            if(stack.has(DataComponents.CONTAINER)){
                ItemStackHandler inventory=new ItemStackHandler(9);
                deserializeItems(inventory,stack.get(DataComponents.CONTAINER));
                for(int i=8;i>=0;i--){
                    if(!inventory.getStackInSlot(i).isEmpty()){
                        slot.set(inventory.getStackInSlot(i));
                        inventory.setStackInSlot(i,ItemStack.EMPTY);
                        if(!isInventoryEmpty(inventory)){
                            stack.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(serializeItems(inventory)));
                        }else{
                            stack.remove(DataComponents.CONTAINER);
                        }
                        return true;
                    }
                }
                return false;
            }else return false;
        }else{
            if(!other.getItem().canFitInsideContainerItems())return false;
            ItemStack original=other.copy();
            ItemStackHandler inventory=new ItemStackHandler(9);
            if(stack.has(DataComponents.CONTAINER)) deserializeItems(inventory,stack.get(DataComponents.CONTAINER));
            for(int i=0;i<9;i++){
                other=inventory.insertItem(i,original,false);
                if(other.isEmpty()) break;
            }
            if(!ItemStack.isSameItemSameComponents(original,other)){
                slot.set(other);
                stack.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(serializeItems(inventory)));
                return true;
            }else return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            if(other.isEmpty()){
                if(stack.has(DataComponents.CONTAINER)){
                    ItemStackHandler inventory=new ItemStackHandler(9);
                    deserializeItems(inventory,stack.get(DataComponents.CONTAINER));
                    for(int i=8;i>=0;i--){
                        if(!inventory.getStackInSlot(i).isEmpty()){
                            access.set(inventory.getStackInSlot(i));
                            inventory.setStackInSlot(i,ItemStack.EMPTY);
                            if(!isInventoryEmpty(inventory)){
                                stack.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(serializeItems(inventory)));
                            }else{
                                stack.remove(DataComponents.CONTAINER);
                            }
                            return true;
                        }
                    }
                    return false;
                }else return false;
            }else{
                if(!other.getItem().canFitInsideContainerItems()) return false;
                ItemStack original=other.copy();
                ItemStackHandler inventory=new ItemStackHandler(9);
                if(stack.has(DataComponents.CONTAINER)) deserializeItems(inventory,stack.get(DataComponents.CONTAINER));
                for(int i=0;i<9;i++){
                    other=inventory.insertItem(i,original,false);
                    if(other.isEmpty()) break;
                }
                if(!ItemStack.isSameItemSameComponents(original,other)){
                    access.set(other);
                    stack.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(serializeItems(inventory)));
                    return true;
                }else return false;
            }
        }
        return false;
    }

    public static List<ItemStack> serializeItems(ItemStackHandler inventory){
        return List.of(inventory.getStackInSlot(0),inventory.getStackInSlot(1),inventory.getStackInSlot(2),
                inventory.getStackInSlot(3),inventory.getStackInSlot(4),inventory.getStackInSlot(5),
                inventory.getStackInSlot(6),inventory.getStackInSlot(7),inventory.getStackInSlot(8));
    }

    public static void deserializeItems(ItemStackHandler inventory, ItemContainerContents items){
        for(int i=0;i<Math.min(9,items.getSlots());i++){
            inventory.setStackInSlot(i,items.getStackInSlot(i));
        }
    }

    public static boolean isInventoryEmpty(ItemStackHandler inventory){
        for(int i=0;i<9;i++){
            if(!inventory.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getPlayer()==null) return super.useOn(context);
        if(context.getItemInHand().getItem() != ItemRegistry.CERAMIC_POT.get()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            if (state.getBlock() == Blocks.WATER_CAULDRON && state.getValue(LayeredCauldronBlock.LEVEL) > 0) {
                ItemStack stack=new ItemStack(ItemRegistry.CERAMIC_POT.get());
                if(context.getItemInHand().has(DataComponents.CONTAINER)){
                    stack.set(DataComponents.CONTAINER,context.getItemInHand().get(DataComponents.CONTAINER));
                }
                context.getPlayer().setItemInHand(context.getHand(),stack);
                LayeredCauldronBlock.lowerFillLevel(state,context.getLevel(),context.getClickedPos());
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
