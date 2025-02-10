package charcoalPit.recipe;

import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import charcoalPit.core.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class MusketReloadingRecipe extends CustomRecipe {
    public MusketReloadingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int i=0;
        int j=0;
        int k=0;
        for(int l=0;l<input.size();l++){
            ItemStack stack=input.getItem(l);
            if(!stack.isEmpty()){
                if(stack.getItem()== ItemRegistry.MUSKET.get()){
                    i++;
                    if(stack.has(DataComponentRegistry.MUSKET_LOADED)){
                        return false;
                    }
                }else if(stack.is(ModTags.GUNPOWDER)){
                    j++;
                }else if(stack.is(ModTags.BULLETS)){
                    k++;
                }else{
                    return false;
                }
            }
        }
        return i==1&&j==1&&k==1;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY,Ingredient.of(ItemRegistry.MUSKET.get()),Ingredient.of(ModTags.GUNPOWDER),Ingredient.of(ModTags.BULLETS));
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        ItemStack stack=new ItemStack(ItemRegistry.MUSKET.get());
        stack.set(DataComponentRegistry.MUSKET_LOADED,Unit.INSTANCE);
        return stack;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack stack=ItemStack.EMPTY;
        for(int i=0;i<input.size();i++){
            if(input.getItem(i).getItem()==ItemRegistry.MUSKET.get()){
                stack=input.getItem(i).copy();
                stack.set(DataComponentRegistry.MUSKET_LOADED, Unit.INSTANCE);
                break;
            }
        }
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width*height>3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.MUSKET_RELOADING.get();
    }
}
