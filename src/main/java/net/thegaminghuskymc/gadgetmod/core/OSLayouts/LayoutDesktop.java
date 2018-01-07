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
    public void init() {

        int posX = (width - DEVICE_WIDTH) / 2;
        int posY = (height - DEVICE_HEIGHT) / 2;

        Minecraft.getMinecraft().getTextureManager().bindTexture(Laptop.WALLPAPERS.get(Laptop.currentWallpaper));
        RenderUtil.drawRectWithFullTexture(posX - 204, posY - 99, 0, 0, SCREEN_WIDTH , SCREEN_HEIGHT);

        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 50);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 65);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 80);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 95);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 110);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), 30, 125);

    }

}
