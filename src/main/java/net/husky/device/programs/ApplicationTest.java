package net.husky.device.programs;

import net.husky.device.api.app.Application;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.Layout.Background;
import net.husky.device.api.app.component.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class ApplicationTest extends Application
{
	public ApplicationTest()
	{
		//super(Reference.MOD_ID + "TestApp", "Test App");
	}
	
	@Override
	public void init()
	{
		Layout one = new Layout(100, 100);
		one.setBackground(new Background()
		{
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive)
			{
				gui.drawRect(x, y, x + width, y + height, Color.GREEN.getRGB());
			}
		});
		
		Label labelOne = new Label("Layout 1", 5, 5);
		one.addComponent(labelOne);
	
		this.addComponent(one);
		
		Layout two = new Layout(100, 0, 100, 100);
		two.setBackground(new Background()
		{
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive)
			{
				gui.drawRect(x, y, x + width, y + height, Color.RED.getRGB());
			}
		});
		
		Label labelTwo = new Label("Layout 2", 5, 5);
		two.addComponent(labelTwo);
		
		this.addComponent(two);
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
