package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutStartMenu;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class TaskBar extends GuiScreen {

    public static final int BAR_HEIGHT = 18;
    private static final ResourceLocation APP_BAR_GUI = new ResourceLocation(MOD_ID, "textures/gui/application_bar.png");
    private static final int APPS_DISPLAYED = 18;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStartButton;
    private Button btnOSSelect;

    private boolean menuOpened = false;
    private float menuAnim = 0.0f;

    private int offset = 0;

    private List<Application> applications;
    private List<TrayItem> trayItems = new ArrayList<>();

    private BaseDevice device;

    private int posX, posY;

    public TaskBar(BaseDevice device) {
        this.device = device;
        trayItems.add(new TrayItemWifi());
        trayItems.add(new ApplicationAppStore.StoreTrayItem());
    }

    public void init() {
        trayItems.forEach(TrayItem::init);
    }

    private void setupApplications(List<Application> applications) {
        final Predicate<Application> VALID_APPS = app ->
        {
            if(app instanceof SystemApplication)
            {
                return true;
            }
            if(HuskyGadgetMod.proxy.hasAllowedApplications())
            {
                if(HuskyGadgetMod.proxy.getAllowedApplications().contains(app.getInfo()))
                {
                    return Settings.isShowAllApps();
                }
                return false;
            }
            return true;
        };
    }

    public void init(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        btnLeft = new Button(0, 0, Icons.CHEVRON_LEFT);
        btnLeft.setPadding(1);
        btnLeft.xPosition = posX + 20;
        btnLeft.yPosition = posY + 3;
        btnLeft.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (offset > 0) {
                offset--;
            }
        });
        btnRight = new Button(0, 0, Icons.CHEVRON_RIGHT);
        btnRight.setPadding(1);
        btnRight.xPosition = posX + 30 + 14 * APPS_DISPLAYED + 38;
        btnRight.yPosition = posY + 3;
        btnRight.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (offset + APPS_DISPLAYED < applications.size()) {
                offset++;
            }
        });

        btnStartButton = new Button(0, 0, Icons.EARTH);
        btnStartButton.setPadding(1);
        btnStartButton.xPosition = posX + 3;
        btnStartButton.yPosition = posY + 3;
        btnStartButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                Layout layout = new LayoutStartMenu();
                layout.init();
                if (!BaseDevice.getSystem().hasContext() || !(BaseDevice.getContext() instanceof LayoutStartMenu)) {
                    BaseDevice.getSystem().openContext(layout, this.posX, this.posY);
                } else {
                    BaseDevice.getSystem().closeContext();
                }
            }
        });

        trayItems.forEach(TrayItem::init);
    }

    public void onTick() {
        trayItems.forEach(TrayItem::tick);
    }

    public void render(BaseDevice gui, Minecraft mc, int x, int y, int mouseX, int mouseY, float partialTicks) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);
        gui.drawTexturedModalRect(x, y, 0, 0, 1, 18);
        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, BaseDevice.SCREEN_WIDTH - 36 - trayItemsWidth, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + BaseDevice.SCREEN_WIDTH - 35 - trayItemsWidth, y, 2, 0, 35 + trayItemsWidth, 18, 1, 18);
        GlStateManager.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        btnLeft.render(gui, mc, btnLeft.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        btnRight.render(gui, mc, btnRight.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        btnStartButton.render(gui, mc, btnStartButton.xPosition, btnStartButton.yPosition, mouseX, mouseY, true, partialTicks);

        for(int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++)
        {
            AppInfo info = gui.installedApps.get(i + offset);
            RenderUtil.drawApplicationIcon(info, x + 2 + i * 16, y + 2);
            if(gui.isApplicationRunning(info))
            {
                mc.getTextureManager().bindTexture(APP_BAR_GUI);
                gui.drawTexturedModalRect(x + 1 + i * 16, y + 1, 35, 0, 16, 16);
            }
        }

        mc.fontRenderer.drawString(timeToString(mc.player.world.getWorldTime()), x + 414, y + 5, Color.WHITE.getRGB(), true);

        int startX = x + 397;
        for (int i = 0; i < trayItems.size(); i++) {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                Gui.drawRect(posX, y + 2, posX + 14, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
            }
            trayItems.get(i).getIcon().draw(mc, posX + 2, y + 4);
        }

        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        /* Other Apps */
        if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
            int appIndex = (mouseX - x - 1) / 16;
            if(appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size())
            {
                gui.drawTexturedModalRect(x + appIndex * 16 + 1, y + 1, 35, 0, 16, 16);
                gui.drawHoveringText(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX, mouseY);
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();

    }

    public void handleClick(BaseDevice laptop, int x, int y, int mouseX, int mouseY, int mouseButton) {
        btnLeft.handleMouseClick(mouseX, mouseY, mouseButton);
        btnRight.handleMouseClick(mouseX, mouseY, mouseButton);
        btnStartButton.handleMouseClick(mouseX, mouseY, mouseButton);

        if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
            int appIndex = (mouseX - x - 1) / 16;
            if(appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < laptop.installedApps.size())
            {
                laptop.openApplication(laptop.installedApps.get(appIndex));
                return;
            }
        }

        int startX = x + 397;
        for (int i = 0; i < trayItems.size(); i++) {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                trayItems.get(i).handleClick(mouseX, mouseY, mouseButton);
                break;
            }
        }

    }

    public boolean isMouseInside(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public String timeToString(long time)  {
        int hours = (int) ((Math.floor(time / 1000.0) + 7) % 24);
        int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    public String dayToString(long time)  {
        return String.format("Day %d", time / 24000);
    }

}