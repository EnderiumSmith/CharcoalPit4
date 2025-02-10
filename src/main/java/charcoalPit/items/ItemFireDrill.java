package charcoalPit.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForgeMod;

public class ItemFireDrill extends Item {
    public ItemFireDrill(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 50;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Vec3 eyepos=new Vec3(player.getX(),player.getEyeY(),player.getZ());
        Vec3 rangedLookRot=player.getLookAngle().scale(player.blockInteractionRange());
        Vec3 lookPos=eyepos.add(rangedLookRot);
        BlockHitResult result=level.clip(new ClipContext(eyepos,lookPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        ItemStack stack=player.getItemInHand(usedHand);
        if(result.getType()== HitResult.Type.BLOCK){
            EntityHitResult result2=ProjectileUtil.getEntityHitResult(player,eyepos,lookPos,new AABB(eyepos,lookPos), e->true,player.blockInteractionRange());
            if(result2==null){
                return ItemUtils.startUsingInstantly(level,player,usedHand);
            }else{
                return new InteractionResultHolder<>(InteractionResult.FAIL,stack);
            }
        }else{
            return new InteractionResultHolder<>(InteractionResult.FAIL,stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(livingEntity instanceof Player player){
            Vec3 eyepos=new Vec3(player.getX(),player.getEyeY(),player.getZ());
            Vec3 rangedLookRot=player.getLookAngle().scale(player.blockInteractionRange());
            Vec3 lookPos=eyepos.add(rangedLookRot);
            BlockHitResult result=level.clip(new ClipContext(eyepos,lookPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if(!level.isClientSide){
                if(result.getType()== HitResult.Type.BLOCK){
                    EntityHitResult result2=ProjectileUtil.getEntityHitResult(player,eyepos,lookPos,new AABB(eyepos,lookPos), e->true,player.blockInteractionRange());
                    if(result2!=null){
                        player.releaseUsingItem();
                    }
                }else
                    player.releaseUsingItem();
            }else{
                if(result.getType()== HitResult.Type.BLOCK){
                    level.addParticle(ParticleTypes.SMOKE, result.getLocation().x,result.getLocation().y,result.getLocation().z,0,0.1F+0.1F*level.getRandom().nextFloat(),0);
                }
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(livingEntity instanceof Player player) {
            Vec3 eyepos = new Vec3(player.getX(), player.getEyeY(), player.getZ());
            Vec3 rangedLookRot = player.getLookAngle().scale(player.blockInteractionRange());
            Vec3 lookPos = eyepos.add(rangedLookRot);
            BlockHitResult result = level.clip(new ClipContext(eyepos, lookPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if(result.getType()== HitResult.Type.BLOCK){
                BlockPos hit=result.getBlockPos().relative(result.getDirection());
                if(CampfireBlock.canLight(level.getBlockState(result.getBlockPos()))){
                    level.playSound(null,result.getBlockPos(), SoundEvents.FLINTANDSTEEL_USE,SoundSource.BLOCKS,1,0.8F+0.4F*level.getRandom().nextFloat());
                    level.setBlock(result.getBlockPos(),level.getBlockState(result.getBlockPos()).setValue(CampfireBlock.LIT,true),11);
                    stack.hurtAndBreak(1,player,player.getEquipmentSlotForItem(stack));
                    return stack;
                }else if(BaseFireBlock.canBePlacedAt(level,hit, Direction.UP)){
                    level.setBlock(hit,BaseFireBlock.getState(level,hit),11);
                    level.playSound(null,result.getBlockPos(), SoundEvents.FLINTANDSTEEL_USE,SoundSource.BLOCKS,1,0.8F+0.4F*level.getRandom().nextFloat());
                    stack.hurtAndBreak(1,player,player.getEquipmentSlotForItem(stack));
                    return stack;
                }
            }
        }
        return stack;
    }
}
