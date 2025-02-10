package charcoalPit.items;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;

public class ItemGoldPan extends Item {

    public static ResourceKey<LootTable> GOLD_PAN=ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"gold_pan"));

    public ItemGoldPan(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 50;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GRAVEL_BREAK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        /*if (usedHand == InteractionHand.OFF_HAND)
        {
            // We require pans be operated with the main hand - as that's when they render as two-handed
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }*/
        if (level.getFluidState(player.blockPosition()).is(FluidTags.WATER))
        {
            return ItemUtils.startUsingInstantly(level, player, usedHand);
        }
        if (!level.isClientSide)
        {
            player.displayClientMessage(Component.translatable("tooltip.charcoal_pit.pan_error"), true);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(Items.BOWL);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide()){
            LootTable table=level.getServer().reloadableRegistries().getLootTable(GOLD_PAN);
            LootParams.Builder builder=new LootParams.Builder((ServerLevel) level)
                    .withParameter(LootContextParams.THIS_ENTITY, livingEntity)
                    .withLuck(player.getLuck());
            List<ItemStack> loot=table.getRandomItems(builder.create(LootContextParamSets.EMPTY));
            loot.forEach(item-> ItemHandlerHelper.giveItemToPlayer(player,item));
            player.awardStat(Stats.ITEM_USED.get(this));
            return this.getCraftingRemainingItem(stack);

        }
        return  stack;
    }
}
