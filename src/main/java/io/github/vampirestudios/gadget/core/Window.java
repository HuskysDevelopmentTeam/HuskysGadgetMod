package io.github.vampirestudios.gadget.core;

import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.Dialog;
import io.github.vampirestudios.gadget.api.utils.RenderUtil;
import io.github.vampirestudios.gadget.gui.GuiButtonFullScreen;
import io.github.vampirestudios.gadget.gui.GuiButtonMaximize;
import io.github.vampirestudios.gadget.gui.GuiButtonMinimize;
import io.github.vampirestudios.gadget.gui.GuiButtonWindow;
import io.github.vampirestudios.gadget.programs.system.object.ColourScheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;

public class Window<T extends Wrappable> {

    public static final ResourceLocation WINDOW_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/application.png");

    public static ColourScheme colourScheme = new ColourScheme();
    public static final int Color_WINDOW_DARK = new Color(0F, 0F, 0F, 0.25F).getRGB();

    protected T content;
    private int width, height;
    private int offsetX, offsetY;
    private int smallOffsetX, smallOffsetY;
    private int smallWidth, smallHeight;
    private boolean maximized, minimized, fullScreen;

    private BaseDevice laptop;
    private Window<Dialog> dialogWindow = null;
    private Window<? extends Wrappable> parent = null;

    protected GuiButtonMaximize btnMaximize;
    protected GuiButtonMinimize btnMinimize;
    protected GuiButtonFullScreen btnFullscreen;
    protected GuiButtonWindow btnClose;

    public Window(T wrappable, BaseDevice laptop) {
        this.content = wrappable;
        this.laptop = laptop;
        wrappable.setWindow(this);
    }

    protected void setWidth(int width) {
        this.width = width;
        if (this.width > BaseDevice.SCREEN_WIDTH - offsetX) {
            this.width = BaseDevice.SCREEN_WIDTH - offsetX;
        }
    }

    protected void setHeight(int height) {
        this.height = height + 18;
        if (this.height > Laptop.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - offsetY) {
            this.height = 178 - offsetY;
        }
    }

    public void init(int x, int y, @Nullable NBTTagCompound intent) {
        btnClose = new GuiButtonWindow(0, x + offsetX + width - 12, y + offsetY + 3);
        btnMinimize = new GuiButtonMinimize(1, x + offsetX + width - 12, y + offsetY + 3);
        btnFullscreen = new GuiButtonFullScreen(2, x + offsetX + width - 12, y + offsetY + 3);
        btnMaximize = new GuiButtonMaximize(1, x + offsetX + width - 12 * 2 + 1, y + offsetY + 1);
        content.init(intent);
    }

    public void onTick() {
        if (dialogWindow != null) {
            dialogWindow.onTick();
        }
        content.onTick();

        btnMaximize.enabled = content.isResizable();
        btnMaximize.visible = content.isDecorated();
        btnClose.visible = content.isDecorated();
    }

    public void render(BaseDevice gui, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        if (content.isPendingLayoutUpdate()) {
            this.setWidth(content.getWidth());
            this.setHeight(content.getHeight());
            this.offsetX = (BaseDevice.SCREEN_WIDTH - width) / 2;
            this.offsetY = (BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height) / 2;
            updateComponents(x, y);
            content.clearPendingLayout();
        }

        int width = this.getWidth();
        int height = this.getHeight();
        int offsetX = this.getOffsetX();
        int offsetY = this.getOffsetY();

        if(content.isDecorated()) {
            GlStateManager.enableBlend();

            /* Theme Top Bar */
            Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + width, y + offsetY + 18, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            /* Center */
            Gui.drawRect(x + offsetX, y + offsetY + 18, x + offsetX + width, y + offsetY + height + 5, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB());

            RenderUtil.drawApplicationIcon(content.getAppInfo(), x + offsetX + 2, y + offsetY + 2);
            String windowTitle = content.getWindowTitle();
            if (mc.fontRenderer.getStringWidth(windowTitle) > width - 2 - 13 - 3) // window width, border, close button, padding, padding
            {
                windowTitle = mc.fontRenderer.trimStringToWidth(windowTitle, width - 2 - 13 - 3);
            }
            mc.fontRenderer.drawString(windowTitle, x + offsetX + 3, y + offsetY + 3, Color.WHITE.getRGB(), true);
        }

        btnClose.drawButton(mc, mouseX, mouseY, partialTicks);
        btnMinimize.drawButton(mc, mouseX, mouseY, partialTicks);
        btnFullscreen.drawButton(mc, mouseX, mouseY, partialTicks);
        btnMaximize.drawButton(mc, mouseX, mouseY, partialTicks);

        GlStateManager.disableBlend();

        /* Render content */
        content.render(gui, mc, x + offsetX, y + offsetY + 18, mouseX, mouseY, active && dialogWindow == null, partialTicks);

        if (dialogWindow != null) {
            Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + width, y + offsetY + height, new Color(1.0f, 1.0f, 1.0f, 0.0f).getAlpha());
            if (content.isDecorated())
            {
                Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + width, y + offsetY + height, Color_WINDOW_DARK);
            } else
            {
                Gui.drawRect(x + offsetX + 1, y + offsetY + 13, x + offsetX + width - 1, y + offsetY + height - 1, Color_WINDOW_DARK);
            }
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

    public void handleWindowMove(int mouseDX, int mouseDY) {
        setPosition(offsetX + mouseDX, offsetY + mouseDY);
    }

    public boolean resize(int width, int height)
    {
        boolean result = content.resize(width, height);
        content.onResize(content.getWidth(), content.getHeight());
        setWidth(content.getWidth());
        setHeight(content.getHeight());
        updateComponents((laptop.width - BaseDevice.SCREEN_WIDTH) / 2, (laptop.height - BaseDevice.SCREEN_HEIGHT) / 2);
        return result;
    }

    public void setPosition(int x, int y) {
        int screenStartX = (laptop.width - BaseDevice.SCREEN_WIDTH) / 2;
        int screenStartY = (laptop.height - BaseDevice.SCREEN_HEIGHT) / 2;
        if (x >= 0 && x <= BaseDevice.SCREEN_WIDTH - width)
        {
            this.offsetX = x;
        } else if (x < 0)
        {
            this.offsetX = 0;
        } else
        {
            this.offsetX = BaseDevice.SCREEN_WIDTH - width;
        }

        if (y >= 0 && y <= BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height)
        {
            this.offsetY = y;
        } else if (y < 0)
        {
            this.offsetY = 0;
        } else
        {
            this.offsetY = BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT - height;
        }
        updateComponents(screenStartX, screenStartY);
    }

    public void setMaximized(boolean maximized) {
        int posX = (laptop.width - BaseDevice.SCREEN_WIDTH) / 2;
        int posY = (laptop.height - BaseDevice.SCREEN_HEIGHT) / 2;

        if (!this.maximized)
        {
            smallOffsetX = offsetX;
            smallOffsetY = offsetY;
            smallWidth = width;
            smallHeight = height;
            offsetX = 0;
            offsetY = 0;
            width = BaseDevice.SCREEN_WIDTH;
            height = BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT;
        } else
        {
            width = smallWidth - 2;
            height = smallHeight - 14;
            offsetX = smallOffsetX;
            offsetY = smallOffsetY;
        }
        this.maximized = maximized;

        this.resize(width, height);
        this.updateComponents(posX, posY);
    }

    public void setMinimized(boolean minimized) {
        int posX = (laptop.width - BaseDevice.SCREEN_WIDTH) / 2;
        int posY = (laptop.height - BaseDevice.SCREEN_HEIGHT) / 2;

        if (!this.minimized)
        {
            smallOffsetX = offsetX;
            smallOffsetY = offsetY;
            smallWidth = width;
            smallHeight = height;
            offsetX = 0;
            offsetY = 0;
            width = BaseDevice.SCREEN_WIDTH;
            height = BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT;
        } else
        {
            width = smallWidth - 2;
            height = smallHeight - 14;
            offsetX = smallOffsetX;
            offsetY = smallOffsetY;
        }
        this.minimized = minimized;

        this.resize(width, height);
        this.updateComponents(posX, posY);
    }

    public void setFullScreen(boolean fullScreen) {
        int posX = (laptop.width - BaseDevice.SCREEN_WIDTH) / 2;
        int posY = (laptop.height - BaseDevice.SCREEN_HEIGHT) / 2;

        if (!this.fullScreen)
        {
            smallOffsetX = offsetX;
            smallOffsetY = offsetY;
            smallWidth = width;
            smallHeight = height;
            offsetX = 0;
            offsetY = 0;
            width = BaseDevice.SCREEN_WIDTH;
            height = BaseDevice.SCREEN_HEIGHT - TaskBar.BAR_HEIGHT;
        } else
        {
            width = smallWidth - 2;
            height = smallHeight - 14;
            offsetX = smallOffsetX;
            offsetY = smallOffsetY;
        }
        this.fullScreen = fullScreen;

        this.resize(width, height);
        this.updateComponents(posX, posY);
    }

    void handleMouseClick(BaseDevice gui, int x, int y, int mouseX, int mouseY, int mouseButton)
    {
        if (dialogWindow == null)
        {
            if (btnMaximize.isMouseOver())
            {
                if (content.isResizable())
                {
                    this.setMaximized(!this.maximized);
                }
            }

            if (btnMinimize.isMouseOver())
            {
                if (content.isResizable())
                {
                    this.setMinimized(!this.minimized);
                }
            }

            if (btnFullscreen.isMouseOver())
            {
                if (content.isResizable())
                {
                    this.setFullScreen(!this.fullScreen);
                }
            }

            if (btnClose.isMouseOver())
            {
                if (content instanceof Application)
                {
                    gui.closeApplication(((Application) content).getInfo());
                    return;
                }

                if (parent != null)
                {
                    parent.closeDialog();
                }
            }

            content.handleMouseClick(mouseX, mouseY, mouseButton);
        } else
        {
            dialogWindow.handleMouseClick(gui, x, y, mouseX, mouseY, mouseButton);
        }
    }

    public void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseDrag(mouseX, mouseY, mouseButton);
            return;
        }
        content.handleMouseDrag(mouseX, mouseY, mouseButton);
    }

    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseRelease(mouseX, mouseY, mouseButton);
            return;
        }
        content.handleMouseRelease(mouseX, mouseY, mouseButton);
    }

    public void handleMouseScroll(int mouseX, int mouseY, boolean direction) {
        if (dialogWindow != null) {
            dialogWindow.handleMouseScroll(mouseX, mouseY, direction);
            return;
        }
        content.handleMouseScroll(mouseX, mouseY, direction);
    }

    public void handleClose() {
        content.onClose();
    }

    private void updateComponents(int x, int y) {

        int width = this.getWidth();
        int height = this.getHeight();
        int offsetX = this.getOffsetX();
        int offsetY = this.getOffsetY();

        content.updateComponents(x + offsetX + 1, y + offsetY + 18);
        btnClose.x = x + offsetX + width - 12;
        btnClose.y = y + offsetY + 3;

        btnFullscreen.x = x + offsetX + width - 24;
        btnFullscreen.y = y + offsetY + 3;

        btnMaximize.x = x + offsetX + width - 12 * 2 + 1;
        btnMaximize.y = y + offsetY + 1;

        btnMinimize.x = x + offsetX + width - 35;
        btnMinimize.y = y + offsetY + 3;

        if (dialogWindow != null)
        {
            dialogWindow.updateComponents(x, y);
        }
    }

    public void openDialog(Dialog dialog) {
        if (dialogWindow != null) {
            dialogWindow.openDialog(dialog);
        } else {
            dialogWindow = new Window<>(dialog, null);
            dialogWindow.init((laptop.width - Laptop.SCREEN_WIDTH) / 2, (laptop.height - Laptop.SCREEN_HEIGHT) / 2, null);
            dialogWindow.setPosition((Laptop.SCREEN_WIDTH - dialog.getWidth()) / 2, (Laptop.SCREEN_HEIGHT - dialog.getHeight()) / 2 - TaskBar.BAR_HEIGHT);
            dialogWindow.setParent(this);
        }
    }

    private void closeDialog() {
        if (dialogWindow != null) {
            dialogWindow.handleClose();
            dialogWindow = null;
        }
    }

    public Window getDialogWindow() {
        return dialogWindow;
    }

    public void close() {
        if (content instanceof Application) {
            laptop.closeApplication(((Application) content).getInfo());
            return;
        }
        if (parent != null) {
            parent.closeDialog();
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

    public int getWidth()
    {
        return this.width;
    }
    public int getHeight()
    {
        return this.height;
    }
    public int getOffsetX()
    {
        return this.offsetX;
    }
    public int getOffsetY()
    {
        return this.offsetY;
    }

    public boolean isResizable()
    {
        return content.isResizable();
    }
    public boolean isDecorated()
    {
        return content.isDecorated();
    }
    public boolean isMaximized()
    {
        return maximized;
    }
    public boolean isMinimized()
    {
        return minimized;
    }
    public boolean isFullScreen()
    {
        return fullScreen;
    }

}
