package charcoalPit.gui;

import charcoalPit.CharcoalPit;
import charcoalPit.gui.menu.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

public class MenuTypeRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES=DeferredRegister.create(Registries.MENU, CharcoalPit.MODID);

    public static final Supplier<MenuType<BloomeryMenu>> BLOOMERY=MENU_TYPES.register("bloomery",
            ()->new MenuType<>(BloomeryMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<BlastFurnaceMenu>> BLAST_FURNACE=MENU_TYPES.register("blast_furnace",
            ()->new MenuType<>(BlastFurnaceMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<BarrelMenu>> BARREL=MENU_TYPES.register("barrel",
            ()->new MenuType<>(BarrelMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<StillMenu>> STILL=MENU_TYPES.register("still",
            ()->new MenuType<>(StillMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<PressMenu>> PRESS=MENU_TYPES.register("press",
            ()->new MenuType<>(PressMenu::new,FeatureFlags.DEFAULT_FLAGS));
    /*public static final Supplier<MenuType<CrusherMenu>> CRUSHER=MENU_TYPES.register("crusher",
            ()->new MenuType<>(CrusherMenu::new,FeatureFlags.DEFAULT_FLAGS));*/
    public static final Supplier<MenuType<CeramicPotMenu>> CERAMIC_POT=MENU_TYPES.register("ceramic_pot",
            ()->new MenuType<>(CeramicPotMenu::new,FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<CokeOvenMenu>> COKE_OVEN=MENU_TYPES.register("coke_oven",
            ()->new MenuType<>(CokeOvenMenu::new,FeatureFlags.DEFAULT_FLAGS));

}
