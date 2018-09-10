package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.AppInfo;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutStartMenu;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.core.trayItems.TrayItemClipboard;
import net.thegaminghuskymc.gadgetmod.core.trayItems.TrayItemConnectedDevices;
import net.thegaminghuskymc.gadgetmod.core.trayItems.TrayItemFlameChat;
import net.thegaminghuskymc.gadgetmod.core.trayItems.TrayItemSound;
import net.thegaminghuskymc.gadgetmod.object.AnalogClock;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class TaskBar extends GuiScreen {

    static final int BAR_HEIGHT = 18;
    private static final ResourceLocation APP_BAR_GUI = new ResourceLocation("cdm:textures/gui/application_bar.png");
    private static final int APPS_DISPLAYED = 11;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStartButton;

    private int offset = 0;

    private List<TrayItem> trayItems = new ArrayList<>();

    private BaseDevice device;

    private int posX, posY;

    public TaskBar(BaseDevice device) {
        this.device = device;
        trayItems.add(new TrayItemWifi());
        trayItems.add(new TrayItemSound());
        trayItems.add(new TrayItemConnectedDevices());
        trayItems.add(new TrayItemClipboard());
        trayItems.add(new TrayItemFlameChat());
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
            else if(Settings.isShowAllApps())
            {
                return true;
            }
            return ApplicationManager.isApplicationWhitelisted(app.getInfo());
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
            if (offset + APPS_DISPLAYED < device.installedApps.size()) {
                offset++;
            }
        });

        btnStartButton = new Button(0, 0, new ResourceLocation("minecraft","textures/items/redstone_dust.png"), 0, 0, 16, 16);
        btnStartButton.setPadding(1);
        btnStartButton.setBackground(false);
        btnStartButton.xPosition = posX;
        btnStartButton.yPosition = posY;
        btnStartButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                Layout layout = new LayoutStartMenu();
                layout.init();
                if (!BaseDevice.getSystem().hasContext() || !(BaseDevice.getContext() instanceof LayoutStartMenu)) {
                    switch (BaseDevice.getSystem().getSettings().getTaskBarPlacement()) {
                        case "Bottom":
                            BaseDevice.getSystem().openContext(layout, this.posX, this.posY - layout.height + 2);
                            break;
                        case "Top":
                            BaseDevice.getSystem().openContext(layout, this.posX, this.posY);
                            break;
                        case "Left":
                            BaseDevice.getSystem().openContext(layout, this.posX - layout.width, this.posY - layout.height);
                            break;
                        case "Right":
                            BaseDevice.getSystem().openContext(layout, this.posX - layout.width, this.posY - layout.height);
                            break;
                    }
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

        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        Color bgColor = new Color(gui.getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 0.3F));
        GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.7F);

        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x, y, 0, 0, 1, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, Laptop.SCREEN_WIDTH - 36 - trayItemsWidth, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + Laptop.SCREEN_WIDTH - 35 - trayItemsWidth, y, 2, 0, 35 + trayItemsWidth, 18, 1, 18);

        GlStateManager.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if(gui.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.render(gui, mc, btnLeft.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
            btnRight.render(gui, mc, btnRight.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        }
        btnStartButton.render(gui, mc, btnStartButton.xPosition, btnStartButton.yPosition, mouseX, mouseY, true, partialTicks);

        if(gui.installedApps.size() < APPS_DISPLAYED) {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 19 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.drawTexturedModalRect(x + 18 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        } else {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 34 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.drawTexturedModalRect(x + 33 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        }

        mc.fontRenderer.drawString(timeToString(mc.player.world.getWorldTime()), x + 414, y + 5, Color.WHITE.getRGB(), true);
        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16))
        {
            Gui.drawRect(x + 412, y + 2, x + 412 + 30, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
        }

        /* Settings App */
        int startX = x + 397;
        for(int i = 0; i < trayItems.size(); i++)
        {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if(isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15))
            {
                Gui.drawRect(posX, y + 2, posX + 14, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
            }
            trayItems.get(i).getIcon().draw(mc, posX + 2, y + 4);
        }

        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        /* Other Apps */
        if(gui.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.drawTexturedModalRect(x + appIndex * 16 + 18, y + 1, 35, 0, 16, 16);
                    gui.drawHoveringText(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 33) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.drawTexturedModalRect(x + appIndex * 16 + 33, y + 1, 35, 0, 16, 16);
                    gui.drawHoveringText(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();

    }

    public void renderOnSide(BaseDevice gui, Minecraft mc, int x, int y, int mouseX, int mouseY, float partialTicks) {

        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        Color bgColor = new Color(gui.getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 0.3F));
        GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.7F);

        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x, y, 0, 0, 18, 1, 19, 1);
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, 18, Laptop.SCREEN_HEIGHT - 36, 1, 18);
        RenderUtil.drawRectWithTexture(x, y, 2, 0, 18, 35, 1, 18);

        GlStateManager.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if(gui.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.render(gui, mc, btnLeft.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
            btnRight.render(gui, mc, btnRight.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        }
        btnStartButton.render(gui, mc, btnStartButton.xPosition, btnStartButton.yPosition, mouseX, mouseY, true, partialTicks);

        if(gui.installedApps.size() < APPS_DISPLAYED) {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 19 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.drawTexturedModalRect(x + 1, y + 18 + i * 16, 35, 0, 16, 16);
                }
            }
        } else {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 34 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.drawTexturedModalRect(x + 33 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        }

        mc.fontRenderer.drawString(timeToString(mc.player.world.getWorldTime()), x + 414, y + 5, Color.WHITE.getRGB(), true);
        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16))
        {
            Gui.drawRect(x + 412, y + 2, x + 412 + 30, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
        }

        /* Settings App */
        int startX = x + 397;
        for(int i = 0; i < trayItems.size(); i++)
        {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if(isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15))
            {
                Gui.drawRect(posX, y + 2, posX + 14, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
            }
            trayItems.get(i).getIcon().draw(mc, posX + 2, y + 4);
        }

        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        /* Other Apps */
        if(gui.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.drawTexturedModalRect(x + appIndex * 16 + 18, y + 1, 35, 0, 16, 16);
                    gui.drawHoveringText(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 33) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.drawTexturedModalRect(x + appIndex * 16 + 33, y + 1, 35, 0, 16, 16);
                    gui.drawHoveringText(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();

    }

    public void handleClick(BaseDevice laptop, int x, int y, int mouseX, int mouseY, int mouseButton) {
        if(laptop.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.handleMouseClick(mouseX, mouseY, mouseButton);
            btnRight.handleMouseClick(mouseX, mouseY, mouseButton);
        }
        btnStartButton.handleMouseClick(mouseX, mouseY, mouseButton);

        if(laptop.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < laptop.installedApps.size()) {
                    laptop.openApplication(laptop.installedApps.get(appIndex));
                    return;
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 34, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 34) / 16;
                if (appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < laptop.installedApps.size()) {
                    laptop.openApplication(laptop.installedApps.get(appIndex));
                    return;
                }
            }
        }

        int startX = x + 397;
        for(int i = 0; i < trayItems.size(); i++)
        {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                trayItems.get(i).handleClick(mouseX, mouseY, mouseButton);
                break;
            }
        }

        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16))
        {
            Layout layout = createClockLayout();
            Laptop.getSystem().openContext(layout, layout.width + 233, layout.height - 83);
        }

    }

    public boolean isMouseInside(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public String timeToString(long time) {
        int hours = (int) ((Math.floor(time / 1000.0) + 7) % 24);
        int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    private Layout createClockLayout()
    {
        Layout layout = new Layout.Context(115, 115);
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB());
        });
        layout.addComponent(new AnalogClock(layout.width / 2 - 100/2, 12 + (layout.height - 12) / 2 - 100/2, 100, 100));

        Label label = new Label("Day -1", layout.width / 2, 5) {
            @Override
            public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks)
            {
                this.setText("Day " + Minecraft.getMinecraft().player.world.getWorldTime() / 24000);
                super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
            }
        };
        label.setAlignment(2);
        layout.addComponent(label);
        return layout;
    }

}