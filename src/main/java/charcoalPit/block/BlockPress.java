package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.gui.menu.PressMenu;
import charcoalPit.gui.menu.StillMenu;
import charcoalPit.tile.TIleStill;
import charcoalPit.tile.TilePress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockPress extends BlockStill{
    public BlockPress(Properties properties) {
        super(properties);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        TilePress press= (TilePress) level.getBlockEntity(pos);
        return new SimpleMenuProvider((id, inv, player)->new PressMenu(id,inv,press, ContainerLevelAccess.create(level,pos)), Component.translatable("screen.charcoal_pit.steam_press"));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TilePress(blockPos,blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()&&blockEntityType== TileEntityRegistry.PRESS.get()){
            return (l, p, s, e)->((TilePress)e).clientTick();
        }
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.PRESS.get())?(l, p, s, e)->((TilePress)e).tick():null;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            ((TilePress)level.getBlockEntity(pos)).dropInventory();
            level.removeBlockEntity(pos);
        }
    }
}
