package charcoalPit.items;

import charcoalPit.DataComponents.BloomData;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Optional;

public class ItemAlloyMold extends Item {
    boolean finish;
    public ItemAlloyMold(boolean finish,Properties properties) {
        super(properties);
        this.finish=finish;
    }

    /*@Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
                ? Optional.ofNullable(stack.get(DataComponents.BUNDLE_CONTENTS)).map(BundleTooltip::new)
                : Optional.empty();
    }*/

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        BloomData contents=stack.get(DataComponentRegistry.BLOOM_DATA);
        if(contents!=null){
            ItemStack stack1=contents.stack().copy();
            tooltipComponents.add(Component.literal("").append(stack1.getHoverName()).append(" x"+stack1.getCount()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!finish)
            return super.useOn(context);
        if (!context.getLevel().isClientSide()) {
            if (context.getItemInHand().has(DataComponentRegistry.BLOOM_DATA)&&context.getPlayer()!=null) {
                BloomData contents=context.getItemInHand().get(DataComponentRegistry.BLOOM_DATA);
                ItemHandlerHelper.giveItemToPlayer(context.getPlayer(),contents.stack().copy());
                context.getItemInHand().shrink(1);
                if(context.getLevel().getRandom().nextFloat()<0.1F){
                    context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS,1F,1F);
                }else{
                    context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.STONE_BREAK, SoundSource.PLAYERS,1F,1F);
                    ItemHandlerHelper.giveItemToPlayer(context.getPlayer(),new ItemStack(ItemRegistry.ALLOY_MOLD.get()));
                }
            }
        }
        return InteractionResult.CONSUME;
    }
}
