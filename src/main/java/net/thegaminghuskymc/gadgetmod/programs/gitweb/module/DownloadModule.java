package net.thegaminghuskymc.gadgetmod.programs.gitweb.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.io.File;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.component.GitWebFrame;

import java.awt.*;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class DownloadModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "file-app", "file-data" };
    }

    @Override
    public String[] getOptionalData()
    {
        return new String[] { "file-name", "text" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        return 45;
    }

    @Override
    public void generate(GitWebFrame frame, Layout layout, int width, Map<String, String> data)
    {
        int height = calculateHeight(data, width) - 5;
        AppInfo info = ApplicationManager.getApplication(data.get("file-app"));
        layout.setBackground((gui, mc, x, y, width1, height1, mouseX, mouseY, windowActive) ->
        {
            int section = layout.width / 6;
            int subWidth = section * 4;
            int posX = x + section;
            int posY = y + 5;
            Gui.drawRect(posX, posY, posX + subWidth, posY + height - 5, Color.BLACK.getRGB());
            Gui.drawRect(posX + 1, posY + 1, posX + subWidth - 1, posY + height - 5 - 1, Color.DARK_GRAY.getRGB());

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(BaseDevice.ICON_TEXTURES);
            int iconU = 0, iconV = 0;
            if(info != null)
            {
                iconU = info.getIconU();
                iconV = info.getIconV();
            }
            RenderUtil.drawRectWithTexture(posX + 5, posY + 3, iconU, iconV, 28, 28, 14, 14, 224, 224);

            int textWidth = subWidth - 70 - 10 - 30 - 5;
            RenderUtil.drawStringClipped(data.getOrDefault("file-name", "File"), posX + 37, posY + 7, textWidth, Color.ORANGE.getRGB(), true);
            if(data.containsKey("text"))
            {
                RenderUtil.drawStringClipped(data.get("text"), posX + 37, posY + 19, textWidth, Color.LIGHT_GRAY.getRGB(), false);
            }
        });

        int section = layout.width / 6;
        Button button = new Button(0, 10, "Download", Icons.IMPORT);
        button.left = section * 5 - 70 - 5;
        button.setSize(70, height - 15);
        button.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            try
            {
                NBTTagCompound tag = JsonToNBT.getTagFromJson(data.get("file-data"));
                File file = new File(data.getOrDefault("file-name", ""), data.get("file-app"), tag);
                Dialog dialog = new Dialog.SaveFile(frame.getApp(), file);
                frame.getApp().openDialog(dialog);
            }
            catch(NBTException e)
            {
                e.printStackTrace();
            }
        });
        layout.addComponent(button);
    }
}
