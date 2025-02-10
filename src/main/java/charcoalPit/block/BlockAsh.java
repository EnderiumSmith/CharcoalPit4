package charcoalPit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

public class BlockAsh extends ColoredFallingBlock {
    public BlockAsh(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        ResourceKey<LootTable> resourcekey = this.getLootTable();
        if (resourcekey == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            ItemStack tool=params.getOptionalParameter(LootContextParams.TOOL);
            int fortune=0;
            if(tool!=null&&!tool.isEmpty()){
                fortune=tool.getEnchantmentLevel(params.getLevel().registryAccess().holderOrThrow(Enchantments.FORTUNE));
            }
            LootParams lootparams = params.withParameter(LootContextParams.BLOCK_STATE, state).withLuck(fortune).create(LootContextParamSets.BLOCK);
            ServerLevel serverlevel = lootparams.getLevel();
            LootTable loottable = serverlevel.getServer().reloadableRegistries().getLootTable(resourcekey);
            return loottable.getRandomItems(lootparams);
        }
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {

            LootTable table = level.getServer().reloadableRegistries().getLootTable(this.getLootTable());
            LootParams.Builder builder = new LootParams.Builder((ServerLevel) level)
                    .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                    .withParameter(LootContextParams.BLOCK_STATE, this.defaultBlockState())
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY);
            List<ItemStack> loot = table.getRandomItems(builder.create(LootContextParamSets.BLOCK));
            loot.forEach(item -> Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), item));
    }
}
