package charcoalPit.block;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.gui.menu.BarrelMenu;
import charcoalPit.gui.menu.TheVatMenu;
import charcoalPit.tile.TileBarrel;
import charcoalPit.tile.TileTheVat;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockTheVat extends BlockMachineBase implements EntityBlock {
    public BlockTheVat(Properties properties) {
        super(properties);

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileTheVat(blockPos,blockState);
    }

    /*@Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (!level.isClientSide()&&blockEntityType== TileEntityRegistry.THE_VAT.get())?(l, p, s, e)->((TileTheVat)e).tick():null;
    }*/

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        TileTheVat vat= (TileTheVat) level.getBlockEntity(pos);
        return new SimpleMenuProvider((id, inv, player)->new TheVatMenu(id,inv,vat, ContainerLevelAccess.create(level,pos)), Component.translatable("screen.charcoal_pit.the_vat"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer){
            serverPlayer.openMenu(state.getMenuProvider(level,pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
