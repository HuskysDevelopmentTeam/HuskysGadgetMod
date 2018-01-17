package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.gui.GuiButtonClose;
import net.thegaminghuskymc.gadgetmod.gui.GuiButtonFullscreen;
import net.thegaminghuskymc.gadgetmod.gui.GuiButtonMinimise;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Window<T extends Wrappable> {

    public static final ResourceLocation WINDOW_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/application.png");

    private static ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();

    public static final int COLOUR_WINDOW_DARK_1 = 0xFF9E9E9E;
    private static final int COLOUR_WINDOW_DARK_2 = 0xCC535861;
    T content;
    int width, height;
    int offsetX, offsetY;
    private Laptop laptop;
    private Window<Dialog> dialogWindow = null;
    private Window<? extends Wrappable> parent = null;
    private GuiButton btnClose, btnMinimize, btnFullscreen;

    public Window(T wrappable, Laptop laptop) {
        this.content = wrappable;
        this.laptop = laptop;
        wrappable.setWindow(this);
    }

    void setWidth(int width) {
        this.width = width + 2;
        if (this.width > Laptop.SCREEN_WIDTH) {
            this.width = Laptop.SCREEN_WIDTH;
        }
    }

    void setHeight(int height) {
        this.height = height + 14;
        if (this.height > 178) {
            this.height = 178;
        }
    }

    void init(int x, int y) {
        btnClose = new GuiButtonClose(0, x + offsetX + width - 12, y + offsetY + 1);
        btnMinimize = new GuiButtonMinimise(1, x + offsetX + width - 12, y + offsetY + 1);
        btnFullscreen = new GuiButtonFullscreen(2, x + offsetX + width - 12, y + offsetY + 1);
        content.init();
    }

    public void onTick() {
        if (dialogWindow != null) {
            dialogWindow.onTick();
        }
        content.onTick();
    }

    public void render(Laptop gui, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        if (content.isPendingLayoutUpdate()) {
            this.setWidth(content.getWidth());
            this.setHeight(content.getHeight());
            this.offsetX = (Laptop.SCREEN_WIDTH - width) / 2;
            this.offsetY = (Laptop.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height) / 2;
            updateComponents(x, y);
            content.clearPendingLayout();
        }

        GlStateManager.enableBlend();

        /* Edges */
        Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + width, y + offsetY + 13, COLOUR_WINDOW_DARK_1);

        /* Center */
        Gui.drawRect(x + offsetX, y + offsetY + 13, x + offsetX + width, y + offsetY + height, COLOUR_WINDOW_DARK_2);

        mc.fontRenderer.drawString(content.getWindowTitle(), x + offsetX + 3, y + offsetY + 3, Color.WHITE.getRGB(), true);

        btnClose.drawButton(mc, mouseX, mouseY, partialTicks);
        btnMinimize.drawButton(mc, mouseX, mouseY, partialTicks);
        btnFullscreen.drawButton(mc, mouseX, mouseY, partialTicks);

        GlStateManager.disableBlend();

        /* Render content */
        content.render(gui, mc, x + offsetX + 1, y + offsetY + 13, mouseX, mouseY, active && dialogWindow == null, partialTicks);

        if (dialogWindow != null) {
            Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + width, y + offsetY + height, new Color(1.0f, 1.0f, 1.0f, 0.0f).getAlpha());
            dialogWindow.render(gui, mc, x, y, mouseX, mouseY, active, partialTicks);
        }
    }

    public void handleKeyTyped(char character, int code) {
        if (dialogWindow != null) {
            dialogWindow.handleKeyTyped(character, code);
            return;
        }
        content.handleKeyTyped(character, code);
    }

    public void handleKeyReleased(char character, int code) {
        if (dialogWindow != null) {
            dialogWindow.handleKeyReleased(character, code);
            return;
        }
        content.handleKeyReleased(character, code);
    }

    public void handleWindowMove(int screenStartX, int screenStartY, int mouseDX, int mouseDY) {
        int newX = offsetX + mouseDX;
        int newY = offsetY + mouseDY;

        if (newX >= 0 && newX <= Laptop.SCREEN_WIDTH - width) {
            this.offsetX = newX;
        } else if (newX < 0) {
            this.offsetX = 0;
        } else {
            this.offsetX = Laptop.SCREEN_WIDTH - width;
        }
        if (newY >= 0 && newY <= Laptop.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height) {
        
            this.offsetY = newY;
        } else if (newY < 0) {
            this.offsetY = 0;
        } else {
            this.offsetY = Laptop.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height;
        }

        updateComponents(screenStartX, screenStartY);
    }

    void handleMouseClick(Laptop gui, int x, int y, int mouseX, int mouseY, int mouseButton) {
        if (btnClose.isMouseOver()) {
            if (content instanceof Application) {
                gui.close((Application) content);
                return;
            }

            if (parent != null) {
                parent.closeDialog();
            }
        }

        if (btnMinimize.isMouseOver()) {
            if (content instanceof Application) {
                gui.minimize((Application) content);
                return;
            }
        }

        if (btnFullscreen.isMouseOver()) {
            if (content instanceof Application) {
                gui.fullscreen((Application) content);
                return;
            }
        }

        if (dialogWindow != null) {
            dialogWindow.handleMouseClick(gui, x, y, mouseX, mouseY, mouseButton);
            return;
        }

        content.handleMouseClick(mouseX, mouseY, mouseButton);
    }

    void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseDrag(mouseX, mouseY, mouseButton);
            return;
        }
        content.handleMouseDrag(mouseX, mouseY, mouseButton);
    }

    void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseRelease(mouseX, mouseY, mouseButton);
            return;
        }
        content.handleMouseRelease(mouseX, mouseY, mouseButton);
    }

    void handleMouseScroll(int mouseX, int mouseY, boolean direction) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseScroll(mouseX, mouseY, direction);
            return;
        }
        content.handleMouseScroll(mouseX, mouseY, direction);
    }

    public void handleClose() {
        content.onClose();
    }

    public void handleMinimize() {
        content.onMinimize();
    }

    public void handleFullscreen() {
        content.onFullscreen();
    }

    private void updateComponents(int x, int y) {
        content.updateComponents(x + offsetX + 1, y + offsetY + 13);
        btnClose.x = x + offsetX + width - 12;
        btnClose.y = y + offsetY + 1;

        btnFullscreen.x = x + offsetX + width - 24;
        btnFullscreen.y = y + offsetY + 1;

        btnMinimize.x = x + offsetX + width - 35;
        btnMinimize.y = y + offsetY + 1;
    }

    public void openDialog(Dialog dialog) {
        if (dialogWindow != null) {
            dialogWindow.openDialog(dialog);
        } else {
            dialogWindow = new Window(dialog, laptop);
            dialogWindow.init(0, 0);
            dialogWindow.setParent(this);
        }
    }

    public void closeDialog() {
        if (dialogWindow != null) {
            dialogWindow.handleClose();
            dialogWindow = null;
        }
    }

    public Window<Dialog> getDialogWindow() {
        return dialogWindow;
    }

    public void close() {
        if (content instanceof Application) {
            laptop.close((Application) content);
            return;
        }
        if (parent != null) {
            parent.closeDialog();
        }
    }

    public void minimize() {
        if (content instanceof Application) {
            laptop.minimize((Application) content);
            return;
        }
    }

    public void fullscreen() {
        if (content instanceof Application) {
            laptop.fullscreen((Application) content);
            return;
        }
    }

    public Window getParent() {
        return parent;
    }

    public void setParent(Window parent) {
        this.parent = parent;
    }

    public T getContent() {
        return content;
    }

}
