package charcoalPit.items;

import charcoalPit.core.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class ItemBlockDynamite extends BlockItem {
    public ItemBlockDynamite(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result= super.useOn(context);
        if(result.consumesAction()){
            ItemStack stack=context.getPlayer().getItemInHand(InteractionHand.OFF_HAND);
            if(stack.getItem() instanceof ItemDynamiteRemote remote){
                remote.toggleTarget(stack,context.getClickedPos().relative(context.getClickedFace()),context.getPlayer());
            }
        }
        return result;
    }
}
