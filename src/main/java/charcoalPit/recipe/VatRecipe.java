package charcoalPit.recipe;

import charcoalPit.core.RecipeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class VatRecipe implements Recipe<SingleRecipeInput> {

    protected final FluidIngredient vat,distillate,concentrate;
    protected final int cookTime;
    protected final boolean needsFuel;
    protected final NonNullList<Ingredient> ingredients;
    protected final Ingredient container;
    protected final ItemStack result;

    public VatRecipe(FluidIngredient vat, NonNullList<Ingredient> ingredients, int cookTime,boolean needsFuel,FluidIngredient distillate,FluidIngredient concentrate,Ingredient container,ItemStack result){
        this.vat=vat;
        this.distillate=distillate;
        this.container=container;
        this.concentrate=concentrate;
        this.ingredients=ingredients;
        this.result=result;
        this.cookTime=cookTime;
        this.needsFuel=needsFuel;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return container.test(singleRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i*i1>=ingredients.size();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.THE_VAT.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.THE_VAT_RECIPE.get();
    }

    public static VatRecipe getRecipe(ItemStackHandler inventory, FluidStack vat, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<VatRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.THE_VAT_RECIPE.get());
        for(RecipeHolder<VatRecipe> recipe:list){
            if((recipe.value().container.isEmpty()||recipe.value().container.test(inventory.getStackInSlot(4))) && checkRecipe(recipe.value(),
                    new ItemStack[]{inventory.getStackInSlot(0).copy(),inventory.getStackInSlot(1).copy(),inventory.getStackInSlot(2).copy(),inventory.getStackInSlot(3).copy()}) && recipe.value().vat.test(vat)){
                return recipe.value();
            }
        }
        return null;
    }

    public static boolean checkRecipe(VatRecipe recipe, ItemStack[] items){
        for(int i=0;i<recipe.ingredients.size();i++){
            boolean found=false;
            for(int j=0;j<items.length;j++){
                if(recipe.ingredients.get(i).test(items[j])){
                    found=true;
                    items[j]=ItemStack.EMPTY;
                    break;
                }
            }
            if(!found)
                return false;
        }
        return false;
    }

    public static boolean isIngredientValid(ItemStack stack, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<VatRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.THE_VAT_RECIPE.get());
        for(RecipeHolder<VatRecipe> recipe:list){
            for(Ingredient ing:recipe.value().ingredients){
                if(ing.test(stack))
                    return true;
            }
        }
        return false;
    }

    public static boolean isContainerValid(ItemStack stack, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<VatRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.THE_VAT_RECIPE.get());
        for(RecipeHolder<VatRecipe> recipe:list){
            if(recipe.value().container.test(stack)){
                return true;
            }
        }
        return false;
    }

    public static boolean isFluidValid(FluidStack stack, Level level){
        RecipeManager recipes = level.getRecipeManager();
        List<RecipeHolder<VatRecipe>> list = recipes.getAllRecipesFor(RecipeRegistry.THE_VAT_RECIPE.get());
        for(RecipeHolder<VatRecipe> recipe:list){
            if(recipe.value().vat.test(stack)){
                return true;
            }
        }
        return false;
    }

    public static class Serializer implements RecipeSerializer<VatRecipe>{
        private static final MapCodec<VatRecipe> CODEC= RecordCodecBuilder.mapCodec(par->par.group(
                FluidIngredient.CODEC_NON_EMPTY.fieldOf("fluid_input").forGetter(par1->par1.vat),
                Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(par3->{
                    Ingredient[] ingredients1=par3.toArray(Ingredient[]::new);
                    return ingredients1.length > 4
                            ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is 4")
                            : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients1));
                },DataResult::success).forGetter(par4->par4.ingredients),
                Codec.INT.fieldOf("cooking_time").orElse(200).forGetter(par5->par5.cookTime),
                Codec.BOOL.fieldOf("needs_fuel").orElse(true).forGetter(par6->par6.needsFuel),
                FluidIngredient.CODEC.fieldOf("distillate").forGetter(par7->par7.distillate),
                FluidIngredient.CODEC.fieldOf("concentrate").forGetter(par8->par8.concentrate),
                Ingredient.CODEC.fieldOf("container").forGetter(par9->par9.container),
                ItemStack.CODEC.fieldOf("result").forGetter(par10->par10.result)
        ).apply(par,VatRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, VatRecipe> STREAM_CODEC = StreamCodec.of(
                VatRecipe.Serializer::toNetwork, VatRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<VatRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VatRecipe> streamCodec() {
            return null;
        }

        private static VatRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            FluidIngredient vat=FluidIngredient.STREAM_CODEC.decode(buffer);
            int i=buffer.readVarInt();
            NonNullList<Ingredient> list=NonNullList.withSize(i,Ingredient.EMPTY);
            list.replaceAll(par->Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            int t=buffer.readVarInt();
            boolean f=buffer.readBoolean();
            FluidIngredient distillate=FluidIngredient.STREAM_CODEC.decode(buffer);
            FluidIngredient concentrate=FluidIngredient.STREAM_CODEC.decode(buffer);
            Ingredient cont=Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result=ItemStack.STREAM_CODEC.decode(buffer);
            return new VatRecipe(vat,list,t,f,distillate,concentrate,cont,result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, VatRecipe recipe) {
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.vat);
            buffer.writeVarInt(recipe.ingredients.size());
            for(Ingredient ingredient:recipe.ingredients){
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer,ingredient);
            }
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeBoolean(recipe.needsFuel);
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.distillate);
            FluidIngredient.STREAM_CODEC.encode(buffer,recipe.concentrate);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer,recipe.container);
            ItemStack.STREAM_CODEC.encode(buffer,recipe.result);
        }
    }
}
