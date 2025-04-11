package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.recipe.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Supplier;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS=DeferredRegister.create(Registries.RECIPE_SERIALIZER, CharcoalPit.MODID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES=DeferredRegister.create(Registries.RECIPE_TYPE, CharcoalPit.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ALLOYING=SERIALIZERS.register("alloying",
            ()->new SimpleCookingSerializer<>(NBTSmeltingRecipe::new, 400));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ALLOY_MOLD=SERIALIZERS.register("alloy_mold",
            AlloyMoldRecipe.Serializer::new);

    public static final Supplier<RecipeSerializer<BloomeryRecipe>> BLOOMING=SERIALIZERS.register("blooming",
            BloomeryRecipe.BloomerySerializer::new);

    public static final Supplier<RecipeSerializer<BlastFurnaceRecipe>> HOT_BLASTING=SERIALIZERS.register("hot_blasting",
            BlastFurnaceRecipe.BlastFurnaceSerializer::new);

    public static final Supplier<SimpleCraftingRecipeSerializer<MusketReloadingRecipe>> MUSKET_RELOADING=SERIALIZERS.register("musket_loading",()->new SimpleCraftingRecipeSerializer<>(MusketReloadingRecipe::new));

    public static final Supplier<VatRecipe.Serializer> THE_VAT=SERIALIZERS.register("the_vat", VatRecipe.Serializer::new);

    public static final Supplier<BarrelRecipe.Serializer> BARREL=SERIALIZERS.register("barrel",BarrelRecipe.Serializer::new);

    public static final Supplier<StillRecipe.Serializer> STILL=SERIALIZERS.register("still",StillRecipe.Serializer::new);

    public static final Supplier<CrusherRecipe.Serializer> CRUSHER=SERIALIZERS.register("shatter",CrusherRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> QUERN=SERIALIZERS.register("quern",
            ()->new SimpleCookingSerializer<>(QuernRecipe::new, 40));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> COKING=SERIALIZERS.register("coking",
            ()->new SimpleCookingSerializer<>(CokingRecipe::new, 1000));


    //RECIPE TYPES
    public static final Supplier<RecipeType<BloomeryRecipe>> BLOOMERY=RECIPE_TYPES.register("bloomery",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"bloomery")));

    public static final Supplier<RecipeType<BlastFurnaceRecipe>> HOT_BLAST_FURNACE=RECIPE_TYPES.register("hot_blasting",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"hot_blasting")));

    public static final Supplier<RecipeType<VatRecipe>> THE_VAT_RECIPE=RECIPE_TYPES.register("the_vat",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"the_vat")));

    public static final Supplier<RecipeType<BarrelRecipe>> BARREL_RECIPE=RECIPE_TYPES.register("barrel",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"barrel")));

    public static final Supplier<RecipeType<StillRecipe>> STILL_RECIPE=RECIPE_TYPES.register("still",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"still")));

    public static final Supplier<RecipeType<CrusherRecipe>> CRUSHER_RECIPE=RECIPE_TYPES.register("shatter",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"shatter")));

    public static final Supplier<RecipeType<QuernRecipe>> QUERN_RECIPE=RECIPE_TYPES.register("quern",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"quern")));

    public static final Supplier<RecipeType<CokingRecipe>> COKING_RECIPE=RECIPE_TYPES.register("coking",
            ()->RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"coking")));

}
