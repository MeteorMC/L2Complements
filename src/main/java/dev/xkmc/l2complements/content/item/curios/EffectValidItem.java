package dev.xkmc.l2complements.content.item.curios;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EffectValidItem {

	boolean isEffectValid(MobEffectInstance ins, ItemStack stack, LivingEntity user);

}
