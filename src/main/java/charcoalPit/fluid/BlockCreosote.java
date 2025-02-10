package charcoalPit.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.WaterFluid;

public class BlockCreosote extends LiquidBlock {
    public BlockCreosote(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 100;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(entity instanceof LivingEntity livingEntity){
            if(livingEntity.getType().is(EntityTypeTags.ARTHROPOD)){
                livingEntity.hurt(level.damageSources().drown(),1);
            }
            if(livingEntity.isSensitiveToWater()&&livingEntity.getType()!= EntityType.ENDERMAN){
                livingEntity.hurt(level.damageSources().drown(),1);
            }
        }
    }
}
