package charcoalPit.recipe;

import charcoalPit.DataComponents.BloomData;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.RecipeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class AlloyMoldRecipe implements CraftingRecipe {
    final String group;
    final CraftingBookCategory category;
    final Ingredient result;
    final int count;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public AlloyMoldRecipe(String group, CraftingBookCategory category, Ingredient result, NonNullList<Ingredient> ingredients,int count) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
        this.count=count;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.ALLOY_MOLD.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        ItemStack stack=this.result.getItems()[0].copy();
        stack.setCount(this.count);
        return stack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != this.ingredients.size()) {
            return false;
        } else if (!isSimple) {
            var nonEmptyItems = new java.util.ArrayList<ItemStack>(input.ingredientCount());
            for (var item : input.items())
                if (!item.isEmpty())
                    nonEmptyItems.add(item);
            return net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
        } else {
            return input.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(input.getItem(0))
                    : input.stackedContents().canCraft(this, null);
        }
    }

    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack stack=new ItemStack(ItemRegistry.ALLOY_MOLD_FULL.get());
        ItemStack nested=result.getItems()[0].copy();
        nested.setCount(count);
        stack.set(DataComponentRegistry.BLOOM_DATA,new BloomData(nested, 1,0));
        return stack;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    public static class Serializer implements RecipeSerializer<AlloyMoldRecipe> {
        private static final MapCodec<AlloyMoldRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340779_ -> p_340779_.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(p_301127_ -> p_301127_.group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301133_ -> p_301133_.category),
                                Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(p_301142_ -> p_301142_.result),
                                Ingredient.CODEC_NONEMPTY
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                p_301021_ -> {
                                                    Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new); // Neo skip the empty check and immediately create the array.
                                                    if (aingredient.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
                                                                : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(p_300975_ -> p_300975_.ingredients),
                                Codec.INT.fieldOf("count").forGetter(p-> p.count)
                        )
                        .apply(p_340779_, AlloyMoldRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyMoldRecipe> STREAM_CODEC = StreamCodec.of(
                AlloyMoldRecipe.Serializer::toNetwork, AlloyMoldRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<AlloyMoldRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyMoldRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AlloyMoldRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CraftingBookCategory craftingbookcategory = buffer.readEnum(CraftingBookCategory.class);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll(p_319735_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            Ingredient itemstack = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int c=buffer.readVarInt();
            return new AlloyMoldRecipe(s, craftingbookcategory, itemstack, nonnulllist,c);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, AlloyMoldRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            buffer.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeVarInt(recipe.count);
        }
    }
}
