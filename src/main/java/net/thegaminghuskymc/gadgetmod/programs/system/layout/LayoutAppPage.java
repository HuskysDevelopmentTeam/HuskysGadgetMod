package net.thegaminghuskymc.gadgetmod.programs.system.layout;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.component.SlideShow;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.object.AppEntry;
import net.thegaminghuskymc.gadgetmod.programs.system.object.LocalEntry;
import net.thegaminghuskymc.gadgetmod.programs.system.object.RemoteEntry;
import net.thegaminghuskymc.gadgetmod.util.GuiHelper;

import java.awt.*;
import java.net.URI;
import java.net.URL;

public class LayoutAppPage extends Layout {

    private BaseDevice laptop;
    private AppEntry entry;
    private ApplicationAppStore store;

    private Image imageBanner;
    private Image imageIcon;
    private Label labelTitle;
    private Label labelVersion;

    private boolean installed;

    public LayoutAppPage(BaseDevice laptop, AppEntry entry, ApplicationAppStore store)
    {
        super(250, 150);
        this.laptop = laptop;
        this.entry = entry;
        this.store = store;
    }

    @Override
    public void init()
    {
        if(entry instanceof LocalEntry)
        {
        	installed = BaseDevice.getSystem().getInstalledApplications().contains(((LocalEntry) entry).getInfo());
        }

        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 40, x + width, y + 41, color.brighter().getRGB());
            Gui.drawRect(x, y + 41, x + width, y + 60, color.getRGB());
            Gui.drawRect(x, y + 60, x + width, y + 61, color.darker().getRGB());
        });

        ResourceLocation resource = new ResourceLocation(entry.getId());

        imageBanner = new Image(0, 0, 250, 40);
        imageBanner.setDrawFull(true);
        if(entry instanceof LocalEntry)
        {
            imageBanner.setImage(new ResourceLocation(resource.getResourceDomain(), "textures/app/banner/banner_" + resource.getResourcePath() + ".png"));
        }
        else if(entry instanceof RemoteEntry)
        {
            imageBanner.setImage(ApplicationAppStore.CERTIFIED_APPS_URL + "/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath() + "/banner.png");
        }
        this.addComponent(imageBanner);

        if(entry instanceof LocalEntry)
        {
            LocalEntry localEntry = (LocalEntry) entry;
            AppInfo info = localEntry.getInfo();
            imageIcon = new Image(5, 26, 28, 28, info.getIconU(), info.getIconV(), 14, 14, 224, 224, BaseDevice.ICON_TEXTURES);
        }
        else if(entry instanceof RemoteEntry)
        {
            imageIcon = new Image(5, 26, 28, 28, ApplicationAppStore.CERTIFIED_APPS_URL + "/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath() + "/icon.png");
        }
        this.addComponent(imageIcon);

        if(store.certifiedApps.contains(entry))
        {
            int width = BaseDevice.fontRenderer.getStringWidth(entry.getName()) * 2;
            Image certifiedIcon = new Image(38 + width + 3, 29, 20, 20, Icons.VERIFIED);
            this.addComponent(certifiedIcon);
        }
        labelTitle = new Label(entry.getName(), 38, 32);
        labelTitle.setScale(2);
        this.addComponent(labelTitle);

        String version = entry instanceof LocalEntry ? "v" + entry.getVersion() + " - " + entry.getAuthor() : entry.getAuthor();
        labelVersion = new Label(version, 38, 50);
        this.addComponent(labelVersion);

//        String description = GitWebFrame.parseFormatting(entry.getDescription());
//        ScrollableLayout descriptionLayout = ScrollableLayout.create(130, 67, 115, 78, description);
//        this.addComponent(descriptionLayout);

        SlideShow slideShow = new SlideShow(5, 67, 120, 78);
        if(entry instanceof LocalEntry)
        {
            if(entry.getScreenshots() != null)
            {
                for(String image : entry.getScreenshots())
                {
                    if(image.startsWith("http://") || image.startsWith("https://"))
                    {
                        slideShow.addImage(image);
                    }
                    else
                    {
                        slideShow.addImage(new ResourceLocation(image));
                    }
                }
            }
        }
        else if(entry instanceof RemoteEntry) {
            RemoteEntry remoteEntry = (RemoteEntry) entry;
            String screenshotUrl = ApplicationAppStore.CERTIFIED_APPS_URL + "/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath() + "/screenshots/screenshot_%d.png";
            for(int i = 0; i < remoteEntry.app_screenshots; i++) {
                slideShow.addImage(String.format(screenshotUrl, i));
            }
        }
        this.addComponent(slideShow);

        if(entry instanceof LocalEntry) {
            AppInfo info = ((LocalEntry) entry).getInfo();
            Button btnInstall = new Button(174, 44, installed ? "Delete" : "Install", installed ? Icons.CROSS : Icons.PLUS);
            btnInstall.setSize(55, 14);
            btnInstall.setClickListener((mouseX, mouseY, mouseButton) -> {
                if(mouseButton == 0) {
                    if(installed) {
                        laptop.removeApplication(info, (o, success) -> {
                            btnInstall.setText("Install");
                            btnInstall.setIcon(Icons.PLUS);
                            installed = false;
                        });
                    }
                    else {
                        laptop.installApplication(info, (o, success) -> {
                            btnInstall.setText("Delete");
                            btnInstall.setIcon(Icons.CROSS);
                            installed = true;
                        });
                    }
                }
            });
            this.addComponent(btnInstall);

            if(info.getSupport() != null) {
                Button btnDonate = new Button(234, 44, Icons.COIN);
                btnDonate.setToolTip("Donate", "Opens a link to donate to author of the application");
                btnDonate.setSize(14, 14);
                this.addComponent(btnDonate);
            }
        }
        else if(entry instanceof RemoteEntry) {
            Button btnDownload = new Button(20, 2, "Download", Icons.IMPORT);
            btnDownload.setSize(66, 16);
            btnDownload.setClickListener((mouseX, mouseY, mouseButton) -> this.openWebLink("https://minecraft.curseforge.com/projects/" + ((RemoteEntry) entry).project_id));
            this.addComponent(btnDownload);
        }

        if(entry instanceof LocalEntry && installed) {
            AppInfo info = ((LocalEntry) entry).getInfo();
            Button btnStart = new Button(80, 2, Icons.PLAY);
            btnStart.setToolTip("Start", "Starts the application");
            if(installed) {
                btnStart.setVisible(true);
                btnStart.setEnabled(true);
            } else {
                btnStart.setVisible(false);
                btnStart.setEnabled(false);
            }
            btnStart.setClickListener((mouseX, mouseY, mouseButton) -> {
                laptop.openApplication(info);
                laptop.closeContext();
            });
//            this.addComponent(btnStart);
        }

    }

    @Override
    public void renderOverlay(BaseDevice laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive)
    {
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
        int width = BaseDevice.fontRenderer.getStringWidth(entry.getName()) * 2;
        if(GuiHelper.isMouseWithin(mouseX, mouseY, xPosition + 38 + width + 3, yPosition + 29, 20, 20))
        {
            if(store.certifiedApps.contains(entry)) {
                laptop.drawHoveringText(Lists.newArrayList(TextFormatting.GREEN + "Certified App"), mouseX, mouseY);
            }
        }
    }

    private void openWebLink(String url)
    {
        try
        {
            URI uri = new URL(url).toURI();
            Class<?> class_ = Class.forName("java.awt.Desktop");
            Object object = class_.getMethod("getDesktop").invoke(null);
            class_.getMethod("browse", URI.class).invoke(object, uri);
        }
        catch (Throwable throwable1)
        {
            Throwable throwable = throwable1.getCause();
            HuskyGadgetMod.getLogger().error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
        }
    }
}