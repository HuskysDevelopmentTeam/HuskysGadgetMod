package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static net.thegaminghuskymc.gadgetmod.core.BaseDevice.*;

public class LayoutDesktopCraftOS extends Layout {

    public LayoutDesktopCraftOS() {
        super(0, 10, 908, 472);
    }

    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {

        Color bgColor = new Color(laptop.getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 1.0F));

        if(BaseDevice.getSystem().getSettings().hasWallpaperOrColor().equals("Wallpaper")) {

            GlStateManager.popMatrix();
            GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.3F);
            mc.getTextureManager().bindTexture(BaseDevice.WALLPAPERS.get(BaseDevice.currentWallpaper));
            RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.7f);
            GlStateManager.enableBlend();
            mc.getTextureManager().bindTexture(BOOT_CRAFT_TEXTURES);
            this.drawTexturedModalRect(x + 170, y + 100, 2, 94, 128, 30);
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().fontRenderer.drawString(TextFormatting.GOLD + String.format("CraftOS v%s", Reference.CRAFT_VERSION), x + 370, y + 210, 0xFFFFFF, true);
        } else {
            Gui.drawRect(x, y, x + SCREEN_WIDTH, y + SCREEN_HEIGHT, new Color(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 1.0F).getRGB());
        }
    }

}
