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
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Logos;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColorThemes;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ApplicationSettings extends SystemApplication {

    private Button btnPrevious;
    private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;
    private Layout layoutInformationApps;
    private Button btnColourSchemeApply, btnResetColors;
    private Layout layoutInformation;
    private Stack<Layout> predecessor = new Stack<>();

    public ApplicationSettings() {
        this.setDefaultWidth(330);
        this.setDefaultHeight(260);
    }

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        btnPrevious = new Button(2, 2, Icons.ARROW_LEFT);
        btnPrevious.setVisible(false);
        btnPrevious.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                if (predecessor.size() > 0) {
                    setCurrentLayout(predecessor.pop());
                }
                if (predecessor.isEmpty()) {
                    btnPrevious.setVisible(false);
                }
            }
        });

        Layout layoutMain = new Menu("Home");
        layoutMain.addComponent(btnPrevious);

        layoutColourScheme = new Menu("Colour Scheme");
        layoutColourScheme.addComponent(btnPrevious);

        layoutPersonalise = new Menu("Personalise");
        layoutPersonalise.addComponent(btnPrevious);

        layoutWallpapers = new Menu("Wallpapers");
        layoutWallpapers.addComponent(btnPrevious);
        layoutWallpapers.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            int wallpaperX = 7;
            int wallpaperY = 28;
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 162, y + wallpaperY - 1 + 90, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().brighter().getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = BaseDevice.getWallapapers();
            Color bgColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
            float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
            bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 1.0F));
            GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.3F);
            mc.getTextureManager().bindTexture(wallpapers.get(BaseDevice.getCurrentWallpaper()));
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            RenderUtil.drawRectWithFullTexture(x + wallpaperX, y + wallpaperY, 0, 0, 160, 88);
            mc.fontRenderer.drawString("Wallpaper", x + wallpaperX + 3, y + wallpaperY + 3, BaseDevice.getSystem().getSettings().getColourScheme().getTextColour(), true);
        });

        Layout themes = new Menu("Themes");
        themes.addComponent(btnPrevious);
        themes.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            int wallpaperX = 7;
            int wallpaperY = 28;
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 162, y + wallpaperY - 1 + 90, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().brighter().getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = BaseDevice.getThemes();
            Color bgColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
            float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
            bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 1.0F));
            GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.3F);
            mc.getTextureManager().bindTexture(wallpapers.get(BaseDevice.getCurrentTheme()));
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            RenderUtil.drawRectWithFullTexture(x + wallpaperX, y + wallpaperY, 0, 0, 160, 88);
            mc.fontRenderer.drawString("Theme", x + wallpaperX + 3, y + wallpaperY + 3, BaseDevice.getSystem().getSettings().getColourScheme().getTextColour(), true);
        });

        layoutInformation = new Menu("Information");
        layoutInformation.addComponent(btnPrevious);

        Layout layoutInformationComputer = new Menu("Computer Information");
        layoutInformationComputer.addComponent(btnPrevious);

        layoutInformationApps = new Menu("App Information");
        layoutInformationApps.addComponent(btnPrevious);

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
        layoutWifi.addComponent(btnPrevious);

        Button personalise = new Button(5, 25, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        for(int i = 0; i < 10; i++) {
            Image image = new Image(3 * i * 20, 3 * i * 20, Logos.LOVE2D);
            layoutInformationApps.addComponent(image);
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

        ComboBox.List<String> wallpaperOrColorSelection = new ComboBox.List<>(215, 47, new String[]{ "Wallpaper", "Color" });
        wallpaperOrColorSelection.setChangeListener((oldValue, newValue) -> {
            if(wallpaperOrColorSelection.getSelectedItem().equals("Color")) {
                BaseDevice.getSystem().getSettings().setHasWallpaperOrColor("Color");
            } else {
                BaseDevice.getSystem().getSettings().setHasWallpaperOrColor("Wallpaper");
            }
        });
        layoutWallpapers.addComponent(wallpaperOrColorSelection);

        ComboBox.List<String> taskbarPlacement = new ComboBox.List<>(215, 47, new String[]{ "Top", "Bottom", "Left", "Right" });
        taskbarPlacement.setChangeListener((oldValue, newValue) -> {
            switch (taskbarPlacement.getSelectedItem()) {
                case "Top":
                    BaseDevice.getSystem().getSettings().setTaskbarPlacement("Top");
                    break;
                case "Bottom":
                    BaseDevice.getSystem().getSettings().setTaskbarPlacement("Bottom");
                    break;
                case "Left":
                    BaseDevice.getSystem().getSettings().setTaskbarPlacement("Left");
                    break;
                case "Right":
                    BaseDevice.getSystem().getSettings().setTaskbarPlacement("Right");
                    break;
            }
        });
        layoutPersonalise.addComponent(taskbarPlacement);

        ComboBox.List<String> colourThemes = new ComboBox.List<>(215, 87, new String[]{ "Default", "Dark", "Light" });
        colourThemes.setChangeListener((oldValue, newValue) -> {
            ColourScheme colourScheme = BaseDevice.getSystem().getSettings().getColourScheme();
            switch (colourThemes.getSelectedItem()) {
                case "Default":
                    colourScheme.resetDefault();
                    break;
                case "Dark":
                    colourScheme.setBackgroundColour(ColorThemes.darkMode[0]);
                    colourScheme.setSecondApplicationBarColour(ColorThemes.darkMode[1]);
                    break;
                case "Light":
                    colourScheme.setBackgroundColour(ColorThemes.lightMode[0]);
                    colourScheme.setSecondApplicationBarColour(ColorThemes.lightMode[1]);
                    break;
            }
        });
        layoutColourScheme.addComponent(colourThemes);

        Label labelColorThemes = new Label("Color Themes", 145, 90);
        layoutColourScheme.addComponent(labelColorThemes);

        Button reload = new Button(250, 27, Icons.RELOAD);
        layoutWallpapers.addComponent(reload);

        Button buttonWallpaperUrl = new Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        buttonWallpaperUrl.setClickListener((mouseX, mouseY, mouseButton) -> {

        });
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        Label secondApplicationBarColour = new Label("Base Application Color", 175, 49);
        layoutColourScheme.addComponent(secondApplicationBarColour);

        ComboBox.Custom<Integer> comboBoxSecondaryApplicationBarColour = createColourPicker(117, 46, BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour());
        layoutColourScheme.addComponent(comboBoxSecondaryApplicationBarColour);

        Label backgroundColour = new Label("Background Colour", 175, 69);
        layoutColourScheme.addComponent(backgroundColour);

        ComboBox.Custom<Integer> comboBoxBackgroundColour = createColourPicker(117, 66, BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour());
        layoutColourScheme.addComponent(comboBoxBackgroundColour);

        btnResetColors = new Button(5, 99, Icons.UNDO);
        btnResetColors.setEnabled(false);
        btnResetColors.setToolTip("Reset Colors", "This will reset all of the custom colors you have set");
        btnResetColors.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                ColourScheme colourScheme = BaseDevice.getSystem().getSettings().getColourScheme();
                colourScheme.resetDefault();
                btnResetColors.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(btnResetColors);

        btnColourSchemeApply = new Button(5, 79, Icons.CHECK);
        btnColourSchemeApply.setEnabled(false);
        btnColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        btnColourSchemeApply.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                ColourScheme colourScheme = BaseDevice.getSystem().getSettings().getColourScheme();
                colourScheme.setSecondApplicationBarColour(comboBoxSecondaryApplicationBarColour.getValue());
                colourScheme.setBackgroundColour(comboBoxBackgroundColour.getValue());
                btnColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(btnColourSchemeApply);

        Label nameOnPage = new Label("Basic information about the computer", 40, 25);
        layoutInformationComputer.addComponent(nameOnPage);

        layoutInformationComputer.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y + 35, x + width, y + 36, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            Gui.drawRect(x, y + 49, x + width, y + 50, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            Gui.drawRect(x, y + 80, x + width, y + 81, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            Gui.drawRect(x, y + 93, x + width, y + 94, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            Gui.drawRect(x, y + 147, x + width, y + 148, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());
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

        Button btnThemes = new Button(5, 88, "Themes", Icons.PICTURE);
        btnThemes.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                showMenu(themes);
            }
        });
        layoutMain.addComponent(btnThemes);

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
        btnPrevious.setVisible(true);
        setCurrentLayout(layout);
    }

    @Override
    public void onClose() {
        super.onClose();
        predecessor.clear();
    }

    private ComboBox.Custom<Integer> createColourPicker(int left, int top, int baseColor) {
        ComboBox.Custom<Integer> colourPicker = new ComboBox.Custom<>(left, top, 50, 100, 100);
        colourPicker.setValue(baseColor);
        colourPicker.setItemRenderer(new ItemRenderer<Integer>() {
            @Override
            public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height) {
                if (integer != null) {
                    Gui.drawRect(x + 1, y, x + width + 1, y + height, integer);
                }
            }
        });
        colourPicker.setChangeListener((oldValue, newValue) -> {
            btnColourSchemeApply.setEnabled(true);
            btnResetColors.setEnabled(true);
        });

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
            Gui.drawRect(x - 1, y, x + width + 1, y + 20, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).getRGB());
            mc.fontRenderer.drawString(title, x + 22, y + 6, Color.WHITE.getRGB(), true);
            super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
        }
    }
}