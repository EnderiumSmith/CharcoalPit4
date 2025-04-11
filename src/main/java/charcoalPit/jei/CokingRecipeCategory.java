package charcoalPit.jei;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.recipe.CokingRecipe;
import charcoalPit.recipe.QuernRecipe;
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
import net.neoforged.neoforge.fluids.FluidStack;

public class CokingRecipeCategory implements IRecipeCategory<RecipeHolder<CokingRecipe>> {

	public static final ResourceLocation UID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"coking");
	private static final ResourceLocation BARREL_GUI_TEXTURES =ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "textures/gui/container/coke_oven_recipe.png");
	public final IGuiHelper guiHelper;
	public final Component title;
	public IDrawableStatic background;
	public IDrawable icon,tankOverlay;
	public IDrawableAnimated flame;

	public CokingRecipeCategory(IGuiHelper helper){
		guiHelper=helper;
		title=Component.translatable("charcoal_pit.jei.coking");
		icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemRegistry.COKE_OVEN.get()));
		background=guiHelper.createDrawable(BARREL_GUI_TEXTURES,0,0,175,85);
		flame=guiHelper.drawableBuilder(BARREL_GUI_TEXTURES,176,0,14,14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP,true);
		tankOverlay=guiHelper.createDrawable(BARREL_GUI_TEXTURES,176,31,16,29);
	}

	static final RecipeType<RecipeHolder<CokingRecipe>> COKING=RecipeType.createFromVanilla(RecipeRegistry.COKING_RECIPE.get());

	@Override
	public RecipeType<RecipeHolder<CokingRecipe>> getRecipeType() {
		return COKING;
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
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CokingRecipe> recipe, IFocusGroup focuses) {
		CokingRecipe recipe1=recipe.value();
		builder.addSlot(RecipeIngredientRole.INPUT,71,44).addIngredients(VanillaTypes.ITEM_STACK, Arrays.stream(recipe1.getIngredient().getItems()).toList());
		builder.addSlot(RecipeIngredientRole.OUTPUT,98,53).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe1.getResult().copy()));
		if((int)recipe1.getExperience()>0){
			builder.addSlot(RecipeIngredientRole.OUTPUT,98,22).addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(FluidRegistry.CREOSOTE.source, (int)recipe1.getExperience()))).setFluidRenderer(Math.min(16000,(int)recipe1.getExperience()*2),false,16,29).setOverlay(tankOverlay,0,0);
		}
	}

	@Override
	public void draw(RecipeHolder<CokingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		flame.draw(guiGraphics,71,23);
	}

	@Override
	public boolean needsRecipeBorder() {
		return false;
	}
}
