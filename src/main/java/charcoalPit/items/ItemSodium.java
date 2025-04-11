package charcoalPit.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemSodium extends Item {
	RandomSource rand=RandomSource.create();

	public ItemSodium(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if(entity.isInWater()){
			if(entity.getDeltaMovement().y<0)
				entity.addDeltaMovement(new Vec3(0,0.1,0));
		}
		if(entity.isInWaterOrRain()){
			entity.level().playSound(null, entity.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 0.9F, 1F);
			if(entity.level().isClientSide) {
				entity.level().addParticle(ParticleTypes.LAVA, entity.getX(), entity.getY(), entity.getZ(), 0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
				entity.level().addParticle(ParticleTypes.LAVA, entity.getX(), entity.getY(), entity.getZ(), 0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
			}else if(rand.nextFloat()<0.05){
				entity.level().explode(entity,entity.getX(),entity.getY(),entity.getZ(), (float)(1+Math.sqrt(stack.getCount())),true, Level.ExplosionInteraction.TNT);
				entity.kill();
			}
		}
		return false;
	}
}
