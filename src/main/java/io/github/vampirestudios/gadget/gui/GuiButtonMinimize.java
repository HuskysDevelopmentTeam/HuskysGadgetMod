package io.github.vampirestudios.gadget.gui;

import io.github.vampirestudios.gadget.core.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonMinimize extends GuiButtonWindow
{
	private boolean minimized;

	public GuiButtonMinimize(int buttonId, int x, int y)
	{
		super(buttonId, x, y);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible) {
			mc.getTextureManager().bindTexture(Window.WINDOW_GUI);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);

			int state = this.getHoverState(this.hovered);
			this.drawTexturedModalRect(this.x, this.y, state * this.width + 26, (2 - this.id + (this.minimized ? 1 : 0)) * this.height, this.width, this.height);
		}
	}

	public void setMinimized(boolean minimized)
	{
		this.minimized = minimized;
	}
}