package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockBloomeryChimney extends Block {

    public static final BooleanProperty LIT= BlockStateProperties.LIT;
    public static final VoxelShape CHIMNEY=Block.box(2F,0F,2F,14F,16F,14F);

    public BlockBloomeryChimney(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT,false));
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (state.getValue(LIT)&& !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }

        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT)?15:0;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(direction==Direction.DOWN&&!(isSupport(neighborState.getBlock()))){
            return Blocks.AIR.defaultBlockState();
        }else
            return state;
    }

    public boolean isSupport(Block block){
        return block instanceof BlockBloomery||block instanceof BlockBlastFurnaceMiddle;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.getBlock() instanceof BlockBloomery;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return CHIMNEY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (state.getValue(LIT))
        {
            double x = pos.getX() + rand.nextFloat();
            double y = pos.getY() + 1.125;
            double z = pos.getZ() + rand.nextFloat();
            if(!level.getBlockState(pos.above()).canBeReplaced())
                y+=1;
            else
                level.addParticle(ParticleTypes.LAVA, x, y, z, 0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
            if (rand.nextInt(12) == 0)
            {
                level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, (0.5F - rand.nextFloat()) / 10, 0.1f + rand.nextFloat() / 8, (0.5F - rand.nextFloat()) / 10);
        }
    }
}
