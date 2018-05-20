package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

import static net.thegaminghuskymc.gadgetmod.core.BaseDevice.SCREEN_HEIGHT;
import static net.thegaminghuskymc.gadgetmod.core.BaseDevice.SCREEN_WIDTH;

public class LayoutDesktop extends Layout {

    public LayoutDesktop() {
        super(0, 10, 908, 472);
    }

    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if(BaseDevice.getSystem().getSettings().hasWallpaperOrColor().equals("Wallpaper")) {
            mc.getTextureManager().bindTexture(BaseDevice.WALLPAPERS.get(BaseDevice.currentWallpaper));
            RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        } else {
            Gui.drawRect(x, y, x + SCREEN_WIDTH, y + SCREEN_HEIGHT, BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour());
        }
    }

}
