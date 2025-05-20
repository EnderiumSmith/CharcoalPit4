package charcoalPit.core;

import java.util.ArrayList;

import charcoalPit.CharcoalPit;
import charcoalPit.effect.ModPotions;
import charcoalPit.effect.PotionRecipe;
import charcoalPit.items.ItemCeramicPot;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import charcoalPit.items.ItemSoulDrinker;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(modid = CharcoalPit.MODID)
public class EventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderMusket(RenderPlayerEvent.Pre event){
        if(event.getEntity().getMainHandItem().getItem()==ItemRegistry.MUSKET.get() && !event.getEntity().swinging && event.getEntity().getMainHandItem().has(DataComponentRegistry.MUSKET_LOADED.get())){
            if(event.getEntity().getMainArm()== HumanoidArm.RIGHT)
                event.getRenderer().getModel().rightArmPose= HumanoidModel.ArmPose.BOW_AND_ARROW;
            else
                event.getRenderer().getModel().leftArmPose=HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
    }

    @SubscribeEvent
    public static void changeRepairMaterials(AnvilUpdateEvent event){
        if(isRepairMaterialRemoved(event.getLeft(),event.getRight())){
            event.setCanceled(true);
        }
        if(event.getLeft().isDamageableItem()&&isItemRepairMaterial(event.getLeft(),event.getRight())){
            ItemStack itemstack1=event.getLeft().copy();
            int i=0;
            int l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
            int j3;
            for (j3 = 0; l2 > 0 && j3 < event.getRight().getCount(); j3++) {
                int k3 = itemstack1.getDamageValue() - l2;
                itemstack1.setDamageValue(k3);
                i++;
                l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
            }
            int i3 = itemstack1.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0));
            int k2 = (int)Mth.clamp(i3 + (long)i, 0L, 2147483647L);
            event.setCost(k2);
            i3=AnvilMenu.calculateIncreasedRepairCost(i3);
            itemstack1.set(DataComponents.REPAIR_COST, i3);
            if(i>0){
                event.setMaterialCost(i);
                event.setOutput(itemstack1);
            }
        }
    }

    public static boolean isRepairMaterialRemoved(ItemStack left, ItemStack right){
        return (left.getItem() == Items.WOODEN_PICKAXE || left.getItem() == Items.STONE_PICKAXE || left.getItem() == Items.STONE_SHOVEL || left.getItem() == Items.STONE_AXE ||
                left.getItem() == Items.STONE_SWORD || left.getItem() == Items.STONE_HOE) && left.getItem().isValidRepairItem(left, right);
    }

    public static boolean isItemRepairMaterial(ItemStack left, ItemStack right){
        if(left.getItem()== Items.WOODEN_PICKAXE&&right.getItem()== Items.BONE){
            return true;
        }
        return right.getItem() == Items.FLINT && (left.getItem() == Items.STONE_PICKAXE || left.getItem() == Items.STONE_SHOVEL || left.getItem() == Items.STONE_AXE ||
                left.getItem() == Items.STONE_SWORD || left.getItem() == Items.STONE_HOE);
    }

    @SubscribeEvent
    public static void combineSoulDrinkers(AnvilRepairEvent event){
        if(event.getLeft().getItem()==ItemRegistry.SOUL_DRINKER.get()&&event.getRight().getItem()==ItemRegistry.SOUL_DRINKER.get()){
            event.getOutput().set(DataComponentRegistry.SOULS_DRANK,event.getOutput().get(DataComponentRegistry.SOULS_DRANK)+event.getRight().get(DataComponentRegistry.SOULS_DRANK));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chargeSoulDrinker(LivingDeathEvent event){
        if(!event.isCanceled()&&event.getSource().isDirect()&&event.getSource().getDirectEntity() instanceof Player player){
            if(player.getMainHandItem().getItem()==ItemRegistry.SOUL_DRINKER.get()){
                if(event.getSource().is(DamageTypeTags.IS_PLAYER_ATTACK)&&!event.getSource().is(DamageTypeTags.IS_PROJECTILE)){
                    ItemSoulDrinker.charge(player.getMainHandItem(),event.getEntity(),player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void addSnifferLoot(LootTableLoadEvent event){
        if(event.getName().equals(BuiltInLootTables.SNIFFER_DIGGING.location())){
            LootPool main=event.getTable().getPool("main");
            if(main!=null){
                ArrayList<LootPoolEntryContainer> entries = new ArrayList<>(main.entries);
                entries.add(LootItem.lootTableItem(ItemRegistry.AMARANTH_SAPLING.asItem()).build());
                main.entries = entries;
            }
        }
    }

    /*public static void fillGoldPan(UseItemOnBlockEvent event){
        if(event.getPlayer()!=null&&event.getUsePhase()== UseItemOnBlockEvent.UsePhase.BLOCK&&event.getItemStack().getItem()==Items.BOWL){
            if(event.getLevel().getBlockState(event.getPos()).is(Tags.Blocks.GRAVELS)){
                if(!event.getPlayer().isCreative()){
                    event.getPlayer().getItemInHand(event.getHand()).shrink(1);
                    event.getLevel().removeBlock(event.getPos(),false);
                }
                if(event.getPlayer().getItemInHand(event.getHand()).isEmpty()){
                    event.getPlayer().setItemInHand(event.getHand(),new ItemStack(ItemRegistry.GOLD_PAN.get()));
                }else {
                    ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), new ItemStack(ItemRegistry.GOLD_PAN.get()));
                }
                event.getLevel().playLocalSound(event.getPlayer(), SoundEvents.GRAVEL_BREAK, SoundSource.BLOCKS,1F,1F);
                event.cancelWithResult(ItemInteractionResult.SUCCESS);
            }
        }
    }*/

    /*public static void ignitePiles(BlockEvent.NeighborNotifyEvent event){
        if(event.getState().getBlock() instanceof BaseFireBlock){
            for(Direction dir:event.getNotifiedSides()){
                BlockPos newPos=event.getPos().relative(dir);
                BlockState newState=event.getLevel().getBlockState(newPos);
                if(newState.getBlock()==Blocks.COAL_BLOCK){
                    event.getLevel().setBlock(newPos,BlockRegistry.ACTIVE_COAL_PILE.get().defaultBlockState(),3);
                    event.getLevel().playSound(null,newPos,SoundEvents.FIRECHARGE_USE,SoundSource.BLOCKS,1F,1F);
                }else if(newState.getBlock()==BlockRegistry.LOG_PILE.get()){
                    event.getLevel().setBlock(newPos,BlockRegistry.ACTIVE_LOG_PILE.get().defaultBlockState().setValue(BlockStateProperties.AXIS,newState.getValue(BlockStateProperties.AXIS)),3);
                    event.getLevel().playSound(null,newPos,SoundEvents.FIRECHARGE_USE,SoundSource.BLOCKS,1F,1F);
                }
            }
        }
    }*/

    public static final ResourceKey<MobEffect> DRUNK=ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"drunk"));

    @SubscribeEvent
    public static void drunk(MobEffectEvent.Expired event){
        if(event.getEffectInstance()!=null&&event.getEffectInstance().getEffect().is(DRUNK)&&event.getEffectInstance().getAmplifier()>0){
            int level=event.getEffectInstance().getAmplifier()-1;
            event.getEntity().removeEffect(ModPotions.DRUNK);
            event.getEntity().addEffect(new MobEffectInstance(ModPotions.DRUNK,20*60*3,level));
        }
    }

    @SubscribeEvent
    public static void potionRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(new PotionRecipe(Potions.LONG_REGENERATION, ItemRegistry.AMARANTH_SAPLING.get(), ModPotions.HEALTH_BOOST,0x8c000f));
        event.getBuilder().addRecipe(new PotionRecipe(ModPotions.HEALTH_BOOST, Items.REDSTONE, ModPotions.HEALTH_BOOST_EXTENDED,0x8c000f));
        event.getBuilder().addRecipe(new PotionRecipe(ModPotions.HEALTH_BOOST, Items.GLOWSTONE_DUST, ModPotions.HEALTH_BOOST_ENHANCED,0x8c000f));
        event.getBuilder().addRecipe(new PotionRecipe(Potions.STRONG_HEALING, ItemRegistry.AMARANTH_SAPLING.get(), ModPotions.ABSORPTION,0xd648d7));
        event.getBuilder().addRecipe(new PotionRecipe(ModPotions.ABSORPTION, Items.REDSTONE, ModPotions.ABSORPTION_EXTENDED,0xd648d7));
        event.getBuilder().addMix(Potions.LONG_REGENERATION, ItemRegistry.AMARANTH_SAPLING.get(), ModPotions.HEALTH_BOOST);
        event.getBuilder().addMix(Potions.STRONG_HEALING, ItemRegistry.AMARANTH_SAPLING.get(), ModPotions.ABSORPTION);
    }

    //public static VillagerTrades.ItemListing Cherry_Sapling=new BasicItemListing(6,new ItemStack(ItemRegistry.CHERRY_SAPLING.get()),6,1);
    //public static VillagerTrades.ItemListing Apple_Sapling=new BasicItemListing(12,new ItemStack(ItemRegistry.APPLE_SAPLING.get()),6,1);

    @SubscribeEvent
    public static void wandererTrades(WandererTradesEvent event){
        event.getRareTrades().add(new BasicItemListing(6,new ItemStack(ItemRegistry.CHERRY_SAPLING.get()),6,1));
        event.getRareTrades().add(new BasicItemListing(12,new ItemStack(ItemRegistry.APPLE_SAPLING.get()),6,1));
    }

    //public static VillagerTrades.ItemListing Leek=new BasicItemListing(new ItemStack(ItemRegistry.LEEKS.get(),20),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,2,0.05F);
    //public static VillagerTrades.ItemListing Chocolate=new BasicItemListing(new ItemStack(ItemRegistry.CHOCOLATE_BAR.get(),6),ItemStack.EMPTY, new ItemStack(Items.EMERALD),12,15,0.05F);
    //public static VillagerTrades.ItemListing Dango=new BasicItemListing(1,new ItemStack(ItemRegistry.DANGOS.get(),6),12,30,0.05F);

    //public static VillagerTrades.ItemListing Cheese=new BasicItemListing(new ItemStack(ItemRegistry.CHEESE.get(),7),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,20,0.05F);
    //public static VillagerTrades.ItemListing Frog=new BasicItemListing(new ItemStack(ItemRegistry.FROG_LEG.get(),4),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,2,0.05F);
    //public static VillagerTrades.ItemListing Alcohol=new BasicItemListing(new ItemStack(ItemRegistry.ALCOHOL_BOTTLE.get(),4),ItemStack.EMPTY, new ItemStack(Items.EMERALD),12,30,0.05F);
    //public static VillagerTrades.ItemListing Kebab=new BasicItemListing(1,new ItemStack(ItemRegistry.KEBABS.get(),6),12,30,0.05F);

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event){
        if(event.getType()== VillagerProfession.BUTCHER){
            event.getTrades().get(1).add(new BasicItemListing(new ItemStack(ItemRegistry.FROG_LEG.get(),4),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,2,0.05F));
            event.getTrades().get(3).add(new BasicItemListing(new ItemStack(ItemRegistry.CHEESE.get(),7),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,20,0.05F));
            event.getTrades().get(4).add(new BasicItemListing(new ItemStack(ItemRegistry.ALCOHOL_BOTTLE.get(),4),ItemStack.EMPTY, new ItemStack(Items.EMERALD),12,30,0.05F));
            event.getTrades().get(5).add(new BasicItemListing(1,new ItemStack(ItemRegistry.KEBABS.get(),6),12,30,0.05F));
        }
        if(event.getType()==VillagerProfession.FARMER){
            event.getTrades().get(1).add(new BasicItemListing(new ItemStack(ItemRegistry.LEEKS.get(),20),ItemStack.EMPTY, new ItemStack(Items.EMERALD),16,2,0.05F));
            event.getTrades().get(4).add(new BasicItemListing(new ItemStack(ItemRegistry.CHOCOLATE_BAR.get(),6),ItemStack.EMPTY, new ItemStack(Items.EMERALD),12,15,0.05F));
            event.getTrades().get(5).add(new BasicItemListing(1,new ItemStack(ItemRegistry.DANGOS.get(),6),12,30,0.05F));
        }
    }

    @SubscribeEvent
    public static void savaPorInventory(PlayerEvent.ItemCraftedEvent event){
        if(event.getCrafting().getItem() instanceof ItemCeramicPot){
            for(int i=0;i<event.getInventory().getContainerSize();i++){
                if(event.getInventory().getItem(i).getItem() instanceof ItemCeramicPot){
                    if(event.getInventory().getItem(i).has(DataComponents.CONTAINER)){
                        event.getCrafting().set(DataComponents.CONTAINER,event.getInventory().getItem(i).get(DataComponents.CONTAINER));
                    }
                    return;
                }
            }
        }
    }
}
