package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

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
    public void init(@Nullable NBTTagCompound intent) {
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
        layoutWallpapers.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            int wallpaperX = 7;
            int wallpaperY = 28;
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 162, y + wallpaperY - 1 + 90, getLaptop().getSettings().getColourScheme().getHeaderColour());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = getLaptop().getWallapapers();
            mc.getTextureManager().bindTexture(wallpapers.get(getLaptop().getCurrentWallpaper()));
            RenderUtil.drawRectWithFullTexture(x + wallpaperX, y + wallpaperY, 0, 0, 160, 88);
            mc.fontRenderer.drawString("Wallpaper", x + wallpaperX + 3, y + wallpaperY + 3, getLaptop().getSettings().getColourScheme().getTextColour(), true);
        });

        layoutInformation = new Menu("Information");
        layoutInformation.addComponent(buttonPrevious);

        Layout layoutInformationComputer = new Menu("Computer Information");
        layoutInformationComputer.addComponent(buttonPrevious);

        layoutInformationApps = new Menu("App Information");
        layoutInformationApps.addComponent(buttonPrevious);

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

        Button personalise = new Button(5, 25, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        for(int i = 0; i > 10; i++) {
            int padding = 5;
            int iconOffset = (15 - 14 * 3) / 2;
            Image image = new Image(iconOffset, padding, Icons.ARROW_LEFT);
            this.addComponent(image);
        }

        Button information = new Button(5, 46, "Information", Icons.HELP);
        information.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutInformation);
            }
        });
        layoutMain.addComponent(information);

        Button wallpapers = new Button(5, 25, "Wallpapers", Icons.PICTURE);
        wallpapers.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutWallpapers);
            }
        });
        layoutPersonalise.addComponent(wallpapers);

        Button buttonColourScheme = new Button(5, 46, "Colour Schemes", Icons.TRASH);
        buttonColourScheme.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutColourScheme);
            }
        });
        layoutPersonalise.addComponent(buttonColourScheme);

        Button buttonWiFi = new Button(5, 67, "Wifi", Icons.WIFI_HIGH);
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

                BlockPos laptopPos = BaseDevice.getPos();
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
            BlockPos laptopPos = BaseDevice.getPos();
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
                    TaskConnect connect = new TaskConnect(BaseDevice.getPos(), itemListRouters.getSelectedItem());
                    connect.setCallback((tagCompound, success) ->
                    {
                        if (success) {
                            TrayItemWifi.trayItem.setIcon(Icons.WIFI_HIGH);
                            BaseDevice.getSystem().closeContext();
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
        layoutWallpapers.addComponent(reload);

        Button buttonWallpaperUrl = new Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        Label mainApplicationBarColour = new Label("Main Application Bar Colour", 175, 29);
        layoutColourScheme.addComponent(mainApplicationBarColour);

        ComboBox.Custom<Integer> comboBoxMainApplicationBarColour = createColourPicker(117, 26);
        layoutColourScheme.addComponent(comboBoxMainApplicationBarColour);

        Label secondApplicationBarColour = new Label("Second Application Bar Colour", 175, 49);
        layoutColourScheme.addComponent(secondApplicationBarColour);

        ComboBox.Custom<Integer> comboBoxSecondaryApplicationBarColour = createColourPicker(117, 46);
        layoutColourScheme.addComponent(comboBoxSecondaryApplicationBarColour);

        Label backgroundColour = new Label("Background Colour", 175, 69);
        layoutColourScheme.addComponent(backgroundColour);

        ComboBox.Custom<Integer> comboBoxBackgroundColour = createColourPicker(117, 66);
        layoutColourScheme.addComponent(comboBoxBackgroundColour);

        buttonColourSchemeApply = new Button(5, 79, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                ColourScheme colourScheme = BaseDevice.getSystem().getSettings().getColourScheme();
                colourScheme.setMainApplicationBarColour(comboBoxMainApplicationBarColour.getValue());
                colourScheme.setSecondApplicationBarColour(comboBoxSecondaryApplicationBarColour.getValue());
                colourScheme.setBackgroundColour(comboBoxBackgroundColour.getValue());
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        Label nameOnPage = new Label("Basic information about the computer", 40, 25);
        layoutInformationComputer.addComponent(nameOnPage);

        layoutInformationComputer.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y + 35, x + width, y + 36, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 49, x + width, y + 50, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 80, x + width, y + 81, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 93, x + width, y + 94, Color.GRAY.getRGB());

            Gui.drawRect(x, y + 147, x + width, y + 148, Color.GRAY.getRGB());
        });

        Label NeonOSVersion = new Label("NeonOS-Version", 40, 38);
        layoutInformationComputer.addComponent(NeonOSVersion);

        Label OS = new Label("NeonOS 3 Professional", 40, 54);
        layoutInformationComputer.addComponent(OS);

        Label copyright = new Label("© 2017 HextCraft Corporation. With sole rights", 40, 69);
        layoutInformationComputer.addComponent(copyright);

        Label system = new Label("System", 40, 83);
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

    private ComboBox.Custom<Integer> createColourPicker(int left, int top) {
        ComboBox.Custom<Integer> colourPicker = new ComboBox.Custom<>(left, top, 50, 100, 100);
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
        public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
            Gui.drawRect(x - 1, y, x + width + 1, y + 20, BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour());
            mc.fontRenderer.drawString(title, x + 22, y + 6, Color.WHITE.getRGB(), true);
            super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
        }
    }
}