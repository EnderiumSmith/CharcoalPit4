package charcoalPit.items;

import java.util.function.Consumer;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ToolTiers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class ItemSoulDrinker extends SwordItem {
	public ItemSoulDrinker(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponentRegistry.SOULS_DRANK,0).component(DataComponentRegistry.SOUL_LEVEL,4));
	}

	public static void charge(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(target.isDeadOrDying()) {
			int souls=stack.get(DataComponentRegistry.SOULS_DRANK)+1;
			int level=stack.get(DataComponentRegistry.SOUL_LEVEL);
			if(Math.pow(level,3)<=souls){
				//souls=0;
				level++;
				stack.set(DataComponents.ATTRIBUTE_MODIFIERS,createAttributes(ToolTiers.SOUL_DRINKER,level-1,-2.4F));
				attacker.level().playSound(null, attacker.blockPosition(),SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1F, 1F);
			}
			stack.set(DataComponentRegistry.SOULS_DRANK,souls);
			stack.set(DataComponentRegistry.SOUL_LEVEL,level);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBarColor(ItemStack stack) {
		if(Screen.hasShiftDown()){
			return 0x29DFEB;
		}
		return super.getBarColor(stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBarWidth(ItemStack stack) {
		if(Screen.hasShiftDown()){
			int souls=stack.get(DataComponentRegistry.SOULS_DRANK);
			int lvl=stack.get(DataComponentRegistry.SOUL_LEVEL);
			double level=Math.pow(lvl,3);
			if(lvl>4){
				double lm1=Math.pow(lvl-1,3);
				level-=lm1;
				souls-=lm1;
			}
			return Math.round((float)souls * 13.0F / (float)level);
		}
		return super.getBarWidth(stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isBarVisible(ItemStack stack) {
		if(Screen.hasShiftDown())
			return true;
		return super.isBarVisible(stack);
	}

}
