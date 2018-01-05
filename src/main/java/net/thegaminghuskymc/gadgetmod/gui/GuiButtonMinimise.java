package net.thegaminghuskymc.gadgetmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.thegaminghuskymc.gadgetmod.core.Window;

public class GuiButtonMinimise extends GuiButton {
    public GuiButtonMinimise(int buttonId, int x, int y) {
        super(buttonId, x, y, 11, 11, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(Window.WINDOW_GUI);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);

            int state = this.getHoverState(this.hovered);
            if(state != getHoverState(true)) {
                this.drawTexturedModalRect(this.x, this.y, state * this.width + 35 + 3, 0, this.width, this.height);
            } else if (state != getHoverState(false)){
                this.drawTexturedModalRect(this.x, this.y, state * this.width + 36, 0, this.width, this.height);
            }
        }
    }
}
