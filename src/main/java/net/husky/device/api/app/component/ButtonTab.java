package net.husky.device.api.app.component;

import net.husky.device.api.app.Component;
import net.husky.device.api.app.IIcon;
import net.husky.device.api.utils.RenderUtil;
import net.husky.device.core.Laptop;
import net.husky.device.core.NeonOS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ButtonTab extends Button {

    private int tabIndex;

    protected boolean hasItemIcon = false;
    protected ItemStack itemIcon = null;

    protected boolean hasResourceIcon = false;
    protected ResourceLocation resourceIcon = null;
    protected int iconU, iconV;
    protected int iconWidth, iconHeight;

    protected boolean hasIcon = false;
    protected IIcon icon = null;

    public ButtonTab(int left, int top, int width, int height, String text, int tabIndex) {
        super(left, top, width, height, text);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, ResourceLocation icon, int iconU, int iconV, int iconWidth, int iconHeight, int tabIndex) {
        super(left, top, iconWidth + 6, iconHeight + 6, "");
        super.setIcon(icon, iconU, iconV, iconWidth, iconHeight);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, int width, int height, ResourceLocation icon, int iconU, int iconV, int iconWidth, int iconHeight, int tabIndex) {
        super(left, top, Math.max(width, iconWidth + 6), Math.max(height, iconHeight + 6), "");
        super.setIcon(icon, iconU, iconV, iconWidth, iconHeight);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(String text, int left, int top, ResourceLocation icon, int iconU, int iconV, int iconWidth, int iconHeight, int tabIndex) {
        super(left, top, iconWidth + 6, iconHeight + 6, text);
        super.setIcon(icon, iconU, iconV, iconWidth, iconHeight);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(String text, int left, int top, int width, int height, ResourceLocation icon, int iconU, int iconV, int iconWidth, int iconHeight, int tabIndex) {
        super(left, top, Math.max(width, iconWidth + 6), Math.max(height, iconHeight + 6), text);
        super.setIcon(icon, iconU, iconV, iconWidth, iconHeight);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, ItemStack itemIcon, int tabIndex) {
        super(left, top, 20, 20, "");
        this.setItemIcon(itemIcon);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, int width, int height, ItemStack itemIcon, int tabIndex) {
        super(left, top, Math.max(width, 20), Math.max(height, 20), "");
        this.setItemIcon(itemIcon);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, int width, int height, int tabIndex) {
        super(left, top, Math.max(width, 20), Math.max(height, 20), "");
        this.tabIndex = tabIndex;
    }

    public ButtonTab(int left, int top, int width, int height, IIcon itemIcon, int tabIndex) {
        super(left, top, Math.max(width, 20), Math.max(height, 20), itemIcon);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(String text, int left, int top, ItemStack itemIcon, int tabIndex) {
        super(left, top, 20, 20, text);
        this.setItemIcon(itemIcon);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(String text, int left, int top, IIcon icon, int tabIndex) {
        super(left, top, 20, 20, icon);
        this.tabIndex = tabIndex;
    }

    public ButtonTab(String text, int left, int top, int width, int height, ItemStack itemIcon, int tabIndex) {
        super(left, top, Math.max(width, 20), Math.max(height, 20), text);
        this.setItemIcon(itemIcon);
        this.tabIndex = tabIndex;
    }

    public void setItemIcon(ItemStack itemIcon) {
        this.itemIcon = itemIcon;
        this.hasItemIcon = true;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    @Override
    public void render(NeonOS laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if(this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = isInside(mouseX, mouseY) && windowActive;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);

            /* Corners */
            RenderUtil.drawRectWithTexture(xPosition, yPosition, 96 + i * 5, 12, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition, 99 + i * 5, 12, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + height - 2, 99 + i * 5, 15, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition, yPosition + height - 2, 96 + i * 5, 15, 2, 2, 2, 2);

            /* Middles */
            RenderUtil.drawRectWithTexture(xPosition + 2, yPosition, 98 + i * 5, 12, width - 4, 2, 1, 2);
            RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + 2, 99 + i * 5, 14, 2, height - 4, 2, 1);
            RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + height - 2, 98 + i * 5, 15, width - 4, 2, 1, 2);
            RenderUtil.drawRectWithTexture(xPosition, yPosition + 2, 96 + i * 5, 14, 2, height - 4, 2, 1);

            /* Center */
            RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + 2, 98 + i * 5, 14, width - 4, height - 4, 1, 1);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            int j = 14737632;

            if(!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }

            boolean hasText = this.text != null && !this.text.isEmpty();

            if(this.hasResourceIcon) {
                mc.getTextureManager().bindTexture(resourceIcon);
                if(hasText) {
                    this.drawTexturedModalRect(xPosition + 3, yPosition + 3 + (height - iconHeight - 6) / 2, iconU, iconV, iconWidth, iconHeight);
                    this.drawString(fontrenderer, this.text, this.xPosition + 6 + this.iconWidth, this.yPosition + (this.height - 8) / 2, j);
                } else {
                    this.drawTexturedModalRect(xPosition + 3 + (this.width - iconWidth - 6) / 2, yPosition + 3 + (height - iconHeight - 6) / 2, iconU, iconV, iconWidth, iconHeight);
                }
            } else if(this.hasItemIcon) {
                if(hasText) {
                    RenderUtil.renderItem(xPosition + 2, yPosition + (this.height - 16)/2, this.itemIcon, true);
                    this.drawString(fontrenderer, this.text, this.xPosition + 20, this.yPosition + (this.height - 8) / 2, j);
                } else {
                    RenderUtil.renderItem(xPosition + (this.width - 16)/2, yPosition + (this.height - 16)/2, this.itemIcon, true);
                }
            } else if(this.hasIcon) {
                if(hasText) {
                    super.setIcon(icon);
                    this.drawString(fontrenderer, this.text, this.xPosition + 20, this.yPosition + (this.height - 8) / 2, j);
                } else {
                    super.setIcon(icon);
                }
            } else if(hasText) {
                this.drawCenteredString(fontrenderer, this.text, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
            }

        }
    }

}