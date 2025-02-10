package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CharcoalPit.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.PICKAXES)
                .add(ItemRegistry.COPPER_PICK.get(),ItemRegistry.BRONZE_PICK.get(),ItemRegistry.STEEL_PICK.get());
        tag(ItemTags.SHOVELS)
                .add(ItemRegistry.COPPER_SHOVEL.get(),ItemRegistry.BRONZE_SHOVEL.get(),ItemRegistry.STEEL_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ItemRegistry.COPPER_AXE.get(),ItemRegistry.BRONZE_AXE.get(),ItemRegistry.STEEL_AXE.get());
        tag(ItemTags.SWORDS)
                .add(ItemRegistry.COPPER_SWORD.get(),ItemRegistry.BRONZE_SWORD.get(),ItemRegistry.STEEL_SWORD.get());
        tag(ItemTags.HOES)
                .add(ItemRegistry.COPPER_HOE.get(),ItemRegistry.BRONZE_HOE.get(),ItemRegistry.STEEL_HOE.get());
        tag(ItemTags.MACE_ENCHANTABLE)
                .add(ItemRegistry.CLUB.get());
        tag(ItemTags.HEAD_ARMOR)
                .add(ItemRegistry.BRONZE_HELMET.get(),ItemRegistry.STEEL_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ItemRegistry.BRONZE_CHESTPLATE.get(),ItemRegistry.STEEL_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ItemRegistry.BRONZE_LEGGINGS.get(),ItemRegistry.STEEL_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ItemRegistry.BRONZE_BOOTS.get(),ItemRegistry.STEEL_BOOTS.get());
        tag(ModTags.TIN)
                .add(ItemRegistry.TIN.get());
        tag(ModTags.RAW_TIN)
                .add(ItemRegistry.RAW_TIN.get());
        tag(ModTags.BRONZE)
                .add(ItemRegistry.BRONZE.get());
        tag(ModTags.PIG_IRON)
                .add(ItemRegistry.PIG_IRON.get());
        tag(ModTags.STEEL)
                .add(ItemRegistry.STEEL.get());
        tag(ModTags.RED_ALLOY)
                .add(ItemRegistry.RED_ALLOY.get());
        tag(Tags.Items.INGOTS)
                .addTag(ModTags.TIN).addTag(ModTags.BRONZE).addTag(ModTags.PIG_IRON).addTag(ModTags.STEEL).addTag(ModTags.RED_ALLOY);
        tag(Tags.Items.RAW_MATERIALS)
                .addTag(ModTags.RAW_TIN);
        tag(Tags.Items.STONES)
                .add(ItemRegistry.BASALT.get());
        tag(ModTags.CALCAREOUS_ROCKS)
                .add(Items.DRIPSTONE_BLOCK,Items.CALCITE,ItemRegistry.MARBLE.get(),
                        Items.DEAD_BRAIN_CORAL_BLOCK,Items.DEAD_BUBBLE_CORAL_BLOCK,Items.DEAD_FIRE_CORAL_BLOCK,Items.DEAD_HORN_CORAL_BLOCK,Items.DEAD_TUBE_CORAL_BLOCK)
                .addOptional(ResourceLocation.fromNamespaceAndPath("quark","limestone"));
        tag(ModTags.CALCAREOUS_ITEMS)
                .add(Items.POINTED_DRIPSTONE,
                        Items.DEAD_BRAIN_CORAL,Items.DEAD_TUBE_CORAL,Items.DEAD_FIRE_CORAL,Items.DEAD_HORN_CORAL,Items.DEAD_BUBBLE_CORAL,
                        Items.DEAD_BRAIN_CORAL_FAN,Items.DEAD_BUBBLE_CORAL_FAN,Items.DEAD_FIRE_CORAL_FAN,Items.DEAD_HORN_CORAL_FAN,Items.DEAD_TUBE_CORAL_FAN);
        tag(Tags.Items.CROPS)
                .add(ItemRegistry.LEEKS.get(),ItemRegistry.SUNFLOWER_SEEDS.get());
        tag(Tags.Items.FOODS_VEGETABLE)
                .add(ItemRegistry.LEEKS.get());
        tag(ModTags.CROPS_LEEK)
                .add(ItemRegistry.LEEKS.get());
        tag(ItemTags.RABBIT_FOOD)
                .add(ItemRegistry.LEEKS.get());
        tag(ItemTags.MEAT)
                .add(ItemRegistry.FARFETCH_STEW.get(),ItemRegistry.SERINAN_STEW.get(),ItemRegistry.KEBABS.get(),ItemRegistry.FROG_LEG.get(),ItemRegistry.FROG_LEG_COOKED.get(),
                        ItemRegistry.INGOT_CHICKEN.get(),ItemRegistry.NUGGET_CHICKEN.get());
        tag(ItemTags.FISHES)
                .add(ItemRegistry.RAW_CALAMARI.get(),ItemRegistry.COOKED_CALAMARI.get());
        tag(Tags.Items.FOODS_SOUP)
                .add(ItemRegistry.FARFETCH_STEW.get(),ItemRegistry.SERINAN_STEW.get());
        tag(ModTags.FLOUR)
                .add(ItemRegistry.FLOUR.get());
        tag(Tags.Items.FOODS)
                .add(ItemRegistry.SUNFLOWER_SEEDS.get(),ItemRegistry.DANGOS.get(),ItemRegistry.CHEESE.get(),ItemRegistry.POISONOUS_POTATO_FRIES.get(),
                        ItemRegistry.KEBABS.get(),ItemRegistry.COOKED_CALAMARI.get(),ItemRegistry.RAW_CALAMARI.get(),ItemRegistry.FROG_LEG.get(),ItemRegistry.FROG_LEG_COOKED.get(),
                        ItemRegistry.INGOT_CHICKEN.get(),ItemRegistry.NUGGET_CHICKEN.get(),ItemRegistry.AMARANTH_BREAD.get(),ItemRegistry.CHERRY_SANDWITCH.get());
        tag(Tags.Items.FOODS_CANDY)
                .add(ItemRegistry.CHOCOLATE_BAR.get(),ItemRegistry.CROISSANT.get(),ItemRegistry.PANCAKES.get(),ItemRegistry.CHERRY_JAM.get());
        tag(Tags.Items.FOODS_FRUIT)
                .add(ItemRegistry.CHERRY.get(),ItemRegistry.ORANGE.get(),ItemRegistry.OLIVES.get(),ItemRegistry.WALNUT.get(),ItemRegistry.STRAWBERRY.get());
        tag(ModTags.SUNFLOWER_SEEDS)
                .add(ItemRegistry.SUNFLOWER_SEEDS.get());
        tag(ModTags.ASH)
                .add(ItemRegistry.ASH.get());
        tag(ModTags.GUNPOWDER)
                .add(ItemRegistry.SMALL_GUNPOWDER.get());
        tag(ModTags.BULLETS)
                .add(Items.GOLD_NUGGET)
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","nuggets/lead"));
        tag(ItemTags.BOW_ENCHANTABLE)
                .add(ItemRegistry.MUSKET.get());
        tag(ItemTags.CROSSBOW_ENCHANTABLE)
                .add(ItemRegistry.MUSKET.get());
        tag(ModTags.KNIFES)
                .add(ItemRegistry.BRONZE_KNIFE.get(),ItemRegistry.STEEL_KNIFE.get());
        tag(ModTags.TEMPERATURE_FUELS)
                .add(Items.OAK_LOG,Items.CHARCOAL,ItemRegistry.CHARCOAL_BLOCK.get(),Items.COAL,Items.COAL_BLOCK,ItemRegistry.COKE.get(),ItemRegistry.COKE_BLOCK.get(),Items.BLAZE_ROD,ItemRegistry.AETERNALIS.get());
        tag(ModTags.TEMPERATURE_FUELS_CLEAN)
                .add(Items.CHARCOAL,ItemRegistry.CHARCOAL_BLOCK.get(),ItemRegistry.COKE.get(),ItemRegistry.COKE_BLOCK.get(),Items.BLAZE_ROD,ItemRegistry.AETERNALIS.get());
        tag(ModTags.ALCOHOL_ITEMS)
                .add(Items.APPLE,Items.POTATO,Items.BEETROOT,Items.WHEAT,Items.SWEET_BERRIES,Items.HONEYCOMB,Items.SUGAR,Items.GLOW_BERRIES)
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","crops/grapes"))
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","grain/rice"))
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","crops/plum"))
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","crops/maize"))
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c","grain/maize"));
        tag(ItemTags.LEAVES)
                .add(ItemRegistry.AMARANTH_LEAVES.get(),ItemRegistry.DOUGLAS_LEAVES.get(),ItemRegistry.APPLE_LEAVES.get(),ItemRegistry.CHERRY_LEAVES.get(),ItemRegistry.ORANGE_LEAVES.get(),ItemRegistry.OLIVE_LEAVES.get());
        tag(ItemTags.TRIM_MATERIALS)
                .add(ItemRegistry.TIN.get());
        tag(ModTags.BRONZE_BLOCK).add(ItemRegistry.BRONZE_BLOCK.get());
        tag(ModTags.TIN_BLOCK).add(ItemRegistry.TIN_BLOCK.get());
        tag(ModTags.STEEL_BLOCK).add(ItemRegistry.STEEL_BLOCK.get());
        tag(ModTags.CHARCOAL_BLOCK).add(ItemRegistry.CHARCOAL_BLOCK.get());
        tag(ModTags.COKE_BLOCK).add(ItemRegistry.COKE_BLOCK.get());
        tag(ModTags.COKE).add(ItemRegistry.COKE.get());

    }
}
