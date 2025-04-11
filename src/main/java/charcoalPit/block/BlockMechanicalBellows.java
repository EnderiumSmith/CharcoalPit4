package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockMechanicalBellows extends BlockBellows{
    public BlockMechanicalBellows(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(!level.isClientSide){
            if(!state.getValue(POWERED)&&getNeighborSignal(level,state,pos)){
                level.scheduleTick(pos,this,10);
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(POWERED)){
            if(!getNeighborSignal(level,state,pos)){
                level.setBlockAndUpdate(pos,state.setValue(POWERED,false));
                level.scheduleTick(pos,this,10);
            }else{
                level.scheduleTick(pos,this,2);
            }
        }else{
            if(getNeighborSignal(level,state,pos)){
                level.setBlockAndUpdate(pos,state.setValue(POWERED,true));
                level.scheduleTick(pos,this,20);
                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS,0.9F,1F);
            }
        }
    }

    private boolean getNeighborSignal(SignalGetter getter, BlockState state, BlockPos pos){
        if(state.getValue(POWERED)){
            return getter.hasSignal(pos.relative(state.getValue(FACING)),state.getValue(FACING));
        }
        return getter.hasNeighborSignal(pos);
    }
}
