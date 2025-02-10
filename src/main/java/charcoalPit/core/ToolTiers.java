package charcoalPit.core;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ToolTiers {

    public static final Tier BONE=new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL,59,2.0F,0.0F,15,()-> Ingredient.of(Items.BONE));
    public static final Tier FLINT=new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,131,4.0F,1.0F,5,()-> Ingredient.of(Items.FLINT));
    public static final Tier COPPER=new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,165,4.5F,1.25F,15,()-> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier BRONZE=new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL,250,6.0F,2.0F,13,()-> Ingredient.of(ItemRegistry.BRONZE.get()));
    public static final Tier STEEL=new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL,375,7.0F,2.5F,7,()-> Ingredient.of(ItemRegistry.STEEL.get()));
    /*
    ALUMITE                                             909     6.5     2.25    1
    ENDERIUM                                            1751    10      4.5     15

    WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL,           59,     2.0F,   0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE(BlockTags.INCORRECT_FOR_STONE_TOOL,           131,    4.0F,   1.0F, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(BlockTags.INCORRECT_FOR_IRON_TOOL,             250,    6.0F,   2.0F, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,       1561,   8.0F,   3.0F, 10, () -> Ingredient.of(Items.DIAMOND)),
    GOLD(BlockTags.INCORRECT_FOR_GOLD_TOOL,             32,     12.0F,  0.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,   2031,   9.0F,   4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));
     */

}
