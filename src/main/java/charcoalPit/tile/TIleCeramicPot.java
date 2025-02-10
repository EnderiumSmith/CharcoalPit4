package charcoalPit.tile;

import charcoalPit.block.BlockCeramicPot;
import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TIleCeramicPot extends BlockEntity {

    public ItemStackHandler inventory;

    public TIleCeramicPot(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.CERAMIC_POT.get(), pos, blockState);
        inventory=new ItemStackHandler(9){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem().canFitInsideContainerItems();
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
    }
}
