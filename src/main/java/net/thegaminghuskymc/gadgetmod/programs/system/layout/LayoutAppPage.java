package net.thegaminghuskymc.gadgetmod.programs.system.layout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class LayoutAppPage extends Layout {

    private AppInfo info;

    public LayoutAppPage(AppInfo info) {
        super(250, 150);
        this.info = info;
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Color color = new Color(Laptop.getSystem().getSettings().getColourScheme().getHeaderColour());
            Gui.drawRect(x, y + 40, x + width, y + 41, color.brighter().getRGB());
            Gui.drawRect(x, y + 41, x + width, y + 60, color.getRGB());
            Gui.drawRect(x, y + 60, x + width, y + 61, color.darker().getRGB());
        });

        Image imageBanner = new Image(0, 0, 250, 40);
        imageBanner.setDrawFull(true);
        if (info.getBanner() != null) {
            imageBanner.setImage(new ResourceLocation(info.getBanner()));
        } else {
            imageBanner.setImage(new ResourceLocation(Reference.MOD_ID, "textures/app/banner/banner_default"));
        }
        this.addComponent(imageBanner);

        Label labelTitle = new Label(info.getName(), 38, 32);
        labelTitle.setScale(2);
        this.addComponent(labelTitle);

        Label labelVersion = new Label("v" + info.getVersion() + " - " + info.getAuthor(), 38, 50);
        this.addComponent(labelVersion);

        Text textDescription = new Text(info.getDescription(), 130, 70, 115);
        this.addComponent(textDescription);

        SlideShow slideShow = new SlideShow(5, 67, 120, 78);
        if (info.getScreenshots() != null) {
            for (String image : info.getScreenshots()) {
                if (image.startsWith("http://") || image.startsWith("https://")) {
                    slideShow.addImage(image);
                } else {
                    slideShow.addImage(new ResourceLocation(info.getId().getResourceDomain(), image));
                }
            }
        }
        this.addComponent(slideShow);

        if (info.getSupport() != null) {
            Button btnDonate = new Button(174, 44, Icons.COIN);
            btnDonate.setToolTip("Donate", "Opens a link to donate to author of the application");
            btnDonate.setSize(14, 14);
            this.addComponent(btnDonate);
        }

        Button btnInstall = new Button(190, 44, "Install", Icons.IMPORT);
        btnInstall.setSize(55, 14);
        this.addComponent(btnInstall);

        loadScreenshots();
    }

    @Override
    public void renderOverlay(Laptop laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(Laptop.ICON_TEXTURES);
        RenderUtil.drawRectWithTexture(xPosition + 5, yPosition + 26, info.getIconU(), info.getIconV(), 28, 28, 14, 14, 224, 224);
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
    }

    private void loadScreenshots() {
        String screenshots = "assets/" + info.getId().getResourceDomain() + "/textures/app/screenshots/" + info.getId().getResourcePath();
        URL url = LayoutAppPage.class.getResource(screenshots);
        try {
            if (url != null) {
                File file = new File(url.toURI());
                HuskyGadgetMod.getLogger().info(file.exists() + " is true");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}