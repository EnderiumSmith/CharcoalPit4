package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.recipe.BarrelRecipe;
import charcoalPit.recipe.BloomeryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public class BarrelRecipeCategory implements IRecipeCategory<RecipeHolder<BarrelRecipe>> {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"barrel");
    private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/barrel_recipe.png");
    public final IGuiHelper guiHelper;
    public final Component title;
    public IDrawableStatic background;
    public IDrawable icon,tankOverlay;

    public BarrelRecipeCategory(IGuiHelper helper){
        guiHelper=helper;
        title=Component.translatable("charcoal_pit.jei.barrel");
        icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.BARREL.get()));
        background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,175,85);
        tankOverlay=guiHelper.createDrawable(BARREL_GUI_TEXTURES,176,0,16,58);
    }

    static final RecipeType<RecipeHolder<BarrelRecipe>> BARREL=RecipeType.createFromVanilla(RecipeRegistry.BARREL_RECIPE.get());

    @Override
    public RecipeType<RecipeHolder<BarrelRecipe>> getRecipeType() {
        return BARREL;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BarrelRecipe> recipe, IFocusGroup focuses) {
        BarrelRecipe recipe1=recipe.value();
        builder.addSlot(RecipeIngredientRole.INPUT,80,17).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.itemIn.getItems()).map(i->new ItemStack(i.getItem(),recipe1.itemAmount)).toList());
        builder.addSlot(RecipeIngredientRole.INPUT,44,14).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe1.fluidIn.getStacks()[0].getFluid(),recipe1.amountIn))).setFluidRenderer(Math.min(16000,recipe1.amountIn*2),false,16,58).setOverlay(tankOverlay,0,0);
        if(!recipe1.itemOut.isEmpty())
            builder.addSlot(RecipeIngredientRole.OUTPUT,80,53).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe1.itemOut.copy()));
        if(!recipe1.fluidOut.isEmpty()){
            builder.addSlot(RecipeIngredientRole.OUTPUT,116,14).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe1.fluidOut.getStacks()[0].getFluid(),recipe1.amountOut))).setFluidRenderer(Math.min(16000,recipe1.amountOut*2),false,16,58).setOverlay(tankOverlay,0,0);
        }
    }

    @Override
    public boolean needsRecipeBorder() {
        return false;
    }
}
