package charcoalPit;

import charcoalPit.TESR.TESRQuern;
import charcoalPit.block.BlockFruitLeaves;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.entity.*;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.gui.screen.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(value = Dist.CLIENT,modid = CharcoalPit.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(MenuTypeRegistry.BLOOMERY.get(), BloomeryScreen::new);
        event.register(MenuTypeRegistry.BLAST_FURNACE.get(), BlastFurnaceScreen::new);
        event.register(MenuTypeRegistry.BARREL.get(), BarrelScreen::new);
        event.register(MenuTypeRegistry.STILL.get(), StillScreen::new);
        event.register(MenuTypeRegistry.PRESS.get(), PressScreen::new);
        //event.register(MenuTypeRegistry.CRUSHER.get(), CrusherScreen::new);
        event.register(MenuTypeRegistry.CERAMIC_POT.get(), CeramicPotScreen::new);
        event.register(MenuTypeRegistry.COKE_OVEN.get(), CokeOvenScreen::new);
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LEEKS.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SUNFLOWER.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.APPLE_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.APPLE_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CHERRY_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CHERRY_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.OLIVE_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.OLIVE_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ORANGE_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ORANGE_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WALNUT_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WALNUT_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOUGLAS_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOUGLAS_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AMARANTH_SAPLING.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AMARANTH_LEAVES.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.STRAWBERRY_BUSH.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.COKE_OVEN.get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AIKO_PLUSH.get(), RenderType.CUTOUT);
        /*ItemBlockRenderTypes.setRenderLayer(FluidRegistry.HYDROGEN_SULFIDE.source.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(FluidRegistry.HYDROGEN_SULFIDE.flowing.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(FluidRegistry.CHLORINE.source.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(FluidRegistry.CHLORINE.flowing.get(), RenderType.TRANSLUCENT);*/
        ItemProperties.register(ItemRegistry.JAVELIN.get(), ResourceLocation.parse("throwing"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                return entity!=null&&entity.isUsingItem()?1:0;
            }
        });
        ItemProperties.register(ItemRegistry.EXPLOSIVE_SPEAR.get(), ResourceLocation.parse("throwing"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                return entity!=null&&entity.isUsingItem()?1:0;
            }
        });
        ItemProperties.register(ItemRegistry.APPLE_LEAVES.get(), ResourceLocation.parse("charcoal_pit:stage"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                if(itemStack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
                    return switch (itemStack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                        case 1 -> 0.25F;
                        case 2 -> 0.5F;
                        case 3 -> 1;
                        default -> 0F;
                    };
                }
                return 0F;
            }
        });
        ItemProperties.register(ItemRegistry.CHERRY_LEAVES.get(), ResourceLocation.parse("charcoal_pit:stage"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                if(itemStack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
                    return switch (itemStack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                        case 1 -> 0.25F;
                        case 2 -> 0.5F;
                        case 3 -> 1;
                        default -> 0F;
                    };
                }
                return 0F;
            }
        });
        ItemProperties.register(ItemRegistry.OLIVE_LEAVES.get(), ResourceLocation.parse("charcoal_pit:stage"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                if(itemStack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
                    return switch (itemStack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                        case 1 -> 0.25F;
                        case 2 -> 0.5F;
                        case 3 -> 1;
                        default -> 0F;
                    };
                }
                return 0F;
            }
        });
        ItemProperties.register(ItemRegistry.ORANGE_LEAVES.get(), ResourceLocation.parse("charcoal_pit:stage"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                if(itemStack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
                    return switch (itemStack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                        case 1 -> 0.25F;
                        case 2 -> 0.5F;
                        case 3 -> 1;
                        default -> 0F;
                    };
                }
                return 0F;
            }
        });
        ItemProperties.register(ItemRegistry.WALNUT_LEAVES.get(), ResourceLocation.parse("charcoal_pit:stage"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                if(itemStack.has(DataComponentRegistry.FRUIT_LEAVES_STATE)){
                    return switch (itemStack.get(DataComponentRegistry.FRUIT_LEAVES_STATE)) {
                        case 1 -> 0.25F;
                        case 2 -> 0.5F;
                        case 3 -> 1;
                        default -> 0F;
                    };
                }
                return 0F;
            }
        });
    }
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.getBlockColors().register(new BlockColor() {
            @Override
            public int getColor(BlockState p_getColor_1_, @Nullable BlockAndTintGetter p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
                if(p_getColor_4_==0){
                    return BiomeColors.getAverageFoliageColor(p_getColor_2_,p_getColor_3_);
                }
                return 0xFFFFFF;
            }
        },BlockRegistry.APPLE_LEAVES.get(),BlockRegistry.OLIVE_LEAVES.get(),BlockRegistry.ORANGE_LEAVES.get(),BlockRegistry.WALNUT_LEAVES.get(),
                BlockRegistry.DOUGLAS_LEAVES.get(),BlockRegistry.AMARANTH_LEAVES.get(),BlockRegistry.STRAWBERRY_BUSH.get());
        event.getBlockColors().register(new BlockColor() {
            @Override
            public int getColor(BlockState p_getColor_1_, @Nullable BlockAndTintGetter p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
                if(p_getColor_4_==0){
                    if(p_getColor_1_.getValue(BlockFruitLeaves.AGE)>1&&p_getColor_1_.getValue(BlockFruitLeaves.AGE)<5)
                        return 0xFFFFFF;
                    else
                        return BiomeColors.getAverageFoliageColor(p_getColor_2_,p_getColor_3_);
                }
                return 0xFFFFFF;
            }
        },BlockRegistry.CHERRY_LEAVES.get());
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event){
        event.getItemColors().register(new ItemColor() {
            @Override
            public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
                if(p_getColor_2_==0){
                    return 0x48B518;
                }
                return 0xFFFFFF;
            }
        },ItemRegistry.APPLE_LEAVES.get(),ItemRegistry.OLIVE_LEAVES.get(),ItemRegistry.ORANGE_LEAVES.get(),ItemRegistry.WALNUT_LEAVES.get(),
                ItemRegistry.DOUGLAS_LEAVES.get(),ItemRegistry.AMARANTH_LEAVES.get(),ItemRegistry.STRAWBERRY_BUSH.get());
        event.getItemColors().register(new ItemColor() {
            @Override
            public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
                if(p_getColor_2_==0){
                    if(p_getColor_1_.has(DataComponentRegistry.FRUIT_LEAVES_STATE)&&p_getColor_1_.get(DataComponentRegistry.FRUIT_LEAVES_STATE)==1)
                        return 0xFFFFFF;
                    else
                        return 0x48B518;
                }
                return 0xFFFFFF;
            }
        },ItemRegistry.CHERRY_LEAVES.get());
    }

    public static final ResourceLocation waterfx=ResourceLocation.parse("minecraft:textures/misc/underwater.png");

    public static final ResourceLocation waterSource=ResourceLocation.fromNamespaceAndPath("minecraft","block/water_still");
    public static final ResourceLocation waterFlowing=ResourceLocation.fromNamespaceAndPath("minecraft","block/water_flow");

    public static final ResourceLocation gasSource=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/gas_still");
    public static final ResourceLocation gasFlowing=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/gas_flow");
    public static final ResourceLocation acidSource=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/acid_still");

    @SubscribeEvent
    public static void fluidTextures(RegisterClientExtensionsEvent event){
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return FluidRegistry.CREOSOTE.stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FluidRegistry.CREOSOTE.flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return waterfx;
            }
        }, FluidRegistry.CREOSOTE.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFF6c3461;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.ALCOHOL.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFe6daa6;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.ETHANOL.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFF8B7901;//0xFFCEB301;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.VINEGAR.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFBBC39B;//0xFFCEB301;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.LIMEWATER.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFacbf69;//0xFFCEB301;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.SEED_OIL.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFf97306;//0xFFf97306;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.BIODIESEL.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFFFD52A;//0xFFFFBF00
            }

            @Override
            public ResourceLocation getStillTexture() {
                return acidSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.OIL_OF_VITRIOL.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFFEF65B;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return gasSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return gasFlowing;
            }
        }, FluidRegistry.HYDROGEN_SULFIDE.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFF8aff00;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return acidSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return gasFlowing;
            }
        }, FluidRegistry.MURIATIC_ACID.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFF78B842;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return gasSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return gasFlowing;
            }
        }, FluidRegistry.CHLORINE.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFF808080;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return gasSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return gasFlowing;
            }
        }, FluidRegistry.ACETYLENE.fluidType.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xFFC7B6B6;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return waterSource;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return waterFlowing;
            }
        }, FluidRegistry.NITERWATER.fluidType.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityRegistry.JAVELIN.get(), JavelinRenderer::new);
        event.registerBlockEntityRenderer(TileEntityRegistry.QUERN.get(), context -> new TESRQuern());
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event){
        event.register(TESRQuern.QUERN_STONE);
    }
}
