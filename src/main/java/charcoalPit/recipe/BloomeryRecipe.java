package charcoalPit.recipe;


import charcoalPit.core.RecipeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class BloomeryRecipe implements Recipe<SingleRecipeInput> {

    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookingTime;
    protected final int temperature;

    public BloomeryRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime, int temperature) {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.temperature=temperature;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.BLOOMING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.BLOOMERY.get();
    }

    public float getXp(){return experience;}

    public int getCookingTime(){return cookingTime;}

    public int getTemperature(){return temperature;}

    public ItemStack getResult(){return result;}

    public Ingredient getIngredient(){
        return ingredient;
    }

    public static BloomeryRecipe getRecipe(ItemStack stack, Level level){
        if(stack.isEmpty())
            return null;
        RecipeManager recipes=level.getRecipeManager();
        Optional<RecipeHolder<BloomeryRecipe>> recipe=recipes.getRecipeFor(RecipeRegistry.BLOOMERY.get(), new SingleRecipeInput(stack),level);
        return recipe.map(RecipeHolder::value).orElse(null);
    }

    public static class BloomerySerializer implements RecipeSerializer<BloomeryRecipe>{
        private static final MapCodec<BloomeryRecipe> codec= RecordCodecBuilder.mapCodec(param1->param1.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ing->ing.ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(res->res.result),
                Codec.FLOAT.fieldOf("experience").orElse(0F).forGetter(ex-> ex.experience),
                Codec.INT.fieldOf("cooking_time").orElse(200).forGetter(cook->cook.cookingTime),
                Codec.INT.fieldOf("temperature").forGetter(temp-> temp.temperature)
        ).apply(param1,BloomeryRecipe::new));;


        private static final StreamCodec<RegistryFriendlyByteBuf, BloomeryRecipe> streamCodec=StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, ing->ing.ingredient,
                ItemStack.STREAM_CODEC, res->res.result,
                ByteBufCodecs.FLOAT, ex->ex.experience,
                ByteBufCodecs.INT, cook->cook.cookingTime,
                ByteBufCodecs.INT, temp->temp.temperature,
                BloomeryRecipe::new
        );

        @Override
        public MapCodec<BloomeryRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BloomeryRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
