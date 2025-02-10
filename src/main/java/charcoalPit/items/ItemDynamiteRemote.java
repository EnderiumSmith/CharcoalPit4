package charcoalPit.items;

import charcoalPit.DataComponents.DynamitePositions;
import charcoalPit.block.BlockDwarvenCandle;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ItemDynamiteRemote extends Item {
    public ItemDynamiteRemote(Properties properties) {
        super(properties);
    }

    public void toggleTarget(ItemStack stack, BlockPos pos, Player player){
        if(!stack.has(DataComponentRegistry.DYNAMITE_POSITIONS.get())){
            stack.set(DataComponentRegistry.DYNAMITE_POSITIONS.get(),new DynamitePositions(List.of(pos)));
            player.displayClientMessage(Component.literal("Added Candle"),true);
            return;
        }else{
            DynamitePositions dyn=stack.get(DataComponentRegistry.DYNAMITE_POSITIONS.get());
            List<BlockPos> list= new ArrayList<>(dyn.candles);
            if(list.contains(pos)){
                list.remove(pos);
                stack.set(DataComponentRegistry.DYNAMITE_POSITIONS.get(),new DynamitePositions(list));
                if(player!=null){
                    player.displayClientMessage(Component.literal("Removed Candle"),true);
                }
            }else{
                list.add(pos);
                stack.set(DataComponentRegistry.DYNAMITE_POSITIONS.get(),new DynamitePositions(list));
                if(player!=null){
                    player.displayClientMessage(Component.literal("Added Candle"),true);
                }
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide&&context.getLevel().getBlockState(context.getClickedPos()).getBlock()== BlockRegistry.DWARVEN_CANDLE.get()){
            toggleTarget(context.getItemInHand(),context.getClickedPos(),context.getPlayer());
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    public void igniteDynamites(Level level,BlockPos pos){
        BlockState state=level.getBlockState(pos);
        if(state.getBlock()==BlockRegistry.DWARVEN_CANDLE.get()&&!state.getValue(BlockDwarvenCandle.LIT)){
            level.setBlock(pos,state.setValue(BlockDwarvenCandle.LIT,true),3);
            level.playSound(null,pos,SoundEvents.TNT_PRIMED,SoundSource.BLOCKS,1F,1F);
            level.scheduleTick(pos,BlockRegistry.DWARVEN_CANDLE.get(), 40);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack=player.getItemInHand(usedHand);
        if(stack.has(DataComponentRegistry.DYNAMITE_POSITIONS.get())){
            level.playSound(null,player.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS,1F,1F);
            stack.get(DataComponentRegistry.DYNAMITE_POSITIONS.get()).candles.forEach(pos->igniteDynamites(level,pos));
            stack.remove(DataComponentRegistry.DYNAMITE_POSITIONS.get());
            return new InteractionResultHolder<>(InteractionResult.SUCCESS,stack);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS,stack);
    }
}
