package charcoalPit.core;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ToolTiers {

    public static final Tier BONE=new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL,59,2.0F,0.0F,15,()-> Ingredient.of(Items.BONE));
    public static final Tier FLINT=new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,131,4.0F,1.0F,5,()-> Ingredient.of(Items.FLINT));
    public static final Tier COPPER=new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,165,4.5F,1.25F,16,()-> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier BRONZE=new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL,250,6.0F,2.0F,15,()-> Ingredient.of(ItemRegistry.BRONZE.get()));
    public static final Tier STEEL=new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL,500,7.0F,3F,7,()-> Ingredient.of(ItemRegistry.STEEL.get()));
    public static final Tier ALUMINIUM=new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,145,4.0F,1.25F,5,()-> Ingredient.of(ItemRegistry.ALUMINIUM.get()));
    public static final Tier ALUMITE=new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,909,7F,2F,1,()-> Ingredient.of(ItemRegistry.ALUMITE.get()));
    public static final Tier ENDERIUM=new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,1751,12F,5F,15,()-> Ingredient.of(ItemRegistry.ENDERIUM.get()));
    public static final Tier PLATINUM=new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL,1400,14F,1F,20,()-> Ingredient.of(ItemRegistry.PLATINUM.get()));
    public static final Tier SOUL_DRINKER=new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,780,8F,2F,15,()-> Ingredient.of(Items.ECHO_SHARD));
    /*
    COPPER  16
    SILVER  18
    GOLD    22
    TIN     7
    ALUMITE                                             909     6.5     2.25    1
    ENDERIUM                                            1751    10      4.5     15

    WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL,           59,     2.0F,   0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE(BlockTags.INCORRECT_FOR_STONE_TOOL,           131,    4.0F,   1.0F, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(BlockTags.INCORRECT_FOR_IRON_TOOL,             250,    6.0F,   2.0F, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,       1561,   8.0F,   3.0F, 10, () -> Ingredient.of(Items.DIAMOND)),
    GOLD(BlockTags.INCORRECT_FOR_GOLD_TOOL,             32,     12.0F,  0.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,   2031,   9.0F,   4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));

    LEATHER 5
    IRON 15
    STEEL 22
    GOLD 7
    ALUMITE 27
    DIAMOND 33
    NETHERITE 37
     */

}
