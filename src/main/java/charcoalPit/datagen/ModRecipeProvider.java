package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import charcoalPit.core.ToolTiers;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static charcoalPit.core.ModTags.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //MACHINES
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BLOOMERY_BRICK.get())
                .pattern(" B ")
                .pattern("B B")
                .pattern("BFB")
                .define('B', Items.BRICKS)
                .define('F', Items.FURNACE)
                .unlockedBy("",has(Items.BRICKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BLOOMERY_SANDY.get())
                .pattern(" B ")
                .pattern("B B")
                .pattern("BFB")
                .define('B', ItemRegistry.SANDY_BRICKS)
                .define('F', Items.FURNACE)
                .unlockedBy("",has(ItemRegistry.SANDY_BRICKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BLOOMERY_NETHER.get())
                .pattern(" B ")
                .pattern("B B")
                .pattern("BFB")
                .define('B', Items.NETHER_BRICKS)
                .define('F', Items.FURNACE)
                .unlockedBy("",has(Items.NETHER_BRICKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BLAST_FURNACE.get())
                .pattern(" B ")
                .pattern("B B")
                .pattern("BFB")
                .define('B', ItemRegistry.HIGH_REFRACTORY_BRICKS)
                .define('F', Items.BLAST_FURNACE)
                .unlockedBy("",has(ItemRegistry.HIGH_REFRACTORY_BRICKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.COKE_OVEN.get(),8)
                .pattern("BBB")
                .pattern("B B")
                .pattern("BFB")
                .define('B', ItemRegistry.HIGH_REFRACTORY_BRICKS)
                .define('F', Items.IRON_TRAPDOOR)
                .unlockedBy("",has(ItemRegistry.HIGH_REFRACTORY_BRICKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BELLOWS.get())
                .pattern("PLP")
                .pattern("PLP")
                .pattern("PLP")
                .define('P', ItemTags.WOODEN_SLABS)
                .define('L', Items.LEATHER)
                .unlockedBy("",has(Items.LEATHER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.MECHANICAL_BELLOWS.get())
                .pattern("PRP")
                .pattern("ILI")
                .pattern("PRP")
                .define('P', Items.SMOOTH_STONE_SLAB)
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_INGOT)
                .define('L', ItemRegistry.BELLOWS.get())
                .unlockedBy("",has(Items.LEATHER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BELLOW_PUMP.get())
                .pattern("SCS")
                .pattern("PLP")
                .pattern("SCS")
                .define('S', Items.SMOOTH_STONE_SLAB)
                .define('P', ItemRegistry.BELLOWS)
                .define('L', Items.REPEATER)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("",has(Items.LEATHER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BARREL.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("BBB")
                .define('B', ItemTags.WOODEN_SLABS)
                .unlockedBy("",has(ItemTags.WOODEN_SLABS))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.BARREL.get())
                .requires(ItemRegistry.BARREL.get())
                .unlockedBy("",has(ItemTags.WOODEN_SLABS))
                .save(recipeOutput,"charcoal_pit:barrel_refresh");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.QUERN.get())
                .pattern("I  ")
                .pattern("SSS")
                .pattern("MMM")
                .define('I', Items.STICK)
                .define('S', Tags.Items.STONES)
                .define('M', Items.SMOOTH_STONE)
                .unlockedBy("",has(Items.SMOOTH_STONE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.STILL.get())
                .pattern("III")
                .pattern("ICI")
                .pattern("IFI")
                .define('I', Items.COPPER_INGOT)
                .define('C', Items.CAULDRON)
                .define('F', Items.FURNACE)
                .unlockedBy("",has(Items.FURNACE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.PRESS.get())
                .pattern("III")
                .pattern("ICI")
                .pattern("IFI")
                .define('I', BRONZE)
                .define('C', Items.HOPPER)
                .define('F', Items.FURNACE)
                .unlockedBy("",has(Items.FURNACE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.NEST_BOX.get())
                .pattern("SHS")
                .pattern("SSS")
                .define('S', ItemTags.WOODEN_SLABS)
                .define('H', Items.HAY_BLOCK)
                .unlockedBy("",has(Items.HAY_BLOCK))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.FEEDING_THROUGH.get())
                .pattern("SHS")
                .pattern(" S ")
                .define('S', ItemTags.WOODEN_SLABS)
                .define('H', Items.HAY_BLOCK)
                .unlockedBy("",has(Items.HAY_BLOCK))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.AIKO_PLUSH.get())
                .pattern("BGB")
                .pattern("KAK")
                .define('B', Items.BLUE_WOOL)
                .define('K', Items.BLACK_WOOL)
                .define('G', Items.LIME_DYE)
                .define('A', ALUMINIUM)
                .unlockedBy("",has(ALUMINIUM))
                .save(recipeOutput);

        //ITEMS
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.FERTILIZER.get(),2)
                .requires(Items.DIRT)
                .requires(ASH)
                .requires(ASH)
                .requires(ASH)
                .requires(ASH)
                .unlockedBy("",has(ASH))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.FERTILIZER.get(),2)
                .requires(Items.ROTTEN_FLESH)
                .requires(ASH)
                .requires(ASH)
                .requires(ASH)
                .requires(ASH)
                .unlockedBy("",has(ASH))
                .save(recipeOutput,"charcoal_pit:fertilizer_flesh");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.FERTILIZER.get(),4)
                .requires(DUST_NITER)
                .requires(Items.BONE_MEAL)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.ROTTEN_FLESH)
                .unlockedBy("",has(DUST_NITER))
                .save(recipeOutput,"charcoal_pit:fertilizer_niter");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.RAW_TIN.get())
                .requires(ItemRegistry.RAW_TIN_NUGGET)
                .requires(ItemRegistry.RAW_TIN_NUGGET)
                .requires(ItemRegistry.RAW_TIN_NUGGET)
                .requires(ItemRegistry.RAW_TIN_NUGGET)
                .unlockedBy("",has(ItemRegistry.RAW_TIN_NUGGET))
                .save(recipeOutput);
        oreSmelting(recipeOutput,List.of(ItemRegistry.CLAY_POT.get()),RecipeCategory.MISC,ItemRegistry.CERAMIC_POT.get(),0.7F,200,"ceramic");
        oreSmelting(recipeOutput,List.of(ItemRegistry.RAW_TIN.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.7F,200,"tin");
        oreBlasting(recipeOutput,List.of(ItemRegistry.RAW_TIN.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.7F,100,"tin");
        oreSmelting(recipeOutput,List.of(ItemRegistry.ORE_TIN.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.7F,200,"tin");
        oreBlasting(recipeOutput,List.of(ItemRegistry.ORE_TIN.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.7F,100,"tin");
        oreSmelting(recipeOutput,List.of(ItemRegistry.TIN_DUST.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.1F,200,"tin");
        oreBlasting(recipeOutput,List.of(ItemRegistry.TIN_DUST.get()),RecipeCategory.MISC,ItemRegistry.TIN.get(),0.1F,100,"tin");
        oreBlasting(recipeOutput,List.of(ItemRegistry.PIG_IRON.get()),RecipeCategory.MISC,Items.IRON_INGOT,0.7F,200,"steel");

        makeCompressionRecipe(recipeOutput, ItemRegistry.RAW_CHALCOCITE.get(), ItemRegistry.RAW_CHALCOCITE_BLOCK.get());
        oreSmelting(recipeOutput,List.of(ItemRegistry.RAW_CHALCOCITE.get()),RecipeCategory.MISC,Items.COPPER_INGOT,0.7F,200,"copper");
        oreBlasting(recipeOutput,List.of(ItemRegistry.RAW_CHALCOCITE.get()),RecipeCategory.MISC,Items.COPPER_INGOT,0.7F,100,"copper");
        oreSmelting(recipeOutput,List.of(ItemRegistry.ORE_CHALCOCITE.get()),RecipeCategory.MISC,Items.COPPER_INGOT,0.7F,200,"copper");
        oreBlasting(recipeOutput,List.of(ItemRegistry.ORE_CHALCOCITE.get()),RecipeCategory.MISC,Items.COPPER_INGOT,0.7F,100,"copper");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.CLAY_MOLD.get(),2)
                .pattern("C C")
                .pattern("CCC")
                .define('C', Items.CLAY_BALL)
                .unlockedBy("",has(Items.CLAY_BALL))
                .save(recipeOutput);
        oreSmelting(recipeOutput,List.of(ItemRegistry.CLAY_MOLD.get()),RecipeCategory.MISC,ItemRegistry.ALLOY_MOLD, 0.35F, 200, "clay_mold");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ItemRegistry.UNFIRED_SANDY_BRICK.get(),4)
                .requires(ItemTags.SAND)
                .requires(Items.CLAY_BALL)
                .requires(Items.CLAY_BALL)
                .requires(Items.CLAY_BALL)
                .requires(Items.CLAY_BALL)
                .unlockedBy("",has(Items.CLAY_BALL))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS,ItemRegistry.DWARVEN_CANDLE.get(),2)
                .requires(Items.CLAY_BALL)
                .requires(Items.GUNPOWDER)
                .requires(Items.GUNPOWDER)
                .requires(Items.STRING)
                .unlockedBy("",has(Items.CLAY_BALL))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS,ItemRegistry.DYNAMITE_REMOTE.get())
                .requires(Items.ENDER_EYE)
                .requires(Items.REDSTONE_TORCH)
                .requires(Items.STONE_BUTTON)
                .requires(Tags.Items.STONES)
                .unlockedBy("",has(Items.CLAY_BALL))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS,ItemRegistry.DYNAMITE_REMOTE.get())
                .requires(ItemRegistry.DYNAMITE_REMOTE.get())
                .unlockedBy("",has(Items.CLAY_BALL))
                .save(recipeOutput,"charcoal_pit:clear_remote");
        oreSmelting(recipeOutput,List.of(ItemRegistry.GLYCERINE.get()),RecipeCategory.MISC,Items.SLIME_BALL,0.35F,200,"glycerine");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SUGAR,3)
                .requires(ItemRegistry.GLYCERINE.get())
                .unlockedBy("",has(ItemRegistry.GLYCERINE))
                .save(recipeOutput,"charcoal_pit:glycerine_sugar");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.GUNPOWDER,5)
                .requires(ItemRegistry.GLYCERINE.get())
                .requires(Items.BONE_MEAL)
                .requires(Items.BONE_MEAL)
                .requires(Items.BONE_MEAL)
                .requires(Items.BONE_MEAL)
                .unlockedBy("",has(ItemRegistry.GLYCERINE))
                .save(recipeOutput,"charcoal_pit:glycerine_gunpowder");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.JERRY_CAN.get())
                .pattern("II ")
                .pattern("IBI")
                .pattern("III")
                .define('I', Items.COPPER_INGOT)
                .define('B', Items.BUCKET)
                .unlockedBy("",has(Items.BUCKET))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.SUNFLOWER_SEEDS)
                .requires(Items.SUNFLOWER)
                .requires(Items.SUNFLOWER)
                .unlockedBy("",has(Items.SUNFLOWER))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.BLACK_DYE, 2)
                .requires(ItemRegistry.GREEN_VITRIOL)
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .unlockedBy("",has(ItemRegistry.GREEN_VITRIOL))
                .save(recipeOutput,"charcoal_pit:gall_ink");
        //
        oreSmelting(recipeOutput,List.of(ItemRegistry.FLOUR.get()),RecipeCategory.FOOD,Items.BREAD,0.35F,200,"four");
        oreBaking(recipeOutput,List.of(ItemRegistry.FLOUR.get()),RecipeCategory.FOOD,Items.BREAD,0.35F,100,"four");
        oreCamping(recipeOutput,List.of(ItemRegistry.FLOUR.get()),RecipeCategory.FOOD,Items.BREAD,0.35F,400,"four");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.FARFETCH_STEW)
                .requires(ItemRegistry.LEEKS)
                .requires(Items.COOKED_CHICKEN)
                .requires(ItemRegistry.LEEKS)
                .requires(Items.BOWL)
                .unlockedBy("",has(ItemRegistry.LEEKS.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.KEBABS,2)
                .requires(Items.CARROT)
                .requires(Items.STICK)
                .requires(Items.COOKED_MUTTON)
                .requires(Items.STICK)
                .unlockedBy("",has(Items.COOKED_MUTTON))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistry.DANGOS.get(),3)
                .pattern("GWP")
                .pattern("FFF")
                .pattern("SSS")
                .define('G', Tags.Items.DYES_LIME)
                .define('W', Tags.Items.DYES_WHITE)
                .define('P', Tags.Items.DYES_PINK)
                .define('F', FLOUR)
                .define('S', Items.STICK)
                .unlockedBy("",has(FLOUR))
                .save(recipeOutput.withConditions(new TagEmptyCondition(RICE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistry.DANGOS.get(),3)
                .pattern("GWP")
                .pattern("FFF")
                .pattern("SSS")
                .define('G', Tags.Items.DYES_LIME)
                .define('W', Tags.Items.DYES_WHITE)
                .define('P', Tags.Items.DYES_PINK)
                .define('F', RICE)
                .define('S', Items.STICK)
                .unlockedBy("",has(FLOUR))
                .save(recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(RICE))),"charcoal_pit:tricolor_dango_rice");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.SERINAN_STEW)
                .requires(Items.BAMBOO)
                .requires(Items.COOKED_CHICKEN)
                .requires(Items.TROPICAL_FISH)
                .requires(SUNFLOWER_SEEDS)
                .requires(Items.BOWL)
                .unlockedBy("",has(SUNFLOWER_SEEDS))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.CROISSANT,2)
                .requires(ItemRegistry.FLOUR)
                .requires(ItemRegistry.CHOCOLATE_BAR)
                .requires(Items.EGG)
                .unlockedBy("",has(Items.COCOA_BEANS))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.AMARANTH_BREAD)
                .requires(ItemRegistry.FLOUR)
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .requires(ItemRegistry.AMARANTH_SAPLING)
                .unlockedBy("",has(ItemRegistry.FLOUR))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.PANCAKES,2)
                .requires(ItemRegistry.FLOUR)
                .requires(Items.EGG)
                .requires(Items.HONEY_BOTTLE)
                .unlockedBy("",has(ItemRegistry.FLOUR))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.CHERRY_JAM)
                .requires(ItemRegistry.CHERRY)
                .requires(ItemRegistry.CHERRY)
                .requires(ItemRegistry.CHERRY)
                .requires(Items.SUGAR)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("",has(ItemRegistry.CHERRY))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.CHERRY_SANDWITCH)
                .requires(Items.BREAD)
                .requires(ItemRegistry.CHERRY_JAM)
                .unlockedBy("",has(ItemRegistry.CHERRY_JAM))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.ORANGE_JUICE)
                .requires(Items.GLASS_BOTTLE)
                .requires(ItemRegistry.ORANGE)
                .requires(ItemRegistry.ORANGE)
                .unlockedBy("",has(ItemRegistry.ORANGE))
                .save(recipeOutput);
        oreSmelting(recipeOutput,List.of(ItemRegistry.RAW_CALAMARI.get()),RecipeCategory.FOOD,ItemRegistry.COOKED_CALAMARI.get(),0.35F,200,"calamari");
        oreBaking(recipeOutput,List.of(ItemRegistry.RAW_CALAMARI.get()),RecipeCategory.FOOD,ItemRegistry.COOKED_CALAMARI.get(),0.35F,100,"calamari");
        oreCamping(recipeOutput,List.of(ItemRegistry.RAW_CALAMARI.get()),RecipeCategory.FOOD,ItemRegistry.COOKED_CALAMARI.get(),0.35F,400,"calamari");
        oreSmelting(recipeOutput,List.of(ItemRegistry.FROG_LEG.get()),RecipeCategory.FOOD,ItemRegistry.FROG_LEG_COOKED.get(),0.35F,200,"frog");
        oreBaking(recipeOutput,List.of(ItemRegistry.FROG_LEG.get()),RecipeCategory.FOOD,ItemRegistry.FROG_LEG_COOKED.get(),0.35F,100,"frog");
        oreCamping(recipeOutput,List.of(ItemRegistry.FROG_LEG.get()),RecipeCategory.FOOD,ItemRegistry.FROG_LEG_COOKED.get(),0.35F,400,"frog");
        makeCompressionRecipe(recipeOutput,ItemRegistry.NUGGET_CHICKEN.get(), ItemRegistry.INGOT_CHICKEN.get());

        //BLOCKS
        makeBrickWorksRecipes(recipeOutput,ItemRegistry.UNFIRED_SANDY_BRICK.get(), ItemRegistry.SANDY_BRICK.get(),
                ItemRegistry.SANDY_BRICKS.get(),ItemRegistry.SANDY_SLAB.get(),ItemRegistry.SANDY_STAIRS.get(),ItemRegistry.SANDY_WALL.get(),"sandy_bricks");
        makeStoneWorksRecipes(recipeOutput,ItemRegistry.BASALT.get(), null,ItemRegistry.BASALT_POLISHED.get(),ItemRegistry.BASALT_BRICKS.get(),
                ItemRegistry.BASALT_SLAB.get(),ItemRegistry.BASALT_STAIRS.get(),ItemRegistry.BASALT_WALL.get(),ItemRegistry.BASALT_PILLAR.get(),"basalt");
        makeStoneWorksRecipes(recipeOutput,ItemRegistry.MARBLE.get(), null,ItemRegistry.MARBLE_POLISHED.get(),ItemRegistry.MARBLE_BRICKS.get(),ItemRegistry.MARBLE_SLAB.get(),
                ItemRegistry.MARBLE_STAIRS.get(),ItemRegistry.MARBLE_WALL.get(),ItemRegistry.MARBLE_PILLAR.get(),"marble");
        oreSmelting(recipeOutput,List.of(ItemRegistry.FLUX.get()),RecipeCategory.MISC,ItemRegistry.QUICKLIME.get(),0.35F,200,"quicklime");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.UNFIRED_HIGH_REFRACTORY_BRICK.get(),4)
                .pattern("FTF")
                .pattern("CMC")
                .pattern("FTF")
                .define('F', ItemRegistry.CINDER_FLOUR.get())
                .define('T', ItemRegistry.MORTAR_OF_TARTAR.get())
                .define('C', Items.CLAY_BALL)
                .define('M', Items.MAGMA_CREAM)
                .unlockedBy("",has(Items.MAGMA_CREAM))
                .save(recipeOutput);
        oreSmelting(recipeOutput,List.of(ItemRegistry.UNFIRED_HIGH_REFRACTORY_BRICK.get()),RecipeCategory.MISC,ItemRegistry.HIGH_REFRACTORY_BRICK.get(),0.35F,200,"fire_brick");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.HIGH_REFRACTORY_BRICKS.get())
                .requires(ItemRegistry.HIGH_REFRACTORY_BRICK.get())
                .requires(ItemRegistry.HIGH_REFRACTORY_BRICK.get())
                .requires(ItemRegistry.HIGH_REFRACTORY_BRICK.get())
                .requires(ItemRegistry.HIGH_REFRACTORY_BRICK.get())
                .unlockedBy("",has(ItemRegistry.HIGH_REFRACTORY_BRICK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.CHARCOAL_BLOCK.get())
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .requires(Items.CHARCOAL)
                .unlockedBy("",has(Items.CHARCOAL))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.CHARCOAL, 9)
                .requires(ItemRegistry.CHARCOAL_BLOCK)
                .unlockedBy("",has(Items.CHARCOAL))
                .save(recipeOutput,"charcoal_pit:charcoal");
        makeCompressionRecipe(recipeOutput,ItemRegistry.COKE.get(),ItemRegistry.COKE_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.BRONZE.get(),ItemRegistry.BRONZE_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.STEEL.get(),ItemRegistry.STEEL_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.TIN.get(),ItemRegistry.TIN_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.ALUMINIUM.get(),ItemRegistry.ALUMINIUM_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.ALUMITE.get(),ItemRegistry.ALUMITE_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.PLATINUM.get(),ItemRegistry.PLATINUM_BLOCK.get());
        makeCompressionRecipe(recipeOutput,ItemRegistry.ENDERIUM.get(),ItemRegistry.ENDERIUM_BLOCK.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.STEEL)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .requires(ItemRegistry.STEEL_NUGGET)
                .unlockedBy("",has(ItemRegistry.STEEL_NUGGET))
                .save(recipeOutput,"charcoal_pit:nugget_compression");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.STEEL_NUGGET,9)
                .requires(ItemRegistry.STEEL)
                .unlockedBy("",has(ItemRegistry.STEEL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.ENDERIUM_DUST, 4)
                .requires(ItemRegistry.TIN_DUST)
                .requires(ItemRegistry.TIN_DUST)
                .requires(ItemRegistry.TIN_DUST)
                .requires(ItemRegistry.PLATINUM_DUST)
                .requires(ItemRegistry.RESONANT_BOTTLE)
                .requires(ItemRegistry.RESONANT_BOTTLE)
                .requires(ItemRegistry.RESONANT_BOTTLE)
                .requires(ItemRegistry.RESONANT_BOTTLE)
                .unlockedBy("",has(ItemRegistry.PLATINUM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.TORCH,8)
                .pattern("C")
                .pattern("S")
                .define('C', ItemRegistry.COKE.get())
                .define('S', Items.STICK)
                .unlockedBy("",has(ItemRegistry.COKE.get()))
                .save(recipeOutput,"charcoal_pit:coke_torch");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.SOUL_TORCH,8)
                .pattern("C")
                .pattern("A")
                .pattern("S")
                .define('C', ItemRegistry.COKE.get())
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .define('A', Items.STICK)
                .unlockedBy("",has(ItemRegistry.COKE.get()))
                .save(recipeOutput,"charcoal_pit:coke_soul_torch");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LOG_PILE.get())
                .pattern("L ")
                .pattern("LL")
                .pattern(" L")
                .define('L', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("",has(ItemTags.LOGS_THAT_BURN))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LOG_PILE.get())
                .pattern("LL ")
                .pattern(" LL")
                .define('L', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("",has(ItemTags.LOGS_THAT_BURN))
                .save(recipeOutput,"charcoal_pit:log_pile_2");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.LOG_PILE)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .requires(ItemTags.BAMBOO_BLOCKS)
                .unlockedBy("",has(ItemTags.BAMBOO_BLOCKS))
                .save(recipeOutput, "charcoal_pit:log_pile_3");
        makeCompressionRecipe(recipeOutput,ItemRegistry.ASH.get(),ItemRegistry.ASH_BLOCK.get());
        oreCamping(recipeOutput,List.of(Items.STICK),RecipeCategory.TOOLS, Items.TORCH,0.0F,200,"torch");
        //WOOD TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.CLUB.get())
                .pattern("I")
                .pattern("S")
                .define('I', ItemTags.LOGS)
                .define('S',Items.STICK)
                .unlockedBy("",has(ItemTags.LOGS))
                .save(recipeOutput);
        //BONE TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_PICKAXE)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', Items.BONE)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.BONE))
                .save(recipeOutput,"charcoal_pit:bone_pick");
        //FLINT TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput,"charcoal_pit:flint_pick");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.STONE_SHOVEL)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput,"charcoal_pit:flint_shovel");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.STONE_AXE)
                .pattern("II")
                .pattern("IS")
                .pattern(" S")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput,"charcoal_pit:flint_axe");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.STONE_SWORD)
                .pattern("I")
                .pattern("I")
                .pattern("S")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput,"charcoal_pit:flint_sword");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.STONE_HOE)
                .pattern("II")
                .pattern(" S")
                .pattern(" S")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput,"charcoal_pit:flint_hoe");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.JAVELIN.get(),2)
                .pattern("  I")
                .pattern(" S ")
                .pattern("S  ")
                .define('I', Items.FLINT)
                .define('S',Items.STICK)
                .unlockedBy("",has(Items.FLINT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.EXPLOSIVE_SPEAR.get())
                .pattern("  I")
                .pattern(" SR")
                .pattern("S  ")
                .define('I', ItemRegistry.DWARVEN_CANDLE.get())
                .define('S',Items.STICK)
                .define('R',Items.REDSTONE)
                .unlockedBy("",has(ItemRegistry.DWARVEN_CANDLE.get()))
                .save(recipeOutput);
        //COPPER TOOLS
        makeToolAndArmorRecipes(recipeOutput, Tags.Items.INGOTS_COPPER,ItemRegistry.COPPER_PICK.get(),ItemRegistry.COPPER_SHOVEL.get(),ItemRegistry.COPPER_AXE.get(),ItemRegistry.COPPER_SWORD.get(),ItemRegistry.COPPER_HOE.get(),null,
                ItemRegistry.COPPER_HELMET.get(),ItemRegistry.COPPER_CHESTPLATE.get(),ItemRegistry.COPPER_LEGGINGS.get(),ItemRegistry.COPPER_BOOTS.get());
        //BRONZE TOOLS
        makeToolAndArmorRecipes(recipeOutput, BRONZE,ItemRegistry.BRONZE_PICK.get(),ItemRegistry.BRONZE_SHOVEL.get(),ItemRegistry.BRONZE_AXE.get(),ItemRegistry.BRONZE_SWORD.get(),ItemRegistry.BRONZE_HOE.get(),ItemRegistry.BRONZE_KNIFE.get(),
                ItemRegistry.BRONZE_HELMET.get(),ItemRegistry.BRONZE_CHESTPLATE.get(),ItemRegistry.BRONZE_LEGGINGS.get(),ItemRegistry.BRONZE_BOOTS.get());
        makeBronzeAlternatives(recipeOutput);
        //STEEL TOOLS
        makeToolAndArmorRecipes(recipeOutput, STEEL,ItemRegistry.STEEL_PICK.get(),ItemRegistry.STEEL_SHOVEL.get(),ItemRegistry.STEEL_AXE.get(),ItemRegistry.STEEL_SWORD.get(),ItemRegistry.STEEL_HOE.get(),ItemRegistry.STEEL_KNIFE.get(),
                ItemRegistry.STEEL_HELMET.get(),ItemRegistry.STEEL_CHESTPLATE.get(),ItemRegistry.STEEL_LEGGINGS.get(),ItemRegistry.STEEL_BOOTS.get());
        makeSteelAlternatives(recipeOutput);
        //MUSKET
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.MUSKET)
                .pattern("I  ")
                .pattern("KIF")
                .pattern(" WI")
                .define('I', STEEL)
                .define('F',Items.FLINT_AND_STEEL)
                .define('W',ItemTags.PLANKS)
                .define('K',ItemRegistry.STEEL_KNIFE.get())
                .unlockedBy("",has(STEEL))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.SMALL_GUNPOWDER.get(),4)
                .requires(Items.GUNPOWDER)
                .unlockedBy("",has(Items.GUNPOWDER))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.GUNPOWDER)
                .requires(ItemRegistry.SMALL_GUNPOWDER.get())
                .requires(ItemRegistry.SMALL_GUNPOWDER.get())
                .requires(ItemRegistry.SMALL_GUNPOWDER.get())
                .requires(ItemRegistry.SMALL_GUNPOWDER.get())
                .unlockedBy("",has(Items.GUNPOWDER))
                .save(recipeOutput,"charcoal_pit:repack_gunpowder");
        //ALUMITE TOOLS
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ALUMINIUM_KNIFE)
                .pattern("I")
                .pattern("S")
                .define('I', ItemRegistry.ALUMINIUM)
                .define('S', Items.STICK)
                .unlockedBy("", has(ItemRegistry.ALUMINIUM))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PLATINUM_KNIFE)
                .pattern("I")
                .pattern("S")
                .define('I', ItemRegistry.PLATINUM)
                .define('S', Items.STICK)
                .unlockedBy("", has(ItemRegistry.PLATINUM))
                .save(recipeOutput);
        makeToolAndArmorRecipes(recipeOutput, ALUMITE, ItemRegistry.ALUMITE_PICK.get(),ItemRegistry.ALUMITE_SHOVEL.get(),ItemRegistry.ALUMITE_AXE.get(),ItemRegistry.ALUMITE_SWORD.get(),ItemRegistry.ALUMITE_HOE.get(),null,
                ItemRegistry.ALUMITE_HELMET.get(),ItemRegistry.ALUMITE_CHESTPLATE.get(),ItemRegistry.ALUMITE_LEGGINGS.get(),ItemRegistry.ALUMITE_BOOTS.get());
    }

    public void makeBronzeAlternatives(RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.BUCKET)
                .pattern("I I")
                .pattern(" I ")
                .define('I', BRONZE)
                .unlockedBy("",has(BRONZE))
                .save(output,"charcoal_pit:bronze_bucket");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.SHEARS)
                .pattern(" I")
                .pattern("I ")
                .define('I', BRONZE)
                .unlockedBy("",has(BRONZE))
                .save(output,"charcoal_pit:bronze_shears");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.SHIELD)
                .pattern("WIW")
                .pattern("WWW")
                .pattern(" W ")
                .define('I', BRONZE)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("",has(BRONZE))
                .save(output,"charcoal_pit:bronze_shield");
    }
    public void makeSteelAlternatives(RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.ACTIVATOR_RAIL, 12)
                .pattern("IRI")
                .pattern("ISI")
                .pattern("IRI")
                .define('I', STEEL)
                .define('R', Items.REDSTONE)
                .define('S', Items.STICK)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_activator");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.BUCKET, 2)
                .pattern("I I")
                .pattern(" I ")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_bucket");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CAULDRON, 2)
                .pattern("I I")
                .pattern("I I")
                .pattern("III")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_cauldron");
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.DETECTOR_RAIL, 12)
                .pattern("I I")
                .pattern("ISI")
                .pattern("IRI")
                .define('I', STEEL)
                .define('R', Items.REDSTONE)
                .define('S', Items.STONE_PRESSURE_PLATE)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_detector");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.HOPPER, 2)
                .pattern("I I")
                .pattern("ICI")
                .pattern(" I ")
                .define('I', STEEL)
                .define('C', Tags.Items.CHESTS)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_hopper");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_BARS, 32)
                .pattern("III")
                .pattern("III")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_bars");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_DOOR, 6)
                .pattern("II")
                .pattern("II")
                .pattern("II")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_door");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_TRAPDOOR, 2)
                .pattern("II")
                .pattern("II")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_trap");
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.MINECART)
                .pattern("I I")
                .pattern("III")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_cart");
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.RAIL, 32)
                .pattern("I I")
                .pattern("ICI")
                .pattern("I I")
                .define('I', STEEL)
                .define('C', Items.STICK)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_rails");
        ItemStack ore_and_flint=new ItemStack(Items.FLINT_AND_STEEL);
        ore_and_flint.set(DataComponents.MAX_DAMAGE,6);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS,ore_and_flint)
                .requires(Items.RAW_IRON)
                .requires(Items.FLINT)
                .unlockedBy("",has(Items.RAW_IRON))
                .save(output,"charcoal_pit:ore_and_flint");
        ItemStack steel_and_flint=new ItemStack(Items.FLINT_AND_STEEL);
        steel_and_flint.set(DataComponents.MAX_DAMAGE, ToolTiers.STEEL.getUses());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS,steel_and_flint)
                .requires(STEEL)
                .requires(Items.FLINT)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_and_flint");
        ItemStack steel_shears=new ItemStack(Items.SHEARS);
        steel_shears.set(DataComponents.MAX_DAMAGE,ToolTiers.STEEL.getUses());
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, steel_shears)
                .pattern(" I")
                .pattern("I ")
                .define('I', STEEL)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_shears");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.SHIELD)
                .pattern("WIW")
                .pattern("WWW")
                .pattern(" W ")
                .define('I', STEEL)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_shield");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.ANVIL,2)
                .pattern("BBB")
                .pattern(" S ")
                .pattern("SSS")
                .define('S', STEEL)
                .define('B', STEEL_BLOCK)
                .unlockedBy("",has(STEEL))
                .save(output,"charcoal_pit:steel_anvil");
    }


    public void makeToolRecipes(RecipeOutput output, TagKey<Item> material,Item pick,Item shovel,Item axe,Item sword,Item hoe,Item knife){
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pick)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', material)
                .define('S',Items.STICK)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .define('I', material)
                .define('S',Items.STICK)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("II")
                .pattern("IS")
                .pattern(" S")
                .define('I', material)
                .define('S',Items.STICK)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, sword)
                .pattern("I")
                .pattern("I")
                .pattern("S")
                .define('I', material)
                .define('S',Items.STICK)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("II")
                .pattern(" S")
                .pattern(" S")
                .define('I', material)
                .define('S',Items.STICK)
                .unlockedBy("",has(material))
                .save(output);
        if(knife!=null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, knife)
                    .pattern("I")
                    .pattern("S")
                    .define('I', material)
                    .define('S', Items.STICK)
                    .unlockedBy("", has(material))
                    .save(output);
        }
    }

    public void makeToolAndArmorRecipes(RecipeOutput output, TagKey<Item> material,Item pick,Item shovel,Item axe,Item sword,Item hoe,Item knife,Item helmet,Item chestplate,Item leggings,Item boots){
        makeToolRecipes(output,material,pick,shovel,axe,sword,hoe,knife);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("III")
                .pattern("I I")
                .define('I', material)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("I I")
                .pattern("III")
                .pattern("III")
                .define('I', material)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("III")
                .pattern("I I")
                .pattern("I I")
                .define('I', material)
                .unlockedBy("",has(material))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("I I")
                .pattern("I I")
                .define('I', material)
                .unlockedBy("",has(material))
                .save(output);
    }

    public void makeCompressionRecipe(RecipeOutput output,Item material,Item block){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,block)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .requires(material)
                .unlockedBy("",has(material))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,material,9)
                .requires(block)
                .unlockedBy("",has(material))
                .save(output);
    }

    public void makeBrickWorksRecipes(RecipeOutput output,Item raw, Item fired, Item bricks, Item slab, Item stair,Item wall,String group){
        if(raw!=null){
            oreSmelting(output,List.of(raw),RecipeCategory.BUILDING_BLOCKS,fired,0.3F,200,group);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks)
                .pattern("BB")
                .pattern("BB")
                .define('B', fired)
                .unlockedBy("",has(fired))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair,4)
                .pattern("  B")
                .pattern(" BB")
                .pattern("BBB")
                .define('B', bricks)
                .unlockedBy("",has(fired))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab,6)
                .pattern("BBB")
                .define('B', bricks)
                .unlockedBy("",has(fired))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall,6)
                .pattern("BBB")
                .pattern("BBB")
                .define('B', bricks)
                .unlockedBy("",has(fired))
                .save(output);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,slab,bricks,2);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,stair,bricks);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,wall,bricks);
    }

    public void makeStoneWorksRecipes(RecipeOutput output,Item raw, Item cobble, Item polished, Item bricks, Item slab, Item stair,Item wall,Item pillar,String group){
        if(cobble!=null){
            oreSmelting(output,List.of(cobble),RecipeCategory.BUILDING_BLOCKS,raw,0.1F,200,group);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, polished,4)
                .pattern("SS")
                .pattern("SS")
                .define('S', raw)
                .unlockedBy("",has(raw))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks,4)
                .pattern("SS")
                .pattern("SS")
                .define('S', polished)
                .unlockedBy("",has(raw))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab,6)
                .pattern("SSS")
                .define('S', bricks)
                .unlockedBy("",has(raw))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair,4)
                .pattern("  S")
                .pattern(" SS")
                .pattern("SSS")
                .define('S', bricks)
                .unlockedBy("",has(raw))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall,6)
                .pattern("SSS")
                .pattern("SSS")
                .define('S', bricks)
                .unlockedBy("",has(raw))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pillar,2)
                .pattern("S")
                .pattern("S")
                .define('S', polished)
                .unlockedBy("",has(raw))
                .save(output);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,polished,raw);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,bricks,raw);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,slab,raw,2);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,stair,raw);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,wall,raw);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,pillar,raw);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,bricks,polished);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,slab,polished,2);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,stair,polished);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,wall,polished);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,pillar,polished);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,slab,bricks,2);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,stair,bricks);
        stonecutterResultFromBase(output,RecipeCategory.BUILDING_BLOCKS,wall,bricks);
    }

    protected static void oreCamping(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_camping");
    }

    protected static void oreBaking(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_smoking");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, CharcoalPit.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
