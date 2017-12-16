package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.CheckBox;
import net.thegaminghuskymc.gadgetmod.api.app.component.ComboBox;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.component.Palette;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;

import java.awt.*;
import java.util.List;
import java.util.Stack;

public class ApplicationSettings extends SystemApplication {

    private net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonPrevious;

    private Layout layoutMain;
    private Layout layoutGeneral;
    private CheckBox checkBoxShowApps;

    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnWallpaperNext;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnWallpaperPrev;

    private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;

    private net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonColourSchemeApply;

    private net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonWallpaperLeft;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonWallpaperRight;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonWallpaperUrl;

    private Layout layoutInformation;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label OSVersion;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label OSName;

    private Stack<Layout> predecessor = new Stack<>();

    public ApplicationSettings() {
        this.setDefaultWidth(280);
        this.setDefaultHeight(160);
    }

    @Override
    public void init() {
        buttonPrevious = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(2, 2, Icons.ARROW_LEFT);
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

        layoutMain = new Menu("Home");
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
            Gui.drawRect(x + wallpaperX - 1, y + wallpaperY - 1, x + wallpaperX - 1 + 172, y + wallpaperY - 1 + 90, getLaptop().getSettings().getColourScheme().getHeaderColour());
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            List<ResourceLocation> wallpapers = getLaptop().getWallapapers();
            mc.getTextureManager().bindTexture(wallpapers.get(getLaptop().getCurrentWallpaper()));
            RenderUtil.drawRectWithTexture(x + wallpaperX, y + wallpaperY, 0, 0, 170, 88, 256, 144);
            mc.fontRenderer.drawString("Wallpaper", x + wallpaperX + 3, y + wallpaperY + 3, getLaptop().getSettings().getColourScheme().getTextColour(), true);
        });

        layoutInformation = new Menu("Information");
        layoutInformation.addComponent(buttonPrevious);

        net.thegaminghuskymc.gadgetmod.api.app.component.Button personalise = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(5, 66, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        net.thegaminghuskymc.gadgetmod.api.app.component.Button information = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(5, 86, "Information", Icons.HELP);
        information.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutInformation);
            }
        });
        layoutMain.addComponent(information);

        net.thegaminghuskymc.gadgetmod.api.app.component.Button wallpapers = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(20, 66, "Wallpapers", Icons.PICTURE);
        wallpapers.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutWallpapers);
            }
        });
        layoutPersonalise.addComponent(wallpapers);

        net.thegaminghuskymc.gadgetmod.api.app.component.Button buttonColourScheme = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(40, 86, "Colour Schemes", Icons.TRASH);
        buttonColourScheme.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                showMenu(layoutColourScheme);
            }
        });
        layoutPersonalise.addComponent(buttonColourScheme);

        buttonWallpaperLeft = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(185, 27, Icons.ARROW_LEFT);
        buttonWallpaperLeft.setSize(25, 20);
        buttonWallpaperLeft.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                this.getLaptop().prevWallpaper();
            }
        });
        layoutWallpapers.addComponent(buttonWallpaperLeft);

        buttonWallpaperRight = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(215, 27, Icons.ARROW_RIGHT);
        buttonWallpaperRight.setSize(25, 20);
        buttonWallpaperRight.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                this.getLaptop().nextWallpaper();
            }
        });
        layoutWallpapers.addComponent(buttonWallpaperRight);

        buttonWallpaperUrl = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        ComboBox.Custom<Integer> comboBoxTextColour = createColourPicker(145, 26);
        layoutColourScheme.addComponent(comboBoxTextColour);

        ComboBox.Custom<Integer> comboBoxTextSecondaryColour = createColourPicker(145, 44);
        layoutColourScheme.addComponent(comboBoxTextSecondaryColour);

        ComboBox.Custom<Integer> comboBoxHeaderColour = createColourPicker(145, 62);
        layoutColourScheme.addComponent(comboBoxHeaderColour);

        ComboBox.Custom<Integer> comboBoxBackgroundColour = createColourPicker(145, 80);
        layoutColourScheme.addComponent(comboBoxBackgroundColour);

        ComboBox.Custom<Integer> comboBoxBackgroundSecondaryColour = createColourPicker(145, 98);
        layoutColourScheme.addComponent(comboBoxBackgroundSecondaryColour);

        ComboBox.Custom<Integer> comboBoxItemBackgroundColour = createColourPicker(145, 116);
        layoutColourScheme.addComponent(comboBoxItemBackgroundColour);

        ComboBox.Custom<Integer> comboBoxItemHighlightColour = createColourPicker(145, 134);
        layoutColourScheme.addComponent(comboBoxItemHighlightColour);

        buttonColourSchemeApply = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(5, 79, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
                colourScheme.setBackgroundColour(comboBoxHeaderColour.getValue());
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        OSName = new net.thegaminghuskymc.gadgetmod.api.app.component.Label("OS Name: " + Reference.OSName, 10, 50);
        layoutInformation.addComponent(OSName);

        OSVersion = new Label("OS Version: " + Reference.OSVersion, 10, 65);
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

    public ComboBox.Custom<Integer> createColourPicker(int left, int top) {
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
        colourPicker.setChangeListener((oldValue, newValue) ->
        {
            buttonColourSchemeApply.setEnabled(true);
        });

        Palette palette = new Palette(5, 5, colourPicker);
        Layout layout = colourPicker.getLayout();
        layout.addComponent(palette);

        return colourPicker;
    }

    private static class Menu extends Layout {
        private String title;

        public Menu(String title) {
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