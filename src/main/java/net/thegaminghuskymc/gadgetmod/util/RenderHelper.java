package net.thegaminghuskymc.gadgetmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class RenderHelper {

    static Minecraft mc = Minecraft.getMinecraft();

    public static void drawStringWithOutline(String string, int posX, int posY, int fontColor, int outlineColor) {
        mc.fontRenderer.drawString(string, posX + 1, posY, outlineColor);
        mc.fontRenderer.drawString(string, posX - 1, posY, outlineColor);
        mc.fontRenderer.drawString(string, posX, posY + 1, outlineColor);
        mc.fontRenderer.drawString(string, posX, posY - 1, outlineColor);

        mc.fontRenderer.drawString(string, posX, posY, fontColor);
    }

    public static String unlocaliseName(String name) {
        return I18n.format(name, new Object[0]);
    }
}