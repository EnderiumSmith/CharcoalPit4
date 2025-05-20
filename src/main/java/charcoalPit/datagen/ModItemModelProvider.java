package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.items.ItemMusket;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CharcoalPit.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        HandheldItem(ItemRegistry.COPPER_PICK);
        HandheldItem(ItemRegistry.COPPER_SHOVEL);
        HandheldItem(ItemRegistry.COPPER_AXE);
        HandheldItem(ItemRegistry.COPPER_SWORD);
        HandheldItem(ItemRegistry.COPPER_HOE);
        HandheldItem(ItemRegistry.CLUB);
        basicItem(ItemRegistry.TIN.get());
        basicItem(ItemRegistry.BRONZE.get());
        basicItem(ItemRegistry.PIG_IRON.get());
        basicItem(ItemRegistry.STEEL.get());
        basicItem(ItemRegistry.RAW_TIN.get());
        basicItem(ItemRegistry.RAW_TIN_NUGGET.get());
        HandheldItem(ItemRegistry.BRONZE_PICK);
        HandheldItem(ItemRegistry.BRONZE_SHOVEL);
        HandheldItem(ItemRegistry.BRONZE_AXE);
        HandheldItem(ItemRegistry.BRONZE_SWORD);
        HandheldItem(ItemRegistry.BRONZE_HOE);
        basicItem(ItemRegistry.BRONZE_HELMET.get());
        basicItem(ItemRegistry.BRONZE_CHESTPLATE.get());
        basicItem(ItemRegistry.BRONZE_LEGGINGS.get());
        basicItem(ItemRegistry.BRONZE_BOOTS.get());
        basicItem(ItemRegistry.CLAY_MOLD.get());
        basicItem(ItemRegistry.ALLOY_MOLD.get());
        basicItem(ItemRegistry.ALLOY_MOLD_FULL.get());
        basicItem(ItemRegistry.ALLOY_MOLD_FINISHED.get());
        basicItem(ItemRegistry.COKE.get());
        withVanillaTexture(ItemRegistry.ASH,"item/light_gray_dye");
        wallItem(BlockRegistry.SANDY_WALL,BlockRegistry.SANDY_BRICKS);
        basicItem(ItemRegistry.SANDY_BRICK.get());
        basicItem(ItemRegistry.UNFIRED_SANDY_BRICK.get());
        basicItem(ItemRegistry.FLUX.get());
        HandheldItem(ItemRegistry.STEEL_PICK);
        HandheldItem(ItemRegistry.STEEL_SHOVEL);
        HandheldItem(ItemRegistry.STEEL_AXE);
        HandheldItem(ItemRegistry.STEEL_SWORD);
        HandheldItem(ItemRegistry.STEEL_HOE);
        basicItem(ItemRegistry.STEEL_HELMET.get());
        basicItem(ItemRegistry.STEEL_CHESTPLATE.get());
        basicItem(ItemRegistry.STEEL_LEGGINGS.get());
        basicItem(ItemRegistry.STEEL_BOOTS.get());
        wallItem(BlockRegistry.BASALT_WALL,BlockRegistry.BASALT_BRICKS);
        wallItem(BlockRegistry.MARBLE_WALL,BlockRegistry.MARBLE_BRICKS);
        HandheldItem(ItemRegistry.LEEKS);
        basicItem(ItemRegistry.FARFETCH_STEW.get());
        basicItem(ItemRegistry.SERINAN_STEW.get());
        HandheldItem(ItemRegistry.KEBABS);
        HandheldItem(ItemRegistry.DANGOS);
        basicItem(ItemRegistry.FLOUR.get());
        basicItem(ItemRegistry.SUNFLOWER_SEEDS.get());
        basicItem(ItemRegistry.FERTILIZER.get());
        basicItem(ItemRegistry.AETERNALIS.get());
        basicItem(ItemRegistry.DYNAMITE_REMOTE.get());
        basicItem(ItemRegistry.SMALL_GUNPOWDER.get());
        HandheldItem(ItemRegistry.BRONZE_KNIFE);
        HandheldItem(ItemRegistry.STEEL_KNIFE);
        basicItem(FluidRegistry.CREOSOTE.bucket.get());
        basicItem(ItemRegistry.UNFIRED_HIGH_REFRACTORY_BRICK.get());
        basicItem(ItemRegistry.HIGH_REFRACTORY_BRICK.get());
        basicItem(ItemRegistry.CINDER_FLOUR.get());
        basicItem(FluidRegistry.ALCOHOL.bucket.get());
        basicItem(FluidRegistry.ETHANOL.bucket.get());
        basicItem(ItemRegistry.CHOCOLATE_BAR.get());
        basicItem(ItemRegistry.CROISSANT.get());
        basicItem(ItemRegistry.AMARANTH_BREAD.get());
        basicItem(ItemRegistry.CHEESE.get());
        basicItem(FluidRegistry.VINEGAR.bucket.get());
        basicItem(ItemRegistry.CHERRY.get());
        basicItem(ItemRegistry.WALNUT.get());
        basicItem(ItemRegistry.ORANGE.get());
        basicItem(ItemRegistry.OLIVES.get());
        basicItem(ItemRegistry.QUICKLIME.get());
        basicItem(FluidRegistry.LIMEWATER.bucket.get());
        basicItem(ItemRegistry.MORTAR_OF_TARTAR.get());
        basicItem(ItemRegistry.COOKED_CALAMARI.get());
        basicItem(ItemRegistry.RAW_CALAMARI.get());
        basicItem(ItemRegistry.FROG_LEG.get());
        basicItem(ItemRegistry.FROG_LEG_COOKED.get());
        basicItem(FluidRegistry.SEED_OIL.bucket.get());
        basicItem(FluidRegistry.BIODIESEL.bucket.get());
        basicItem(ItemRegistry.GLYCERINE.get());
        basicItem(ItemRegistry.ALCOHOL_BOTTLE.get());
        basicItem(ItemRegistry.ETHANOL_BOTTLE.get());
        basicItem(ItemRegistry.POISONOUS_POTATO_FRIES.get());
        basicItem(ItemRegistry.INGOT_CHICKEN.get());
        basicItem(ItemRegistry.NUGGET_CHICKEN.get());
        basicItem(ItemRegistry.PANCAKES.get());
        basicItem(ItemRegistry.CHERRY_JAM.get());
        basicItem(ItemRegistry.CHERRY_SANDWITCH.get());
        basicItem(ItemRegistry.ORANGE_JUICE.get());
        basicItem(ItemRegistry.STRAWBERRY.get());
        basicItem(ItemRegistry.SALT.get());
        basicItem(ItemRegistry.SALT_CAKE.get());
        basicItem(ItemRegistry.BLACK_ASH.get());
        basicItem(ItemRegistry.NATRON.get());
        basicItem(ItemRegistry.SODIUM.get());
        basicItem(ItemRegistry.COKE_POWDER.get());
        basicItem(ItemRegistry.ALUM.get());
        basicItem(ItemRegistry.ALUMINA.get());
        basicItem(ItemRegistry.ALUMINIUM_CHLORIDE.get());
        basicItem(ItemRegistry.ALUMINIUM.get());
        basicItem(FluidRegistry.OIL_OF_VITRIOL.bucket.get());
        basicItem(FluidRegistry.HYDROGEN_SULFIDE.bucket.get());
        basicItem(FluidRegistry.MURIATIC_ACID.bucket.get());
        basicItem(FluidRegistry.CHLORINE.bucket.get());
        HandheldItem(ItemRegistry.ALUMINIUM_KNIFE);
        basicItem(ItemRegistry.GREEN_VITRIOL.get());
        basicItem(ItemRegistry.ALUMITE.get());
        basicItem(ItemRegistry.OBSIDIAN_POWDER.get());
        HandheldItem(ItemRegistry.ALUMITE_PICK);
        HandheldItem(ItemRegistry.ALUMITE_SHOVEL);
        HandheldItem(ItemRegistry.ALUMITE_AXE);
        HandheldItem(ItemRegistry.ALUMITE_SWORD);
        HandheldItem(ItemRegistry.ALUMITE_HOE);
        basicItem(ItemRegistry.ALUMITE_HELMET.get());
        basicItem(ItemRegistry.ALUMITE_CHESTPLATE.get());
        basicItem(ItemRegistry.ALUMITE_LEGGINGS.get());
        basicItem(ItemRegistry.ALUMITE_BOOTS.get());
        basicItem(ItemRegistry.RAW_PLATINUM.get());
        basicItem(ItemRegistry.PLATINUM.get());
        basicItem(ItemRegistry.CALCIUM_CARBIDE.get());
        basicItem(ItemRegistry.TIN_DUST.get());
        basicItem(ItemRegistry.PLATINUM_DUST.get());
        basicItem(ItemRegistry.ENDERIUM.get());
        basicItem(ItemRegistry.ENDERIUM_DUST.get());
        basicItem(ItemRegistry.RESONANT_BOTTLE.get());
        basicItem(FluidRegistry.ACETYLENE.bucket.get());
        HandheldItem(ItemRegistry.ENDERIUM_PICK);
        HandheldItem(ItemRegistry.ENDERIUM_SHOVEL);
        HandheldItem(ItemRegistry.ENDERIUM_AXE);
        HandheldItem(ItemRegistry.ENDERIUM_SWORD);
        HandheldItem(ItemRegistry.ENDERIUM_HOE);
        HandheldItem(ItemRegistry.PLATINUM_KNIFE);
        basicItem(ItemRegistry.ENDERIUM_HELMET.get());
        basicItem(ItemRegistry.ENDERIUM_CHESTPLATE.get());
        basicItem(ItemRegistry.ENDERIUM_LEGGINGS.get());
        basicItem(ItemRegistry.ENDERIUM_BOOTS.get());
        HandheldItem(ItemRegistry.ENDERIUM_MACE);
        basicItem(ItemRegistry.ENDERIUM_DOUBLE.get());
        basicItem(ItemRegistry.RAW_CHALCOCITE.get());
        basicItem(ItemRegistry.SULFUR.get());
        basicItem(ItemRegistry.NITER.get());
        basicItem(ItemRegistry.LYE.get());
        basicItem(FluidRegistry.NITERWATER.bucket.get());
        basicItem(ItemRegistry.HONEY_DEWOIS.get());
        withVanillaTexture(ItemRegistry.CHARGED_ECHO_SHARD,"item/echo_shard");
        HandheldItem(ItemRegistry.SOUL_DRINKER);
        basicItem(ItemRegistry.COPPER_HELMET.get());
        basicItem(ItemRegistry.COPPER_CHESTPLATE.get());
        basicItem(ItemRegistry.COPPER_LEGGINGS.get());
        basicItem(ItemRegistry.COPPER_BOOTS.get());
        basicItem(ItemRegistry.STEEL_NUGGET.get());
    }

    private ItemModelBuilder HandheldItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"item/"+item.getId().getPath()));
    }

    private ItemModelBuilder withVanillaTexture(DeferredItem<?> item, String name){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse(name));
    }

    private void wallItem(DeferredBlock<?> baseBlock, DeferredBlock<Block> texture){
        this.withExistingParent(baseBlock.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/"+texture.getId().getPath()));
    }
}
