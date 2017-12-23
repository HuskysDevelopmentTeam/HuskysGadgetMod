package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.network.TrayItemWifi;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ApplicationSettings extends SystemApplication {

    private Button buttonPrevious;

    private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;

    private Button buttonColourSchemeApply;

    private Layout layoutInformation;

    private Stack<Layout> predecessor = new Stack<>();

    public ApplicationSettings() {
        this.setDefaultWidth(280);
        this.setDefaultHeight(160);
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
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 520, y + wallpaperY - 1 + 90, Objects.requireNonNull(getLaptop()).getSettings().getColourScheme().getHeaderColour());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = getLaptop().getWallapapers();
            mc.getTextureManager().bindTexture(wallpapers.get(getLaptop().getCurrentWallpaper()));
            RenderUtil.drawRectWithTexture(x + wallpaperX, y + wallpaperY, 0, 0, 520, 289, 520, 288);
            mc.fontRenderer.drawString("Wallpaper", x + wallpaperX + 3, y + wallpaperY + 3, getLaptop().getSettings().getColourScheme().getTextColour(), true);
        });

        layoutInformation = new Menu("Information");
        layoutInformation.addComponent(buttonPrevious);

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

        Button information = new Button(5, 86, "Information", Icons.HELP);
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

        Button buttonWiFi = new Button(50, 96, "Wifi", Icons.WIFI_HIGH);
        buttonWiFi.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0) {
                showMenu(layoutWifi);
            }
        });
        layoutMain.addComponent(buttonWiFi);

        ItemList<BlockPos> itemListRouters = new ItemList<>(5, 25, 90, 4);
        itemListRouters.setItems(TrayItemWifi.getRouters());
        itemListRouters.setListItemRenderer(new ListItemRenderer<BlockPos>(16)
        {
            @Override
            public void render(BlockPos blockPos, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected)
            {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, "Monitor", x + 16, y + 4, Color.WHITE.getRGB());

                BlockPos laptopPos = Laptop.getPos();
                double distance = Math.sqrt(blockPos.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
                if(distance > 20)
                {
                    Icons.WIFI_LOW.draw(mc, x + 3, y + 3);
                }
                else if(distance > 10)
                {
                    Icons.WIFI_MED.draw(mc, x + 3, y + 3);
                }
                else
                {
                    Icons.WIFI_HIGH.draw(mc, x + 3, y + 3);
                }
            }
        });
        itemListRouters.sortBy((o1, o2) -> {
            BlockPos laptopPos = Laptop.getPos();
            double distance1 = Math.sqrt(o1.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            double distance2 = Math.sqrt(o2.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            return Double.compare(distance1, distance2);
        });
        layoutWifi.addComponent(itemListRouters);

        Button buttonConnect = new Button(79, 99, Icons.CHECK);
        buttonConnect.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                if(itemListRouters.getSelectedItem() != null)
                {
                    TaskConnect connect = new TaskConnect(Laptop.getPos(), itemListRouters.getSelectedItem());
                    connect.setCallback((tagCompound, success) ->
                    {
                        if(success)
                        {
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

        Button buttonWallpaperUrl = new Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        ComboBox.Custom<Integer> comboBoxTextColour = createColourPicker(26);
        layoutColourScheme.addComponent(comboBoxTextColour);

        ComboBox.Custom<Integer> comboBoxTextSecondaryColour = createColourPicker(44);
        layoutColourScheme.addComponent(comboBoxTextSecondaryColour);

        ComboBox.Custom<Integer> comboBoxHeaderColour = createColourPicker(62);
        layoutColourScheme.addComponent(comboBoxHeaderColour);

        ComboBox.Custom<Integer> comboBoxBackgroundColour = createColourPicker(80);
        layoutColourScheme.addComponent(comboBoxBackgroundColour);

        ComboBox.Custom<Integer> comboBoxBackgroundSecondaryColour = createColourPicker(98);
        layoutColourScheme.addComponent(comboBoxBackgroundSecondaryColour);

        ComboBox.Custom<Integer> comboBoxItemBackgroundColour = createColourPicker(116);
        layoutColourScheme.addComponent(comboBoxItemBackgroundColour);

        ComboBox.Custom<Integer> comboBoxItemHighlightColour = createColourPicker(134);
        layoutColourScheme.addComponent(comboBoxItemHighlightColour);

        buttonColourSchemeApply = new Button(5, 79, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
                colourScheme.setBackgroundColour(Objects.requireNonNull(comboBoxHeaderColour.getValue()));
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        Label OSName = new Label("OS Name: " + Reference.OSName, 10, 50);
        layoutInformation.addComponent(OSName);

        Label OSVersion = new Label("OS Version: " + Reference.OSVersion, 10, 65);
        layoutInformation.addComponent(OSVersion);

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
            super(280, 160);
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