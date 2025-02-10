package charcoalPit.recipe;

import charcoalPit.core.RecipeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.List;

public class CrusherRecipe implements Recipe<SingleRecipeInput> {

    public final FluidIngredient fluidOut;
    public final int amountOut;
    public final Ingredient itemIn;
    public final int processTime;

    public CrusherRecipe(Ingredient itemIn,FluidIngredient fluidOut,int amountOut,int processTime){
        this.fluidOut=fluidOut;
        this.amountOut=amountOut;
        this.itemIn=itemIn;
        this.processTime=processTime;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return itemIn.test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.CRUSHER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.CRUSHER_RECIPE.get();
    }

    public static CrusherRecipe getRecipe(ItemStack stack, Level level){
        if(stack.isEmpty())
            return null;
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<CrusherRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.CRUSHER_RECIPE.get());
        for(RecipeHolder<CrusherRecipe> recipe:list){
            if(recipe.value().itemIn.test(stack))
                return recipe.value();
        }
        return null;
    }

    public static boolean isPressItemValid(ItemStack stack, Level level){
        if(stack.isEmpty())
            return false;
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<CrusherRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.CRUSHER_RECIPE.get());
        for(RecipeHolder<CrusherRecipe> recipe:list){
            if(recipe.value().itemIn.test(stack))
                return true;
        }
        return false;
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipe>{
        private static final MapCodec<CrusherRecipe> CODEC= RecordCodecBuilder.mapCodec(par->par.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("item_in").forGetter(par3->par3.itemIn),
                FluidIngredient.CODEC_NON_EMPTY.fieldOf("fluid_out").forGetter(par4->par4.fluidOut),
                Codec.INT.fieldOf("amount_out").orElse(1000).forGetter(par5->par5.amountOut),
                Codec.INT.fieldOf("process_time").orElse(100).forGetter(par7->par7.processTime)
        ).apply(par,CrusherRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> STREAM_CODEC = StreamCodec.of(
                CrusherRecipe.Serializer::toNetwork, CrusherRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<CrusherRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CrusherRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient itemIn=Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            FluidIngredient fluidOut=FluidIngredient.STREAM_CODEC.decode(buffer);
            int amountOut=buffer.readVarInt();
            int time=buffer.readVarInt();
            return new CrusherRecipe(itemIn,fluidOut,amountOut,time);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CrusherRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer,recipe.itemIn);
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.fluidOut);
            buffer.writeVarInt(recipe.amountOut);
            buffer.writeVarInt(recipe.processTime);
        }
    }
}
