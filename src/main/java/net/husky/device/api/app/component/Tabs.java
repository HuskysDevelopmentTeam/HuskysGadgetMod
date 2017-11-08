package net.husky.device.api.app.component;

import net.husky.device.api.app.Application;
import net.husky.device.api.app.Component;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.listener.ClickListener;
import net.husky.device.api.utils.RenderUtil;
import net.husky.device.core.Laptop;
import net.husky.device.core.NeonOS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class Tabs extends Component {

    protected String text, toolTip, toolTipTitle;
    protected boolean hovered, active;
    protected int width, height;

    protected int iconU, iconV;
    protected int iconWidth, iconHeight;

    protected ClickListener clickListener = null;

    private final int tabIndex;
    private final String tabLabel = "";
    private boolean hasScrollbar = true;
    private boolean drawTitle = true;
    private ItemStack iconItemStack;

    private Layout newLayout;

    private Application application;

    public Tabs(Application application, int left, int top, int width, int height, int index, String label) {
        super(left, top);

        this.width = width;
        this.height = height;
        this.tabIndex = index;
        this.toolTipTitle = label;
        this.application = application;
    }

    public Tabs(Application application, int left, int width, int height, int top, int index, ItemStack iconItemStack) {
        this(application, left, top, width, height, index, iconItemStack.getDisplayName());
        this.width = width;
        this.height = height;
        this.toolTipTitle = iconItemStack.getDisplayName();
        this.application = application;
    }

    @Override
    public void render(NeonOS laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = isInside(mouseX, mouseY) && windowActive;
            int i = this.getHoverState(this.hovered);
            int i2 = this.getHoverState(this.active);
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

            if(iconItemStack == ItemStack.EMPTY) {

            }
            else {
                RenderUtil.renderItem(xPosition + 4, yPosition + 6, iconItemStack, true);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            int j = 14737632;

            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }


            if(active == true) {
                mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);

                /* Corners */
                RenderUtil.drawRectWithTexture(xPosition, yPosition, 96 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition, 99 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + height - 2, 99 + i * 5, 15, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition, yPosition + height - 2, 96 + i * 5, 15, 2, 2, 2, 2);

                /* Middles */
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition, 98 + i * 30, 12, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + 2, 99 + i * 30, 14, 2, height - 4, 2, 1);
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + height - 2, 98 + i * 30, 15, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(xPosition, yPosition + 2, 96 + i * 30, 14, 2, height - 4, 2, 1);

                /* Center */
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + 2, 98 + i * 5, 14, width - 4, height - 4, 1, 1);

                RenderUtil.renderItem(xPosition + 4, yPosition + 6, iconItemStack, true);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            if(hovered == true) {
                mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);
                GlStateManager.color(1.0F, 3.0F, 1.0F, 1.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);

                /* Corners */
                RenderUtil.drawRectWithTexture(xPosition, yPosition, 96 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition, 99 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + height - 2, 99 + i * 5, 15, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(xPosition, yPosition + height - 2, 96 + i * 5, 15, 2, 2, 2, 2);

                /* Middles */
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition, 98 + i * 15, 12, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(xPosition + width - 2, yPosition + 2, 99 + i * 15, 14, 2, height - 4, 2, 1);
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + height - 2, 98 + i * 15, 15, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(xPosition, yPosition + 2, 96 + i * 15, 14, 2, height - 4, 2, 1);

                /* Center */
                RenderUtil.drawRectWithTexture(xPosition + 2, yPosition + 2, 98 + i * 5, 14, width - 4, height - 4, 1, 1);

                RenderUtil.renderItem(xPosition + 4, yPosition + 6, iconItemStack, true);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

        }
    }

    public Layout getLayout() {
        return newLayout;
    }

    public Layout setLayout(Layout layout) {
        this.newLayout = layout;
        newLayout.addComponent(this);
        return newLayout;
    }

    public boolean setActive(boolean active) {
        this.active = active;
        return active;
    }

    public boolean isActive() {
        return active;
    }

    public Application getApplication() {
        return application;
    }

    public Application setApplication(Application application) {
        this.application = application;
        return application;
    }

    @Override
    public void renderOverlay(NeonOS laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive)
    {
        if(this.hovered && this.toolTip != null)
        {
            laptop.drawHoveringText(Arrays.asList(TextFormatting.GOLD + this.toolTipTitle, this.toolTip), mouseX, mouseY);
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton)
    {
        if(!this.visible || !this.enabled)
            return;

        if(this.hovered)
        {
            if(clickListener != null)
            {
                clickListener.onClick(this, mouseButton);
            }
            playClickSound(Minecraft.getMinecraft().getSoundHandler());
        }
    }

    /**
     * Sets the click listener. Use this to handle custom actions
     * when you press the tab.
     *
     * @param clickListener the click listener
     */
    public final void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    protected void playClickSound(SoundHandler handler)
    {
        handler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    protected boolean isInside(int mouseX, int mouseY)
    {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }


    /**
     * Displays a message when hovering the tab.
     *
     * @param toolTipTitle title of the tool tip
     * @param toolTip description of the tool tip
     */
    public void setToolTip(String toolTipTitle, String toolTip)
    {
        this.toolTipTitle = toolTipTitle;
        this.toolTip = toolTip;
    }

    @SideOnly(Side.CLIENT)
    public int getTabIndex()
    {
        return this.tabIndex;
    }


    @SideOnly(Side.CLIENT)
    public String getTabLabel()
    {
        return this.tabLabel;
    }

    /**
     * Gets the translated Label.
     */
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return "tab." + this.getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        if (this.iconItemStack.isEmpty())
        {
            this.iconItemStack = this.getTabIconItem();
        }

        return this.iconItemStack;
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return iconItemStack;
    }

    public ItemStack setIconItemStack(ItemStack iconItemStack) {
        this.iconItemStack = iconItemStack;
        return iconItemStack;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawInForegroundOfTab()
    {
        return this.drawTitle;
    }

    public Tabs setNoTitle()
    {
        this.drawTitle = false;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldHidePlayerInventory()
    {
        return this.hasScrollbar;
    }

    public Tabs setNoScrollbar()
    {
        this.hasScrollbar = false;
        return this;
    }

    /**
     * returns index % 6
     */
    @SideOnly(Side.CLIENT)
    public int getTabColumn()
    {
        if (tabIndex > 11)
        {
            return ((tabIndex - 12) % 10) % 5;
        }
        return this.tabIndex % 6;
    }

    /**
     * returns tabIndex < 6
     */
    @SideOnly(Side.CLIENT)
    public boolean isTabInFirstRow()
    {
        if (tabIndex > 11)
        {
            return ((tabIndex - 12) % 10) < 5;
        }
        return this.tabIndex < 6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isAlignedRight()
    {
        return this.getTabColumn() == 5;
    }


    public int getTabPage()
    {
        if (tabIndex > 11)
        {
            return ((tabIndex - 12) / 10) + 1;
        }
        return 0;
    }


    /**
     * Gets the width of the search bar of the creative tab, use this if your
     * creative tab name overflows together with a custom texture.
     *
     * @return The width of the search bar, 89 by default
     */
    public int getSearchbarWidth()
    {
        return 89;
    }

}
