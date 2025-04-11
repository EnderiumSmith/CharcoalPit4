package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.recipe.BarrelRecipe;
import charcoalPit.recipe.StillRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public class StillRecipeCategory implements IRecipeCategory<RecipeHolder<StillRecipe>> {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"still");
    private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/still_recipe.png");
    public final IGuiHelper guiHelper;
    public final Component title;
    public IDrawableStatic background;
    public IDrawable icon,tankOverlay,tankOverlay2;
    public IDrawableAnimated flame;

    public StillRecipeCategory(IGuiHelper helper){
        guiHelper=helper;
        title=Component.translatable("charcoal_pit.jei.still");
        icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.STILL.get()));
        background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,175,85);
        tankOverlay=guiHelper.createDrawable(BARREL_GUI_TEXTURES,176,60,16,29);
        tankOverlay2=guiHelper.createDrawable(BARREL_GUI_TEXTURES,206,1,24,29);
        flame=guiHelper.drawableBuilder(BARREL_GUI_TEXTURES,176,0,14,14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP,true);
    }

    static final RecipeType<RecipeHolder<StillRecipe>> STILL=RecipeType.createFromVanilla(RecipeRegistry.STILL_RECIPE.get());

    @Override
    public RecipeType<RecipeHolder<StillRecipe>> getRecipeType() {
        return STILL;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<StillRecipe> recipe, IFocusGroup focuses) {
        StillRecipe recipe1=recipe.value();
        if(!recipe1.itemIn.isEmpty())
            builder.addSlot(RecipeIngredientRole.INPUT,53,17).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.itemIn.getItems()).map(i->new ItemStack(i.getItem(),recipe1.itemAmount)).toList());
        if(!recipe1.catalyst.isEmpty())
            builder.addSlot(RecipeIngredientRole.INPUT,53,35).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.catalyst.getItems()).toList()).addTooltipCallback(new IRecipeSlotTooltipCallback() {
                @Override
                public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {

                }

                @Override
                public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
                    tooltip.add(Component.literal("Use Chance:"+String.format("%."+0+"f",recipe1.useChance*100)+"%").withStyle(ChatFormatting.GRAY));
                }
            });
        builder.addSlot(RecipeIngredientRole.INPUT,76,22).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe1.fluidIn.getStacks()[0].getFluid(),recipe1.amountIn))).setFluidRenderer(Math.min(16000,recipe1.amountIn*2),false,24,29).setOverlay(tankOverlay2,0,0);
        if(!recipe1.itemOut.isEmpty())
            builder.addSlot(RecipeIngredientRole.OUTPUT,107,53).addIngredients(VanillaTypes.ITEM_STACK, List.of(new ItemStack(recipe1.itemOut.getItems()[0].getItem(),recipe1.itemAmountOut)));
        if(!recipe1.fluidOut.isEmpty()){
            builder.addSlot(RecipeIngredientRole.OUTPUT,107,22).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe1.fluidOut.getStacks()[0].getFluid(),recipe1.amountOut))).setFluidRenderer(Math.min(16000,recipe1.amountOut*2),false,16,29).setOverlay(tankOverlay,0,0);
        }
    }

    @Override
    public void draw(RecipeHolder<StillRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        flame.draw(guiGraphics,80,54);
    }

    @Override
    public boolean needsRecipeBorder() {
        return false;
    }
}
