package net.thegaminghuskymc.gadgetmod.api.app.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.IIcon;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import java.awt.*;

public class Label extends Component {

    protected String text;
    protected int width, height;
    protected boolean shadow = true;
    protected double scale = 1;
    protected int alignment = ALIGN_LEFT;

    protected int padding = 5;
    protected boolean explicitSize = false;

    protected ResourceLocation iconResource;
    protected int iconU, iconV;
    protected int iconWidth, iconHeight;
    protected int iconSourceWidth;
    protected int iconSourceHeight;

    protected int textColour = Color.WHITE.getRGB();

    /**
     * Default label constructor
     *
     * @param text the text to display
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Label(String text, int left, int top) {
        super(left, top);
        this.text = text;
        setTextColour(textColour);
    }

    public Label(String text, int left, int top, IIcon icon) {
        super(left, top);
        this.text = text;
        setTextColour(textColour);
        this.setIcon(icon);
    }

    public Label(int left, int top, IIcon icon) {
        super(left, top);
        this.setIcon(icon);
    }

    private static int getTextWidth(String text) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        boolean flag = fontRenderer.getUnicodeFlag();
        fontRenderer.setUnicodeFlag(false);
        int width = fontRenderer.getStringWidth(text);
        fontRenderer.setUnicodeFlag(flag);
        return width;
    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(xPosition, yPosition, 0);
                GlStateManager.scale(scale, scale, scale);
                if (alignment == ALIGN_RIGHT)
                    GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) * scale), 0, 0);
                if (alignment == ALIGN_CENTER)
                    GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) * scale) / (2 * scale), 0, 0);
                mc.fontRenderer.drawString(text, 0, 0, getTextColor(), shadow);
            }
            GlStateManager.popMatrix();

            int contentWidth = (iconResource != null ? iconWidth : 0) + getTextWidth(text);
            if (iconResource != null && text != null) contentWidth += 3;
            int contentX = (int) Math.ceil((width - contentWidth) / 2.0);

            if (iconResource != null) {
                int iconY = (height - iconHeight) / 2;
                mc.getTextureManager().bindTexture(iconResource);
                RenderUtil.drawRectWithTexture(x + contentX, y + iconY, iconU, iconV, iconWidth, iconHeight, iconWidth, iconHeight, iconSourceWidth, iconSourceHeight);
            }
        }
    }

    /**
     * Sets the text in the label
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the text colour for this component
     *
     * @param color the text colour
     */
    public void setTextColour(int color) {
        this.textColour = color;
    }

    public int getTextColor() {
        return textColour;
    }

    /**
     * Sets the whether shadow should show under the text
     *
     * @param shadow if should render shadow
     */
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    /**
     * Scales the text, essentially setting the font size. Minecraft
     * does not support proper font resizing. The default scale is 1
     *
     * @param scale the text scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Sets the alignment of the text. Use {@link Component#ALIGN_LEFT} or
     * {@link Component#ALIGN_RIGHT} to set alignment.
     *
     * @param alignment the alignment type
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setIcon(IIcon icon) {
        this.iconU = icon.getU();
        this.iconV = icon.getV();
        this.iconResource = icon.getIconAsset();
        this.iconWidth = icon.getIconSize();
        this.iconHeight = icon.getIconSize();
        this.iconSourceWidth = icon.getGridWidth() * icon.getIconSize();
        this.iconSourceHeight = icon.getGridHeight() * icon.getIconSize();
        updateSize();
    }

    public void removeIcon() {
        this.iconResource = null;
        updateSize();
    }

    private void updateSize() {
        if (explicitSize) return;
        int height = padding * 2;
        int width = padding * 2;

        if (iconResource != null) {
            height += iconHeight;
            width += iconWidth;
        }

        if (text != null) {
            width += getTextWidth(text);
            height = 16;
        }

        if (iconResource != null && text != null) {
            width += 3;
            height = iconHeight + padding * 2;
        }

        this.width = width;
        this.height = height;
    }

}
