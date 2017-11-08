package net.husky.device.core;

import com.google.common.collect.ImmutableList;
import net.husky.device.HuskyDeviceMod;
import net.husky.device.Reference;
import net.husky.device.api.app.Application;
import net.husky.device.api.app.Dialog;
import net.husky.device.api.app.Layout;
import net.husky.device.api.io.Drive;
import net.husky.device.api.task.TaskManager;
import net.husky.device.api.utils.RenderUtil;
import net.husky.device.programs.system.SystemApplication;
import net.husky.device.programs.system.component.FileBrowser;
import net.husky.device.programs.system.task.TaskUpdateApplicationData;
import net.husky.device.programs.system.task.TaskUpdateSystemData;
import net.husky.device.tileentity.TileEntityLaptop;
import net.husky.device.util.ColorHelper;
import net.husky.device.util.GuiHelper;
import net.husky.device.api.app.System;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Laptop extends NeonOS {
    
    public static final int ID = BASE_ID + 1;

    private static final int DEVICE_WIDTH = BASE_DEVICE_WIDTH + 232;
    private static final int DEVICE_HEIGHT = BASE_DEVICE_HEIGHT + 123;
    static final int SCREEN_WIDTH = BASE_SCREEN_WIDTH * 2;
    static final int SCREEN_HEIGHT = BASE_SCREEN_HEIGHT * 2;

    public Laptop(TileEntityLaptop laptop) {
        this.appData = laptop.getApplicationData();
        this.systemData = laptop.getSystemData();
        super.settings = Settings.fromTag(this.systemData.getCompoundTag("Settings"));
        super.currentWallpaper = this.systemData.getInteger("CurrentWallpaper");
        pos = laptop.getPos();
    }

    @Nullable
    public static BlockPos getPos() {
        return pos;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BASE_GUI);

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
        this.mc.getTextureManager().bindTexture(super.WALLPAPERS.get(super.currentWallpaper));
        RenderUtil.drawRectWithTexture(posX + 10, posY + 10, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 256, 144);

        boolean insideContext = false;
        if (context != null) {
            insideContext = GuiHelper.isMouseInside(mouseX, mouseY, context.xPosition, context.yPosition, context.xPosition + context.width, context.yPosition + context.height);
        }

        /* Window */
        for (int i = windows.length - 1; i >= 0; i--) {
            Window window = windows[i];
            if (window != null) {
                window.render(this, mc, posX + BORDER, posY + BORDER, mouseX, mouseY, i == 0 && !insideContext, partialTicks);
            }
        }

        /* Application Bar */
        bar.render(this, mc, posX + 10, posY + 10, mouseX, mouseY, partialTicks);

        if (context != null) {
            context.render(this, mc, context.xPosition, context.yPosition, mouseX, mouseY, true, partialTicks);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}