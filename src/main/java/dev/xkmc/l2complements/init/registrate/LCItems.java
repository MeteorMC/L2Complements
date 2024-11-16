package dev.xkmc.l2complements.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.content.entity.fireball.BlackFireball;
import dev.xkmc.l2complements.content.entity.fireball.SoulFireball;
import dev.xkmc.l2complements.content.entity.fireball.StrongFireball;
import dev.xkmc.l2complements.content.item.create.RefinedRadianceItem;
import dev.xkmc.l2complements.content.item.create.VoidEyeItem;
import dev.xkmc.l2complements.content.item.misc.FireChargeItem;
import dev.xkmc.l2complements.content.item.misc.*;
import dev.xkmc.l2complements.content.item.wand.DiffusionWand;
import dev.xkmc.l2complements.content.item.wand.HellfireWand;
import dev.xkmc.l2complements.content.item.wand.SonicShooter;
import dev.xkmc.l2complements.content.item.wand.WinterStormWand;
import dev.xkmc.l2complements.events.MaterialDamageListener;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2complements.init.materials.LCMats;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

@SuppressWarnings({"unsafe"})
@MethodsReturnNonnullByDefault
public class LCItems {

	public static final RegistryEntry<CreativeModeTab> TAB_ITEM;
	public static final RegistryEntry<CreativeModeTab> TAB_ENCHMIN;

	static {
		TAB_ENCHMIN = REGISTRATE.buildL2CreativeTab("enchantments_low", "L2 Enchantments - Craftable", b -> b
				.icon(Items.BOOK::getDefaultInstance));

		TAB_ITEM = REGISTRATE.buildL2CreativeTab("l2complements", "L2Complements Items", b -> b
				.icon(LCItems.RESONANT_FEATHER::asStack));

		LCBlocks.register();
	}

	public static final ItemEntry<TooltipItem> WIND_BOTTLE;
	public static final ItemEntry<TooltipItem> VOID_EYE;
	public static final ItemEntry<TooltipItem> SUN_MEMBRANE;
	public static final ItemEntry<TooltipItem> SOUL_FLAME;
	public static final ItemEntry<TooltipItem> CAPTURED_WIND;
	public static final ItemEntry<TooltipItem> CAPTURED_BULLET;
	public static final ItemEntry<TooltipItem> EXPLOSION_SHARD;
	public static final ItemEntry<TooltipItem> HARD_ICE;
	public static final ItemEntry<TooltipItem> STORM_CORE;
	public static final ItemEntry<TooltipItem> BLACKSTONE_CORE;
	public static final ItemEntry<TooltipItem> RESONANT_FEATHER;
	public static final ItemEntry<TooltipItem> SPACE_SHARD;
	public static final ItemEntry<TooltipItem> WARDEN_BONE_SHARD;
	public static final ItemEntry<TooltipItem> GUARDIAN_EYE;
	public static final ItemEntry<TransformItem> GUARDIAN_RUNE;
	public static final ItemEntry<TransformItem> PIGLIN_RUNE;
	public static final ItemEntry<BurntItem> EMERALD;
	public static final ItemEntry<BurntItem> CURSED_DROPLET;
	public static final ItemEntry<BurntItem> LIFE_ESSENCE;
	public static final ItemEntry<SpecialRenderItem> FORCE_FIELD;


	public static final ItemEntry<WarpStone> FRAGILE_WARP_STONE;
	public static final ItemEntry<WarpStone> REINFORCED_WARP_STONE;
	public static final ItemEntry<HomeTotem> TOTEM_OF_DREAM;
	public static final ItemEntry<PoseiditeTotem> TOTEM_OF_THE_SEA;
	public static final ItemEntry<EternalTotem> ETERNAL_TOTEM;

	public static final ItemEntry<FireChargeItem<SoulFireball>> SOUL_CHARGE;
	public static final ItemEntry<FireChargeItem<StrongFireball>> STRONG_CHARGE;
	public static final ItemEntry<FireChargeItem<BlackFireball>> BLACK_CHARGE;

	public static final ItemEntry<SonicShooter> SONIC_SHOOTER;
	public static final ItemEntry<HellfireWand> HELLFIRE_WAND;
	public static final ItemEntry<WinterStormWand> WINTERSTORM_WAND;
	public static final ItemEntry<DiffusionWand> DIFFUSION_WAND;

	public static final ItemEntry<Item> TOTEMIC_CARROT, TOTEMIC_APPLE;
	public static final ItemEntry<EnchantedGoldenAppleItem> ENCHANT_TOTEMIC_CARROT, ENCHANTED_TOTEMIC_APPLE;

	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {

		MAT_INGOTS = L2Complements.MATS.genMats(LCMats.values(), "ingot", Tags.Items.INGOTS);
		MAT_NUGGETS = L2Complements.MATS.genMats(LCMats.values(), "nugget", Tags.Items.NUGGETS);
		{
			WIND_BOTTLE = simpleItem("wind_capture_bottle", "Wind Capturing Bottle", WindBottle::new, Rarity.COMMON, LangData.IDS.WIND_BOTTLE::get); // tested
			VOID_EYE = simpleItem("void_eye", "Void Eye", VoidEyeItem::new, Rarity.EPIC, () -> LangData.IDS.VOID_EYE.get(LCConfig.COMMON.belowVoid.get())); // kill aggroed enderman 16 blocks in void
			CAPTURED_WIND = simpleItem("captured_wind", "Essence of Wind", TooltipItem::new, Rarity.RARE, () -> LangData.IDS.CAPTURED_WIND.get(LCConfig.COMMON.windSpeed.get() * 20)); // player reach 200m/s
			CAPTURED_BULLET = simpleItem("captured_shulker_bullet", "Shulker Bullet in Bottle", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.CAPTURED_BULLET::get); //  capture bullet
			SUN_MEMBRANE = simpleItem("sun_membrane", "Membrane of the Sun", RefinedRadianceItem::new, Rarity.EPIC, () -> LangData.IDS.SUN_MEMBRANE.get(LCConfig.COMMON.phantomHeight.get())); // kill phantom 200 blocks above maximum build height with arrow
			EXPLOSION_SHARD = simpleItem("explosion_shard", "Remnant Shard of Explosion", TooltipItem::new, Rarity.UNCOMMON, () -> LangData.IDS.EXPLOSION_SHARD.get(LCConfig.COMMON.explosionDamage.get())); // endure > 80 explosion damage
			HARD_ICE = simpleItem("hard_ice", "Unliving Ice", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.HARD_ICE::get); // kill drowned with powder snow damage
			SOUL_FLAME = simpleItem("soul_flame", "Soul Flame", RefinedRadianceItem::new, Rarity.RARE, LangData.IDS.SOUL_FLAME::get); // kill ghast with soul flame
			STORM_CORE = simpleItem("storm_core", "Crystal of Storm", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.STORM_CORE::get); // kill phantom with explosion
			BLACKSTONE_CORE = simpleItem("blackstone_core", "Blackstone Core", TooltipItem::new, Rarity.RARE, LangData.IDS.BLACKSTONE_CORE::get); // kill guardian with stone cage effect
			RESONANT_FEATHER = simpleItem("resonant_feather", "Resonant Feather", TooltipItem::new, Rarity.EPIC, LangData.IDS.RESONANT_FEATHER::get); // let chicken survive sonic boom
			SPACE_SHARD = simpleItem("space_shard", "Space Shard (Creative)", TooltipItem::new, Rarity.EPIC, () -> MaterialDamageListener.isSpaceShardBanned() ? null : LangData.IDS.SPACE_SHARD.get(LCConfig.COMMON.spaceDamage.get())); // deal 500 arrow damage
			WARDEN_BONE_SHARD = simpleItem("warden_bone_shard", "Warden Bone Shard", TooltipItem::new, Rarity.RARE, LangData.IDS.WARDEN_BONE_SHARD::get);
			GUARDIAN_EYE = simpleItem("guardian_eye", "Eye of Elder Guardian", TooltipItem::new, Rarity.RARE, LangData.IDS.GUARDIAN_EYE::get);
			EMERALD = REGISTRATE.item("heirophant_green", p -> new BurntItem(p.fireResistant().rarity(Rarity.EPIC))).defaultModel().tag(TagGen.SPECIAL_ITEM).lang("Heirophant Green").register();
			CURSED_DROPLET = REGISTRATE.item("cursed_droplet", p -> new BurntItem(p.fireResistant().rarity(Rarity.RARE))).defaultModel().tag(TagGen.SPECIAL_ITEM).lang("Cursed Droplet").register();
			LIFE_ESSENCE = REGISTRATE.item("life_essence", p -> new BurntItem(p.fireResistant().rarity(Rarity.RARE)
							.food(new FoodProperties.Builder().nutrition(20).saturationMod(1.2f).alwaysEat().fast().build())))
					.defaultModel().tag(TagGen.SPECIAL_ITEM).lang("Essence of Life").register();
			FORCE_FIELD = REGISTRATE.item("force_field", p -> new SpecialRenderItem(p.fireResistant().rarity(Rarity.EPIC), LangData.IDS.FORCE_FIELD::get))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity")))
					.lang("Wither Force Field").tag(TagGen.SPECIAL_ITEM).register();
			GUARDIAN_RUNE = simpleItem("guardian_rune", "Rune of Guardian", (p, t) -> new TransformItem(p, t,
							() -> EntityType.GUARDIAN, () -> EntityType.ELDER_GUARDIAN),
					Rarity.RARE, LangData.IDS.GUARDIAN_RUNE::get);
			PIGLIN_RUNE = simpleItem("piglin_rune", "Rune of Piglin", (p, t) -> new TransformItem(p, t,
							() -> EntityType.PIGLIN, () -> EntityType.PIGLIN_BRUTE),
					Rarity.RARE, LangData.IDS.PIGLIN_RUNE::get);
		}
		{
			FRAGILE_WARP_STONE = REGISTRATE.item("fragile_warp_stone", p ->
							new WarpStone(p.fireResistant().stacksTo(1).rarity(Rarity.RARE), true))
					.defaultModel().defaultLang().register();
			REINFORCED_WARP_STONE = REGISTRATE.item("reinforced_warp_stone", p ->
							new WarpStone(p.fireResistant().stacksTo(1).durability(64).rarity(Rarity.RARE), false))
					.defaultModel().defaultLang().register();

			TagKey<Item> charm = ItemTags.create(new ResourceLocation("curios", "charm"));

			TOTEM_OF_DREAM = REGISTRATE.item("totem_of_dream", p ->
							new HomeTotem(p.fireResistant().stacksTo(1).rarity(Rarity.EPIC)))
					.tag(charm).defaultModel().defaultLang().register();
			TOTEM_OF_THE_SEA = REGISTRATE.item("totem_of_the_sea", p ->
							new PoseiditeTotem(p.fireResistant().stacksTo(16).rarity(Rarity.EPIC)))
					.tag(charm).defaultModel().defaultLang().register();
			ETERNAL_TOTEM = REGISTRATE.item("eternal_totem_of_dream", p ->
							new EternalTotem(p.fireResistant().stacksTo(1).rarity(Rarity.EPIC)))
					.tag(charm).defaultModel().defaultLang().register();
		}
		{
			SOUL_CHARGE = REGISTRATE.item("soul_fire_charge", p ->
							new FireChargeItem<>(p, SoulFireball::new, SoulFireball::new,
									() -> LangData.IDS.EFFECT_CHARGE.get(getTooltip(
											new MobEffectInstance(LCEffects.FLAME.get(),
													LCConfig.COMMON.soulFireChargeDuration.get())))))
					.defaultModel().defaultLang().register();

			STRONG_CHARGE = REGISTRATE.item("strong_fire_charge", p ->
							new FireChargeItem<>(p, StrongFireball::new, StrongFireball::new,
									() -> LangData.IDS.EXPLOSION_CHARGE.get(LCConfig.COMMON.strongFireChargePower.get())))
					.defaultModel().defaultLang().register();

			BLACK_CHARGE = REGISTRATE.item("black_fire_charge", p ->
							new FireChargeItem<>(p, BlackFireball::new, BlackFireball::new,
									() -> LangData.IDS.EFFECT_CHARGE.get(getTooltip(
											new MobEffectInstance(LCEffects.STONE_CAGE.get(),
													LCConfig.COMMON.blackFireChargeDuration.get())))))
					.defaultModel().defaultLang().register();

			SONIC_SHOOTER = REGISTRATE.item("sonic_shooter", p ->
							new SonicShooter(p.durability(64).fireResistant().rarity(Rarity.EPIC)))
					.model((ctx, pvd) -> {
								var parent = new ModelFile.UncheckedModelFile(pvd.modLoc("item/gun"));
								var base = pvd.getBuilder(ctx.getName()).parent(parent)
										.texture("layer0", "item/" + ctx.getName());
								var id = new ResourceLocation(L2Complements.MODID, "shoot");
								for (int i = 0; i < 4; i++) {
									String str = ctx.getName() + "_" + i;
									base.override().predicate(id, i * 0.2f + 0.2f)
											.model(pvd.getBuilder(str).parent(parent)
													.texture("layer0", "item/" + str)).end();
								}
							}
					).defaultLang().register();

			HELLFIRE_WAND = REGISTRATE.item("hellfire_wand", p ->
							new HellfireWand(p.durability(64).fireResistant().rarity(Rarity.EPIC)))
					.model((ctx, pvd) -> pvd.handheld(ctx)).defaultLang().register();

			DIFFUSION_WAND = REGISTRATE.item("diffusion_wand", p ->
							new DiffusionWand(p.durability(8).fireResistant().rarity(Rarity.RARE)))
					.model((ctx, pvd) -> pvd.handheld(ctx)).defaultLang().register();

			WINTERSTORM_WAND = REGISTRATE.item("winterstorm_wand", p ->
							new WinterStormWand(p.durability(128).fireResistant().rarity(Rarity.RARE)))
					.model((ctx, pvd) -> pvd.handheld(ctx)).defaultLang().register();
		}
		{
			TOTEMIC_CARROT = REGISTRATE.item("totemic_carrot", p -> new Item(p.food(
					new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).alwaysEat()
							.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200), 1)
							.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1)
							.build()
			))).defaultModel().defaultLang().register();

			ENCHANT_TOTEMIC_CARROT = REGISTRATE.item("enchanted_totemic_carrot", p -> new EnchantedGoldenAppleItem(p.food(
					new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).alwaysEat()
							.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 3600), 1)
							.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 2), 1)
							.effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 1), 1)
							.effect(() -> new MobEffectInstance(MobEffects.SATURATION, 20), 1)
							.build()
			))).defaultModel().defaultLang().register();

			TOTEMIC_APPLE = REGISTRATE.item("totemic_apple", p -> new Item(p.food(
					new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).alwaysEat()
							.effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1)
							.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 1), 1)
							.effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 4), 1)
							.effect(() -> new MobEffectInstance(MobEffects.SATURATION, 60), 1)
							.build()
			))).defaultModel().defaultLang().register();

			ENCHANTED_TOTEMIC_APPLE = REGISTRATE.item("enchanted_totemic_apple", p -> new EnchantedGoldenAppleItem(p.food(
					new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).alwaysEat()
							.effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1)
							.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 3), 1)
							.effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 4), 1)
							.effect(() -> new MobEffectInstance(MobEffects.SATURATION, 100), 1)
							.build()
			))).defaultModel().defaultLang().register();
		}
		GEN_ITEM = L2Complements.MATS.genItem(LCMats.values());

		UnobtainableEnchantment.injectTab(REGISTRATE, LCEnchantments.ALL);
	}

	public static MutableComponent getTooltip(MobEffectInstance eff) {
		MutableComponent comp = Component.translatable(eff.getDescriptionId());
		MobEffect mobeffect = eff.getEffect();
		if (eff.getAmplifier() > 0) {
			comp = Component.translatable("potion.withAmplifier", comp,
					Component.translatable("potion.potency." + eff.getAmplifier()));
		}
		if (eff.getDuration() > 20) {
			comp = Component.translatable("potion.withDuration", comp,
					MobEffectUtil.formatDuration(eff, 1));
		}
		return comp.withStyle(mobeffect.getCategory().getTooltipFormatting());
	}

	public static <T extends Item> ItemEntry<T> simpleItem(String id, String name, BiFunction<Item.Properties, Supplier<MutableComponent>, T> func, Rarity r, Supplier<MutableComponent> sup) {
		return REGISTRATE.item(id, p -> func.apply(p.fireResistant().rarity(r), sup)).defaultModel().tag(TagGen.SPECIAL_ITEM).lang(name).register();
	}

	public static void register() {
	}

}
