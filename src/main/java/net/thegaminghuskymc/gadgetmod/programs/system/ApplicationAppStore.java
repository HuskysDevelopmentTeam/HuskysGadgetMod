package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
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
import net.thegaminghuskymc.gadgetmod.programs.system.layout.HomePageLayout;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.LayoutAppPage;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.LayoutSearchApps;
import net.thegaminghuskymc.gadgetmod.util.RenderHelper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ApplicationAppStore extends Application {

    public static final int LAYOUT_WIDTH = 270;
    public static final int LAYOUT_HEIGHT = 150;

    private HomePageLayout layoutHome;

    private long lastClick = 0;

    @Override
    public void init() {

        layoutHome = new HomePageLayout(LAYOUT_WIDTH, LAYOUT_HEIGHT, this, null);

        Button btnSearch = new Button(214, 44, Icons.SEARCH);
        btnSearch.setToolTip("Search", "Find a specific application");
        btnSearch.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                this.setCurrentLayout(new LayoutSearchApps(this, getCurrentLayout()));
            }
        });
        layoutHome.addComponent(btnSearch);

        /*Label labelBanner = new Label(RenderHelper.unlocaliseName(ApplicationManager.getApplication("hgm:app_store").getName()), 10, 32);
        labelBanner.setScale(2);
        layoutHome.addComponent(labelBanner);*/

        Image imageBanner = new Image(0, 0, 270, 44, "https://i.imgur.com/VAGCpKY.jpg");
        layoutHome.addComponent(imageBanner);

        Label labelCategories = new Label(RenderHelper.unlocaliseName("appStore.categories"), 5, 70);
        layoutHome.addComponent(labelCategories);

        ItemList itemListCategories = new ItemList(5, 82, 100, 4, true);
        itemListCategories.setListItemRenderer(new ListItemRenderer(14) {
            @Override
            public void render(Object o, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                if (selected)
                    Gui.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
                else
                    Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, o.toString(), x + 3, y + 2, Color.WHITE.getRGB());
            }
        });
        for (int i = 0; i < AppStoreAppCategories.getSize(); i++) {
            itemListCategories.addItem(AppStoreAppCategories.getUnlocalizedName(i));
        }
        layoutHome.addComponent(itemListCategories);

        Label appsLabel = new Label("Application List", 120, 70);
        layoutHome.addComponent(appsLabel);

        ItemList<AppInfo> apps = new ItemList<>(120, 82, 140, 3);
        apps.setItems(new ArrayList<>(ApplicationManager.getAllApplications()));
        apps.sortBy(Comparator.comparing(AppInfo::getName));
        apps.setListItemRenderer(new ListItemRenderer<AppInfo>(20) {
            @Override
            public void render(AppInfo e, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                if (selected)
                    Gui.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
                else
                    Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderUtil.drawApplicationIcon(e, x + 3, y + 3);
                gui.drawString(mc.fontRenderer, e.getName(), x + 20, y + 6, Color.WHITE.getRGB());
            }
        });
        apps.setItemClickListener((info, index, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                if(System.currentTimeMillis() - this.lastClick <= 200)
                {
                    openApplication(info);
                }
                else
                {
                    this.lastClick = System.currentTimeMillis();
                }
            }
        });
        layoutHome.addComponent(apps);

        this.setCurrentLayout(layoutHome);
    }

    private void openApplication(AppInfo info) {
        Layout layout = new LayoutAppPage(info);
        setCurrentLayout(layout);
        Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
        btnPrevious.setClickListener((mouseX1, mouseY1, mouseButton1) -> setCurrentLayout(layoutHome));
        layout.addComponent(btnPrevious);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }
}
