package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.gui.menu.CokeOvenMenu;
import charcoalPit.tile.TileCokeOven;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

public class BlockCokeOven extends Block implements EntityBlock {

	public static final BooleanProperty LIT= BlockStateProperties.LIT;
	public static final IntegerProperty STATE=BlockStateProperties.AGE_4;

	public BlockCokeOven(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(LIT,false).setValue(STATE,0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT,STATE);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(LIT)?15:0;
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}


	@Override
	protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		TileCokeOven cokeOven= (TileCokeOven) level.getBlockEntity(pos);
		if(cokeOven.master!=null)
			return new SimpleMenuProvider((id, inv, player)->new CokeOvenMenu(id,inv,cokeOven.master, ContainerLevelAccess.create(level,pos)), Component.translatable("screen.charcoal_pit.coke_oven"));
		else return null;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		TileCokeOven oven=(TileCokeOven)level.getBlockEntity(pos);
		if(oven.assembled) {
			if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
				serverPlayer.openMenu(state.getMenuProvider(level, pos));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}else return InteractionResult.PASS;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(stack.getCapability(Capabilities.FluidHandler.ITEM)!=null){
			if(level.isClientSide)
				return ItemInteractionResult.SUCCESS;
			if(FluidUtil.interactWithFluidHandler(player,hand,level,pos,hitResult.getDirection()))
				return ItemInteractionResult.SUCCESS;
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
			TileCokeOven oven=(TileCokeOven)level.getBlockEntity(pos);
			if(oven.master!=null)
				oven.master.disassemble();
			level.removeBlockEntity(pos);
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(LIT)) {
			TileCokeOven oven=(TileCokeOven) level.getBlockEntity(pos);
			if(oven.isMaster){
				double d0 = (double) pos.getX() + 1;
				double d1 = (double) pos.getY() + 0.75;
				double d2 = (double) pos.getZ() + 1;
				if (random.nextDouble() < 0.1) {
					level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
				}
				for(Direction direction:Direction.values()){
					Direction.Axis direction$axis = direction.getAxis();
					if(!direction$axis.isHorizontal())
						continue;
					double d3 = 0.52;
					double d4 = random.nextDouble() * 0.6 - 0.3;
					double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 1.04 : d4;
					double d6 = random.nextDouble() * 9.0 / 16.0;
					double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 1.04 : d4;
					level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
				}
				double x = pos.getX() + 0.5 + random.nextFloat();
				double y = pos.getY() + 2.125;
				double z = pos.getZ() + 0.5 + random.nextFloat();
				level.addParticle(ParticleTypes.SMOKE, x, y, z, 0f, 0.1f + 0.1f * random.nextFloat(), 0f);
				level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, (0.5F - random.nextFloat()) / 10, 0.1f + random.nextFloat() / 8, (0.5F - random.nextFloat()) / 10);
			}
		}
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileCokeOven(pos,state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.COKE_OVEN.get())?(l, p, s, e)->((TileCokeOven)e).tick():null;
	}
}
