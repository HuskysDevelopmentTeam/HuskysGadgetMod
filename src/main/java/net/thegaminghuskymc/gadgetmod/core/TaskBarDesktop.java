package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutOSSelect;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutStartMenu;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class TaskBarDesktop extends GuiScreen {

    public static final int BAR_HEIGHT = 18;
    private static final ResourceLocation APP_BAR_GUI = new ResourceLocation(MOD_ID, "textures/gui/bar.png");
    private static final int APPS_DISPLAYED = 18;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStartButton;
    private Button btnOSSelect;

    // References the settings here so we don't need to update
    // the color manually in here
    private CUSettings settings;

    private boolean menuOpened = false;
    private float menuAnim = 0.0f;

    private int offset = 0;

    private List<Application> applications;
    private List<TrayItem> trayItems = new ArrayList<>();

    private BaseDevice device;

    private int posX, posY;

    public TaskBarDesktop(BaseDevice device, List<Application> applications, CUSettings settings) {
        this.device = device;
        this.settings = settings;
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
                if (!BaseDevice.getSystem().hasContext() || !(BaseDevice.getContext() instanceof LayoutStartMenu)) {
                    BaseDevice.getSystem().openContext(layout, this.posX, this.posY);
                } else {
                    BaseDevice.getSystem().closeContext();
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
                if (!BaseDevice.getSystem().hasContext() || !(BaseDevice.getContext() instanceof LayoutOSSelect)) {
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

        TextureManager tm = mc.getTextureManager();

        int color = Color.GREEN.getRGB();
        if(settings != null) {
            color = settings.getBarColor();
        }

        int alpha        = (color >> 24) & 0xFF;
        int red          = (color >> 16) & 0xFF;
        int green        = (color >>  8) & 0xFF;
        int blue         = (color) & 0xFF;

        float alphaFloat = (float) alpha/255.0f;
        float redFloat   = (float) red/255.0f;
        float greenFloat = (float) green/255.0f;
        float blueFloat  = (float) blue/255.0f;

        FontRenderer fr = mc.fontRenderer;

        tm.bindTexture(APP_BAR_GUI);
        GlStateManager.color(redFloat, greenFloat, blueFloat, alphaFloat);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        drawModalRectWithCustomSizedTexture(x, y, 0, 0, BaseDevice.SCREEN_WIDTH, 19, BaseDevice.SCREEN_WIDTH, 16);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        tm.bindTexture(new ResourceLocation(MOD_ID, "textures/gui/dbrownhead"));
        drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 0, 16, 16, 16, 16);
        String time = this.timeToString(Minecraft.getMinecraft().world.getWorldTime());
        String day = this.dayToString(Minecraft.getMinecraft().world.getWorldTime());
        fr.drawString(time, x + BaseDevice.SCREEN_WIDTH - 55 + (55 - fr.getStringWidth(time))/2, y+1, 0xFFFFFFFF);
        fr.drawString(day, x + BaseDevice.SCREEN_WIDTH - 55 + (55 - fr.getStringWidth(day))/2, y+9, 0xFFFFFFFF);

        int menuWidth = 100;
        int menuHeight = 130;
        int border = 2;

        if(this.menuOpened) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int scale = sr.getScaleFactor();
            GlStateManager.pushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((this.device.posX + BaseDevice.BORDER + x), Display.getHeight() - (this.device.posY + BaseDevice.BORDER + y - 15) * scale, menuWidth * scale, -menuHeight * scale);
            int yOffset = (int) ((1-this.menuAnim)*menuHeight);
            int colorOutline = (-16777216) + (red << 16) + (green << 8) + blue;
            int colorInside = 0xFFFFFFFF;
            int colorText = 0xFF000000;
            Gui.drawRect(x, y - menuHeight + yOffset, x + menuWidth, y + yOffset, colorOutline);
            Gui.drawRect(x + border, y - menuHeight + border + yOffset, x + menuWidth - border, y - border + yOffset, colorInside);
            String s = "Start menu";
            int w = fr.getStringWidth(s);
            fr.drawString(s, x + (menuWidth-w)/2, y - menuHeight + border*2 + yOffset, colorText);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();
            if(this.menuAnim < 1.0f) {
                this.menuAnim = (float) Math.min(this.menuAnim + (partialTicks/20.0), 1.0f);
            }
        }

    }

    public void handleClick(BaseDevice device, int x, int y, int mouseX, int mouseY, int mouseButton) {
        btnLeft.handleMouseClick(mouseX, mouseY, mouseButton);
        btnRight.handleMouseClick(mouseX, mouseY, mouseButton);
        btnStartButton.handleMouseClick(mouseX, mouseY, mouseButton);
        btnOSSelect.handleMouseClick(mouseX, mouseY, mouseButton);

        if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
            int appIndex = (mouseX - x - 1) / 16;
            if(appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < device.installedApps.size())
            {
                device.openApplication(device.installedApps.get(appIndex));
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

        if(isMouseInside(mouseX, mouseY, mouseX + 1, mouseX + 16, mouseY + 1, mouseY + 16)) {
            this.menuOpened = !this.menuOpened;
            if(!this.menuOpened) {
                this.menuAnim = 0.0f;
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
        return String.format("Day %d", time/24000);
    }

}