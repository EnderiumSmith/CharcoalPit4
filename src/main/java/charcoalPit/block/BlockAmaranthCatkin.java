package charcoalPit.block;

import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class BlockAmaranthCatkin extends MangrovePropaguleBlock {
    public BlockAmaranthCatkin(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isHanging(state)) {
            TriState soilDecision = level.getBlockState(pos.above()).canSustainPlant(level, pos.above(), Direction.DOWN, state);
            return !soilDecision.isDefault() ? soilDecision.isTrue() : level.getBlockState(pos.above()).is(BlockRegistry.AMARANTH_LEAVES.get());
        } else {
            return super.canSurvive(state, level, pos);
        }
    }

    private static boolean isHanging(BlockState state) {
        return state.getValue(HANGING);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(isHanging(state)){
            if(random.nextInt(7)==0){
                super.randomTick(state,level,pos,random);
            }
        }else
            super.randomTick(state,level,pos,random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(isHanging(state)&&state.getValue(MangrovePropaguleBlock.AGE)==4){
            level.setBlock(pos,state.setValue(MangrovePropaguleBlock.AGE,0),3);
            ItemHandlerHelper.giveItemToPlayer(player,new ItemStack(ItemRegistry.AMARANTH_SAPLING.get()),player.getInventory().selected);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
