package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.gui.screen.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

@JeiPlugin
public class JEICompat implements IModPlugin {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"main");
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BloomeryRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HotBlastingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BarrelRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new StillRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new QuernRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CokingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BLOOM.get()),BloomeryRecipeCategory.BLOOMERY);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BLOOMERY_SANDY.get()),BloomeryRecipeCategory.BLOOMERY);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BLOOMERY_BRICK.get()),BloomeryRecipeCategory.BLOOMERY);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BLOOMERY_NETHER.get()),BloomeryRecipeCategory.BLOOMERY);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BLAST_FURNACE.get()),HotBlastingRecipeCategory.HOT_BLASTING);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.BARREL.get()),BarrelRecipeCategory.BARREL);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.PRESS.get()),CrusherRecipeCategory.CRUHSER);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.STILL.get()),StillRecipeCategory.STILL);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.QUERN.get()),QuernRecipeCategory.QUERN);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.COKE_OVEN.get()),CokingRecipeCategory.COKING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(BloomeryRecipeCategory.BLOOMERY, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.BLOOMERY.get()));
        registration.addRecipes(HotBlastingRecipeCategory.HOT_BLASTING, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.HOT_BLAST_FURNACE.get()));
        registration.addRecipes(BarrelRecipeCategory.BARREL,Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.BARREL_RECIPE.get()));
        registration.addRecipes(CrusherRecipeCategory.CRUHSER,Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.CRUSHER_RECIPE.get()));
        registration.addRecipes(StillRecipeCategory.STILL,Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.STILL_RECIPE.get()));
        registration.addRecipes(QuernRecipeCategory.QUERN,Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.QUERN_RECIPE.get()));
        registration.addRecipes(CokingRecipeCategory.COKING,Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeRegistry.COKING_RECIPE.get()));

        registration.addIngredientInfo(List.of(new ItemStack(ItemRegistry.LOG_PILE.get()),new ItemStack(Items.CHARCOAL),new ItemStack(Items.COAL_BLOCK),new ItemStack(ItemRegistry.COKE.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("charcoal_pit.instruction.build_pit"));
        registration.addIngredientInfo(List.of(new ItemStack(ItemRegistry.BLOOMERY_BRICK.get()),new ItemStack(ItemRegistry.BLOOMERY_SANDY.get()),new ItemStack(ItemRegistry.BLOOMERY_NETHER.get()),new ItemStack(ItemRegistry.BLAST_FURNACE.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("charcoal_pit.instruction.bellows"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.DYNAMITE_REMOTE.get()),VanillaTypes.ITEM_STACK,Component.translatable("charcoal_pit.jei.remote"));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BloomeryScreen.class,79,34,24,16,BloomeryRecipeCategory.BLOOMERY);
        registration.addRecipeClickArea(BlastFurnaceScreen.class,76,34,24,16,HotBlastingRecipeCategory.HOT_BLASTING);
        registration.addRecipeClickArea(BarrelScreen.class,98,35,16,16,BarrelRecipeCategory.BARREL);
        registration.addRecipeClickArea(PressScreen.class,65,19,16,12,CrusherRecipeCategory.CRUHSER);
        registration.addRecipeClickArea(StillScreen.class,124,22,9,29,StillRecipeCategory.STILL);
        registration.addRecipeClickArea(CokeOvenScreen.class,71,23,14,14,CokingRecipeCategory.COKING);
    }
}
