package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.util.tools.TeleportTool;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class HomeTotem extends Item implements ILCTotem {

	public HomeTotem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return self instanceof Player;
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		ILCTotem.super.trigger(self, holded, second);
		self.setHealth(self.getMaxHealth());
		ItemStack stone = LCItems.FRAGILE_WARP_STONE.asStack();
		WarpStone.setPos(stone, self.level(), self.getX(), self.getY() + 1e-3, self.getZ());
		second.accept(stone);
		if (self instanceof ServerPlayer player && self.level() instanceof ServerLevel level)
			TeleportTool.teleportHome(level, player);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.TOTEM_DREAM.get().withStyle(ChatFormatting.GRAY));
	}

}
