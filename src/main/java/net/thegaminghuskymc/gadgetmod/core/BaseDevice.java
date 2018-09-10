package net.thegaminghuskymc.gadgetmod.core;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.Keybinds;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.AppInfo;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.io.Drive;
import net.thegaminghuskymc.gadgetmod.api.io.File;
import net.thegaminghuskymc.gadgetmod.api.operating_system.OperatingSystem;
import net.thegaminghuskymc.gadgetmod.api.task.Callback;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.*;
import net.thegaminghuskymc.gadgetmod.core.client.LaptopFontRenderer;
import net.thegaminghuskymc.gadgetmod.core.tasks.TaskInstallApp;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.network.task.MessageUnlockAdvancement;
import net.thegaminghuskymc.gadgetmod.object.ThemeInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import net.thegaminghuskymc.gadgetmod.programs.system.component.FileBrowser;
import net.thegaminghuskymc.gadgetmod.programs.system.task.TaskUpdateApplicationData;
import net.thegaminghuskymc.gadgetmod.programs.system.task.TaskUpdateSystemData;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBaseDevice;
import net.thegaminghuskymc.gadgetmod.util.GuiHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.*;

public class BaseDevice extends GuiScreen implements System {

    public static int ID;
    public static final ResourceLocation ICON_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/app_icons.png");
    public static final ResourceLocation BANNER_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/app_banners.png");
    public static final FontRenderer fontRenderer = new LaptopFontRenderer(Minecraft.getMinecraft());
    public static final int DEVICE_WIDTH = 464;
    public static final int DEVICE_HEIGHT = 246;
    public static final List<ResourceLocation> WALLPAPERS = new ArrayList<>();
    public static final List<ResourceLocation> THEMES = new ArrayList<>();
    public static final ResourceLocation BOOT_NEON_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/neon/neon_boot.png");
    public static final ResourceLocation BOOT_PIXEL_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/pixel/pixel_boot.png");
    public static final ResourceLocation BOOT_CRAFT_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/craft/craft_boot.png");
    private static final ResourceLocation LAPTOP_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop.png");
    private static final List<Application> APPLICATIONS = new ArrayList<>();
    public static int BORDER = 10;
    public static final int SCREEN_WIDTH = DEVICE_WIDTH - BORDER * 2;
    public static final int SCREEN_HEIGHT = DEVICE_HEIGHT - BORDER * 2;
    private static final int BOOT_ON_TIME = 200;
    private static final int BOOT_OFF_TIME = 100;
    private static final int[] konamiCodes = new int[]{
            Keyboard.KEY_UP,
            Keyboard.KEY_UP,
            Keyboard.KEY_DOWN,
            Keyboard.KEY_DOWN,
            Keyboard.KEY_LEFT,
            Keyboard.KEY_RIGHT,
            Keyboard.KEY_LEFT,
            Keyboard.KEY_RIGHT,
            Keyboard.KEY_B,
            Keyboard.KEY_A
    };
    private static final HashMap<Integer, String> codeToName = new HashMap<>();
    public static int currentWallpaper, currentTheme;
    private static System system;
    private static BlockPos pos;
    private static Drive mainDrive;

    // Populate the list above
    static {
        codeToName.put(Keyboard.KEY_UP, "up");
        codeToName.put(Keyboard.KEY_DOWN, "down");
        codeToName.put(Keyboard.KEY_LEFT, "left");
        codeToName.put(Keyboard.KEY_RIGHT, "right");
        codeToName.put(Keyboard.KEY_A, "A");
        codeToName.put(Keyboard.KEY_B, "B");
    }

    private Settings settings;
    private TaskBar bar;
    private Window[] windows;
    private static Layout context = null;
    private NBTTagCompound appData;
    private NBTTagCompound systemData;
    private int lastMouseX, lastMouseY;
    private boolean dragging = false;
    private int bootTimer = 0;
    private BootMode bootMode = BootMode.BOOTING;
    private int blinkTimer = 0;
    private int lastCode = Keyboard.KEY_DOWN;
    private int konamiProgress = 0;
    private Layout desktop, OSSelect;
    private String wallpaperOrColor, taskbarPlacement, os;

    public int posX, posY;

    List<AppInfo> installedApps = new ArrayList<>();

    public BaseDevice(TileEntityBaseDevice te, int id, OperatingSystem OS) {
        ID = id;
        this.appData = te.getApplicationData();
        this.systemData = te.getSystemData();
        this.windows = new Window[5];
        this.settings = Settings.fromTag(systemData.getCompoundTag("Settings"));
        this.bar = OS.taskBar();
        wallpaperOrColor = Settings.fromTag(systemData.getCompoundTag("wallpaperOrColor")).hasWallpaperOrColor();
        if(wallpaperOrColor.equals("Wallpaper")) {
            currentWallpaper = systemData.getInteger("CurrentWallpaper");
            if (currentWallpaper < 0 || currentWallpaper >= WALLPAPERS.size()) {
                currentWallpaper = 0;
            }
        }
        taskbarPlacement = Settings.fromTag(systemData.getCompoundTag("taskBarPlacement")).getTaskBarPlacement();
        currentTheme = systemData.getInteger("CurrentTheme");
        if (currentTheme < 0 || currentTheme >= THEMES.size()) {
            currentTheme = 0;
        }
        os = OS.name();
        BaseDevice.system = this;
        BaseDevice.pos = te.getPos();
        java.lang.System.out.println(te.getClass().getName());
        if(bootMode == BootMode.NOTHING) {
            this.desktop = new LayoutDesktopOS(OS);
        }
        if(bootMode == BootMode.BIOS) {
            this.desktop = new LayoutBios();
        }

        if (systemData.hasKey("bootMode")) {
            this.bootMode = BootMode.getBootMode(systemData.getInteger("bootMode"));
        }

        if (systemData.hasKey("bootTimer")) {
            this.bootTimer = systemData.getInteger("bootTimer");
        }

        if (this.bootMode == null) {
            this.bootMode = BootMode.BOOTING;
            this.bootTimer = BOOT_ON_TIME;
        }
    }
    
    @Nullable
    public static BlockPos getPos() {
        return pos;
    }

    public static void addWallpaper(ResourceLocation wallpaper) {
        if (wallpaper != null) {
            WALLPAPERS.add(wallpaper);
        }
    }

    public static void addTheme(ResourceLocation theme) {
        if (theme != null) {
            THEMES.add(theme);
        }
    }

    public static System getSystem() {
        return system;
    }

    @Nullable
    public static Drive getMainDrive() {
        return mainDrive;
    }

    public static void setMainDrive(Drive mainDrive) {
        if (BaseDevice.mainDrive == null) {
            BaseDevice.mainDrive = mainDrive;
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        posX = width / 2 - DEVICE_WIDTH / 2;
        posY = height / 2 - DEVICE_HEIGHT / 2;
        switch (taskbarPlacement) {
            case "Top":
                bar.init(posX + BORDER, posY + DEVICE_HEIGHT - 236);
                break;
            case "Bottom":
                bar.init(posX + BORDER, posY + DEVICE_HEIGHT - 28);
                break;
            case "Left":
                bar.init(posX + BORDER, posY + DEVICE_HEIGHT - 28);
                break;
            case "Right":
                bar.init(posX + BORDER, posY + DEVICE_HEIGHT - 28);
                break;
        }

        installedApps.clear();
        NBTTagList tagList = systemData.getTagList("InstalledApps", Constants.NBT.TAG_STRING);
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            AppInfo info = ApplicationManager.getApplication(tagList.getStringTagAt(i));
            if(info != null && !installedApps.contains(info))
            {
                installedApps.add(info);
            }
        }
        installedApps.sort(AppInfo.SORT_NAME);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);

        for (Window window : windows) {
            if (window != null) {
                window.close();
            }
        }

        /* Send system data */
        this.updateSystemData();

        BaseDevice.pos = null;
        BaseDevice.system = null;
        BaseDevice.mainDrive = null;
    }

    private void updateSystemData()
    {
        systemData.setInteger("CurrentWallpaper", currentWallpaper);
        systemData.setInteger("CurrentTheme", currentTheme);
        systemData.setString("wallpaperOrColor", wallpaperOrColor);
        systemData.setString("taskbarPlacement", taskbarPlacement);
        systemData.setString("OS", os);
        systemData.setTag("Settings", settings.toTag());

        NBTTagList tagListApps = new NBTTagList();
        installedApps.forEach(info -> tagListApps.appendTag(new NBTTagString(info.getFormattedId())));
        systemData.setTag("InstalledApps", tagListApps);

        TaskManager.sendTask(new TaskUpdateSystemData(pos, systemData));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onResize(Minecraft mcIn, int width, int height) {
        super.onResize(mcIn, width, height);
        for (Window window : windows) {
            if (window != null)
                window.content.markForLayoutUpdate();
        }
    }

    @Override
    public void updateScreen() {
        if (this.bootMode == BootMode.NOTHING) {
            bar.onTick();

            for (Window window : windows) {
                if (window != null) {
                    window.onTick();
                }
            }

            FileBrowser.refreshList = false;
        } else if (this.bootMode != null) {
            this.bootTimer = Math.max(this.bootTimer - 1, 0);
            this.blinkTimer = Math.max(this.blinkTimer - 1, 0);
            if (this.bootTimer == 0) {
                if (this.bootMode == BootMode.BOOTING) {
                    this.bootMode = BootMode.NOTHING;
                } else {
                    this.bootMode = null;
                }
            }
        }

    }

    public TaskBar getTaskBar() {
        return bar;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	if(DEVICE_WIDTH > this.width || DEVICE_HEIGHT > this.height) {
    		GlStateManager.scale(0.5f, 0.5f, 0.5f);
    	}
    	
        this.drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LAPTOP_GUI);

        /* Physical Screen */
        int posX = width / 2 - DEVICE_WIDTH / 2;
        int posY = height / 2 - DEVICE_HEIGHT / 2;

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

        if(os.equals("None")) {
            OSSelect = new Layout();
            OSSelect.setBackground((gui, mc, x, y, width, height, mouseX1, mouseY1, windowActive) -> {
                RenderUtil.drawRectWithTexture(posX + BORDER, posY + BORDER, 10, 10, SCREEN_WIDTH, SCREEN_HEIGHT, 1, 1);
            });
            Label labelTitle = new Label("Choose What OS You Want To Have", posX + 10, posY + 40);
            labelTitle.setScale(2);
            OSSelect.addComponent(labelTitle);
            OSSelect.init();
            OSSelect.render(this, this.mc, posX + BORDER, posY + BORDER, mouseX, mouseY, true, partialTicks);
        }

        /* Center */
        if(!os.equals("None")) {
            RenderUtil.drawRectWithTexture(posX + BORDER, posY + BORDER, 10, 10, SCREEN_WIDTH, SCREEN_HEIGHT, 1, 1);
        }

        if (this.bootMode == BootMode.BOOTING) {
            Gui.drawRect(posX + BORDER, posY + BORDER, posX + DEVICE_WIDTH - BORDER, posY + DEVICE_HEIGHT - BORDER, 0xFF000000);
            switch (os) {
                case "NeonOS":
                    this.mc.getTextureManager().bindTexture(BOOT_NEON_TEXTURES);
                    break;
                case "CraftOS":
                    this.mc.getTextureManager().bindTexture(BOOT_CRAFT_TEXTURES);
                    break;
                case "PixelOS":
                    this.mc.getTextureManager().bindTexture(BOOT_PIXEL_TEXTURES);
                    break;
                case "None":
                    this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/blocks/dirt.png"));
                    break;
            }
            float f = 1.0f;
            if (this.bootTimer > BOOT_ON_TIME - 20) {
                f = ((float) (BOOT_ON_TIME - this.bootTimer)) / 20.0f;
            }
            int value = (int) (255 * f);
            GlStateManager.color(f, f, f);
            int cX = posX + DEVICE_WIDTH / 2;
            int cY = posY + DEVICE_HEIGHT / 2;

            if(!os.equals("None")) {
                /* Husky and NeonOs logos */
                this.drawTexturedModalRect(cX - 35, cY - 80, 0, 0, 70, 90);
                if ((this.blinkTimer % 10) > 5) {
                    this.drawTexturedModalRect(cX + 1, cY - 48, 70, 15, 24, 22);
                }
                this.drawTexturedModalRect(cX - 64, cY + 15, 2, 94, 128, 30);

                /* Legal information stuff */
                this.drawTexturedModalRect(posX + BORDER + 2, posY + DEVICE_HEIGHT - BORDER - 10, 1, 152, 150, 8);
                this.drawTexturedModalRect(posX + DEVICE_WIDTH - BORDER - 41, posY + DEVICE_HEIGHT - BORDER - 10, 1, 162, 39, 7);

                /* Loading bar */
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                ScaledResolution sr = new ScaledResolution(this.mc);
                int scale = sr.getScaleFactor();
                GL11.glScissor((cX - 70) * scale, (height - (cY + 74)) * scale, 140 * scale, 13 * scale);
                if (this.bootTimer <= BOOT_ON_TIME - 20) {
                    int xAdd = (BOOT_ON_TIME - (this.bootTimer + 20)) * 4;
                    this.drawTexturedModalRect(cX - 87 + xAdd % 184, cY + 61, 78, 1, 17, 13);
                }
                //this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);

                /* Loading bar outline */
                this.drawTexturedModalRect(cX - 70, cY + 60, 70, 0, 3, 15);
                this.drawTexturedModalRect(cX + 67, cY + 60, 74, 0, 3, 15);
                int color = 0xFF000000 + (value << 16) + (value << 8) + value;
                Gui.drawRect(cX - 67, cY + 60, cX + 67, cY + 61, color);
                Gui.drawRect(cX - 67, cY + 74, cX + 67, cY + 75, color);
            }
        } else if (this.bootMode != null) {
            if(!os.equals("None")) {
                /* Wallpaper */
                this.desktop.render(this, this.mc, posX + BORDER, posY + BORDER, mouseX, mouseY, true, partialTicks);

                if (this.bootMode == BootMode.NOTHING) {
                    boolean insideContext = false;
                    if(context != null)
                    {
                        insideContext = GuiHelper.isMouseInside(mouseX, mouseY, context.xPosition, context.yPosition, context.xPosition + context.width, context.yPosition + context.height);
                    }

                    Image.CACHE.entrySet().removeIf(entry ->
                    {
                        Image.CachedImage cachedImage = entry.getValue();
                        if(cachedImage.isDynamic() && cachedImage.isPendingDeletion())
                        {
                            int texture = cachedImage.getTextureId();
                            if(texture != -1)
                            {
                                GL11.glDeleteTextures(texture);
                            }
                            return true;
                        }
                        return false;
                    });

                    /* Window */
                    for(int i = windows.length - 1; i >= 0; i--)
                    {
                        Window window = windows[i];
                        if(window != null)
                        {
                            window.render(this, mc, posX + BORDER, posY + BORDER, mouseX, mouseY, i == 0 && !insideContext, partialTicks);
                        }
                    }

                    /* Application Bar */
                    switch (taskbarPlacement) {
                        case "Top":
                            bar.render(this, mc, posX + BORDER, posY + DEVICE_HEIGHT - 236, mouseX, mouseY, partialTicks);
                            break;
                        case "Bottom":
                            bar.render(this, mc, posX + BORDER, posY + DEVICE_HEIGHT - 28, mouseX, mouseY, partialTicks);
                            break;
                        case "Left":
                            bar.renderOnSide(this, mc, posX + 28, posY + DEVICE_HEIGHT - 28, mouseX, mouseY, partialTicks);
                            break;
                        case "Right":
                            bar.renderOnSide(this, mc, posX + BORDER, posY + DEVICE_HEIGHT - 28, mouseX, mouseY, partialTicks);
                            break;
                    }

                    if (context != null) {
                        context.render(this, mc, context.xPosition, context.yPosition, mouseX, mouseY, true, partialTicks);
                    }

                    super.drawScreen(mouseX, mouseY, partialTicks);
                } else {
                    Gui.drawRect(posX + BORDER, posY + BORDER, posX + DEVICE_WIDTH - BORDER, posY + DEVICE_HEIGHT - BORDER, 0x7F000000);
                    GlStateManager.pushMatrix();
                    StringBuilder s;
                    if (this.konamiProgress == -1) {
                        s = new StringBuilder("Shutting up, up, down, down, left, right, left, right, B, A...");
                    } else if (this.konamiProgress == 0) {
                        s = new StringBuilder("Shutting " + codeToName.get(this.lastCode) + "...");
                    } else {
                        s = new StringBuilder("Shutting ");
                        for (int i = 0; i < this.konamiProgress - 1; i++) {
                            s.append(codeToName.get(konamiCodes[i])).append(", ");
                        }
                        s.append(codeToName.get(konamiCodes[this.konamiProgress - 1])).append("...");
                    }
                    int w = this.mc.fontRenderer.getStringWidth(s.toString());
                    float scale = 3;
                    while (scale > 1 && w * scale > DEVICE_WIDTH) {
                        scale = scale - 0.5f;
                    }
                    GlStateManager.scale(scale, scale, 1);
                    GlStateManager.translate((posX + (DEVICE_WIDTH - w * scale) / 2) / scale, (posY + (DEVICE_HEIGHT - 8 * scale) / 2) / scale, 0);
                    this.mc.fontRenderer.drawString(TextFormatting.ITALIC + s.toString(), 0, 0, 0xFFFFFFFF, true);
                    GlStateManager.popMatrix();
                }
            }
        }
        
        if(DEVICE_WIDTH > this.width || DEVICE_HEIGHT > this.height) {
    		GlStateManager.scale(2f, 2f, 2f);
    	}
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;

        int posX = width / 2 - SCREEN_WIDTH / 2;
        int posY = height / 2 - SCREEN_HEIGHT / 2;

        if (this.bootMode == BootMode.NOTHING) {
            if (context != null) {
                int dropdownX = context.xPosition;
                int dropdownY = context.yPosition;
                if (GuiHelper.isMouseInside(mouseX, mouseY, dropdownX, dropdownY, dropdownX + context.width, dropdownY + context.height)) {
                    context.handleMouseClick(mouseX, mouseY, mouseButton);
                    return;
                } else {
                    context = null;
                }
            }

            switch (taskbarPlacement) {
                case "Top":
                    this.bar.handleClick(this, posX, posY + SCREEN_HEIGHT - 226, mouseX, mouseY, mouseButton);
                    break;
                case "Bottom":
                    this.bar.handleClick(this, posX, posY + SCREEN_HEIGHT - TaskBar.BAR_HEIGHT, mouseX, mouseY, mouseButton);
                    break;
                case "Left":
                    this.bar.handleClick(this, posX - TaskBar.BAR_HEIGHT, posY + SCREEN_HEIGHT, mouseX, mouseY, mouseButton);
                    break;
                case "Right":
                    this.bar.handleClick(this, posX, posY + SCREEN_HEIGHT - TaskBar.BAR_HEIGHT, mouseX, mouseY, mouseButton);
                    break;
            }

            for (int i = 0; i < windows.length; i++) {
                Window<Application> window = windows[i];
                if (window != null) {
                    Window dialogWindow = window.getContent().getActiveDialog();
                    if (isMouseWithinWindow(mouseX, mouseY, window) || isMouseWithinWindow(mouseX, mouseY, dialogWindow)) {
                        windows[i] = null;
                        updateWindowStack();
                        windows[0] = window;

                        windows[0].handleMouseClick(this, mouseX, mouseY, mouseButton);

                        if (isMouseWithinWindowBar(mouseX, mouseY, dialogWindow)) {
                            this.dragging = true;
                            return;
                        }

                        if (isMouseWithinWindowBar(mouseX, mouseY, window) && dialogWindow == null) {
                            this.dragging = true;
                            return;
                        }
                        break;
                    }
                }
            }
        } else if (this.bootMode == BootMode.BOOTING) {
            if (isMouseInHusky(mouseX, mouseY)) {
                this.blinkTimer = 20;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
        if (context != null) {
            int dropdownX = context.xPosition;
            int dropdownY = context.yPosition;
            if (GuiHelper.isMouseInside(mouseX, mouseY, dropdownX, dropdownY, dropdownX + context.width, dropdownY + context.height)) {
                context.handleMouseRelease(mouseX, mouseY, state);
            }
        } else if (windows[0] != null) {
            windows[0].handleMouseRelease(mouseX, mouseY, state);
        }
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState()) {
            char pressed = Keyboard.getEventCharacter();
            int code = Keyboard.getEventKey();

            if (this.bootMode == BootMode.SHUTTING_DOWN) {
                if (codeToName.containsKey(code)) {
                    boolean valid = (this.konamiProgress < 8 && code != Keyboard.KEY_A && code != Keyboard.KEY_B) || (this.konamiProgress >= 8 && (code == Keyboard.KEY_A || code == Keyboard.KEY_B));
                    if (valid) {
                        this.lastCode = code;
                        if (this.konamiProgress != -1 && code == konamiCodes[this.konamiProgress]) {
                            this.konamiProgress++;
                            if (this.konamiProgress == konamiCodes.length) {
                                this.mc.getToastGui().add(new BaseDevice.EasterEggToast());
                                PacketHandler.INSTANCE.sendToServer(new MessageUnlockAdvancement());
                                this.konamiProgress = -1;
                            }
                        } else {
                            this.konamiProgress = 0;
                        }
                    }
                }
            }

            if (this.bootMode == BootMode.BOOTING) {

                if (pressed == Keybinds.bios.getKeyCode()) {
                    Layout bios = new LayoutBios();
                    bios.init();

                    if (pressed == Keybinds.leaveBios.getKeyCode()) {
                        this.desktop.init();
                    }

                }

            }

            if (windows[0] != null) {
                windows[0].handleKeyTyped(pressed, code);
            }
            super.keyTyped(pressed, code);
        } else {
            if (windows[0] != null) {
                windows[0].handleKeyReleased(Keyboard.getEventCharacter(), Keyboard.getEventKey());
            }
        }

        this.mc.dispatchKeypresses();
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (this.bootMode == BootMode.NOTHING) {
            int posX = width / 2 - SCREEN_WIDTH / 2;
            int posY = height / 2 - SCREEN_HEIGHT / 2;

            if (context != null) {
                int dropdownX = context.xPosition;
                int dropdownY = context.yPosition;
                if (GuiHelper.isMouseInside(mouseX, mouseY, dropdownX, dropdownY, dropdownX + context.width, dropdownY + context.height)) {
                    context.handleMouseDrag(mouseX, mouseY, clickedMouseButton);
                }
                return;
            }

            if (windows[0] != null) {
                Window<Application> window = windows[0];
                Window<Dialog> dialogWindow = window.getContent().getActiveDialog();
                if (dragging) {
                    if (isMouseOnScreen(mouseX, mouseY)) {
                        if (dialogWindow == null) {
                            window.handleWindowMove(posX, posY, -(lastMouseX - mouseX), -(lastMouseY - mouseY));
                        } else {
                            dialogWindow.handleWindowMove(posX, posY, -(lastMouseX - mouseX), -(lastMouseY - mouseY));
                        }
                    } else {
                        dragging = false;
                    }
                } else {
                    if (isMouseWithinWindow(mouseX, mouseY, window) || isMouseWithinWindow(mouseX, mouseY, dialogWindow)) {
                        window.handleMouseDrag(mouseX, mouseY, clickedMouseButton);
                    }
                }
            }
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int scroll = Mouse.getEventDWheel();
        if (this.bootMode == BootMode.NOTHING) {
            if (scroll != 0) {
                if (windows[0] != null) {
                    windows[0].handleMouseScroll(mouseX, mouseY, scroll >= 0);
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void drawHoveringText(List<String> textLines, int x, int y) {
        int guiLeft = width / 2 - DEVICE_WIDTH / 2;
        int guiTop = height / 2 - DEVICE_HEIGHT / 2;
        drawHoveringText(textLines, x - guiLeft, y - guiTop, mc.fontRenderer);
    }

    @Override
    public void openApplication(AppInfo info)
    {
        openApplication(info, (NBTTagCompound) null);
    }

    @Override
    public void openApplication(AppInfo info, NBTTagCompound intentTag)
    {
        Application application = getOrCreateApplication(info);
        if(application != null)
        {
            openApplication(application, intentTag);
        }
    }

    private void openApplication(Application app, NBTTagCompound intent)
    {
        if(!ApplicationManager.isApplicationWhitelisted(app.getInfo()))
            return;

        if(!isApplicationInstalled(app.getInfo()))
            return;

        if(sendApplicationToFront(app.getInfo()))
            return;

        Window<Application> window = new Window<>(app, this);
        window.init((width - SCREEN_WIDTH) / 2, (height - SCREEN_HEIGHT) / 2, intent);

        if(appData.hasKey(app.getInfo().getFormattedId()))
        {
            app.load(appData.getCompoundTag(app.getInfo().getFormattedId()));
        }

        if(app instanceof SystemApplication)
        {
            ((SystemApplication) app).setLaptop(this);
        }

        if(app.getCurrentLayout() == null)
        {
            app.restoreDefaultLayout();
        }

        addWindow(window);

        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @Override
    public boolean openApplication(AppInfo info, File file)
    {
        Application application = getOrCreateApplication(info);
        if(application != null)
        {
            return openApplication(application, file);
        }
        return false;
    }

    private boolean openApplication(Application app, File file)
    {
        if(!ApplicationManager.isApplicationWhitelisted(app.getInfo()))
            return false;

        if(!isApplicationInstalled(app.getInfo()))
            return false;

        boolean alreadyRunning = isApplicationRunning(app.getInfo());
        openApplication(app, (NBTTagCompound) null);
        if(isApplicationRunning(app.getInfo()))
        {
            if(!app.handleFile(file))
            {
                if(!alreadyRunning)
                {
                    closeApplication(app);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Nullable
    public Application getOrCreateApplication(AppInfo info)
    {
        Application application = getRunningApplication(info);
        return application != null ? application : info.createInstance();
    }

    private Application getRunningApplication(AppInfo info)
    {
        for(Window window : windows)
        {
            if(window != null && window.content instanceof Application)
            {
                Application application = (Application) window.content;
                if(application.getInfo() == info)
                {
                    return application;
                }
            }
        }
        return null;
    }

    @Override
    public void closeApplication(AppInfo info)
    {
        Application application = getRunningApplication(info);
        if(application != null)
        {
            closeApplication(application);
        }
    }

    private void closeApplication(Application app)
    {
        for(int i = 0; i < windows.length; i++)
        {
            Window window = windows[i];
            if(window != null && window.content instanceof Application)
            {
                if(((Application) window.content).getInfo().equals(app.getInfo()))
                {
                    if(app.isDirty())
                    {
                        NBTTagCompound container = new NBTTagCompound();
                        app.save(container);
                        app.clean();
                        appData.setTag(app.getInfo().getFormattedId(), container);
                        TaskManager.sendTask(new TaskUpdateApplicationData(pos.getX(), pos.getY(), pos.getZ(), app.getInfo().getFormattedId(), container));
                    }

                    if(app instanceof SystemApplication)
                    {
                        ((SystemApplication) app).setLaptop(null);
                    }

                    window.handleClose();
                    windows[i] = null;
                    return;
                }
            }
        }
    }

    private boolean sendApplicationToFront(AppInfo info)
    {
        for(int i = 0; i < windows.length; i++)
        {
            Window window = windows[i];
            if(window != null && window.content instanceof Application && ((Application) window.content).getInfo() == info)
            {
                windows[i] = null;
                updateWindowStack();
                windows[0] = window;
                return true;
            }
        }
        return false;
    }

    private void addWindow(Window<Application> window) {
        if (hasReachedWindowLimit())
            return;

        updateWindowStack();
        windows[0] = window;
    }

    private void updateWindowStack() {
        for (int i = windows.length - 1; i >= 0; i--) {
            if (windows[i] != null) {
                if (i + 1 < windows.length) {
                    if (i == 0 || windows[i - 1] != null) {
                        if (windows[i + 1] == null) {
                            windows[i + 1] = windows[i];
                            windows[i] = null;
                        }
                    }
                }
            }
        }
    }

    private boolean hasReachedWindowLimit() {
        for (Window window : windows) {
            if (window == null) return false;
        }
        return true;
    }

    private boolean isMouseOnScreen(int mouseX, int mouseY) {
        int posX = (width - SCREEN_WIDTH) / 2;
        int posY = (height - SCREEN_HEIGHT) / 2;
        return GuiHelper.isMouseInside(mouseX, mouseY, posX, posY, posX + SCREEN_WIDTH, posY + SCREEN_HEIGHT);
    }

    private boolean isMouseWithinWindowBar(int mouseX, int mouseY, Window window) {
        if (window == null) return false;
        int posX = (width - SCREEN_WIDTH) / 2;
        int posY = (height - SCREEN_HEIGHT) / 2;
        return GuiHelper.isMouseInside(mouseX, mouseY, posX + window.offsetX + 1, posY + window.offsetY + 1, posX + window.offsetX + window.width - 13, posY + window.offsetY + 11);
    }

    private boolean isMouseWithinWindow(int mouseX, int mouseY, Window window) {
        if (window == null) return false;
        int posX = (width - SCREEN_WIDTH) / 2;
        int posY = (height - SCREEN_HEIGHT) / 2;
        return GuiHelper.isMouseInside(mouseX, mouseY, posX + window.offsetX, posY + window.offsetY, posX + window.offsetX + window.width, posY + window.offsetY + window.height);
    }

    public boolean isApplicationRunning(AppInfo info)
    {
        for(Window window : windows)
        {
            if(window != null && ((Application) window.content).getInfo() == info)
            {
                return true;
            }
        }
        return false;
    }

    public static void nextWallpaper() {
        if (currentWallpaper + 1 < WALLPAPERS.size()) {
            currentWallpaper++;
        }
    }

    public static void prevWallpaper() {
        if (currentWallpaper - 1 >= 0) {
            currentWallpaper--;
        }
    }

    public void nextTheme() {
        if (currentTheme + 1 < THEMES.size()) {
            currentTheme++;
        }
    }

    public void prevTheme() {
        if (currentTheme - 1 >= 0) {
            currentTheme--;
        }
    }

    public static int getCurrentWallpaper() {
        return currentWallpaper;
    }

    public static int getCurrentTheme() {
        return currentTheme;
    }

    public static List<ResourceLocation> getWallapapers() {
        return ImmutableList.copyOf(WALLPAPERS);
    }

    public static List<ResourceLocation> getThemes() {
        return ImmutableList.copyOf(THEMES);
    }

    @Override
    public Collection<ThemeInfo> getInstalledThemes() {
        return null;
    }

    @Override
    public List<AppInfo> getInstalledApplications()
    {
        return ImmutableList.copyOf(installedApps);
    }

    public boolean isApplicationInstalled(AppInfo info)
    {
        return info.isSystemApp() || installedApps.contains(info);
    }

    public void installApplication(AppInfo info, @Nullable Callback<Object> callback)
    {
        if(!ApplicationManager.isApplicationWhitelisted(info))
            return;

        Task task = new TaskInstallApp(info, pos, true);
        task.setCallback((tagCompound, success) ->
        {
            if(success)
            {
                installedApps.add(info);
                installedApps.sort(AppInfo.SORT_NAME);
            }
            if(callback != null)
            {
                callback.execute(null, success);
            }
        });
        TaskManager.sendTask(task);
    }

    public void removeApplication(AppInfo info, @Nullable Callback<Object> callback)
    {
        if(!ApplicationManager.isApplicationWhitelisted(info))
            return;

        Task task = new TaskInstallApp(info, pos, false);
        task.setCallback((tagCompound, success) ->
        {
            if(success)
            {
                installedApps.remove(info);
            }
            if(callback != null)
            {
                callback.execute(null, success);
            }
        });
        TaskManager.sendTask(task);
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void openContext(Layout layout, int x, int y)
    {
        layout.updateComponents(x, y);
        context = layout;
        layout.init();
    }

    @Override
    public boolean hasContext()
    {
        return context != null;
    }

    @Override
    public void closeContext()
    {
        context = null;
        dragging = false;
    }

    public static Layout getContext() {
        return context;
    }

    private boolean isMouseInHusky(int mouseX, int mouseY) {
        if (this.bootTimer > BOOT_ON_TIME - 20 || this.blinkTimer > 0) {
            return false;
        }
        int posX = (width - DEVICE_WIDTH) / 2;
        int posY = (height - DEVICE_HEIGHT) / 2;
        int cX = posX + DEVICE_WIDTH / 2;
        int cY = posY + DEVICE_HEIGHT / 2;
        return RenderUtil.isMouseInside(mouseX, mouseY, cX - 34, cY - 80, cX + 34, cY + 10);
    }

    public void shutdown() {
        this.bootTimer = BOOT_OFF_TIME;
        this.bootMode = BootMode.SHUTTING_DOWN;
    }

    public enum BootMode {

        BOOTING,
        NOTHING,
        SHUTTING_DOWN,
        BIOS,
        RESTARTING,
        SLEEPING,
        BIOS_SETTINGS;

        public static BootMode getBootMode(int i) {
            return (i >= 0 && i < values().length) ? values()[i] : null;
        }

        public static int ordinal(BootMode bm) {
            return bm != null ? bm.ordinal() : -1;
        }
    }

    public static class EasterEggToast implements IToast {

        private boolean hasPlayedSound = false;

        @Override
        @MethodsReturnNonnullByDefault
        @ParametersAreNonnullByDefault
        public Visibility draw(GuiToast toastGui, long delta) {
            toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

            int i = 16776960;

            String s = "Easter egg found ;p";
            int w = toastGui.getMinecraft().fontRenderer.getStringWidth(s);
            toastGui.getMinecraft().fontRenderer.drawString(s, 80 - w / 2, 12, i | -16777216);

            if (!this.hasPlayedSound && delta > 0L) {
                this.hasPlayedSound = true;
            }

            RenderHelper.enableGUIStandardItemLighting();
            return delta >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        }
    }

}
