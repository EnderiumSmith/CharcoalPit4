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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.List;

public class StillRecipe implements Recipe<SingleRecipeInput> {

    public final FluidIngredient fluidIn,fluidOut;
    public final int amountIn,amountOut;
    public final Ingredient itemIn;
    public final int itemAmount;
    public final ItemStack itemOut;
    public final int processTime;

    public StillRecipe(FluidIngredient fluidIn,int amountIn,Ingredient itemIn,int itemAmount,FluidIngredient fluidOut,int amountOut,ItemStack itemOut,int processTime){
        this.fluidIn=fluidIn;
        this.fluidOut=fluidOut;
        this.amountIn=amountIn;
        this.amountOut=amountOut;
        this.itemIn=itemIn;
        this.itemAmount=itemAmount;
        this.itemOut=itemOut;
        this.processTime=processTime;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return itemIn.test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return itemOut.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return itemOut.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.STILL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.STILL_RECIPE.get();
    }

    public static StillRecipe getRecipe(ItemStack stack, FluidStack fluid, Level level){
        if(fluid.isEmpty())
            return null;
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<StillRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.STILL_RECIPE.get());
        for(RecipeHolder<StillRecipe> recipe:list){
            if((recipe.value().itemIn.isEmpty()||recipe.value().itemIn.test(stack))&&recipe.value().fluidIn.test(fluid))
                return recipe.value();
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<StillRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.STILL_RECIPE.get());
        for(RecipeHolder<StillRecipe> recipe:list){
            if(recipe.value().itemIn.test(stack))
                return true;
        }
        return false;
    }

    public static boolean isValidFluid(FluidStack stack, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<StillRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.STILL_RECIPE.get());
        for(RecipeHolder<StillRecipe> recipe:list){
            if(recipe.value().fluidIn.test(stack))
                return true;
        }
        return false;
    }

    public static class Serializer implements RecipeSerializer<StillRecipe>{
        private static final MapCodec<StillRecipe> CODEC= RecordCodecBuilder.mapCodec(par->par.group(
                FluidIngredient.CODEC_NON_EMPTY.fieldOf("fluid_in").forGetter(par1->par1.fluidIn),
                Codec.INT.fieldOf("amount_in").orElse(1000).forGetter(par2->par2.amountIn),
                Ingredient.CODEC.fieldOf("item_in").orElse(Ingredient.EMPTY).forGetter(par3->par3.itemIn),
                Codec.INT.fieldOf("item_amount").orElse(1).forGetter(par8->par8.itemAmount),
                FluidIngredient.CODEC.fieldOf("fluid_out").orElse(FluidIngredient.empty()).forGetter(par4->par4.fluidOut),
                Codec.INT.fieldOf("amount_out").orElse(1000).forGetter(par5->par5.amountOut),
                ItemStack.CODEC.fieldOf("item_out").orElse(ItemStack.EMPTY).forGetter(par6->par6.itemOut),
                Codec.INT.fieldOf("process_time").orElse(200).forGetter(par7->par7.processTime)
        ).apply(par,StillRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, StillRecipe> STREAM_CODEC = StreamCodec.of(
                StillRecipe.Serializer::toNetwork, StillRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<StillRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StillRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static StillRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            FluidIngredient fluidIn=FluidIngredient.STREAM_CODEC.decode(buffer);
            int amountIn=buffer.readVarInt();
            Ingredient itemIn=Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int itemAmount=buffer.readVarInt();
            FluidIngredient fluidOut=FluidIngredient.STREAM_CODEC.decode(buffer);
            int amountOut=buffer.readVarInt();
            boolean empty=buffer.readBoolean();
            ItemStack itemOut=ItemStack.EMPTY;
            if(!empty)
                itemOut=ItemStack.STREAM_CODEC.decode(buffer);
            int time=buffer.readVarInt();
            return new StillRecipe(fluidIn,amountIn,itemIn,itemAmount,fluidOut,amountOut,itemOut,time);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, StillRecipe recipe) {
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.fluidIn);
            buffer.writeVarInt(recipe.amountIn);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer,recipe.itemIn);
            buffer.writeVarInt(recipe.itemAmount);
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.fluidOut);
            buffer.writeVarInt(recipe.amountOut);
            buffer.writeBoolean(recipe.itemOut.isEmpty());
            if(!recipe.itemOut.isEmpty())
                ItemStack.STREAM_CODEC.encode(buffer,recipe.itemOut);
            buffer.writeVarInt(recipe.processTime);
        }
    }

}
