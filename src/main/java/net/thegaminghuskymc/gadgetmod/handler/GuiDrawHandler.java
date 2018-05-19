package net.thegaminghuskymc.gadgetmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thegaminghuskymc.gadgetmod.DeviceTab;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;

import java.net.URI;
import java.net.URISyntaxException;

//@Mod.EventBusSubscriber
public class GuiDrawHandler {
    private static final ResourceLocation ICONS = new ResourceLocation("hgm:textures/gui/icons2.png");

    private GuiLinkImageButton buttonWebsite;
    private GuiLinkImageButton buttonTwitter;

    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onDrawGui(InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiContainerCreative) {
            this.guiCenterX = event.getGui().width / 2;
            this.guiCenterY = event.getGui().height / 2;

            event.getButtonList().add(buttonWebsite = new GuiLinkImageButton(10, guiCenterX - 120, guiCenterY - 66, ICONS, 48, 0, "https://thegaminghuskymc.com", TextFormatting.WHITE + ">" + TextFormatting.DARK_GRAY + " Browse Husky's " + TextFormatting.DARK_GREEN.toString() + TextFormatting.BOLD.toString() + "Website"));
            event.getButtonList().add(buttonTwitter = new GuiLinkImageButton(10, guiCenterX - 120, guiCenterY - 22, ICONS, 16, 0, "https://twitter.com/ninathekiller06", TextFormatting.WHITE + ">" + TextFormatting.DARK_GRAY + " Follow Husky on " + TextFormatting.BLUE.toString() + TextFormatting.BOLD.toString() + "Twitter"));
        }
    }

    @SubscribeEvent
    public void onDrawGui(DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiContainerCreative) {
            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();
            if (creative.getSelectedTabIndex() == HuskyGadgetMod.deviceBlocks.getTabIndex() || creative.getSelectedTabIndex() == HuskyGadgetMod.deviceDecoration.getTabIndex() || creative.getSelectedTabIndex() == HuskyGadgetMod.deviceItems.getTabIndex()) {
                buttonWebsite.visible = true;
                buttonTwitter.visible = true;
            } else {
                buttonWebsite.visible = false;
                buttonTwitter.visible = false;
            }
        }
    }

    @SubscribeEvent
    public void onButtonClick(ActionPerformedEvent.Post event) {
        if (event.getButton() instanceof GuiLinkImageButton) {
            GuiLinkImageButton button = (GuiLinkImageButton) event.getButton();
            try {
                openWebLink(new URI(button.link));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void openWebLink(URI url) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GuiLinkImageButton extends GuiButton {
        private final ResourceLocation image;
        private final int u;
        private final int v;
        private final String link;
        private final String toolTip;

        public GuiLinkImageButton(int buttonId, int x, int y, ResourceLocation image, int u, int v, String link, String toolTip) {
            super(buttonId, x, y, 20, 20, "");
            this.image = image;
            this.u = u;
            this.v = v;
            this.link = link;
            this.toolTip = toolTip;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                if (this.hovered && !mousePressed(mc, mouseX, mouseY)) {
                    ((DeviceTab) HuskyGadgetMod.deviceBlocks).setHoveringButton(false);
                    ((DeviceTab) HuskyGadgetMod.deviceItems).setHoveringButton(false);
                    ((DeviceTab) HuskyGadgetMod.deviceDecoration).setHoveringButton(false);
                }

                mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                this.mouseDragged(mc, mouseX, mouseY);
                int j = 14737632;

                if (packedFGColour != 0) {
                    j = packedFGColour;
                } else if (!this.enabled) {
                    j = 10526880;
                } else if (this.hovered) {
                    j = 16777120;
                }

                if (this.hovered) {
                    ((DeviceTab) HuskyGadgetMod.deviceBlocks).setTitle(toolTip);
                    ((DeviceTab) HuskyGadgetMod.deviceBlocks).setHoveringButton(true);

                    ((DeviceTab) HuskyGadgetMod.deviceItems).setTitle(toolTip);
                    ((DeviceTab) HuskyGadgetMod.deviceItems).setHoveringButton(true);

                    ((DeviceTab) HuskyGadgetMod.deviceDecoration).setTitle(toolTip);
                    ((DeviceTab) HuskyGadgetMod.deviceDecoration).setHoveringButton(true);
                }

                mc.getTextureManager().bindTexture(GuiDrawHandler.ICONS);
                this.drawTexturedModalRect(this.x + 2, this.y + 2, u, v, 16, 16);
            }
        }


    }
}
