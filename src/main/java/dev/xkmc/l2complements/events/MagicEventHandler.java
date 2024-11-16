package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.compat.CurioCompat;
import dev.xkmc.l2complements.content.effect.skill.CleanseEffect;
import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.base.effects.ForceAddEffectEvent;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagicEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (EntityFeature.OWNER_PROTECTION.test(event.getEntity())) {
			if (event.getSource().getEntity() instanceof OwnableEntity ownable && ownable.getOwner() == event.getEntity()) {
				event.setCanceled(true);
			}
		}
		if (!LCConfig.COMMON.enableImmunityEnchantments.get()) {
			return;
		}
		if (EntityFeature.INVINCIBLE.test(event.getEntity())) {
			event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return;
		if (EntityFeature.ENVIRONMENTAL_REJECT.test(event.getEntity())) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
		if (EntityFeature.MAGIC_REJECT.test(event.getEntity())) {
			if (event.getSource().is(L2DamageTypes.MAGIC)) event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
			return;
		if (EntityFeature.PROJECTILE_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) event.setCanceled(true);
		}
		if (EntityFeature.FIRE_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_FIRE)) event.setCanceled(true);
		}
		if (EntityFeature.EXPLOSION_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingTick(LivingEvent.LivingTickEvent event) {
		if (event.getEntity().isOnFire()) {
			if (EntityFeature.FIRE_REJECT.test(event.getEntity())) {
				event.getEntity().clearFire();
			}
		}
	}

	@SubscribeEvent
	public static void onItemAttributes(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEnchanted()) {
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(stack).entrySet()) {
				if (ent.getKey() instanceof AttributeEnchantment attr) {
					attr.addAttributes(ent.getValue(), event);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onHeal(LivingHealEvent event) {
		if (event.getEntity().hasEffect(LCEffects.CURSE.get())) {
			event.setCanceled(true);
			return;
		}
		float amount = event.getAmount();
		for (ItemStack stack : CurioCompat.getAllSlots(event.getEntity())) {
			if (!stack.isEnchanted() || !stack.isDamaged()) continue;
			int lv = stack.getEnchantmentLevel(LCEnchantments.LIFE_MENDING.get());
			if (lv > 0) {
				int damage = stack.getDamageValue();
				int repair = 1 << (lv - 1);
				int armor = stack.getEnchantmentLevel(LCEnchantments.DURABLE_ARMOR.get());
				if (armor > 0) {
					repair *= 1 + armor;
				}
				int recover = Math.min(damage, (int) Math.floor(amount * repair));
				stack.setDamageValue(damage - recover);
				amount -= 1f * recover / repair;
				if (amount < 1e-3) break;
			}
		}
		event.setAmount(amount);
	}

	@SubscribeEvent
	public static void onTeleport(EntityTeleportEvent event) {
		if (event.getEntity() instanceof LivingEntity le) {
			if (le.hasEffect(LCEffects.STONE_CAGE.get())) {
				event.setCanceled(true);
			}
		}
	}

	public static boolean isSkill(MobEffectInstance ins, LivingEntity entity) {
		if (ins.getEffect() instanceof SkillEffect)
			return true;
		if (EffectUtil.getReason() == EffectUtil.AddReason.SKILL)
			return true;
		int pred = LCConfig.COMMON.cleansePredicate.get();
		if (ins.getEffect().isBeneficial() && pred > 0) return true;
		if (ins.getEffect().getCategory() == MobEffectCategory.NEUTRAL && pred > 1) return true;
		var tag = ForgeRegistries.MOB_EFFECTS.tags();
		if (tag != null && tag.getTag(TagGen.SKILL_EFFECT).contains(ins.getEffect())) {
			return true;
		}
		return CurioCompat.testEffect(ins, entity);
	}

	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
			if (isSkill(event.getEffectInstance(), event.getEntity())) return;
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onForceAdd(ForceAddEffectEvent event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
			if (isSkill(event.getEffectInstance(), event.getEntity())) return;
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPotionAdded(MobEffectEvent.Added event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
			if (isSkill(event.getEffectInstance(), event.getEntity())) return;
			GeneralEventHandler.schedule(() -> CleanseEffect.clearOnEntity(event.getEntity()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!(event.getPlayer() instanceof ServerPlayer player)) return;
		ItemStack stack = player.getMainHandItem();
		var ent = DiggerHelper.getDigger(stack);
		if (ent == null) return;
		ent.getFirst().onBlockBreak(player, event.getPos(), stack, Math.min(ent.getFirst().getMaxLevel(), ent.getSecond()));

	}

	private static List<BooleanSupplier> TASKS = new ArrayList<>();

	@Deprecated(forRemoval = true)
	public static synchronized void schedule(Runnable runnable) {
		TASKS.add(() -> {
			runnable.run();
			return true;
		});
	}

	@Deprecated(forRemoval = true)
	public static synchronized void schedulePersistent(BooleanSupplier runnable) {
		TASKS.add(runnable);
	}

	private static synchronized void execute() {
		if (TASKS.isEmpty()) return;
		var temp = TASKS;
		TASKS = new ArrayList<>();
		temp.removeIf(BooleanSupplier::getAsBoolean);
		temp.addAll(TASKS);
		TASKS = temp;
	}

	@SubscribeEvent
	public static void onTick(TickEvent.ServerTickEvent event) {
		execute();
	}

}
