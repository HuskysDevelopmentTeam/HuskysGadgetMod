package net.thegaminghuskymc.gadgetmod.programs.system.layout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Banner;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.component.Text;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import java.awt.*;

public class LayoutAppPage extends Layout {

    private AppInfo info;

    public LayoutAppPage(AppInfo info) {
        super(250, 150);
        this.info = info;
    }

    @Override
    public void init() {

        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Color color = new Color(Laptop.getSystem().getSettings().getColourScheme().getHeaderColour());
            Gui.drawRect(x, y + 40, x + width, y + 41, color.brighter().getRGB());
            Gui.drawRect(x, y + 41, x + width, y + 60, color.getRGB());
            Gui.drawRect(x, y + 60, x + width, y + 61, color.darker().getRGB());
        });

        Banner imageBanner = new Banner(0, 0, info.getBannerU(), info.getBannerV(), new ResourceLocation(Reference.MOD_ID,"textures/app/banner/banner_default"));
        this.addComponent(imageBanner);

        Label labelTitle = new Label(info.getName(), 38, 32);
        labelTitle.setScale(2);
        this.addComponent(labelTitle);

        Label labelVersion = new Label("v0.2.0", 38, 50);
        this.addComponent(labelVersion);

        Label labelAuthor = new Label(info.getCreator(), 70, 50);
        this.addComponent(labelAuthor);

        Text textDescription = new Text(info.getDescription(), 150, 70, 95);
        this.addComponent(textDescription);

        /*if(!ApplicationManager.getAvailableApps().contains(info)) {
            Button buttonInstall = new Button(185, 43, 60, 16, "Install", Icons.IMPORT);
            buttonInstall.setToolTip("Install", "This will install the app");
            buttonInstall.setClickListener((mouseX, mouseY, mouseButton) -> {
                ApplicationManager.getAvailableApps().add(info);
                HuskyGadgetMod.getLogger().info("App is now installed");
            });
            this.addComponent(buttonInstall);
        }*/

        if (ApplicationManager.getAvailableApps().contains(info)) {
            Button buttonUninstall = new Button(185, 43, 60, 16, "Uninstall", Icons.EXPORT);
            buttonUninstall.setToolTip("Uninstall", "This will uninstall the app");
            buttonUninstall.setClickListener((mouseX, mouseY, mouseButton) -> {
                ApplicationManager.removeApplication(info.getId(), Application.class);
                HuskyGadgetMod.getLogger().info("App is now uninstalled");
                ApplicationManager.getApplication(info.getName());
            });
            this.addComponent(buttonUninstall);
        }
    }

    @Override
    public void renderOverlay(Laptop laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive) {
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
        /*GlStateManager.color(1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(Laptop.BANNER_TEXTURES);
        RenderUtil.drawRectWithTexture(xPosition, yPosition, info.getBannerU(), info.getBannerV(), 250, 40, 250, 40, 500, 160);*/

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(Laptop.ICON_TEXTURES);
        RenderUtil.drawRectWithTexture(xPosition + 5, yPosition + 26, info.getIconU(), info.getIconV(), 28, 28, 14, 14, 224, 224);
    }
}