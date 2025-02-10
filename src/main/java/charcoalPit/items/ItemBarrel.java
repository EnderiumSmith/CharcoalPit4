package charcoalPit.items;

import charcoalPit.DataComponents.FluidData;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class ItemBarrel extends BlockItem {
    public ItemBarrel(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if(stack.has( DataComponentRegistry.FLUID_DATA)){
            return stack.get(DataComponentRegistry.FLUID_DATA).isEmpty()?super.getMaxStackSize(stack):1;
        }
        return super.getMaxStackSize(stack);
    }

    public static boolean isFluidEmpty(ItemStack stack){
        return !(stack.has(DataComponentRegistry.FLUID_DATA)&&!stack.get(DataComponentRegistry.FLUID_DATA).isEmpty());
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(ItemBarrel.isFluidEmpty(stack)&&!context.getPlayer().isShiftKeyDown()){
            HitResult trace=getPlayerPOVHitResult(context.getLevel(),context.getPlayer(), ClipContext.Fluid.SOURCE_ONLY);
            if(trace.getType()==HitResult.Type.BLOCK){
                BlockHitResult blocktrace=(BlockHitResult)trace;
                BlockPos pos=blocktrace.getBlockPos();
                Direction dir=blocktrace.getDirection();
                BlockPos pos2=pos.relative(dir);
                if (context.getLevel().mayInteract(context.getPlayer(), pos) && context.getPlayer().mayUseItemAt(pos2, dir, stack)) {
                    FluidState state=context.getLevel().getFluidState(pos);
                    if(state.getType()== Fluids.WATER&&state.isSource()){
                        int s=0;
                        for(Direction dir2:Direction.Plane.HORIZONTAL){
                            if(context.getLevel().getFluidState(pos.relative(dir2)).getType()==Fluids.WATER&&
                                    context.getLevel().getFluidState(pos.relative(dir2)).isSource())
                                s++;
                        }
                        if(s>=2){
                            context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                            context.getPlayer().playSound(SoundEvents.BUCKET_FILL,1F,1F);
                            ItemStack stack2=new ItemStack(ItemRegistry.BARREL.get());
                            stack2.set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(new FluidStack(Fluids.WATER,16000)));
                            ItemHandlerHelper.giveItemToPlayer(context.getPlayer(),stack2);
                            context.getPlayer().getItemInHand(context.getHand()).shrink(1);
                            if(context.getLevel().getBlockState(pos).getBlock() instanceof BucketPickup){
                                ((BucketPickup)context.getLevel().getBlockState(pos).getBlock()).pickupBlock(context.getPlayer(),context.getLevel(),pos,context.getLevel().getBlockState(pos));
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            if(other.getCapability(Capabilities.FluidHandler.ITEM)!=null && stack.getCapability(Capabilities.FluidHandler.ITEM)!=null){
                IFluidHandler barrel=stack.getCapability(Capabilities.FluidHandler.ITEM);
                var playerInventory = player.getCapability(Capabilities.ItemHandler.ENTITY);
                FluidActionResult result=FluidUtil.tryFillContainerAndStow(other, barrel, playerInventory, Integer.MAX_VALUE, player, true);
                if (!result.isSuccess()) {
                    result = FluidUtil.tryEmptyContainerAndStow(other, barrel, playerInventory, Integer.MAX_VALUE, player, true);
                }

                if (result.isSuccess()) {
                    access.set(result.result);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
        ItemStack other=slot.getItem();
        if(other.getCapability(Capabilities.FluidHandler.ITEM)!=null && stack.getCapability(Capabilities.FluidHandler.ITEM)!=null){
            IFluidHandler barrel=stack.getCapability(Capabilities.FluidHandler.ITEM);
            var playerInventory = player.getCapability(Capabilities.ItemHandler.ENTITY);
            FluidActionResult result=FluidUtil.tryFillContainerAndStow(other, barrel, playerInventory, Integer.MAX_VALUE, player, true);
            if (!result.isSuccess()) {
                result = FluidUtil.tryEmptyContainerAndStow(other, barrel, playerInventory, Integer.MAX_VALUE, player, true);
            }

            if (result.isSuccess()) {
                slot.set(result.result);
                return true;
            }
        }
        return false;
    }
}
