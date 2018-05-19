package net.thegaminghuskymc.gadgetmod.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;

public class ClientNotification implements IToast {
    private static final ResourceLocation TEXTURE_TOASTS = new ResourceLocation("hgm:textures/gui/toast.png");

    private Icons icon;
    private String title;
    private String subTitle;

    private ClientNotification() {
    }

    public static ClientNotification loadFromTag(NBTTagCompound tag) {
        ClientNotification notification = new ClientNotification();
        notification.icon = Icons.values()[tag.getInteger("icon")];
        notification.title = tag.getString("title");
        if (tag.hasKey("subTitle", Constants.NBT.TAG_STRING)) {
            notification.subTitle = tag.getString("subTitle");
        }
        return notification;
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

        toastGui.getMinecraft().getTextureManager().bindTexture(icon.getIconAsset());
        RenderUtil.drawRectWithTexture(6, 6, icon.getU(), icon.getV(), 20, 20, 10, 10, 200, 200);

        if (subTitle == null) {
            toastGui.getMinecraft().fontRenderer.drawString(RenderUtil.clipStringToWidth(I18n.format(title), 118), 38, 12, -1, true);
        } else {
            toastGui.getMinecraft().fontRenderer.drawString(RenderUtil.clipStringToWidth(I18n.format(title), 118), 38, 7, -1, true);
            toastGui.getMinecraft().fontRenderer.drawString(RenderUtil.clipStringToWidth(I18n.format(subTitle), 118), 38, 18, -1);
        }

        return delta >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

    public void push() {
        Minecraft.getMinecraft().getToastGui().add(this);
    }
}