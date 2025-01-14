package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class IceEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {

	private static final UUID ID = MathHelper.getUUIDFromString(L2Complements.MODID + ":ice");

	public IceEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID.toString(), -0.6F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public void applyEffectTick(LivingEntity target, int level) {
		target.setIsInPowderSnow(true);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return true;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, new ResourceLocation(L2Complements.MODID,
				"textures/effect_overlay/ice.png"));
	}
}
