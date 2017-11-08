package net.husky.device.programs.system;

import net.husky.device.HuskyDeviceMod;
import net.husky.device.Reference;
import net.husky.device.api.app.Component;
import net.husky.device.api.app.Icons;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.component.CheckBox;
import net.husky.device.api.app.component.ComboBox;
import net.husky.device.api.app.component.Label;
import net.husky.device.api.app.component.Palette;
import net.husky.device.api.app.listener.ClickListener;
import net.husky.device.api.app.renderer.ItemRenderer;
import net.husky.device.api.utils.RenderUtil;
import net.husky.device.core.Laptop;
import net.husky.device.init.DeviceBlocks;
import net.husky.device.programs.system.object.ColourScheme;
import net.husky.device.api.app.component.Button;
import net.husky.device.util.ColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;
import java.util.Stack;

public class ApplicationSettings extends SystemApplication
{

    private Button buttonPrevious;

    private Layout layoutMain;
    private Layout layoutGeneral;
    private CheckBox checkBoxShowApps;

	private Button btnWallpaperNext;
	private Button btnWallpaperPrev;

	private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;

	private Button buttonColourSchemeApply;

    private Button buttonWallpaperLeft;
    private Button buttonWallpaperRight;
    private Button buttonWallpaperUrl;

	private Layout layoutInformation;
    private Label OSVersion;
    private Label OSName;

    private Stack<Layout> predecessor = new Stack<>();

	public ApplicationSettings()
	{
		this.setDefaultWidth(280);
		this.setDefaultHeight(160);
	}

	@Override
	public void init()
	{

        buttonPrevious = new Button(2, 2, Icons.CHEVRON_LEFT);
        buttonPrevious.setVisible(false);
        buttonPrevious.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                if(predecessor.size() > 0)
                {
                    setCurrentLayout(predecessor.pop());
                }
                if(predecessor.isEmpty())
                {
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

        Button personalise = new Button(5, 66, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                showMenu(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        Button information = new Button(5, 86, "Information", Icons.HELP);
        information.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                showMenu(layoutInformation);
            }
        });
        layoutMain.addComponent(information);

        Button wallpapers = new Button(20, 66, "Wallpapers", Icons.PICTURE);
        wallpapers.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                showMenu(layoutWallpapers);
            }
        });
        layoutPersonalise.addComponent(wallpapers);

        Button buttonColourScheme = new Button(40, 86, "Colour Schemes", Icons.TRASH);
        buttonColourScheme.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                showMenu(layoutColourScheme);
            }
        });
        layoutPersonalise.addComponent(buttonColourScheme);

        buttonWallpaperLeft = new Button(185, 27, Icons.ARROW_LEFT);
        buttonWallpaperLeft.setSize(25, 20);
        layoutWallpapers.addComponent(buttonWallpaperLeft);

        buttonWallpaperRight = new Button(215, 27, Icons.ARROW_RIGHT);
        buttonWallpaperRight.setSize(25, 20);
        layoutWallpapers.addComponent(buttonWallpaperRight);

        buttonWallpaperUrl = new Button(185, 52, "Load", Icons.EARTH);
        buttonWallpaperUrl.setSize(55, 20);
        layoutWallpapers.addComponent(buttonWallpaperUrl);

        ComboBox.Custom<Integer> comboBoxTextColour = createColourPicker(145, 26);
        layoutColourScheme.addComponent(comboBoxTextColour);

        ComboBox.Custom<Integer> comboBoxTextSecondaryColour = createColourPicker(145, 44);
        layoutColourScheme.addComponent(comboBoxTextSecondaryColour);

        ComboBox.Custom<Integer> comboBoxTaskbarColour = createColourPicker(145, 62);
        comboBoxTaskbarColour.setChangeListener((oldVal, newVal)->{
                ColorHelper.setColor(newVal);
                this.buttonColourSchemeApply.setEnabled(true);
        });
        layoutColourScheme.addComponent(comboBoxTaskbarColour);

        ComboBox.Custom<Integer> comboBoxHeaderColour = createColourPicker(145, 80);
        layoutColourScheme.addComponent(comboBoxHeaderColour);

        ComboBox.Custom<Integer> comboBoxBackgroundColour = createColourPicker(145, 98);
        layoutColourScheme.addComponent(comboBoxBackgroundColour);

        ComboBox.Custom<Integer> comboBoxBackgroundSecondaryColour = createColourPicker(145, 116);
        layoutColourScheme.addComponent(comboBoxBackgroundSecondaryColour);

        ComboBox.Custom<Integer> comboBoxItemBackgroundColour = createColourPicker(145, 134);
        layoutColourScheme.addComponent(comboBoxItemBackgroundColour);

        ComboBox.Custom<Integer> comboBoxItemHighlightColour = createColourPicker(145, 152);
        layoutColourScheme.addComponent(comboBoxItemHighlightColour);

        buttonColourSchemeApply = new Button(5, 79, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
                colourScheme.setBackgroundColour(comboBoxHeaderColour.getValue());
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        OSName = new Label("OS Name: " + Reference.OSName, 10, 50);
        layoutInformation.addComponent(OSName);

        OSVersion = new Label("OS Version: " + Reference.OSVersion, 10, 65);
        layoutInformation.addComponent(OSVersion);

		setCurrentLayout(layoutMain);
	}

    @Override
    public void load(NBTTagCompound tagCompound)
    {

    }

    @Override
    public void save(NBTTagCompound tagCompound)
    {

    }

    private void showMenu(Layout layout)
    {
        predecessor.push(getCurrentLayout());
        buttonPrevious.setVisible(true);
        setCurrentLayout(layout);
    }

    @Override
    public void onClose()
    {
        super.onClose();
        predecessor.clear();
    }

    private static class Menu extends Layout
    {
        private String title;

        public Menu(String title)
        {
            super(280, 160);
            this.title = title;
        }

        @Override
        public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks)
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
            mc.fontRenderer.drawString(title, x + 22, y + 6, Color.WHITE.getRGB(), true);
            super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
        }
    }

    public ComboBox.Custom<Integer> createColourPicker(int left, int top)
    {
        ComboBox.Custom<Integer> colourPicker = new ComboBox.Custom<>(left, top, 50, 100, 100);
        colourPicker.setValue(Color.RED.getRGB());
        colourPicker.setItemRenderer(new ItemRenderer<Integer>()
        {
            @Override
            public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height)
            {
                if(integer != null)
                {
                    Gui.drawRect(x, y, x + width, y + height, integer);
                }
            }
        });
        /*
         * This is the default, you should use your own change listener
         */
        colourPicker.setChangeListener((oldValue, newValue) ->
        {
            buttonColourSchemeApply.setEnabled(true);
        });

        Palette palette = new Palette(5, 5, colourPicker);
        Layout layout = colourPicker.getLayout();
        layout.addComponent(palette);

        return colourPicker;
    }
}