package charcoalPit.jei;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.recipe.BloomeryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
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

import java.util.Arrays;

public class BloomeryRecipeCategory implements IRecipeCategory<RecipeHolder<BloomeryRecipe>> {

    public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"blooming");
    private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/bloomeryy_recipe.png");
    public final IGuiHelper guiHelper;
    public final Component title;
    public IDrawableStatic background;
    public IDrawable icon;
    public IDrawableAnimated flame,arrow;

    public BloomeryRecipeCategory(IGuiHelper helper){
        guiHelper=helper;
        title=Component.translatable("charcoal_pit.jei.blooming");
        icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.BLOOM.get()));
        background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,175,85);
        flame=guiHelper.drawableBuilder(BARREL_GUI_TEXTURES,176,0,14,14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP,true);
        arrow=guiHelper.drawableBuilder(BARREL_GUI_TEXTURES,176,14,25,16).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT,false);
    }

    static final RecipeType<RecipeHolder<BloomeryRecipe>> BLOOMERY=RecipeType.createFromVanilla(RecipeRegistry.BLOOMERY.get());

    @Override
    public RecipeType<RecipeHolder<BloomeryRecipe>> getRecipeType() {
        return BLOOMERY;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BloomeryRecipe> recipe, IFocusGroup focuses) {
        BloomeryRecipe recipe1=recipe.value();
        builder.addSlot(RecipeIngredientRole.INPUT,56,17).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.getIngredient().getItems()).toList());
        builder.addSlot(RecipeIngredientRole.INPUT,56,53).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(Ingredient.of(ModTags.TEMPERATURE_FUELS).getItems()).toList());
        ItemStack[] stacks=new ItemStack[]{new ItemStack(ItemRegistry.BLOOM.get()),recipe1.getResult().copy()};
        builder.addSlot(RecipeIngredientRole.OUTPUT,116,35).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(stacks).toList());
    }

    @Override
    public void draw(RecipeHolder<BloomeryRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        flame.draw(guiGraphics,56,36);
        arrow.draw(guiGraphics,79,34);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<BloomeryRecipe> recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if(isHovering(47,36,3,34,mouseX,mouseY)){
            tooltip.add(Component.literal("Tmin="+recipe.value().getTemperature()));
        }
    }

    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= (double)(x - 1)
                && mouseX < (double)(x + width + 1)
                && mouseY >= (double)(y - 1)
                && mouseY < (double)(y + height + 1);
    }

    @Override
    public boolean needsRecipeBorder() {
        return false;
    }
}
