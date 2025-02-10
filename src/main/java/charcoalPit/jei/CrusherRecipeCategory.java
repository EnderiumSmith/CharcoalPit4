package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.recipe.BarrelRecipe;
import charcoalPit.recipe.CrusherRecipe;
import charcoalPit.recipe.StillRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public class CrusherRecipeCategory implements IRecipeCategory<RecipeHolder<CrusherRecipe>> {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"press");
    private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/press_recipe.png");
    public final IGuiHelper guiHelper;
    public final Component title;
    public IDrawableStatic background;
    public IDrawable icon,tankOverlay,tankOverlay2;
    public IDrawableAnimated flame;

    public CrusherRecipeCategory(IGuiHelper helper){
        guiHelper=helper;
        title=Component.translatable("charcoal_pit.jei.press");
        icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.PRESS.get()));
        background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,175,85);
        tankOverlay=guiHelper.createDrawable(BARREL_GUI_TEXTURES,176,60,32,16);
        tankOverlay2=guiHelper.createDrawable(BARREL_GUI_TEXTURES,176,60,16,29);
        flame=guiHelper.drawableBuilder(BARREL_GUI_TEXTURES,176,0,14,14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP,true);
    }

    static final RecipeType<RecipeHolder<CrusherRecipe>> CRUHSER=RecipeType.createFromVanilla(RecipeRegistry.CRUSHER_RECIPE.get());

    @Override
    public RecipeType<RecipeHolder<CrusherRecipe>> getRecipeType() {
        return CRUHSER;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrusherRecipe> recipe, IFocusGroup focuses) {
        CrusherRecipe recipe1=recipe.value();
        builder.addSlot(RecipeIngredientRole.INPUT,65,35).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.itemIn.getItems()).toList());
        builder.addSlot(RecipeIngredientRole.INPUT,109,11).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(Fluids.WATER,2))).setFluidRenderer(4,false,16,29).setOverlay(tankOverlay2,0,0);
        builder.addSlot(RecipeIngredientRole.OUTPUT,57,53).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe1.fluidOut.getStacks()[0].getFluid(),recipe1.amountOut))).setFluidRenderer(Math.min(16000,recipe1.amountOut*2),false,32,16).setOverlay(tankOverlay,0,0);
    }

    @Override
    public void draw(RecipeHolder<CrusherRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        flame.draw(guiGraphics,91,36);
    }

    @Override
    public boolean needsRecipeBorder() {
        return false;
    }
}
