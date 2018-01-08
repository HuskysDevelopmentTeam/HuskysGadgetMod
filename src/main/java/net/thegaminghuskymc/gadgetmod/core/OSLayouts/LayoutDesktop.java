package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import static net.thegaminghuskymc.gadgetmod.core.Laptop.*;

public class LayoutDesktop extends Layout {

    public LayoutDesktop() {
        super(0, 10, 908, 472);
    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        int posX = (width - DEVICE_WIDTH) / 2;
        int posY = (height - DEVICE_HEIGHT) / 2;

        mc.getTextureManager().bindTexture(Laptop.WALLPAPERS.get(Laptop.currentWallpaper));
        RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH , SCREEN_HEIGHT);

        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 20);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 35);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 50);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 65);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 80);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 95);
    }

}
