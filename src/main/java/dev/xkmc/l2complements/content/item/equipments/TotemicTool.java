package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TotemicTool extends ExtraToolConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		if (entity instanceof Player player) {
			player.heal(amount);
		}
		return super.damageItem(stack, amount, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.TOTEMIC_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack stack) {
		if (cache.getAttackTarget().getMobType() == MobType.UNDEAD) {
			cache.addHurtModifier(DamageModifier.multAttr((float) (1 + LCConfig.COMMON.mobTypeBonus.get())));
		}
	}

}
