package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
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
import net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings.AppStoreAppCategories;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.LayoutAppPage;
import net.thegaminghuskymc.gadgetmod.util.RenderHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplicationAppStore extends Application {

    protected ArrayList<String> repos = new ArrayList<>(Arrays.asList(new String[]{""}));

    public ApplicationAppStore() {
        this.setDefaultWidth(250);
        this.setDefaultHeight(150);
    }

    @Override
    public void init() {

        Layout layoutHome = new Layout(250, 159);
        layoutHome.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y + 50, x + width, y + 51, Color.LIGHT_GRAY.getRGB());
            Gui.drawRect(x, y + 51, x + width, y + 62, Color.GRAY.getRGB());
            Gui.drawRect(x, y + 62, x + width, y + 63, Color.DARK_GRAY.getRGB());
        });

        Image imageBanner = new Image(0, 0, 250, 50, "https://i.imgur.com/VAGCpKY.jpg");
        layoutHome.addComponent(imageBanner);

        Label labelBanner = new Label(RenderHelper.unlocaliseName("appStore.title"), 10, 42);
        labelBanner.setScale(2);
        layoutHome.addComponent(labelBanner);

        Label labelCategories = new Label(RenderHelper.unlocaliseName("appStore.categories"), 5, 70);
        layoutHome.addComponent(labelCategories);

        ItemList itemListCategories = new ItemList<>(5, 82, 100, 5, true);
        for (int i = 0; i < AppStoreAppCategories.getSize(); i++) {
            itemListCategories.addItem(AppStoreAppCategories.getUnlocalizedName(i));
        }
        layoutHome.addComponent(itemListCategories);

        Label appsLabel = new Label("Application List", 120, 70);
        layoutHome.addComponent(appsLabel);

        ItemList apps = new ItemList<AppInfo>(120, 82, 120, 3);
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
        if (tagCompound.hasKey("repos")) {
            repos = new ArrayList<>();
            NBTTagList list = (NBTTagList) tagCompound.getTag("repos");
            for (int i = 0; i < list.tagCount(); i++) {
                repos.add(list.getStringTagAt(i));
            }
        }
    }

    @Override
    public void save(NBTTagCompound tagCompound) {
        NBTTagList list = new NBTTagList();
        for (String r : repos) {
            list.appendTag(new NBTTagString(r));
        }
        tagCompound.setTag("repos", list);
    }
}
