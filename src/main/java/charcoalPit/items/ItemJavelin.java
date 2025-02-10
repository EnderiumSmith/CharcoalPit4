package charcoalPit.items;

import charcoalPit.core.ItemRegistry;
import charcoalPit.entity.ThrownJavelin;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemJavelin extends TridentItem {
    public ItemJavelin(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10) {
                float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
                if (!(f > 0.0F) || player.isInWaterOrRain()) {
                    if (!isTooDamagedToUse(stack)) {
                        Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
                                .orElse(SoundEvents.TRIDENT_THROW);
                        if (!level.isClientSide) {
                            if (f == 0.0F) {
                                ThrownJavelin throwntrident = new ThrownJavelin(player, level, stack.copyWithCount(1));
                                throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                                if(player.getRandom().nextFloat()<0.03F){
                                    throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                    level.playSound(null,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BREAK,SoundSource.PLAYERS,1F,1F);
                                }
                                if (player.hasInfiniteMaterials()) {
                                    throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                }

                                level.addFreshEntity(throwntrident);
                                level.playSound(null, throwntrident, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                                if(stack.getItem()==ItemRegistry.EXPLOSIVE_SPEAR.get()){
                                    level.playSound(null,entityLiving,SoundEvents.TNT_PRIMED,SoundSource.PLAYERS,2F,1F);
                                }
                                if (!player.hasInfiniteMaterials()) {
                                    stack.shrink(1);
                                }
                            }
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        if (f > 0.0F) {
                            float f7 = player.getYRot();
                            float f1 = player.getXRot();
                            float f2 = -Mth.sin(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
                            float f3 = -Mth.sin(f1 * (float) (Math.PI / 180.0));
                            float f4 = Mth.cos(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
                            float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
                            f2 *= f / f5;
                            f3 *= f / f5;
                            f4 *= f / f5;
                            player.push((double)f2, (double)f3, (double)f4);
                            player.startAutoSpinAttack(20, 8.0F, stack);
                            if (player.onGround()) {
                                float f6 = 1.1999999F;
                                player.move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
                            }

                            level.playSound(null, player, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isTooDamagedToUse(itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0F && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return false;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(stack.getItem()== ItemRegistry.JAVELIN.get() && attacker.getRandom().nextFloat()<0.03F){
            stack.shrink(1);
            if(attacker instanceof Player player){
                player.level().playSound(null,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BREAK,SoundSource.PLAYERS,1F,1F);
            }
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownJavelin throwntrident = new ThrownJavelin(pos.x(), pos.y(), pos.z(), level, stack.copyWithCount(1));
        throwntrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return throwntrident;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(stack.getItem()==ItemRegistry.JAVELIN.get())
            tooltipComponents.add(Component.literal("8 Ranged Attack Damage").withStyle(ChatFormatting.GRAY));
    }
}
