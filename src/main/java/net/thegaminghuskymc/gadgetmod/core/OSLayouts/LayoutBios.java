package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

import static net.thegaminghuskymc.gadgetmod.core.BaseDevice.SCREEN_HEIGHT;
import static net.thegaminghuskymc.gadgetmod.core.BaseDevice.SCREEN_WIDTH;

public class LayoutBios extends Layout {

    public LayoutBios() {
        super(0, 10, 908, 472);
    }

    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        mc.getTextureManager().bindTexture(BaseDevice.WALLPAPERS.get(BaseDevice.currentWallpaper));
        RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        /*RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 20);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 35);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 50);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 65);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 80);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 95);*/
    }

}
