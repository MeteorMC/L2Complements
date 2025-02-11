package dev.xkmc.l2complements.content.client;

import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class RangeDiggingOverlay implements IGuiOverlay {

	@Override
	public void render(ForgeGui gui, GuiGraphics g, float partialTick, int screenWidth, int screenHeight) {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return;
		ItemStack stack = player.getMainHandItem();
		var e = DiggerHelper.getDigger(stack);
		if (e == null) return;
		var name = e.getFirst().getFullname(e.getSecond());
		renderText(gui, g, screenWidth / 2, screenHeight / 2 + 34,
				LangData.IDS.DIGGER_ACTIVATED.get(name).withStyle(ChatFormatting.RED));
	}

	private static void renderText(ForgeGui gui, GuiGraphics g, int x, int y, Component text) {
		Font font = gui.getFont();
		x -= font.width(text) / 2;
		g.drawString(font, text, x, y, 0xFFFFFFFF);
	}

}
