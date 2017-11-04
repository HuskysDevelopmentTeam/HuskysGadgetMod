package net.husky.device.programs.system;

import net.husky.device.HuskyDeviceMod;
import net.husky.device.api.app.Icons;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.component.CheckBox;
import net.husky.device.api.app.component.ComboBox;
import net.husky.device.api.app.component.Palette;
import net.husky.device.api.app.renderer.ItemRenderer;
import net.husky.device.core.Laptop;
import net.husky.device.core.Settings;
import net.husky.device.programs.system.object.ColourScheme;
import net.husky.device.api.app.component.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class ApplicationSettings extends SystemApplication
{
	private Layout layoutMain;
	private Layout layoutGeneral;
	private CheckBox checkBoxShowApps;
	private Button btnWallpaperNext;
	private Button btnWallpaperPrev;

	private Layout layoutColourScheme;
	private Button buttonColourSchemeApply;

	private boolean valueChanged;

	public ApplicationSettings()
	{
		this.setDefaultWidth(140);
		this.setDefaultHeight(160);
	}

	@Override
	public void init()
	{
		valueChanged = false;

		layoutMain = new Layout(100, 90);
		layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
		});

		Button buttonColourScheme = new Button(5, 26, "Appearance", Icons.EDIT);
		buttonColourScheme.setToolTip("Appearance", "Change the system colour scheme");
		buttonColourScheme.setClickListener((c, mouseButton) ->
		{
			if(mouseButton == 0)
			{
				setCurrentLayout(layoutColourScheme);
			}
		});
		layoutMain.addComponent(buttonColourScheme);

		layoutGeneral = new Layout(100, 50);

		checkBoxShowApps = new CheckBox("Show All Apps", 5, 5);
		checkBoxShowApps.setSelected(Settings.isShowAllApps());
		checkBoxShowApps.setClickListener((c, mouseButton) ->
		{
			Settings.setShowAllApps(checkBoxShowApps.isSelected());
			Laptop laptop = getLaptop();
			laptop.getTaskBar().setupApplications(laptop.getApplications());
		});
		layoutGeneral.addComponent(checkBoxShowApps);

		layoutColourScheme = new Layout(100, 100);
		layoutColourScheme.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());

			Gui.drawRect(x, y + 21, x + width, y + 40, Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
			Gui.drawRect(x, y + 40, x + width, y + 41, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 41, x + width, y + 60, Laptop.getSystem().getSettings().getColourScheme().getItemHighlightColour());
            Gui.drawRect(x, y + 60, x + width, y + 61, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 61, x + width, y + 80, Laptop.getSystem().getSettings().getColourScheme().getTextColour());
            Gui.drawRect(x, y + 80, x + width, y + 81, Color.DARK_GRAY.getRGB());

            Gui.drawRect(x, y + 81, x + width, y + 100, Laptop.getSystem().getSettings().getColourScheme().getTextSecondaryColour());
            Gui.drawRect(x, y + 100, x + width, y + 101, Color.DARK_GRAY.getRGB());

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

		Button developerMode = new Button(5, 28, "Developer Mode");
		developerMode.setToolTip("Developer Mode", "Do you only want have the developer apps?");
		developerMode.setClickListener((c, mouseButton) ->
		{
			if(mouseButton == 0)
			{
				HuskyDeviceMod.DEVELOPER_MODE = true;
			}
		});
        layoutMain.addComponent(developerMode);

		Button huskyMode = new Button(5, 28, "Husky Mode");
		huskyMode.setToolTip("Husky Mode", "Do you want have all of husky's apps?");
		huskyMode.setClickListener((c, mouseButton) ->
		{
			if(mouseButton == 0)
			{
				HuskyDeviceMod.HUSKY_MODE = true;
			}
		});
        layoutMain.addComponent(huskyMode);

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

}