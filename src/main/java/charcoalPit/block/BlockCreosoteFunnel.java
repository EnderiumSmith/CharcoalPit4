package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.tile.TileCreosoteFunnel;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCreosoteFunnel extends Block implements EntityBlock {
    public BlockCreosoteFunnel(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileCreosoteFunnel(pos,state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.CREOSOTE_FUNNEL.get())?(l, p, s, e)->((TileCreosoteFunnel)e).tick():null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.getCapability(Capabilities.FluidHandler.ITEM)!=null){
            if(level.isClientSide)
                return ItemInteractionResult.SUCCESS;
            if(FluidUtil.interactWithFluidHandler(player,hand,level,pos,hitResult.getDirection()))
                return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(tooltipFlag.hasShiftDown()){
            tooltipComponents.add(Component.literal("Collects Creosote Oil condensate from charcoal pits/cove ovens bellow in a 3x3 area").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("Creosote only flows up between piles").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("When powered by redstone, fluid is pushed upwards into adjacent containers").withStyle(ChatFormatting.GRAY));
        }else{
            tooltipComponents.add(Component.literal("Hold <Shift> for info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
