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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class BlastFurnaceRecipe implements Recipe<DoubleRecipeInput> {

    protected final Ingredient ingredient;
    protected final Ingredient flux;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookingTime;
    protected final int temperature;

    public BlastFurnaceRecipe(Ingredient ingredient, Ingredient flux, ItemStack result, float experience, int cookingTime, int temperature) {
        this.ingredient = ingredient;
        this.flux = flux;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.temperature=temperature;
    }

    @Override
    public boolean matches(DoubleRecipeInput input, Level level) {
        return ingredient.test(input.ore())&&flux.test(input.flux());
    }

    @Override
    public ItemStack assemble(DoubleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.HOT_BLASTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.HOT_BLAST_FURNACE.get();
    }

    public float getXp(){return experience;}

    public int getCookingTime(){return cookingTime;}

    public int getTemperature(){return temperature;}

    public ItemStack getResult(){return result;}

    public Ingredient getIngredient(){return ingredient;}

    public Ingredient getFlux(){return flux;}

    public static BlastFurnaceRecipe getRecipe(ItemStack stack, ItemStack flux, Level level){
        if(stack.isEmpty()||flux.isEmpty())
            return null;
        RecipeManager recipes=level.getRecipeManager();
        Optional<RecipeHolder<BlastFurnaceRecipe>> recipe=recipes.getRecipeFor(RecipeRegistry.HOT_BLAST_FURNACE.get(), new DoubleRecipeInput(stack,flux),level);
        return recipe.map(RecipeHolder::value).orElse(null);
    }

    public static BlastingRecipe getVanillaRecipe(ItemStack stack, Level level){
        RecipeManager recipes=level.getRecipeManager();
        Optional<RecipeHolder<BlastingRecipe>> recipe=recipes.getRecipeFor(RecipeType.BLASTING,new SingleRecipeInput(stack),level);
        return recipe.map(RecipeHolder::value).orElse(null);
    }

    public static boolean isIngredientValid(ItemStack stack,boolean flux, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<BlastFurnaceRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.HOT_BLAST_FURNACE.get());
        for(RecipeHolder<BlastFurnaceRecipe> recipe:list){
            if(flux&&recipe.value().flux.test(stack))
                return true;
            else if(!flux&&recipe.value().ingredient.test(stack))
                return true;
        }
        return false;
    }

    public static boolean isVanillaValid(ItemStack stack,boolean flux ,Level level){
        if(flux)
            return false;
        RecipeManager recipeManager = level.getRecipeManager();
        return recipeManager.getRecipeFor(RecipeType.BLASTING,new SingleRecipeInput(stack),level).isPresent();
    }

    public static class BlastFurnaceSerializer implements RecipeSerializer<BlastFurnaceRecipe>{
        private static final MapCodec<BlastFurnaceRecipe> codec= RecordCodecBuilder.mapCodec(param1->param1.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ing->ing.ingredient),
                Ingredient.CODEC_NONEMPTY.fieldOf("flux").forGetter(fx->fx.flux),
                ItemStack.CODEC.fieldOf("result").forGetter(res->res.result),
                Codec.FLOAT.fieldOf("experience").orElse(0F).forGetter(ex-> ex.experience),
                Codec.INT.fieldOf("cooking_time").orElse(200).forGetter(cook->cook.cookingTime),
                Codec.INT.fieldOf("temperature").forGetter(temp-> temp.temperature)
        ).apply(param1,BlastFurnaceRecipe::new));;


        private static final StreamCodec<RegistryFriendlyByteBuf, BlastFurnaceRecipe> streamCodec=StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, ing->ing.ingredient,
                Ingredient.CONTENTS_STREAM_CODEC, fx->fx.flux,
                ItemStack.STREAM_CODEC, res->res.result,
                ByteBufCodecs.FLOAT, ex->ex.experience,
                ByteBufCodecs.INT, cook->cook.cookingTime,
                ByteBufCodecs.INT, temp->temp.temperature,
                BlastFurnaceRecipe::new
        );

        @Override
        public MapCodec<BlastFurnaceRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlastFurnaceRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
