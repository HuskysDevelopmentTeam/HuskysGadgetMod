package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutOSSelect;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutStartMenu;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskBar {

    public static final int BAR_HEIGHT = 18;
    private static final ResourceLocation APP_BAR_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/application_bar.png");
    private static final int APPS_DISPLAYED = 18;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStartButton;
    private Button btnOSSelect;

    private int offset = 0;

    private List<Application> applications;
    private List<TrayItem> trayItems = new ArrayList<>();

    private int posX, posY;

    public TaskBar(List<Application> applications) {
        setupApplications(applications);
        trayItems.add(new TrayItemWifi());
    }

    public void init() {
        trayItems.forEach(TrayItem::init);
    }

    private void setupApplications(List<Application> applications) {
        final Predicate<Application> VALID_APPS = (Application app) ->
                app instanceof SystemApplication || !HuskyGadgetMod.proxy.hasAllowedApplications() || HuskyGadgetMod.proxy.getAllowedApplications().contains(app.getInfo());
        this.applications = applications.stream().filter(VALID_APPS).collect(Collectors.toList());
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
                if (!Laptop.getSystem().hasContext() || !(Laptop.getSystem().getContext() instanceof LayoutStartMenu)) {
                    Laptop.getSystem().openContext(layout, this.posX, this.posY);
                } else {
                    Laptop.getSystem().closeContext();
                }
            }
        });

        btnOSSelect = new Button(0, 0, Icons.COMPUTER);
        btnOSSelect.setToolTip("Select OS", "This will let you select the OS you want to use");
        btnOSSelect.setPadding(1);
        btnOSSelect.xPosition = posX + 30 + 14 * APPS_DISPLAYED + 58;
        btnOSSelect.yPosition = posY + 3;
        btnOSSelect.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                Layout layout = new LayoutOSSelect();
                layout.init();
                if (!Laptop.getSystem().hasContext() || !(Laptop.getSystem().getContext() instanceof LayoutOSSelect)) {
                    Laptop.getSystem().openContext(layout, this.posX, this.posY);
                } else {
                    Laptop.getSystem().closeContext();
                }
            }
        });

        trayItems.forEach(TrayItem::init);
    }

    public void onTick() {
        trayItems.forEach(TrayItem::tick);
    }

    public void render(Laptop gui, Minecraft mc, int x, int y, int mouseX, int mouseY, float partialTicks) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);
        gui.drawTexturedModalRect(x, y, 0, 0, 1, 18);
        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, Laptop.SCREEN_WIDTH - 36 - trayItemsWidth, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + Laptop.SCREEN_WIDTH - 35 - trayItemsWidth, y, 2, 0, 35 + trayItemsWidth, 18, 1, 18);
        GlStateManager.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        btnLeft.render(gui, mc, btnLeft.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        btnRight.render(gui, mc, btnRight.xPosition, btnLeft.yPosition, mouseX, mouseY, true, partialTicks);
        btnStartButton.render(gui, mc, btnStartButton.xPosition, btnStartButton.yPosition, mouseX, mouseY, true, partialTicks);
        btnOSSelect.render(gui, mc, btnOSSelect.xPosition, btnOSSelect.yPosition, mouseX, mouseY, true, partialTicks);

        for (int i = 0; i < APPS_DISPLAYED && i < applications.size(); i++) {
            AppInfo info = applications.get(i + offset).getInfo();
            RenderUtil.drawApplicationIcon(info, x + 33 + i * 16, y + 2);
            if (gui.isApplicationRunning(info.getFormattedId())) {
                mc.getTextureManager().bindTexture(APP_BAR_GUI);
                gui.drawTexturedModalRect(x + 32 + i * 16, y + 1, 35, 0, 16, 16);
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
            for (int i = 0; i < APPS_DISPLAYED; i++) {
                if (RenderUtil.isMouseInside(mouseX, mouseY, x + 32 + i * 16, y + 1, x + 32 + (i + 1) * 16 - 2, y + 14) && i + offset < applications.size()) {
                    gui.drawTexturedModalRect(x + 32 + i * 16, y + 1, 35, 0, 16, 16);
                    gui.drawHoveringText(Collections.singletonList(applications.get(i + offset).getInfo().getName()), mouseX, mouseY);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();

    }

    public void handleClick(Laptop laptop, int x, int y, int mouseX, int mouseY, int mouseButton) {
        btnLeft.handleMouseClick(mouseX, mouseY, mouseButton);
        btnRight.handleMouseClick(mouseX, mouseY, mouseButton);
        btnStartButton.handleMouseClick(mouseX, mouseY, mouseButton);
        btnOSSelect.handleMouseClick(mouseX, mouseY, mouseButton);

        if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
            for (int i = 0; i < APPS_DISPLAYED; i++) {
                if (RenderUtil.isMouseInside(mouseX, mouseY, x + 32 + i * 16, y + 1, x + 32 + (i + 1) * 16 - 2, y + 14) && i + offset < applications.size()) {
                    laptop.open(applications.get(i + offset));
                    return;
                }
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

    public String timeToString(long time) {
        int hours = (int) ((Math.floor(time / 1000.0) + 7) % 24);
        int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

}