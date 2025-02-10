package charcoalPit.block;

import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ModTags;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.tile.TileCreosoteFunnel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class BlockActivePile extends Block {

    public static final IntegerProperty AGE= BlockStateProperties.AGE_5;
    public static final BooleanProperty INVALID= BooleanProperty.create("invalid");
    public boolean isRefractory;
    public float tick_chance;
    public final Holder<Block> origin, destination;

    public BlockActivePile(boolean isRefractory,float tick_chance,Holder<Block> origin,Holder<Block> destination,Properties properties) {
        super(properties);
        this.isRefractory=isRefractory;
        this.tick_chance=tick_chance;
        this.origin=origin;
        this.destination=destination;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 9;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //check valid
        if(state.getValue(INVALID)){
            boolean valid=true;
            for(Direction dir:Direction.values()){
                BlockPos newPos=pos.relative(dir);
                BlockState newState=level.getBlockState(newPos);
                //non-full blocks or non-refractory blocks
                if(valid&&!newState.isFaceSturdy(level,newPos,dir.getOpposite())||(isRefractory&&!newState.is(ModTags.REFRACTORY_BLOCKS))){
                    valid=false;
                }
                //flammable blocks but not itself
                if(newState.isFlammable(level,newPos,dir.getOpposite())&&!(newState.getBlock() instanceof BlockActivePile)){
                    burn(level,newPos,newState);
                }
            }
            if(valid) {
                level.setBlock(pos, state.setValue(INVALID, false), 2);
            }else{
                level.removeBlock(pos,false);
                level.playSound(null,pos,SoundEvents.FIRE_EXTINGUISH,SoundSource.BLOCKS,1F,1F);
                return;
            }
        }
        //progress
        processTick(state,level,pos,random);
    }

    public void processTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        if(random.nextFloat()<tick_chance){
            int stage=state.getValue(AGE);
            for(Direction dir:Direction.values()){
                BlockState nextState=level.getBlockState(pos.relative(dir));
                if(nextState.getBlock()==this){
                    int stage2=nextState.getValue(AGE);
                    if(stage2<stage){
                        //cant progress if nearby blocks are still behind
                        ((BlockActivePile)nextState.getBlock()).processTick(nextState,level,pos.relative(dir),random);
                        return;
                    }
                }
            }
            /*for(BlockPos mutable:BlockPos.MutableBlockPos.betweenClosed(pos.below().north().west(),pos.above().south().east())){
                BlockState nextState=level.getBlockState(mutable);
                if(nextState.getBlock()==this){
                    int stage2=nextState.getValue(AGE);
                    if(stage2<stage){
                        //cant progress if nearby blocks are still behind
                        return;
                    }
                }
            }*/
            diffuseCreosote(pos, level, state.getBlock()==BlockRegistry.ACTIVE_LOG_PILE.get()?75:150);
            if(stage<5){
                level.setBlock(pos,state.setValue(AGE,stage+1),2);
            }else{
                level.setBlock(pos,destination.value().defaultBlockState(),2);
            }
        }
    }

    public void diffuseCreosote(BlockPos pos, Level level, int amount){
        BlockPos.MutableBlockPos newPos=pos.above().mutable();
        BlockState state=level.getBlockState(newPos);
        while(state.getBlock() instanceof BlockActivePile || state.getBlock() instanceof BlockAsh){
            newPos.move(Direction.UP);
            state=level.getBlockState(newPos);
        }
        BlockState aboveState=level.getBlockState(newPos);
        if(aboveState.getBlock() instanceof BlockCreosoteFunnel){
            amount-=fillCreosote(newPos,level,amount);
            if(amount<=0)
                return;
        }
        newPos.move(Direction.DOWN);
        for(Direction dir:Direction.Plane.HORIZONTAL){
            BlockPos posPos=newPos.relative(dir);
            BlockState stateState=level.getBlockState(posPos);
            if(stateState.getBlock() instanceof BlockActivePile || stateState.getBlock() instanceof BlockAsh){
                BlockPos posPosUp=posPos.above();
                BlockState stateStateUp=level.getBlockState(posPosUp);
                if(stateStateUp.getBlock() instanceof BlockCreosoteFunnel){
                    amount-=fillCreosote(posPosUp,level,amount);
                    if(amount<=0)
                        return;
                }
                BlockPos posPosRight=posPos.relative(dir.getClockWise());
                BlockState stateStateRight=level.getBlockState(posPosRight);
                if(stateStateRight.getBlock() instanceof BlockActivePile || stateStateRight.getBlock() instanceof BlockAsh){
                    posPosUp=posPosRight.above();
                    stateStateUp=level.getBlockState(posPosUp);
                    if(stateStateUp.getBlock() instanceof BlockCreosoteFunnel){
                        amount-=fillCreosote(posPosUp,level,amount);
                        if(amount<=0)
                            return;
                    }
                }
                BlockPos posPosLeft=posPos.relative(dir.getCounterClockWise());
                BlockState stateStateLeft=level.getBlockState(posPosLeft);
                if(stateStateLeft.getBlock() instanceof BlockActivePile || stateStateLeft.getBlock() instanceof BlockAsh){
                    posPosUp=posPosLeft.above();
                    stateStateUp=level.getBlockState(posPosUp);
                    if(stateStateUp.getBlock() instanceof BlockCreosoteFunnel){
                        amount-=fillCreosote(posPosUp,level,amount);
                        if(amount<=0)
                            return;
                    }
                }
            }
        }
    }

    public int fillCreosote(BlockPos pos,Level level, int amount){
        TileCreosoteFunnel tile=((TileCreosoteFunnel) level.getBlockEntity(pos));
        return tile.tank.fill(new FluidStack(FluidRegistry.CREOSOTE.source,amount), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for(Direction dir:Direction.values()){
            BlockState newState=level.getBlockState(pos.relative(dir));
            if(newState.getBlock()==origin.value()){
                igniteNeighbor(level,pos.relative(dir),newState);
            }
        }
    }

    public void burn(Level level, BlockPos pos, BlockState state){
        if(state.getBlock()==origin.value()){
            igniteNeighbor(level,pos,state);
        }else {
            level.setBlock(pos, BaseFireBlock.getState(level,pos), 3);
        }
    }

    public void igniteNeighbor(Level level, BlockPos pos, BlockState state){
        level.setBlock(pos,this.defaultBlockState(),3);
        level.playSound(null,pos,SoundEvents.FIRECHARGE_USE,SoundSource.BLOCKS,1F,1F);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if(level.getBlockState(neighborPos).isAir()){
            level.setBlock(neighborPos, BaseFireBlock.getState(level,neighborPos),3);
        }
        level.setBlock(pos,state.setValue(INVALID,true),2);

    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(oldState.getBlock()!=this){
            level.scheduleTick(pos,this,40);
            for(Direction dir:Direction.values()){
                if(level.getBlockState(pos.relative(dir)).isAir()){
                    level.setBlock(pos.relative(dir),BaseFireBlock.getState(level,pos.relative(dir)),3);
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (level.getBlockState(pos.above(2)).canBeReplaced())
        {
            double x = pos.getX() + rand.nextFloat();
            double y = pos.getY() + 1.125;
            double z = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
            if (rand.nextInt(12) == 0)
            {
                level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, (0.5F - rand.nextFloat()) / 10, 0.1f + rand.nextFloat() / 8, (0.5F - rand.nextFloat()) / 10);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE,INVALID);
    }
}
