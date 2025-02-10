package charcoalPit.recipe;

import charcoalPit.core.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class NBTSmeltingRecipe extends SmeltingRecipe {
    public NBTSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.ALLOYING.get();
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        ItemStack resultStack=result.copy();
        resultStack.applyComponents(input.getItem(0).getComponents());
        resultStack.applyComponents(input.getItem(0).getComponentsPatch());
        return resultStack;
    }
}
