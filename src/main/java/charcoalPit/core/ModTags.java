package charcoalPit.core;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {

    public static final ResourceKey<Enchantment> SILVER_BULLET=ResourceKey.create(Registries.ENCHANTMENT,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"silver_bullet"));

    public static final ResourceKey<DamageType> MUSKET=ResourceKey.create(Registries.DAMAGE_TYPE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"musket"));
    public static final ResourceKey<DamageType> MUSKET_SILVER=ResourceKey.create(Registries.DAMAGE_TYPE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"musket_silver"));

    public static final TagKey<Block> REFRACTORY_BLOCKS=TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"refractory_blocks"));
    public static final TagKey<Block> TIN_ORE=TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c","ores/tin"));
    public static final TagKey<Block> KNIFE_BLOCKS=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("farmersdelight","mineable/knife"));
    public static final TagKey<Item> KNIFES=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("farmersdelight","tools/knives"));
    public static final TagKey<Block> PLATINUM_ORE=TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c","ores/platinum"));

    public static final TagKey<Item> TIN=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/tin"));
    public static final TagKey<Item> BRONZE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/bronze"));
    public static final TagKey<Item> PIG_IRON=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/pigiron"));
    public static final TagKey<Item> STEEL=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/steel"));
    public static final TagKey<Item> RED_ALLOY=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/red_alloy"));
    public static final TagKey<Item> SODIUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/sodium"));
    public static final TagKey<Item> ALUMINIUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/aluminium"));
    public static final TagKey<Item> ALUMINUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/aluminum"));
    public static final TagKey<Item> ALUMITE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/alumite"));
    public static final TagKey<Item> PLATINUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/platinum"));
    public static final TagKey<Item> ENDERIUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ingots/enderium"));

    public static final TagKey<Item> COKE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","coal_coke"));
    public static final TagKey<Item> COKE_DUST=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/coke"));

    public static final TagKey<Item> RAW_TIN=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","raw_materials/tin"));
    public static final TagKey<Item> ORE_TIN=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ores/tin"));
    public static final TagKey<Item> ASH=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/ash"));
    public static final TagKey<Item> SALT=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/salt"));
    public static final TagKey<Item> SALT_CAKE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/sodium_sulfate"));
    public static final TagKey<Item> NATRON=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/sodium_carbonate"));
    public static final TagKey<Item> LYE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/sodium_hydroxide"));
    public static final TagKey<Item> ALUMINA=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/alumina"));
    public static final TagKey<Item> RAW_PLATINUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","raw_materials/platinum"));
    public static final TagKey<Item> ORE_PLATINUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","ores/platinum"));

    public static final TagKey<Item> DUST_PLATINUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/platinum"));
    public static final TagKey<Item> DUST_TIN=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/tin"));
    public static final TagKey<Item> DUST_ENDERIUM=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/enderium"));
    public static final TagKey<Item> DUST_SULFUR=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/sulfur"));
    public static final TagKey<Item> DUST_NITER=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/niter"));
    public static final TagKey<Item> CALCIUM_CARBIDE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/calcium_carbide"));

    public static final TagKey<Item> CALCAREOUS_ROCKS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"calcareous_rocks"));
    public static final TagKey<Item> CALCAREOUS_ITEMS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"calcareous_items"));
    public static final TagKey<Item> ALCOHOL_ITEMS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"alcohol_items"));

    public static final TagKey<Item> TEMPERATURE_FUELS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"jei_fuels"));
    public static final TagKey<Item> TEMPERATURE_FUELS_CLEAN=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"jei_fuels_clean"));

    public static final TagKey<Item> CROPS_LEEK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","crops/leek"));
    public static final TagKey<Item> FLOUR=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","dusts/flour"));
    public static final TagKey<Item> RICE=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","grain/rice"));
    public static final TagKey<Item> SUNFLOWER_SEEDS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","crops/sunflower"));

    public static final TagKey<Item> BULLETS=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"bullets"));
    public static final TagKey<Item> GUNPOWDER=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"gunpowder"));

    public static final TagKey<Fluid> CREOSOTE_OIL=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","creosote"));
    public static final TagKey<Fluid> ETHANOL=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","ethanol"));
    public static final TagKey<Fluid> SEED_OIL=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","seed_oil"));
    public static final TagKey<Fluid> BIODIESEL=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","biodiesel"));
    public static final TagKey<Fluid> SULFURIC_ACID=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","sulfuric_acid"));
    public static final TagKey<Fluid> CHLORINE=TagKey.create(Registries.FLUID,ResourceLocation.fromNamespaceAndPath("c","chlorine"));

    public static final TagKey<Item> BRONZE_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/bronze"));
    public static final TagKey<Item> TIN_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/tin"));
    public static final TagKey<Item> STEEL_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/steel"));
    public static final TagKey<Item> CHARCOAL_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/charcoal"));
    public static final TagKey<Item> COKE_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/coke"));
    public static final TagKey<Item> ALUMINIUM_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/aluminium"));
    public static final TagKey<Item> ALUMITE_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/alumite"));
    public static final TagKey<Item> PLATINUM_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/platinum"));
    public static final TagKey<Item> ENDERIUM_BLOCK=TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/enderium"));

    public static final TagKey<Block> BRONZE_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/bronze"));
    public static final TagKey<Block> TIN_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/tin"));
    public static final TagKey<Block> STEEL_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/steel"));
    public static final TagKey<Block> CHARCOAL_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/charcoal"));
    public static final TagKey<Block> COKE_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/coke"));
    public static final TagKey<Block> ALUMINUM_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/aluminium"));
    public static final TagKey<Block> ALUMITE_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/alumite"));
    public static final TagKey<Block> PLATINUM_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/platinum"));
    public static final TagKey<Block> ENDERIUM_BLOCK_BLOCK=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("c","storage_blocks/enderium"));

}
