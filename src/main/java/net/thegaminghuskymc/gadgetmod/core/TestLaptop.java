package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.io.Drive;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.client.LaptopFontRenderer;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TestLaptop extends GuiScreen implements System {

    public static final int ID = 2;
    public static final FontRenderer fontRenderer = new LaptopFontRenderer(Minecraft.getMinecraft());
    public static final int DEVICE_WIDTH = 464;
    public static final int DEVICE_HEIGHT = 246;
    public static final List<ResourceLocation> WALLPAPERS = new ArrayList<>();
    private static final ResourceLocation LAPTOP_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop.png");
    private static final List<Application> APPLICATIONS = new ArrayList<>();
    private static final int BORDER = 10;
    public static final int SCREEN_WIDTH = DEVICE_WIDTH - BORDER * 2;
    public static final int SCREEN_HEIGHT = DEVICE_HEIGHT - BORDER * 2;

    public static int currentWallpaper;
    private static System system;
    private static BlockPos pos;
    private static Drive mainDrive;

    private Settings settings;
    private TaskBar bar;
    private Window[] windows;
    private Layout context = null;
    private NBTTagCompound appData;
    private boolean dragging = false;

    public TestLaptop(TileEntityLaptop laptop) {
        this.appData = laptop.getApplicationData();
        NBTTagCompound systemData = laptop.getSystemData();
        this.windows = new Window[5];
        this.settings = Settings.fromTag(systemData.getCompoundTag("Settings"));
        this.bar = new TaskBar(APPLICATIONS);
        currentWallpaper = systemData.getInteger("CurrentWallpaper");
        if (currentWallpaper < 0 || currentWallpaper >= WALLPAPERS.size()) {
            currentWallpaper = 0;
        }
        TestLaptop.system = this;
        TestLaptop.pos = laptop.getPos();
    }

    @Override
    public void openContext(Layout layout, int x, int y) {
        layout.updateComponents(x, y);
        context = layout;
        layout.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LAPTOP_GUI);

        /* Physical Screen */
        int posX = (width - DEVICE_WIDTH) / 2;
        int posY = (height - DEVICE_HEIGHT) / 2;

        /* Corners */
        this.drawTexturedModalRect(posX, posY, 0, 0, BORDER, BORDER); // TOP-LEFT
        this.drawTexturedModalRect(posX + DEVICE_WIDTH - BORDER, posY, 11, 0, BORDER, BORDER); // TOP-RIGHT
        this.drawTexturedModalRect(posX + DEVICE_WIDTH - BORDER, posY + DEVICE_HEIGHT - BORDER, 11, 11, BORDER, BORDER); // BOTTOM-RIGHT
        this.drawTexturedModalRect(posX, posY + DEVICE_HEIGHT - BORDER, 0, 11, BORDER, BORDER); // BOTTOM-LEFT

        /* Edges */
        RenderUtil.drawRectWithTexture(posX + BORDER, posY, 10, 0, SCREEN_WIDTH, BORDER, 1, BORDER); // TOP
        RenderUtil.drawRectWithTexture(posX + DEVICE_WIDTH - BORDER, posY + BORDER, 11, 10, BORDER, SCREEN_HEIGHT, BORDER, 1); // RIGHT
        RenderUtil.drawRectWithTexture(posX + BORDER, posY + DEVICE_HEIGHT - BORDER, 10, 11, SCREEN_WIDTH, BORDER, 1, BORDER); // BOTTOM
        RenderUtil.drawRectWithTexture(posX, posY + BORDER, 0, 11, BORDER, SCREEN_HEIGHT, BORDER, 1); // LEFT

        /* Center */
        RenderUtil.drawRectWithTexture(posX + BORDER, posY + BORDER, 10, 10, SCREEN_WIDTH, SCREEN_HEIGHT, 1, 1);

        /* Wallpaper */
        mc.getTextureManager().bindTexture(WALLPAPERS.get(currentWallpaper));
        RenderUtil.drawRectWithFullTexture(posX, posY, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        /*boolean insideContext = false;
        if (context != null) {
            insideContext = GuiHelper.isMouseInside(mouseX, mouseY, context.xPosition, context.yPosition, context.xPosition + context.width, context.yPosition + context.height);
        }*/

        /* Window */
        /*for (int i = windows.length - 1; i >= 0; i--) {
            Window window = windows[i];
            if (window != null) {
                window.render(this, mc, posX + BORDER, posY + BORDER, mouseX, mouseY, i == 0 && !insideContext, partialTicks);
            }
        }

        *//* Application Bar *//*
        bar.render(this, mc, posX + 10, posY + DEVICE_HEIGHT - 236, mouseX, mouseY, partialTicks);

        if (context != null) {
            context.render(this, mc, context.xPosition, context.yPosition, mouseX, mouseY, true, partialTicks);
        }*/

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean hasContext() {
        return context != null;
    }

    @Override
    public void closeContext() {
        context = null;
        dragging = false;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Layout getContext() {
        return context;
    }

}
