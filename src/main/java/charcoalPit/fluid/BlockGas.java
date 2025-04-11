package charcoalPit.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class BlockGas extends LiquidBlock {
	final int type;
	public BlockGas(FlowingFluid fluid, Properties properties, int type) {
		super(fluid, properties);
		this.type=type;
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if(entity instanceof LivingEntity livingEntity){
			if(entity.getAirSupply()<=0 && !livingEntity.getType().is(EntityTypeTags.UNDEAD)){
				if(type==0){
					livingEntity.hurt(level.damageSources().drown(),100);
				}
				if(type==1){
					livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,20*60*3,1));
				}
			}
			if(type==1){
				livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20*60));
			}
		}
	}
}
