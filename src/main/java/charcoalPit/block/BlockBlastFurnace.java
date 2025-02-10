package charcoalPit.block;

import charcoalPit.core.BlockRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.gui.menu.BlastFurnaceMenu;
import charcoalPit.tile.TileBlastFurnace;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockBlastFurnace extends Block implements EntityBlock {

    public static final DirectionProperty FACING= BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT=BlockStateProperties.LIT;

    public BlockBlastFurnace(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LIT,false));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT)?15:0;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 2 &&
                level.getBlockState(blockpos.above()).canBeReplaced(context)&&
                level.getBlockState(blockpos.above(2)).canBeReplaced()) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }else{
            return null;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(direction==Direction.UP&&!(neighborState.getBlock() instanceof BlockBlastFurnaceMiddle)){
            return Blocks.AIR.defaultBlockState();
        }
        if(!level.isClientSide() && neighborState.getBlock() instanceof BlockBellows && direction.getAxis().isHorizontal() && direction!=state.getValue(FACING)){
            ((TileBlastFurnace)level.getBlockEntity(pos)).calculateBlastBoost();
        }
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), BlockRegistry.BLAST_FURNACE_MIDDLE.get().defaultBlockState(),3);
        level.setBlock(pos.above(2),BlockRegistry.BLAST_FURNACE_CHIMNEY.get().defaultBlockState(),3);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            ((TileBlastFurnace)level.getBlockEntity(pos)).dropInventory();
            level.removeBlockEntity(pos);
        }
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        TileBlastFurnace bloomery= (TileBlastFurnace) level.getBlockEntity(pos);
        return new SimpleMenuProvider((id, inv, player)->new BlastFurnaceMenu(id,inv,bloomery, ContainerLevelAccess.create(level,pos)), Component.translatable("screen.charcoal_pit.blast_furnace"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer){
            serverPlayer.openMenu(state.getMenuProvider(level,pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,LIT);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileBlastFurnace(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.BLAST_FURNACE.get())?(l, p, s, e)->((TileBlastFurnace)e).tick():null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d0 = (double) pos.getX() + 0.5;
            double d1 = (double) pos.getY();
            double d2 = (double) pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52;
            double d4 = random.nextDouble() * 0.6 - 0.3;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52 : d4;
            double d6 = random.nextDouble() * 9.0 / 16.0;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52 : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
        }
    }
}
