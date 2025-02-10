package charcoalPit.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DoubleRecipeInput(ItemStack ore, ItemStack flux) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if(index==0){
            return ore;
        }
        return flux;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return flux.isEmpty()&& ore.isEmpty();
    }
}
