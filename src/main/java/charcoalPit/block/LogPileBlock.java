package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import charcoalPit.core.BlockRegistry;

public class LogPileBlock extends RotatedPillarBlock {
	public LogPileBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 5;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 5;
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		BlockState state2=level.getBlockState(neighborPos);
		if(state2.getBlock() instanceof BaseFireBlock){
			level.setBlock(pos, BlockRegistry.ACTIVE_LOG_PILE.get().defaultBlockState().setValue(BlockStateProperties.AXIS,state.getValue(BlockStateProperties.AXIS)),3);
			level.playSound(null,pos,SoundEvents.FIRECHARGE_USE,SoundSource.BLOCKS,1F,1F);
		}
	}
}
