package charcoalPit.recipe;

import charcoalPit.core.RecipeRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class QuernRecipe extends SmeltingRecipe {
    public QuernRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.QUERN_RECIPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.QUERN.get();
    }

    public Ingredient getIngredient(){
        return ingredient;
    }

    public ItemStack getResult(){
        return result;
    }
}
