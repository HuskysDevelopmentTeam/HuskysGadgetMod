package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.LayoutAppPage;

import java.awt.*;

public class ApplicationAppStore extends Application {

    public ApplicationAppStore() {
        this.setDefaultWidth(250);
        this.setDefaultHeight(150);
    }

    @Override
    public void init() {

        Layout layoutHome = new Layout(250, 159);
        layoutHome.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y + 50, x + width, y + 51, Color.DARK_GRAY.getRGB());
            Gui.drawRect(x, y + 51, x + width, y + 63, Color.DARK_GRAY.getRGB());
        });

        Image imageBanner = new Image(0, 0, 250, 50, "https://i.imgur.com/VAGCpKY.jpg");
        layoutHome.addComponent(imageBanner);

        Label labelBanner = new Label("App Market", 10, 42);
        labelBanner.setScale(2);
        layoutHome.addComponent(labelBanner);

        Label labelCategories = new Label("Categories", 5, 70);
        layoutHome.addComponent(labelCategories);

        ItemList itemListCategories = new ItemList<>(5, 82, 100, 5, true);
        itemListCategories.addItem("Games");
        itemListCategories.addItem("Tools");
        itemListCategories.addItem("Education");
        itemListCategories.addItem("Entertainment");
        itemListCategories.addItem("Sports");
        itemListCategories.addItem("VR");
        itemListCategories.addItem("Finance");
        itemListCategories.addItem("Multiplayer");
        itemListCategories.addItem("Shopping");
        itemListCategories.addItem("Social");
        layoutHome.addComponent(itemListCategories);

        Label appsLabel = new Label("Application List", 120, 70);
        layoutHome.addComponent(appsLabel);

        ItemList apps = new ItemList<AppInfo>(120, 80, 100, 3);
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "bank")));
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "pixel_book")));
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "cackler")));
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "pixel_plus")));
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "pixel_tube")));
        apps.addItem(new AppInfo(new ResourceLocation(Reference.MOD_ID, "pixel_browser")));
        apps.setListItemRenderer(new ListItemRenderer<AppInfo>(20) {
            @Override
            public void render(AppInfo e, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                if (selected)
                    gui.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
                else
                    gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
                RenderUtil.drawApplicationIcon(e, x + 3, y + 3);
                gui.drawString(mc.fontRenderer, e.getName(), x + 20, y + 6, Color.WHITE.getRGB());
            }
        });
        apps.setItemClickListener((info, index, mouseButton) ->
        {
            if (apps.getSelectedIndex() == 0) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:bank"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            } else if (apps.getSelectedIndex() == 1) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:pixel_book"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            } else if (apps.getSelectedIndex() == 2) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:cackler"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            } else if (apps.getSelectedIndex() == 3) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:pixel_plus"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            } else if (apps.getSelectedIndex() == 4) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:pixel_tube"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            } else if (apps.getSelectedIndex() == 5) {
                if (mouseButton == 0) {
                    Layout layout = new LayoutAppPage(ApplicationManager.getApplication("hgm:pixel_browser"));
                    this.setCurrentLayout(layout);
                    Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
                    btnPrevious.setClickListener((mouseX, mouseY, mouseButton1) -> setCurrentLayout(layoutHome));
                    layout.addComponent(btnPrevious);
                }
            }
        });
        layoutHome.addComponent(apps);

        this.setCurrentLayout(layoutHome);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }
}
