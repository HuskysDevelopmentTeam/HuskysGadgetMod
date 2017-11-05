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
import net.husky.device.core.Settings;
import net.husky.device.programs.system.object.ColourScheme;
import net.husky.device.api.app.component.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ApplicationSettings extends SystemApplication
{

    private Layout layoutMain;

	private Button btnWallpaperNext;
	private Button btnWallpaperPrev;

	private Layout layoutPersonalise;
    private Layout layoutWallpapers;
    private Layout layoutColourScheme;

	private Button buttonColourSchemeApply;

	private Layout layoutInformation;
    private Label OSVersion;
    private Label OSName;

	public ApplicationSettings()
	{
		this.setDefaultWidth(280);
		this.setDefaultHeight(160);
	}

	@Override
	public void init()
	{
        layoutMain = new Layout(280, 160);
        layoutMain.addComponent(new Label("Home", 5, 6));
        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width , y + 21, Color.DARK_GRAY.getRGB());
        });

        layoutColourScheme = new Layout(280, 160);
        layoutColourScheme.addComponent(new Label("Colour Scheme", 25, 6));
        layoutColourScheme.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width , y + 21, Color.DARK_GRAY.getRGB());
        });

        layoutPersonalise = new Layout(280, 160);
        layoutPersonalise.addComponent(new Label("Personalise", 25, 6));
        layoutPersonalise.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width , y + 21, Color.DARK_GRAY.getRGB());
        });

        layoutWallpapers = new Layout(280, 160);
        layoutWallpapers.addComponent(new Label("Wallpapers", 25, 6));
        layoutWallpapers.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width , y + 21, Color.DARK_GRAY.getRGB());
        });

        layoutInformation = new Layout(280, 160);
        layoutInformation.addComponent(new Label("Information", 25, 6));
        layoutInformation.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width , y + 21, Color.DARK_GRAY.getRGB());
        });

        Button back = new Button(2, 2, Icons.CHEVRON_LEFT);
        back.setToolTip("Back", "Go back to the last menu");
        back.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                setCurrentLayout(layoutMain);
            }
        });
        layoutColourScheme.addComponent(back);
        layoutInformation.addComponent(back);
        layoutPersonalise.addComponent(back);
        layoutWallpapers.addComponent(back);

        Button personalise = new Button(5, 66, "Personalise", Icons.EYE_DROPPER);
        personalise.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                setCurrentLayout(layoutPersonalise);
            }
        });
        layoutMain.addComponent(personalise);

        Button information = new Button(5, 86, "Information", Icons.HELP);
        information.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                setCurrentLayout(layoutInformation);
            }
        });
        layoutMain.addComponent(information);

        Button wallpapers = new Button(20, 66, "Wallpapers", Icons.PICTURE);
        personalise.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                setCurrentLayout(layoutWallpapers);
            }
        });
        layoutMain.addComponent(wallpapers);

        Button buttonColourScheme = new Button(40, 86, "Colour Schemes", Icons.HELP);
        information.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                setCurrentLayout(layoutColourScheme);
            }
        });
        layoutMain.addComponent(buttonColourScheme);

        btnWallpaperNext = new Button(40, 36, Icons.CHEVRON_RIGHT);
		btnWallpaperNext.setClickListener(new ClickListener() {
			@Override
			public void onClick(Component c, int mouseButton) {
				Laptop.nextWallpaper();
			}
		});
		layoutWallpapers.addComponent(btnWallpaperNext);

		btnWallpaperPrev = new Button(5, 36, Icons.CHEVRON_LEFT);
		btnWallpaperPrev.setClickListener(new ClickListener() {
			@Override
			public void onClick(Component c, int mouseButton) {
				Laptop.prevWallpaper();
			}
        });
        layoutWallpapers.addComponent(btnWallpaperPrev);

        layoutColourScheme = new Layout(280, 160);
        layoutColourScheme.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width - 40, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width - 40, y + 21, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 21, x + width - 40, y + 40, Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            Gui.drawRect(x, y + 40, x + width - 40, y + 41, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 41, x + width - 40, y + 60, Laptop.getSystem().getSettings().getColourScheme().getItemHighlightColour());
            Gui.drawRect(x, y + 60, x + width - 40, y + 61, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 61, x + width - 40, y + 80, Laptop.getSystem().getSettings().getColourScheme().getTextColour());
            Gui.drawRect(x, y + 80, x + width - 40, y + 81, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 81, x + width - 40, y + 100, Laptop.getSystem().getSettings().getColourScheme().getTextSecondaryColour());
            Gui.drawRect(x, y + 100, x + width - 40, y + 101, Color.DARK_GRAY.getRGB());

        });

        ComboBox.Custom<Integer> colourPicker = new ComboBox.Custom<>(5, 26, 50, 100, 100);
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
        colourPicker.setChangeListener((oldValue, newValue) ->
        {
            buttonColourSchemeApply.setEnabled(true);
        });

        Layout layout = colourPicker.getLayout();

        Palette palette = new Palette(5, 5, colourPicker);
        layout.addComponent(palette);

        layoutColourScheme.addComponent(colourPicker);

        buttonColourSchemeApply = new Button(5, 99, Icons.CHECK);
        buttonColourSchemeApply.setEnabled(false);
        buttonColourSchemeApply.setToolTip("Apply", "Set these colours as the new colour scheme");
        buttonColourSchemeApply.setClickListener((c, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
                colourScheme.setBackgroundColour(colourPicker.getValue());
                buttonColourSchemeApply.setEnabled(false);
            }
        });
        layoutColourScheme.addComponent(buttonColourSchemeApply);

        OSName = new Label("OS Name: " + Reference.OSName, 10, 10);
        layoutInformation.addComponent(OSName);

        OSVersion = new Label("OS Version: " + Reference.OSVersion, 10, 25);
        layoutInformation.addComponent(OSVersion);

		setCurrentLayout(layoutMain);
	}

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
        if(this.getCurrentLayout() == layoutWallpapers) {
            laptop.drawString(mc.fontRenderer, "Wallpaper", x + 20, y + 40, Color.WHITE.getRGB());
            laptop.drawCenteredString(mc.fontRenderer, Integer.toString(Laptop.getCurrentWallpaper() + 1), x + 28, y + 48, Color.WHITE.getRGB());
        }
    }

    @Override
	public void load(NBTTagCompound tagCompound)
	{

	}

	@Override
	public void save(NBTTagCompound tagCompound)
	{

	}

}