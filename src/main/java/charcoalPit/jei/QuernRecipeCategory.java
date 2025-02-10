package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.recipe.QuernRecipe;
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
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public class QuernRecipeCategory implements IRecipeCategory<RecipeHolder<QuernRecipe>> {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"quern");
    private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/quern_recipe.png");
    public final IGuiHelper guiHelper;
    public final Component title;
    public IDrawableStatic background;
    public IDrawable icon;

    public QuernRecipeCategory(IGuiHelper helper){
        guiHelper=helper;
        title=Component.translatable("charcoal_pit.jei.quern");
        icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.QUERN.get()));
        background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,82,34);
    }

    static final RecipeType<RecipeHolder<QuernRecipe>> QUERN=RecipeType.createFromVanilla(RecipeRegistry.QUERN_RECIPE.get());

    @Override
    public RecipeType<RecipeHolder<QuernRecipe>> getRecipeType() {
        return QUERN;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<QuernRecipe> recipe, IFocusGroup focuses) {
        QuernRecipe recipe1=recipe.value();
        builder.addSlot(RecipeIngredientRole.INPUT,1,9).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.getIngredient().getItems()).toList());
        builder.addSlot(RecipeIngredientRole.OUTPUT,61,9).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe1.getResult().copy()));
    }

}
