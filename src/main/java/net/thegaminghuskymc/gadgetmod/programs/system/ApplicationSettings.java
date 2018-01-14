package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.io.File;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.LayoutAppPage;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;
import net.thegaminghuskymc.gadgetmod.proxy.ClientProxy;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ApplicationSettings extends SystemApplication {

    private static final Color ITEM_BACKGROUND = Color.decode("0x9E9E9E");
    private static final Color ITEM_SELECTED = Color.decode("0x757575");
    private Button buttonPrevious;
    private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;
    private Layout layoutInformationApps;
    private Button buttonColourSchemeApply;
    private Layout layoutInformation;
    private long lastClick = 0;
    private Stack<Layout> predecessor = new Stack<>();

    public ApplicationSettings() {
        this.setDefaultWidth(330);
        this.setDefaultHeight(260);
    }

    @Override
    public void init() {
        buttonPrevious = new Button(2, 2, Icons.ARROW_LEFT);
        buttonPrevious.setVisible(false);
        buttonPrevious.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                if (predecessor.size() > 0) {
                    setCurrentLayout(predecessor.pop());
                }
                if (predecessor.isEmpty()) {
                    buttonPrevious.setVisible(false);
                }
            }
        });

        Layout layoutMain = new Menu("Home");
        layoutMain.addComponent(buttonPrevious);

        layoutColourScheme = new Menu("Colour Scheme");
        layoutColourScheme.addComponent(buttonPrevious);

        layoutPersonalise = new Menu("Personalise");
        layoutPersonalise.addComponent(buttonPrevious);

        layoutWallpapers = new Menu("Wallpapers");
        layoutWallpapers.addComponent(buttonPrevious);
        layoutWallpapers.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            int wallpaperX = 7;
            int wallpaperY = 28;
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 150, y + wallpaperY - 1 + 90, Objects.requireNonNull(getLaptop()).getSettings().getColourScheme().getHeaderColour());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = getLaptop().getWallapapers();
            mc.getTextureManager().bindTexture(wallpapers.get(getLaptop().getCurrentWallpaper()));
            RenderUtil.drawRectWithTexture(x + wallpaperX, y + wallpaperY, 0, 0, 148, 88, 252, 256);
            mc.fontRenderer.drawString("Wallpaper", x + wallpaperX + 3, y + wallpaperY + 3, getLaptop().getSettings().getColourScheme().getTextColour(), true);
        });

        layoutInformation = new Menu("Information");
        layoutInformation.addComponent(buttonPrevious);

        Layout layoutInformationComputer = new Menu("Computer Information");
        layoutInformationComputer.addComponent(buttonPrevious);

        layoutInformationApps = new Menu("App Information");
        layoutInformationApps.addComponent(buttonPrevious);

        Layout layoutThemes = new Menu("Themes");
        layoutThemes.addComponent(buttonPrevious);

        Button btnThemes = new Button(5, 33, "Themes", Icons.PICTURE);
        btnThemes.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutThemes);
            }
        });
        layoutMain.addComponent(btnThemes);

        Button buttonInformationApps = new Button(5, 25, "App Information", Icons.CONTACTS);
        buttonInformationApps.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutInformationApps);
            }
        });
        layoutInformation.addComponent(buttonInformationApps);

        Button buttonInformationComputer = new Button(5, 46, "Computer Information", Icons.COMPUTER);
        buttonInformationComputer.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutInformationComputer);
            }
        });
        layoutInformation.addComponent(buttonInformationComputer);

        Layout layoutWifi = new Menu("WiFi");
        layoutWifi.addComponent(buttonPrevious);

        Button personalise = new Button(5, 66, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        Button information = new Button(5, 87, "Information", Icons.HELP);
        information.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutInformation);
            }
        });
        layoutMain.addComponent(information);

        Button wallpapers = new Button(20, 66, "Wallpapers", Icons.PICTURE);
        wallpapers.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutWallpapers);
            }
        });
        layoutPersonalise.addComponent(wallpapers);

        Button buttonColourScheme = new Button(40, 86, "Colour Schemes", Icons.TRASH);
        buttonColourScheme.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutColourScheme);
            }
        });
        layoutPersonalise.addComponent(buttonColourScheme);

        Button buttonWiFi = new Button(5, 108, "Wifi", Icons.WIFI_HIGH);
        buttonWiFi.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                showMenu(layoutWifi);
            }
        });
        layoutMain.addComponent(buttonWiFi);

        ItemList<BlockPos> itemListRouters = new ItemList<>(5, 25, 90, 4);
        itemListRouters.setItems(TrayItemWifi.getRouters());
        itemListRouters.setListItemRenderer(new ListItemRenderer<BlockPos>(16) {
            @Override
            public void render(BlockPos blockPos, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, "Router", x + 16, y + 4, Color.WHITE.getRGB());

                BlockPos laptopPos = Laptop.getPos();
                double distance = Math.sqrt(blockPos.distanceSqToCenter(Objects.requireNonNull(laptopPos).getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
                if (distance > 20) {
                    Icons.WIFI_LOW.draw(mc, x + 3, y + 3);
                } else if (distance > 10) {
                    Icons.WIFI_MED.draw(mc, x + 3, y + 3);
                } else {
                    Icons.WIFI_HIGH.draw(mc, x + 3, y + 3);
                }
            }
        });
        itemListRouters.sortBy((o1, o2) -> {
            BlockPos laptopPos = Laptop.getPos();
            double distance1 = Math.sqrt(o1.distanceSqToCenter(Objects.requireNonNull(laptopPos).getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            double distance2 = Math.sqrt(o2.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            return Double.compare(distance1, distance2);
        });
        layoutWifi.addComponent(itemListRouters);

        Button buttonConnect = new Button(79, 99, Icons.CHECK);
        buttonConnect.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                if (itemListRouters.getSelectedItem() != null) {
                    TaskConnect connect = new TaskConnect(Laptop.getPos(), itemListRouters.getSelectedItem());
                    connect.setCallback((tagCompound, success) ->
                    {
                        if (success) {
                            TrayItemWifi.trayItem.setIcon(Icons.WIFI_HIGH);
                            Laptop.getSystem().closeContext();
                        }
                    });
                    TaskManager.sendTask(connect);
                }
            }
        });
        layoutWifi.addComponent(buttonConnect);

        Button buttonWallpaperLeft = new Button(185, 27, Icons.ARROW_LEFT);
        buttonWallpaperLeft.setSize(25, 20);
        buttonWallpaperLeft.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                Objects.requireNonNull(this.getLaptop()).prevWallpaper();
            }
        });
        layoutWallpapers.addComponent(buttonWallpaperLeft);

        Button buttonWallpaperRight = new Button(215, 27, Icons.ARROW_RIGHT);
        buttonWallpaperRight.setSize(25, 20);
        buttonWallpaperRight.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                Objects.requireNonNull(this.getLaptop()).nextWallpaper();
            }
        });
        layoutWallpapers.addComponent(buttonWallpaperRight);

        Button reload = new Button(250, 27, Icons.RELOAD);
        reload.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                ClientProxy.cache.values().iterator().next();
            }
        });
        layoutWallpapers.addComponent(reload);

        Button buttonWallpaperUrl = new Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        ComboBox.Custom<Integer> comboBoxApplicationBarColour = createColourPicker(26);
        layoutColourScheme.addComponent(comboBoxApplicationBarColour);

        Label applicationBarColour = new Label("Application Bar Colour", 200, 29);
        layoutColourScheme.addComponent(applicationBarColour);

        buttonColourSchemeApply = new Button(5, 79, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
                colourScheme.setApplicationBarColour(Objects.requireNonNull(comboBoxApplicationBarColour.getValue()));
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        ItemList<AppInfo> itemListApps = new ItemList<>(10, 30, 310, 5, true);
        itemListApps.setItems(new ArrayList<>(ApplicationManager.getAvailableApplications()));
        itemListApps.sortBy(Comparator.comparing(AppInfo::getName));
        itemListApps.setListItemRenderer(new ListItemRenderer<AppInfo>(18) {
            @Override
            public void render(AppInfo info, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? ITEM_SELECTED.getRGB() : ITEM_BACKGROUND.getRGB());

                GlStateManager.color(1.0F, 1.0F, 1.0F);
                RenderUtil.drawApplicationIcon(info, x + 2, y + 2);
                RenderUtil.drawStringClipped(info.getName() + TextFormatting.GRAY + " - " + TextFormatting.DARK_GRAY + info.getDescription(), x + 20, y + 5, itemListApps.getWidth() - 22, Color.WHITE.getRGB(), false);
            }
        });
        itemListApps.setItemClickListener((info, index, mouseButton) ->
        {
            if (mouseButton == 0) {
                if (System.currentTimeMillis() - this.lastClick <= 200) {
                    openApplication(info);
                } else {
                    this.lastClick = System.currentTimeMillis();
                }
            }
        });
        layoutInformationApps.addComponent(itemListApps);

        Label nameOnPage = new Label("Basic information about the computer", 40, 25);
        nameOnPage.setTextColour(Color.GRAY.getRGB());
        layoutInformationComputer.addComponent(nameOnPage);

        layoutInformationComputer.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y + 35, x + width, y + 36, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 49, x + width, y + 50, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 80, x + width, y + 81, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 93, x + width, y + 94, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 147, x + width, y + 148, Color.GRAY.getRGB());
        });

        Label NeonOSVersion = new Label("NeonOS-Version", 40, 38);
        NeonOSVersion.setTextColour(Color.LIGHT_GRAY.getRGB());
        layoutInformationComputer.addComponent(NeonOSVersion);

        Label OS = new Label("NeonOS 3 Professional", 40, 54);
        layoutInformationComputer.addComponent(OS);

        Label copyright = new Label("© 2017 HextCraft Corporation. With sole rights", 40, 69);
        layoutInformationComputer.addComponent(copyright);

        Label system = new Label("System", 40, 83);
        system.setTextColour(Color.LIGHT_GRAY.getRGB());
        layoutInformationComputer.addComponent(system);

        Label graphicCard = new Label("Graphic Card: Mine-Vidia Titan X 12GPB GDDR5X", 40, 97);
        layoutInformationComputer.addComponent(graphicCard);

        Label CPU = new Label("CPU: Minetel i9-7980XE Extreme Edition", 40, 110);
        layoutInformationComputer.addComponent(CPU);

        Label Ram = new Label("Ram: Mineston Hyper X Beast 64GPB " + "(63GPB can be used)", 40, 123);
        layoutInformationComputer.addComponent(Ram);

        Label systemType = new Label("System Type: 64-bit-OS, x64-based-processor", 40, 135);
        layoutInformationComputer.addComponent(systemType);

        Layout menuCredits = new Menu("Credits");

        Label labelCredits = new Label("Credits", 80, 10);
        menuCredits.addComponent(labelCredits);

        setCurrentLayout(layoutMain);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

    private void showMenu(Layout layout) {
        predecessor.push(getCurrentLayout());
        buttonPrevious.setVisible(true);
        setCurrentLayout(layout);
    }

    @Override
    public void onClose() {
        super.onClose();
        predecessor.clear();
    }

    private void openApplication(AppInfo info) {
        Layout layout = new LayoutAppPage(info);
        setCurrentLayout(layout);
        Button btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
        btnPrevious.setClickListener((mouseX1, mouseY1, mouseButton1) -> setCurrentLayout(layoutInformationApps));
        layout.addComponent(btnPrevious);
    }

    private ComboBox.Custom<Integer> createColourPicker(int top) {
        ComboBox.Custom<Integer> colourPicker = new ComboBox.Custom<>(145, top, 50, 100, 100);
        colourPicker.setValue(Color.RED.getRGB());
        colourPicker.setItemRenderer(new ItemRenderer<Integer>() {
            @Override
            public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height) {
                if (integer != null) {
                    Gui.drawRect(x, y, x + width, y + height, integer);
                }
            }
        });
        colourPicker.setChangeListener((oldValue, newValue) -> buttonColourSchemeApply.setEnabled(true));

        Palette palette = new Palette(5, 5, colourPicker);
        Layout layout = colourPicker.getLayout();
        layout.addComponent(palette);

        return colourPicker;
    }

    private static class Menu extends Layout {
        private String title;

        Menu(String title) {
            super(330, 260);
            this.title = title;
        }

        @Override
        public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
            mc.fontRenderer.drawString(title, x + 22, y + 6, Color.WHITE.getRGB(), true);
            super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
        }
    }
}