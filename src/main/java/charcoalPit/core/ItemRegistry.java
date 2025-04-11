package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.block.BlockBellowPump;
import charcoalPit.items.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS=DeferredRegister.createItems(CharcoalPit.MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,CharcoalPit.MODID);

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> MAIN_TAB=CREATIVE_TABS.register("main_tab",
            ()-> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.charcoal_pit"))
                    .icon(Items.CHARCOAL::getDefaultInstance)
                    .displayItems((params,output)->{
                        ITEMS.getEntries().stream().map(Holder::value).map(Item::getDefaultInstance).forEach(output::accept);
                        fillItemTypes(output);
                    })
                    .build());

    //misc items
    public static final DeferredItem<Item> COKE=ITEMS.registerSimpleItem("coke");
    public static final DeferredItem<Item> COKE_POWDER=ITEMS.registerSimpleItem("coke_powder");
    public static final DeferredItem<ItemAeternalisFuel> AETERNALIS=ITEMS.register("aeternalis_fuel",
            ()->new ItemAeternalisFuel(new Item.Properties().rarity(Rarity.EPIC)));
    //chemicals
    public static final DeferredItem<Item> FLUX=ITEMS.registerSimpleItem("flux");
    public static final DeferredItem<Item> QUICKLIME=ITEMS.registerSimpleItem("quicklime");
    public static final DeferredItem<Item> CALCIUM_CARBIDE=ITEMS.registerSimpleItem("calcium_carbide");
    public static final DeferredItem<Item> SULFUR=ITEMS.registerSimpleItem("sulfur");
    public static final DeferredItem<Item> NITER=ITEMS.registerSimpleItem("niter");
    public static final DeferredItem<Item> OBSIDIAN_POWDER=ITEMS.registerSimpleItem("obsidian_powder");
    public static final DeferredItem<Item> TIN_DUST=ITEMS.registerSimpleItem("dust_tin");
    public static final DeferredItem<Item> PLATINUM_DUST=ITEMS.registerSimpleItem("dust_platinum",new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> ENDERIUM_DUST=ITEMS.registerSimpleItem("dust_enderium",new Item.Properties().rarity(Rarity.RARE).fireResistant());

    public static final DeferredItem<Item> SALT=ITEMS.registerSimpleItem("salt");
    public static final DeferredItem<Item> SALT_CAKE=ITEMS.registerSimpleItem("salt_cake");
    public static final DeferredItem<Item> BLACK_ASH=ITEMS.registerSimpleItem("black_ash");
    public static final DeferredItem<Item> NATRON=ITEMS.registerSimpleItem("natron");
    public static final DeferredItem<Item> LYE=ITEMS.registerSimpleItem("lye");
    public static final DeferredItem<Item> ALUM=ITEMS.registerSimpleItem("alum");
    public static final DeferredItem<Item> ALUMINA=ITEMS.registerSimpleItem("alumina");
    public static final DeferredItem<Item> ALUMINIUM_CHLORIDE=ITEMS.registerSimpleItem("aluminium_chloride");
    public static final DeferredItem<Item> GREEN_VITRIOL=ITEMS.registerSimpleItem("green_vitriol");
    public static final DeferredItem<Item> CINDER_FLOUR=ITEMS.registerSimpleItem("cinder_flour");
    public static final DeferredItem<Item> MORTAR_OF_TARTAR=ITEMS.registerSimpleItem("mortar_of_tartar");
    public static final DeferredItem<Item> ASH=ITEMS.registerSimpleItem("ash");
    public static final DeferredItem<BoneMealItem> FERTILIZER=ITEMS.register("fertilizer",
            ()->new BoneMealItem(new Item.Properties()));
    public static final DeferredItem<Item> GLYCERINE=ITEMS.registerSimpleItem("glycerine");
    public static final DeferredItem<Item> SMALL_GUNPOWDER=ITEMS.registerSimpleItem("tiny_gunpowder");
    public static final DeferredItem<Item> UNFIRED_SANDY_BRICK=ITEMS.registerSimpleItem("unfired_sandy_brick");
    public static final DeferredItem<Item> SANDY_BRICK=ITEMS.registerSimpleItem("sandy_brick");
    public static final DeferredItem<Item> UNFIRED_HIGH_REFRACTORY_BRICK=ITEMS.registerSimpleItem("unfired_high_refractory_brick");
    public static final DeferredItem<Item> HIGH_REFRACTORY_BRICK=ITEMS.registerSimpleItem("high_refractory_brick",new Item.Properties().fireResistant());

    public static final DeferredItem<Item> RAW_TIN_NUGGET=ITEMS.registerSimpleItem("raw_tin_nugget");
    public static final DeferredItem<Item> RAW_TIN=ITEMS.registerSimpleItem("raw_tin");
    public static final DeferredItem<Item> RAW_PLATINUM=ITEMS.registerSimpleItem("raw_platinum", new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> RAW_CHALCOCITE=ITEMS.registerSimpleItem("raw_chalcocite");

    //ingots
    public static final DeferredItem<Item> TIN=ITEMS.registerSimpleItem("ingot_tin");
    public static final DeferredItem<Item> BRONZE=ITEMS.registerSimpleItem("alloy_bronze");
    public static final DeferredItem<Item> PIG_IRON=ITEMS.registerSimpleItem("alloy_pig_iron");
    public static final DeferredItem<Item> STEEL=ITEMS.registerSimpleItem("alloy_steel");
    public static final DeferredItem<Item> SODIUM=ITEMS.register("ingot_sodium",
            ()->new ItemSodium(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ALUMINIUM=ITEMS.registerSimpleItem("ingot_aluminium",new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> ALUMITE=ITEMS.registerSimpleItem("alloy_alumite",new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> PLATINUM=ITEMS.registerSimpleItem("ingot_platinum",new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> ENDERIUM=ITEMS.registerSimpleItem("alloy_enderium",new Item.Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> ENDERIUM_DOUBLE=ITEMS.registerSimpleItem("double_ingot_enderium",new Item.Properties().rarity(Rarity.RARE).fireResistant());
    //public static final DeferredItem<Item> RED_ALLOY=ITEMS.registerSimpleItem("alloy_red");
    public static final DeferredItem<Item> RESONANT_BOTTLE=ITEMS.registerSimpleItem("resonant_bottle",new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).rarity(Rarity.UNCOMMON).stacksTo(16));
    public static final DeferredItem<ItemChargedEchoShard> CHARGED_ECHO_SHARD=ITEMS.register("charged_echo_shard",
            ()->new ItemChargedEchoShard(new Item.Properties().rarity(Rarity.UNCOMMON)));

    //other tools
    public static final DeferredItem<ItemBlockDynamite> DWARVEN_CANDLE=ITEMS.register("dwarven_candle",
            ()->new ItemBlockDynamite(BlockRegistry.DWARVEN_CANDLE.get(), new Item.Properties()));
    public static final DeferredItem<ItemDynamiteRemote> DYNAMITE_REMOTE=ITEMS.register("dynamite_remote",
            ()->new ItemDynamiteRemote(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CLAY_MOLD=ITEMS.registerSimpleItem("clay_mold");
    public static final DeferredItem<Item> ALLOY_MOLD=ITEMS.registerSimpleItem("alloy_mold");
    public static final DeferredItem<ItemAlloyMold> ALLOY_MOLD_FULL=ITEMS.register("alloy_mold_full",
            ()->new ItemAlloyMold(false,new Item.Properties().stacksTo(16).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)));
    public static final DeferredItem<ItemAlloyMold> ALLOY_MOLD_FINISHED=ITEMS.register("alloy_mold_finished",
            ()->new ItemAlloyMold(true,new Item.Properties().stacksTo(16).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)));
    public static final DeferredItem<ItemJerryCan> JERRY_CAN=ITEMS.register("jerry_can",
            ()->new ItemJerryCan(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<ItemMusket> MUSKET=ITEMS.register("musket",
            ()->new ItemMusket(new Item.Properties().stacksTo(1).durability(ToolTiers.STEEL.getUses()).attributes(SwordItem.createAttributes(ToolTiers.STEEL,0.5F,-2F))));


    public static final DeferredItem<ItemClub> CLUB=ITEMS.register("wooden_club",
            ()->new ItemClub(new Item.Properties().durability(59).component(DataComponents.TOOL, MaceItem.createToolProperties()).attributes(MaceItem.createAttributes())));
    public static final DeferredItem<ItemJavelin> JAVELIN=ITEMS.register("javelin",
            ()->new ItemJavelin(new Item.Properties().stacksTo(16).attributes(SwordItem.createAttributes(ToolTiers.FLINT,0.5F,-2F))));
    public static final DeferredItem<ItemJavelin> EXPLOSIVE_SPEAR=ITEMS.register("explosive_spear",
            ()->new ItemJavelin(new Item.Properties().stacksTo(16)));
    //copper tools
    public static final DeferredItem<PickaxeItem> COPPER_PICK=ITEMS.register("copper_pickaxe",
            ()->new PickaxeItem(ToolTiers.COPPER,new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.COPPER,1F,-2.8F))));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL=ITEMS.register("copper_shovel",
            ()->new ShovelItem(ToolTiers.COPPER,new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.COPPER,1.5F,-3F))));
    public static final DeferredItem<AxeItem> COPPER_AXE=ITEMS.register("copper_axe",
            ()->new AxeItem(ToolTiers.COPPER,new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.COPPER,6.75F,-3.2F))));
    public static final DeferredItem<SwordItem> COPPER_SWORD=ITEMS.register("copper_sword",
            ()->new SwordItem(ToolTiers.COPPER,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.COPPER,3F,-2.4F))));
    public static final DeferredItem<HoeItem> COPPER_HOE=ITEMS.register("copper_hoe",
            ()->new HoeItem(ToolTiers.COPPER,new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.COPPER,-1F,-2F))));
    //bronze tools
    public static final DeferredItem<PickaxeItem> BRONZE_PICK=ITEMS.register("bronze_pickaxe",
            ()->new PickaxeItem(ToolTiers.BRONZE,new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.BRONZE,1F,-2.8F))));
    public static final DeferredItem<ShovelItem> BRONZE_SHOVEL=ITEMS.register("bronze_shovel",
            ()->new ShovelItem(ToolTiers.BRONZE,new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.BRONZE,1.5F,-3F))));
    public static final DeferredItem<AxeItem> BRONZE_AXE=ITEMS.register("bronze_axe",
            ()->new AxeItem(ToolTiers.BRONZE,new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.BRONZE,6F,-3.1F))));
    public static final DeferredItem<SwordItem> BRONZE_SWORD=ITEMS.register("bronze_sword",
            ()->new SwordItem(ToolTiers.BRONZE,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.BRONZE,3F,-2.4F))));
    public static final DeferredItem<HoeItem> BRONZE_HOE=ITEMS.register("bronze_hoe",
            ()->new HoeItem(ToolTiers.BRONZE,new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.BRONZE,-2F,-1F))));
    public static final DeferredItem<ItemKnife> BRONZE_KNIFE=ITEMS.register("bronze_knife",
            ()->new ItemKnife(ToolTiers.BRONZE,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.BRONZE,0.5F,-2F))));
    //steel tools
    public static final DeferredItem<PickaxeItem> STEEL_PICK=ITEMS.register("steel_pickaxe",
            ()->new PickaxeItem(ToolTiers.STEEL,new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.STEEL,1F,-2.8F))));
    public static final DeferredItem<ShovelItem> STEEL_SHOVEL=ITEMS.register("steel_shovel",
            ()->new ShovelItem(ToolTiers.STEEL,new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.STEEL,1.5F,-3F))));
    public static final DeferredItem<AxeItem> STEEL_AXE=ITEMS.register("steel_axe",
            ()->new AxeItem(ToolTiers.STEEL,new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.STEEL,5F,-3F))));
    public static final DeferredItem<SwordItem> STEEL_SWORD=ITEMS.register("steel_sword",
            ()->new SwordItem(ToolTiers.STEEL,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.STEEL,3F,-2.4F))));
    public static final DeferredItem<HoeItem> STEEL_HOE=ITEMS.register("steel_hoe",
            ()->new HoeItem(ToolTiers.STEEL,new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.STEEL,-2.5F,-1F))));
    public static final DeferredItem<ItemKnife> STEEL_KNIFE=ITEMS.register("steel_knife",
            ()->new ItemKnife(ToolTiers.STEEL,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.STEEL,0.5F,-2F))));
    //alumite tools
    public static final DeferredItem<PickaxeItem> ALUMITE_PICK=ITEMS.register("alumite_pickaxe",
            ()->new PickaxeItem(ToolTiers.ALUMITE,new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.ALUMITE,1F,-2.8F)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<ShovelItem> ALUMITE_SHOVEL=ITEMS.register("alumite_shovel",
            ()->new ShovelItem(ToolTiers.ALUMITE,new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.ALUMITE,1.5F,-3F)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<AxeItem> ALUMITE_AXE=ITEMS.register("alumite_axe",
            ()->new AxeItem(ToolTiers.ALUMITE,new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.ALUMITE,6F,-3F)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<SwordItem> ALUMITE_SWORD=ITEMS.register("alumite_sword",
            ()->new SwordItem(ToolTiers.ALUMITE,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.ALUMITE,3F,-2.4F)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<HoeItem> ALUMITE_HOE=ITEMS.register("alumite_hoe",
            ()->new HoeItem(ToolTiers.ALUMITE,new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.ALUMITE,-2F,-1F)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<ItemKnife> ALUMINIUM_KNIFE=ITEMS.register("aluminium_knife",
            ()->new ItemKnife(ToolTiers.ALUMINIUM,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.ALUMINIUM,0.5F,-2F)).rarity(Rarity.UNCOMMON)));
    //enderium tools
    public static final DeferredItem<PickaxeItem> ENDERIUM_PICK=ITEMS.register("enderium_pickaxe",
            ()->new PickaxeItem(ToolTiers.ENDERIUM,new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.ENDERIUM,1F,-2.8F)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<ShovelItem> ENDERIUM_SHOVEL=ITEMS.register("enderium_shovel",
            ()->new ShovelItem(ToolTiers.ENDERIUM,new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.ENDERIUM,1.5F,-3F)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<AxeItem> ENDERIUM_AXE=ITEMS.register("enderium_axe",
            ()->new AxeItem(ToolTiers.ENDERIUM,new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.ENDERIUM,5F,-3F)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<SwordItem> ENDERIUM_SWORD=ITEMS.register("enderium_sword",
            ()->new SwordItem(ToolTiers.ENDERIUM,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.ENDERIUM,3F,-2.4F)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<HoeItem> ENDERIUM_HOE=ITEMS.register("enderium_hoe",
            ()->new HoeItem(ToolTiers.ENDERIUM,new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.ENDERIUM,-5F,-0F)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<MaceItem> ENDERIUM_MACE=ITEMS.register("enderium_mace",
            ()->new MaceItem(new Item.Properties().durability(1751).component(DataComponents.TOOL, MaceItem.createToolProperties()).attributes(createMaceAttributes()).rarity(Rarity.RARE).fireResistant()){
                @Override
                public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
                    return repairCandidate.is(ENDERIUM);
                }
            });
    public static final DeferredItem<ItemKnife> PLATINUM_KNIFE=ITEMS.register("platinum_knife",
            ()->new ItemKnife(ToolTiers.PLATINUM,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.PLATINUM,0.5F,-2F)).rarity(Rarity.UNCOMMON).fireResistant()));

    public static final DeferredItem<ItemSoulDrinker> SOUL_DRINKER=ITEMS.register("soul_drinker",
            ()->new ItemSoulDrinker(ToolTiers.SOUL_DRINKER,new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.SOUL_DRINKER,3F,-2.4F)).rarity(Rarity.UNCOMMON).fireResistant()));

    public static ItemAttributeModifiers createMaceAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -3.2F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 6.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    //copper armor
    public static final DeferredItem<ArmorItem> COPPER_HELMET=ITEMS.register("copper_helmet",
            ()->new ArmorItem(ArmorMaterials.COPPER, ArmorItem.Type.HELMET,new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(7))));
    public static final DeferredItem<ArmorItem> COPPER_CHESTPLATE=ITEMS.register("copper_chestplate",
            ()->new ArmorItem(ArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(7))));
    public static final DeferredItem<ArmorItem> COPPER_LEGGINGS=ITEMS.register("copper_leggings",
            ()->new ArmorItem(ArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS,new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(7))));
    public static final DeferredItem<ArmorItem> COPPER_BOOTS=ITEMS.register("copper_boots",
            ()->new ArmorItem(ArmorMaterials.COPPER, ArmorItem.Type.BOOTS,new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(7))));
    //bronze armor
    public static final DeferredItem<ArmorItem> BRONZE_HELMET=ITEMS.register("bronze_helmet",
            ()->new ArmorItem(ArmorMaterials.BRONZE, ArmorItem.Type.HELMET,new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final DeferredItem<ArmorItem> BRONZE_CHESTPLATE=ITEMS.register("bronze_chestplate",
            ()->new ArmorItem(ArmorMaterials.BRONZE, ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<ArmorItem> BRONZE_LEGGINGS=ITEMS.register("bronze_leggings",
            ()->new ArmorItem(ArmorMaterials.BRONZE, ArmorItem.Type.LEGGINGS,new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final DeferredItem<ArmorItem> BRONZE_BOOTS=ITEMS.register("bronze_boots",
            ()->new ArmorItem(ArmorMaterials.BRONZE, ArmorItem.Type.BOOTS,new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));
    //steel armor
    public static final DeferredItem<ArmorItem> STEEL_HELMET=ITEMS.register("steel_helmet",
            ()->new ArmorItem(ArmorMaterials.STEEL, ArmorItem.Type.HELMET,new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredItem<ArmorItem> STEEL_CHESTPLATE=ITEMS.register("steel_chestplate",
            ()->new ArmorItem(ArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredItem<ArmorItem> STEEL_LEGGINGS=ITEMS.register("steel_leggings",
            ()->new ArmorItem(ArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS,new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredItem<ArmorItem> STEEL_BOOTS=ITEMS.register("steel_boots",
            ()->new ArmorItem(ArmorMaterials.STEEL, ArmorItem.Type.BOOTS,new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(22))));
    //alumite armor
    public static final DeferredItem<ArmorItem> ALUMITE_HELMET=ITEMS.register("alumite_helmet",
            ()->new ArmorItem(ArmorMaterials.ALUMITE, ArmorItem.Type.HELMET,new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(27)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<ArmorItem> ALUMITE_CHESTPLATE=ITEMS.register("alumite_chestplate",
            ()->new ArmorItem(ArmorMaterials.ALUMITE, ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(27)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<ArmorItem> ALUMITE_LEGGINGS=ITEMS.register("alumite_leggings",
            ()->new ArmorItem(ArmorMaterials.ALUMITE, ArmorItem.Type.LEGGINGS,new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(27)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<ArmorItem> ALUMITE_BOOTS=ITEMS.register("alumite_boots",
            ()->new ArmorItem(ArmorMaterials.ALUMITE, ArmorItem.Type.BOOTS,new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(27)).rarity(Rarity.UNCOMMON)));
    //enderium armor
    public static final DeferredItem<ArmorItem> ENDERIUM_HELMET=ITEMS.register("enderium_helmet",
            ()->new ArmorItem(ArmorMaterials.ENDERIUM, ArmorItem.Type.HELMET,new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(35)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<ArmorItem> ENDERIUM_CHESTPLATE=ITEMS.register("enderium_chestplate",
            ()->new ArmorItem(ArmorMaterials.ENDERIUM, ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(35)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<ArmorItem> ENDERIUM_LEGGINGS=ITEMS.register("enderium_leggings",
            ()->new ArmorItem(ArmorMaterials.ENDERIUM, ArmorItem.Type.LEGGINGS,new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(35)).rarity(Rarity.RARE).fireResistant()));
    public static final DeferredItem<ArmorItem> ENDERIUM_BOOTS=ITEMS.register("enderium_boots",
            ()->new ArmorItem(ArmorMaterials.ENDERIUM, ArmorItem.Type.BOOTS,new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(35)).rarity(Rarity.RARE).fireResistant()));

    //foods
    public static final FoodProperties LEEK=new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).fast().build();
    public static final FoodProperties FARFETCH=new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).usingConvertsTo(Items.BOWL).build();
    public static final FoodProperties SERINAN=new FoodProperties.Builder().nutrition(10).saturationModifier(0.8F).usingConvertsTo(Items.BOWL).build();
    public static final FoodProperties KEBAB=new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).usingConvertsTo(Items.STICK).build();
    public static final FoodProperties DANGO=new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).usingConvertsTo(Items.STICK).build();
    public static final FoodProperties SQUID=new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties SQUID_COOKED=new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties SUNFLOWER=new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).fast().build();
    public static final FoodProperties RAW_FROG=new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.POISON,600,0),0.3F).build();
    public static final FoodProperties FROG=new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties CHOCOLATE=new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).fast().build();
    public static final FoodProperties CROISSANTE=new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final FoodProperties BUTTER_FOOD=new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.CONFUSION,200,0),0.8F).build();
    public static final FoodProperties CHESSE=new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build();
    public static final FoodProperties FOOD_CHERRY=new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).fast().build();
    public static final FoodProperties FOOD_WALNUT=new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build();
    public static final FoodProperties FOOD_ORANGE=new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties FOOD_RAW_OLIVE=new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.HUNGER,600,0),1F).build();
    public static final FoodProperties FOOD_OLIVE=new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties POTATO_FRIES=new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).build();
    public static final FoodProperties CHICKEN_INGOT=new FoodProperties.Builder().nutrition(7).saturationModifier(0.6F).build();
    public static final FoodProperties CHICKEN_NUG=new FoodProperties.Builder().nutrition(1).saturationModifier(0.35F).fast().build();
    public static final FoodProperties ALCOHOL=new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).alwaysEdible()
            .effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20*90),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.DIG_SPEED,20*90),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST,20*90),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.JUMP,20*90,1),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,20*90),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.HEALTH_BOOST,20*90),0.1F)
            .effect(()->new MobEffectInstance(MobEffects.BAD_OMEN,20*90),0.02F).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties ETHANOL=new FoodProperties.Builder().nutrition(1).saturationModifier(1.2F).alwaysEdible().usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties AMARANTH=new FoodProperties.Builder().nutrition(5).saturationModifier(1.2F).build();
    public static final FoodProperties PANCAKE=new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build();
    public static final FoodProperties CHERRY_JAM_FOOD=new FoodProperties.Builder().nutrition(6).saturationModifier(0.1F).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties CHERRY_SANDWITCH_FOOD=new FoodProperties.Builder().nutrition(7).saturationModifier(0.8F).build();
    public static final FoodProperties ORANGE_JUICE_FOOD=new FoodProperties.Builder().nutrition(2).saturationModifier(1.2F).alwaysEdible().usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties STRAWBERRY_FOOD=new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).fast().build();
    public static final FoodProperties HONEY_DEWOIS_FOOD=new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).alwaysEdible()
            .effect(()->new MobEffectInstance(MobEffects.DIG_SPEED,20*60*3),1F).usingConvertsTo(Items.GLASS_BOTTLE).build();


    public static final DeferredItem<Item> FLOUR=ITEMS.registerSimpleItem("flour", new Item.Properties());
    public static final DeferredItem<Item> RAW_CALAMARI=ITEMS.registerSimpleItem("calamari",new Item.Properties().food(SQUID));
    public static final DeferredItem<Item> COOKED_CALAMARI=ITEMS.registerSimpleItem("cooked_calamari",new Item.Properties().food(SQUID_COOKED));
    public static final DeferredItem<ItemNameBlockItem> LEEKS=ITEMS.register("leek",
            ()->new ItemNameBlockItem(BlockRegistry.LEEKS.get(), new Item.Properties().food(LEEK)));
    public static final DeferredItem<Item> FARFETCH_STEW=ITEMS.registerSimpleItem("farfetch_stew", new Item.Properties().stacksTo(16).food(FARFETCH));
    public static final DeferredItem<ItemNameBlockItem> SUNFLOWER_SEEDS=ITEMS.register("sunflower_seeds",
            ()->new ItemNameBlockItem(BlockRegistry.SUNFLOWER.get(), new Item.Properties().food(SUNFLOWER)));
    public static final DeferredItem<Item> SERINAN_STEW=ITEMS.registerSimpleItem("serinan_stew", new Item.Properties().stacksTo(16).food(SERINAN));
    public static final DeferredItem<Item> KEBABS=ITEMS.registerSimpleItem("kebabs",new Item.Properties().food(KEBAB));
    public static final DeferredItem<Item> DANGOS=ITEMS.registerSimpleItem("tricolor_dango",new Item.Properties().food(DANGO));
    public static final DeferredItem<Item> FROG_LEG=ITEMS.registerSimpleItem("frog_leg_raw",new Item.Properties().food(RAW_FROG));
    public static final DeferredItem<Item> FROG_LEG_COOKED=ITEMS.registerSimpleItem("frog_leg_cooked",new Item.Properties().food(FROG));
    public static final DeferredItem<Item> CHOCOLATE_BAR=ITEMS.registerSimpleItem("chocolate_bar",new Item.Properties().food(CHOCOLATE));
    public static final DeferredItem<Item> CROISSANT=ITEMS.registerSimpleItem("croissant",new Item.Properties().food(CROISSANTE));
    public static final DeferredItem<Item> CHEESE=ITEMS.registerSimpleItem("cheese",new Item.Properties().food(CHESSE));
    public static final DeferredItem<Item> PANCAKES=ITEMS.registerSimpleItem("pancakes",new Item.Properties().food(PANCAKE));
    //public static final DeferredItem<Item> RAW_VEGEMITE=ITEMS.registerSimpleItem("raw_vegemite",new Item.Properties().food(RVEG));
    public static final DeferredItem<Item> POISONOUS_POTATO_FRIES=ITEMS.registerSimpleItem("poisonous_potato_fries",new Item.Properties().food(POTATO_FRIES));
    public static final DeferredItem<Item> INGOT_CHICKEN=ITEMS.registerSimpleItem("ingot_chicken",new Item.Properties().food(CHICKEN_INGOT));
    public static final DeferredItem<Item> NUGGET_CHICKEN=ITEMS.registerSimpleItem("nugget_chicken",new Item.Properties().food(CHICKEN_NUG));
    public static final DeferredItem<Item> AMARANTH_BREAD=ITEMS.registerSimpleItem("amaranth_bread",new Item.Properties().food(AMARANTH));
    public static final DeferredItem<ItemDrinkableBottle> CHERRY_JAM=ITEMS.register("cherry_jam",
            ()->new ItemDrinkableBottle(false,new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(CHERRY_JAM_FOOD)));
    public static final DeferredItem<Item> CHERRY_SANDWITCH=ITEMS.registerSimpleItem("cherry_sandwitch",new Item.Properties().food(CHERRY_SANDWITCH_FOOD));
    public static final DeferredItem<ItemDrinkableBottle> ORANGE_JUICE=ITEMS.register("orange_juice",
            ()->new ItemDrinkableBottle(true,new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(ORANGE_JUICE_FOOD)));
    public static final DeferredItem<Item> CHERRY=ITEMS.registerSimpleItem("cherry",new Item.Properties().food(FOOD_CHERRY));
    public static final DeferredItem<Item> WALNUT=ITEMS.registerSimpleItem("walnut",new Item.Properties().food(FOOD_WALNUT));
    public static final DeferredItem<Item> ORANGE=ITEMS.registerSimpleItem("orange",new Item.Properties().food(FOOD_ORANGE));
    public static final DeferredItem<Item> OLIVES=ITEMS.registerSimpleItem("olives",new Item.Properties().food(FOOD_OLIVE));
    public static final DeferredItem<Item> STRAWBERRY=ITEMS.registerSimpleItem("strawberry",new Item.Properties().food(STRAWBERRY_FOOD));

    public static final DeferredItem<ItemAlcoholBottle> ALCOHOL_BOTTLE=ITEMS.register("alcohol_bottle",
            ()->new ItemAlcoholBottle(1,new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(ALCOHOL)));
    public static final DeferredItem<ItemAlcoholBottle> ETHANOL_BOTTLE=ITEMS.register("ethanol_bottle",
            ()->new ItemAlcoholBottle(8,new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(ETHANOL)));
    public static final DeferredItem<ItemAlcoholBottle> HONEY_DEWOIS=ITEMS.register("honey_dewois",
            ()->new ItemAlcoholBottle(2,new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(HONEY_DEWOIS_FOOD)));

    public static final DeferredItem<BlockItem> GRAVEL_TIN=ITEMS.registerSimpleBlockItem(BlockRegistry.GRAVEL_TIN);
    public static final DeferredItem<BlockItem> ORE_TIN=ITEMS.registerSimpleBlockItem(BlockRegistry.ORE_TIN);
    public static final DeferredItem<BlockItem> ORE_PLATINUM=ITEMS.registerSimpleBlockItem(BlockRegistry.ORE_PLATINUM, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<BlockItem> ORE_DEEPSLATE_PLATINUM=ITEMS.registerSimpleBlockItem(BlockRegistry.ORE_DEEPSLATE_PLATINUM, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<BlockItem> ORE_CHALCOCITE=ITEMS.registerSimpleBlockItem(BlockRegistry.ORE_CHALCOCITE);
    public static final DeferredItem<BlockItem> RAW_CHALCOCITE_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.RAW_CHALCOCITE_BLOCK);

    //natural blocks
    public static final DeferredItem<BlockItem> BASALT=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT);
    public static final DeferredItem<BlockItem> BASALT_POLISHED=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_POLISHED);
    public static final DeferredItem<BlockItem> BASALT_BRICKS=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_BRICKS);
    public static final DeferredItem<BlockItem> BASALT_SLAB=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_SLAB);
    public static final DeferredItem<BlockItem> BASALT_STAIRS=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_STAIRS);
    public static final DeferredItem<BlockItem> BASALT_WALL=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_WALL);
    public static final DeferredItem<BlockItem> BASALT_PILLAR=ITEMS.registerSimpleBlockItem(BlockRegistry.BASALT_PILLAR);

    public static final DeferredItem<BlockItem> MARBLE=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE);
    public static final DeferredItem<BlockItem> MARBLE_POLISHED=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_POLISHED);
    public static final DeferredItem<BlockItem> MARBLE_BRICKS=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_BRICKS);
    public static final DeferredItem<BlockItem> MARBLE_SLAB=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_SLAB);
    public static final DeferredItem<BlockItem> MARBLE_STAIRS=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_STAIRS);
    public static final DeferredItem<BlockItem> MARBLE_WALL=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_WALL);
    public static final DeferredItem<BlockItem> MARBLE_PILLAR=ITEMS.registerSimpleBlockItem(BlockRegistry.MARBLE_PILLAR);

    public static final DeferredItem<BlockItem> BRONZE_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.BRONZE_BLOCK);
    public static final DeferredItem<BlockItem> STEEL_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> TIN_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.TIN_BLOCK);
    public static final DeferredItem<BlockItem> ALUMINIUM_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.ALUMINIUM_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<BlockItem> ALUMITE_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.ALUMITE_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<BlockItem> PLATINUM_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.PLATINUM_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<BlockItem> ENDERIUM_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.ENDERIUM_BLOCK, new Item.Properties().rarity(Rarity.RARE).fireResistant());

    public static final DeferredItem<BlockItem> LOG_PILE=ITEMS.registerSimpleBlockItem(BlockRegistry.LOG_PILE);

    public static final DeferredItem<BlockItem> WOOD_ASH=ITEMS.register("wood_ash",
            ()->new MimicBlockItem(BlockRegistry.WOOD_ASH.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> COAL_ASH=ITEMS.register("coal_ash",
            ()->new MimicBlockItem(BlockRegistry.COAL_ASH.get(),new Item.Properties()));
    public static final DeferredItem<BlockItem> ASH_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.ASH_BLOCK);

    public static final DeferredItem<BlockItem> CHARCOAL_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.CHARCOAL_BLOCK);
    public static final DeferredItem<BlockItem> BAMBOO_CHARCOAL=ITEMS.registerSimpleBlockItem(BlockRegistry.BAMBOO_CHARCOAL);
    public static final DeferredItem<BlockItem> COKE_BLOCK=ITEMS.registerSimpleBlockItem(BlockRegistry.COKE_BLOCK);

    public static final DeferredItem<BlockItem> SANDY_BRICKS=ITEMS.registerSimpleBlockItem(BlockRegistry.SANDY_BRICKS);
    public static final DeferredItem<BlockItem> SANDY_SLAB=ITEMS.registerSimpleBlockItem(BlockRegistry.SANDY_SLAB);
    public static final DeferredItem<BlockItem> SANDY_STAIRS=ITEMS.registerSimpleBlockItem(BlockRegistry.SANDY_STAIRS);
    public static final DeferredItem<BlockItem> SANDY_WALL=ITEMS.registerSimpleBlockItem(BlockRegistry.SANDY_WALL);
    public static final DeferredItem<BlockItem> HIGH_REFRACTORY_BRICKS=ITEMS.registerSimpleBlockItem(BlockRegistry.HIGH_REFRACTORY_BRICKS,new Item.Properties().fireResistant());

    public static final DeferredItem<BlockItem> BELLOWS=ITEMS.registerSimpleBlockItem(BlockRegistry.BELLOWS);
    public static final DeferredItem<BlockItem> MECHANICAL_BELLOWS=ITEMS.registerSimpleBlockItem(BlockRegistry.MECHANICAL_BELLOWS);

    public static final DeferredItem<BlockItem> BLOOMERY_BRICK=ITEMS.registerSimpleBlockItem(BlockRegistry.BLOOMERY_BRICK);
    public static final DeferredItem<BlockItem> BLOOMERY_SANDY=ITEMS.registerSimpleBlockItem(BlockRegistry.BLOOMERY_SANDY);
    public static final DeferredItem<BlockItem> BLOOMERY_NETHER=ITEMS.registerSimpleBlockItem(BlockRegistry.BLOOMERY_NETHER);

    public static final DeferredItem<BlockItem> BLOOM=ITEMS.registerSimpleBlockItem(BlockRegistry.BLOOM);

    public static final DeferredItem<BlockItem> BLAST_FURNACE=ITEMS.registerSimpleBlockItem(BlockRegistry.BLAST_FURNACE);

    public static final DeferredItem<BlockItem> COKE_OVEN=ITEMS.registerSimpleBlockItem(BlockRegistry.COKE_OVEN);

    public static final DeferredItem<ItemBarrel> BARREL=ITEMS.register("barrel",
            ()->new ItemBarrel(BlockRegistry.BARREL.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> QUERN=ITEMS.registerSimpleBlockItem(BlockRegistry.QUERN);
    public static final DeferredItem<BlockItem> NEST_BOX=ITEMS.registerSimpleBlockItem(BlockRegistry.NEST_BOX);
    public static final DeferredItem<BlockItem> FEEDING_THROUGH=ITEMS.registerSimpleBlockItem(BlockRegistry.FEEDING_THROUGH);

    public static final DeferredItem<BlockItem> STILL=ITEMS.registerSimpleBlockItem(BlockRegistry.STILL);
    public static final DeferredItem<BlockItem> PRESS=ITEMS.registerSimpleBlockItem(BlockRegistry.PRESS);

    public static final DeferredItem<BlockItem> BELLOW_PUMP=ITEMS.registerSimpleBlockItem(BlockRegistry.BELLOW_PUMP);

    /*public static final DeferredItem<BlockItem> CRUSHER=ITEMS.registerSimpleBlockItem(BlockRegistry.CRUSHER);

    public static final DeferredItem<BlockItem> GENERATOR=ITEMS.registerSimpleBlockItem(BlockRegistry.GENERATOR);*/

    public static final DeferredItem<Item> CLAY_POT=ITEMS.registerSimpleItem("clay_pot");
    public static final DeferredItem<BlockItem> CERAMIC_POT=ITEMS.register("ceramic_pot",
            ()->new ItemCeramicPot(BlockRegistry.CERAMIC_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> WHITE_POT=ITEMS.register("white_pot",
            ()->new ItemCeramicPot(BlockRegistry.WHITE_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LIGHT_GRAY_POT=ITEMS.register("light_gray_pot",
            ()->new ItemCeramicPot(BlockRegistry.LIGHT_GRAY_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> GRAY_POT=ITEMS.register("gray_pot",
            ()->new ItemCeramicPot(BlockRegistry.GRAY_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> BLACK_POT=ITEMS.register("black_pot",
            ()->new ItemCeramicPot(BlockRegistry.BLACK_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> BROWN_POT=ITEMS.register("brown_pot",
            ()->new ItemCeramicPot(BlockRegistry.BROWN_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> RED_POT=ITEMS.register("red_pot",
            ()->new ItemCeramicPot(BlockRegistry.RED_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> ORANGE_POT=ITEMS.register("orange_pot",
            ()->new ItemCeramicPot(BlockRegistry.ORANGE_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> YELLOW_POT=ITEMS.register("yellow_pot",
            ()->new ItemCeramicPot(BlockRegistry.YELLOW_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LIME_POT=ITEMS.register("lime_pot",
            ()->new ItemCeramicPot(BlockRegistry.LIME_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> GREEN_POT=ITEMS.register("green_pot",
            ()->new ItemCeramicPot(BlockRegistry.GREEN_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> CYAN_POT=ITEMS.register("cyan_pot",
            ()->new ItemCeramicPot(BlockRegistry.CYAN_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LIGHT_BLUE_POT=ITEMS.register("light_blue_pot",
            ()->new ItemCeramicPot(BlockRegistry.LIGHT_BLUE_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> BLUE_POT=ITEMS.register("blue_pot",
            ()->new ItemCeramicPot(BlockRegistry.BLUE_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> PURPLE_POT=ITEMS.register("purple_pot",
            ()->new ItemCeramicPot(BlockRegistry.PURPLE_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> MAGENTA_POT=ITEMS.register("magenta_pot",
            ()->new ItemCeramicPot(BlockRegistry.MAGENTA_POT.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> PINK_POT=ITEMS.register("pink_pot",
            ()->new ItemCeramicPot(BlockRegistry.PINK_POT.get(), new Item.Properties().stacksTo(16)));

    public static final DeferredItem<BlockItem> APPLE_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.APPLE_SAPLING);
    public static final DeferredItem<BlockItem> CHERRY_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.CHERRY_SAPLING);
    public static final DeferredItem<BlockItem> OLIVE_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.OLIVE_SAPLING);
    public static final DeferredItem<BlockItem> ORANGE_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.ORANGE_SAPLING);
    public static final DeferredItem<BlockItem> WALNUT_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_SAPLING);
    public static final DeferredItem<BlockItem> DOUGLAS_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.DOUGLAS_SAPLING);
    public static final DeferredItem<BlockItem> AMARANTH_SAPLING=ITEMS.registerSimpleBlockItem(BlockRegistry.AMARANTH_SAPLING,new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<BlockItem> STRAWBERRY_BUSH=ITEMS.registerSimpleBlockItem(BlockRegistry.STRAWBERRY_BUSH);

    public static final DeferredItem<BlockItem> APPLE_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.APPLE_LEAVES);
    public static final DeferredItem<BlockItem> CHERRY_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.CHERRY_LEAVES);
    public static final DeferredItem<BlockItem> OLIVE_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.OLIVE_LEAVES);
    public static final DeferredItem<BlockItem> ORANGE_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.ORANGE_LEAVES);
    public static final DeferredItem<BlockItem> WALNUT_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_LEAVES);
    public static final DeferredItem<BlockItem> DOUGLAS_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.DOUGLAS_LEAVES);
    public static final DeferredItem<BlockItem> AMARANTH_LEAVES=ITEMS.registerSimpleBlockItem(BlockRegistry.AMARANTH_LEAVES);

    public static void fillItemTypes(CreativeModeTab.Output output){
        ItemStack APPLE1=new ItemStack(APPLE_LEAVES.get());
        ItemStack APPLE2=APPLE1.copy();
        ItemStack APPLE3=APPLE1.copy();
        APPLE1.set(DataComponentRegistry.FRUIT_LEAVES_STATE,1);
        APPLE2.set(DataComponentRegistry.FRUIT_LEAVES_STATE,2);
        APPLE3.set(DataComponentRegistry.FRUIT_LEAVES_STATE,3);
        output.accept(APPLE1);
        output.accept(APPLE2);
        output.accept(APPLE3);
        ItemStack CHERRY1=new ItemStack(CHERRY_LEAVES.get());
        ItemStack CHERRY2= CHERRY1.copy();
        ItemStack CHERRY3=CHERRY1.copy();
        CHERRY1.set(DataComponentRegistry.FRUIT_LEAVES_STATE,1);
        CHERRY2.set(DataComponentRegistry.FRUIT_LEAVES_STATE,2);
        CHERRY3.set(DataComponentRegistry.FRUIT_LEAVES_STATE,3);
        output.accept(CHERRY1);
        output.accept(CHERRY2);
        output.accept(CHERRY3);
        ItemStack OLIVE1=new ItemStack(OLIVE_LEAVES.get());
        ItemStack OLIVE2=OLIVE1.copy();
        ItemStack OLIVE3=OLIVE1.copy();
        OLIVE1.set(DataComponentRegistry.FRUIT_LEAVES_STATE,1);
        OLIVE2.set(DataComponentRegistry.FRUIT_LEAVES_STATE,2);
        OLIVE3.set(DataComponentRegistry.FRUIT_LEAVES_STATE,3);
        output.accept(OLIVE1);
        output.accept(OLIVE2);
        output.accept(OLIVE3);
        ItemStack ORANGE1=new ItemStack(ORANGE_LEAVES.get());
        ItemStack ORANGE2=ORANGE1.copy();
        ItemStack ORANGE3=ORANGE1.copy();
        ORANGE1.set(DataComponentRegistry.FRUIT_LEAVES_STATE,1);
        ORANGE2.set(DataComponentRegistry.FRUIT_LEAVES_STATE,2);
        ORANGE3.set(DataComponentRegistry.FRUIT_LEAVES_STATE,3);
        output.accept(ORANGE1);
        output.accept(ORANGE2);
        output.accept(ORANGE3);
        ItemStack WALNUT1=new ItemStack(WALNUT_LEAVES.get());
        ItemStack WALNUT2=WALNUT1.copy();
        ItemStack WALNUT3=WALNUT1.copy();
        WALNUT1.set(DataComponentRegistry.FRUIT_LEAVES_STATE,1);
        WALNUT2.set(DataComponentRegistry.FRUIT_LEAVES_STATE,2);
        WALNUT3.set(DataComponentRegistry.FRUIT_LEAVES_STATE,3);
        output.accept(WALNUT1);
        output.accept(WALNUT2);
        output.accept(WALNUT3);
    }

}
