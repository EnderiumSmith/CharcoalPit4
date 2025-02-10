package charcoalPit;

import charcoalPit.core.*;
import charcoalPit.effect.ModPotions;
import charcoalPit.entity.ModEntityRegistry;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.tree.ModTreeFeatures;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.slf4j.Logger;

@Mod(CharcoalPit.MODID)
public class CharcoalPit {

    public static final String MODID="charcoal_pit";

    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        NeoForgeMod.enableMilkFluid();
    }

    public CharcoalPit(IEventBus eventBus, ModContainer modContainer){

        ItemRegistry.ITEMS.register(eventBus);
        BlockRegistry.BLOCKS.register(eventBus);
        TileEntityRegistry.TILE_ENTITIES.register(eventBus);

        FluidRegistry.FLUID_TYPES.register(eventBus);
        FluidRegistry.FLUIDS.register(eventBus);

        DataComponentRegistry.DATA_COMPONENTS.register(eventBus);

        ModTreeFeatures.TRUNKS.register(eventBus);
        ModTreeFeatures.FOLIAGE.register(eventBus);

        MenuTypeRegistry.MENU_TYPES.register(eventBus);

        ModEntityRegistry.ENTITIES.register(eventBus);

        ItemRegistry.CREATIVE_TABS.register(eventBus);

        RecipeRegistry.SERIALIZERS.register(eventBus);

        RecipeRegistry.RECIPE_TYPES.register(eventBus);

        ModPotions.EFFECTS.register(eventBus);
        ModPotions.POTIONS.register(eventBus);

    }
}
