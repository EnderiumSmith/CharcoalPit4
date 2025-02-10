package charcoalPit.block;

import charcoalPit.DataComponents.BloomData;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.tile.TileBloom;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockBloom extends Block implements EntityBlock {

    public static final VoxelShape BOX= Shapes.box(2D/16D, 0D, 2D/16D, 14D/16D, 12D/16D, 14D/16D);

    public BlockBloom(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 9;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!level.isClientSide()) {
            TileBloom bloom = (TileBloom) level.getBlockEntity(pos);
            BloomData data = bloom.components().get(DataComponentRegistry.BLOOM_DATA.get());
            if (data != null && bloom.workCount < data.work()) {
                state.getBlock().playerWillDestroy(level, pos, state, player);
                player.causeFoodExhaustion(0.1F);
                level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.8F, 1F);
                bloom.workCount++;
                return false;
            }else{
                int fortune=0;
                ItemStack tool=player.getMainHandItem();
                if(!tool.isEmpty()){
                    fortune=tool.getEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.FORTUNE));
                    ((TileBloom)level.getBlockEntity(pos)).dropInventory(fortune);
                }
                return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(stack.has(DataComponentRegistry.BLOOM_DATA.get())){
            ItemStack nested=stack.get(DataComponentRegistry.BLOOM_DATA.get()).stack();
            tooltipComponents.add(Component.literal("").append(nested.getHoverName()).append(" x"+nested.getCount()).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileBloom(pos,state);
    }
}
