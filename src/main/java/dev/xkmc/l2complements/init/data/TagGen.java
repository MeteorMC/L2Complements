package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2complements.compat.TFCompat;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.init.data.L2TagGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TagGen {

	public static final TagKey<Block> REQUIRES_SCULK_TOOL = BlockTags.create(new ResourceLocation(L2Complements.MODID, "requires_sculk_tool"));
	public static final TagKey<Item> SCULK_MATS = ItemTags.create(new ResourceLocation("modulargolems", "sculk_materials"));
	public static final TagKey<Item> TOTEM = ItemTags.create(new ResourceLocation(L2Complements.MODID, "l2c_totems"));
	public static final TagKey<Item> SPECIAL_ITEM = ItemTags.create(new ResourceLocation(L2Complements.MODID, "l2c_legendary"));
	public static final TagKey<Item> DELICATE_BONE = ItemTags.create(new ResourceLocation(L2Complements.MODID, "delicate_bone"));

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(REQUIRES_SCULK_TOOL).add(Blocks.REINFORCED_DEEPSLATE);
		pvd.addTag(BlockTags.MINEABLE_WITH_PICKAXE).add(Blocks.REINFORCED_DEEPSLATE);
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(SCULK_MATS).add(LCMats.SCULKIUM.getIngot());
		pvd.addTag(TOTEM).add(LCItems.TOTEM_OF_DREAM.get(), LCItems.TOTEM_OF_THE_SEA.get());
		pvd.addTag(DELICATE_BONE).add(Items.SCULK_CATALYST, Items.SCULK_SHRIEKER);
		TFCompat.onItemTagGen(pvd);
		pvd.addTag(L2TagGen.QUICK_ACCESS_VANILLA).add(LCBlocks.ETERNAL_ANVIL.asItem());
	}

}
