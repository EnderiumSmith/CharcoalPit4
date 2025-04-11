package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class BlockBellowPump extends Block {

	public static final DirectionProperty FACING= BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty POWERED=BlockStateProperties.POWERED;

	public BlockBellowPump(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(POWERED,false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING,POWERED);
	}

	@Override
	protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if(!level.isClientSide){
			if(!state.getValue(POWERED)&&level.hasNeighborSignal(pos)){
				level.scheduleTick(pos,this,20);
			}
		}
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if(!state.getValue(POWERED)){
			if(level.hasNeighborSignal(pos)){
				level.setBlockAndUpdate(pos,state.setValue(POWERED,true));
				level.scheduleTick(pos,this,20);
				level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS,0.9F,1F);
				pumpGas(level,pos,state);
			}
		}else{
			level.setBlockAndUpdate(pos,state.setValue(POWERED,false));
			if(level.hasNeighborSignal(pos)){
				level.scheduleTick(pos,this,20);
			}
		}
	}

	public void pumpGas(ServerLevel level, BlockPos pos, BlockState state){
		Direction facing=state.getValue(FACING);
		IFluidHandler source=level.getCapability(Capabilities.FluidHandler.BLOCK,pos.relative(facing.getOpposite()),facing);
		IFluidHandler sink=level.getCapability(Capabilities.FluidHandler.BLOCK,pos.relative(facing),facing.getOpposite());
		if(source!=null&&sink!=null){
			FluidStack stack=source.drain(1000, IFluidHandler.FluidAction.SIMULATE);
			if(stack.is(Tags.Fluids.GASEOUS)){
				source.drain(sink.fill(stack, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
			}
		}
	}
}
