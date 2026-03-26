package net.teamaurorisla.auroramagic.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.teamaurorisla.auroramagic.capability.mana.ManaManager;

import net.teamaurorisla.auroramagic.capability.mana.ManaType;

public class ManaDataOverlay implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = gui.getMinecraft();
        Player player = mc.player;
        Font font = mc.font;
        if (player != null && player.isAlive()) {
            ManaManager manager = ManaManager.of(player);
            graphics.drawString(font, manager.get(ManaType.STABLE) + " | " + manager.get(ManaType.MAX_STABLE), 0, 0, 0xEE7942, false);
            graphics.drawString(font, manager.get(ManaType.SURGE) + " | " + manager.get(ManaType.MAX_SURGE), 0, 10, 0x00FFFF, false);
            graphics.drawString(font, manager.isOverloaded() ? "OVERLOAD" : "READY", 0, 20, manager.isOverloaded() ? 0xFFD700 : 0x98FB98, false);
        }
    }

}
