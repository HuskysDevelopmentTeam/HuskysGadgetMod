package net.thegaminghuskymc.gadgetmod.programs.gitweb.component.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.util.GuiHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class ContainerBox extends Component
{
    protected static final ResourceLocation CONTAINER_BOXES_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container_boxes.png");

    public static final int WIDTH = 128;

    protected List<Slot> slots = new ArrayList<>();
    protected int boxU, boxV;
    protected int height;
    protected ItemStack icon;
    protected String title;

    public ContainerBox(int left, int top, int boxU, int boxV, int height, ItemStack icon, String title)
    {
        super(left, top);
        this.boxU = boxU;
        this.boxV = boxV;
        this.height = height;
        this.icon = icon;
        this.title = title;
    }

    @Override
    protected void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks)
    {
        mc.getTextureManager().bindTexture(CONTAINER_BOXES_TEXTURE);
        RenderUtil.drawRectWithTexture(x, y + 12, boxU, boxV, WIDTH, height, WIDTH, height, 256, 256);

        int contentOffset = (WIDTH - (BaseDevice.fontRenderer.getStringWidth(title) + 8 + 4)) / 2;
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x + contentOffset, y, 0);
            GlStateManager.scale(0.5, 0.5, 0.5);
            RenderUtil.renderItem(0, 0, icon, false);
        }
        GlStateManager.popMatrix();

        RenderUtil.drawStringClipped(title, x + contentOffset + 8 + 4, y, 110, Color.WHITE.getRGB(), true);

        slots.forEach(slot -> slot.render(x, y + 12));
    }

    @Override
    protected void renderOverlay(BaseDevice laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive)
    {
        slots.forEach(slot -> slot.renderOverlay(laptop, xPosition, yPosition + 12, mouseX, mouseY));
    }

    protected class Slot
    {
        private int slotX;
        private int slotY;
        private ItemStack stack;

        public Slot(int slotX, int slotY, ItemStack stack)
        {
            this.slotX = slotX;
            this.slotY = slotY;
            this.stack = stack;
        }

        public void render(int x, int y)
        {
            RenderUtil.renderItem(x + slotX, y + slotY, stack, true);
        }

        public void renderOverlay(BaseDevice laptop, int x, int y, int mouseX, int mouseY)
        {
            if(GuiHelper.isMouseWithin(mouseX, mouseY, x + slotX, y + slotY, 16, 16))
            {
                if(!stack.isEmpty())
                {
                    net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
                    laptop.drawHoveringText(laptop.getItemToolTip(stack), mouseX, mouseY);
                    net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
                }
            }

            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableDepth();
        }

        public ItemStack getStack()
        {
            return stack;
        }
    }
}
