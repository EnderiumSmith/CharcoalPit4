package charcoalPit.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

import charcoalPit.core.RecipeRegistry;

public class CokingRecipe extends SmeltingRecipe {
	public CokingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
		super(group, category, ingredient, result, experience, cookingTime);
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeRegistry.COKING_RECIPE.get();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeRegistry.COKING.get();
	}

	public Ingredient getIngredient(){
		return ingredient;
	}

	public ItemStack getResult(){
		return result;
	}

	public static CokingRecipe getRecipeFor(ItemStack stack, Level level){
		return level.getRecipeManager().getRecipeFor(RecipeRegistry.COKING_RECIPE.get(), new SingleRecipeInput(stack),level).map(RecipeHolder::value).orElse(null);
	}
}
