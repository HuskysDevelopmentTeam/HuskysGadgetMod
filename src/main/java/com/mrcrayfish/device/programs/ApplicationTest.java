package com.mrcrayfish.device.programs;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.component.*;
import com.mrcrayfish.device.api.app.listener.SlideListener;
import net.minecraft.nbt.NBTTagCompound;
import com.mrcrayfish.device.core.io.*;
import com.mrcrayfish.device.object.Canvas;
import com.mrcrayfish.device.object.ColourGrid;
import com.mrcrayfish.device.object.Picture;
import com.mrcrayfish.device.object.Picture.Size;

public class ApplicationTest extends Application
{
	private Label label;
	private Button button;
	private Canvas canvas;
	private ItemList<String> itemList;

	
	public ApplicationTest() 
	{
		//super("example", "UI Components");
		this.setDefaultWidth(300);
		this.setDefaultHeight(140);
	}
	
	@Override
	public void init() 
	{


		itemList = new ItemList<String>(5, 60, 63, 4);
		itemList.addItem("Item #1");
		itemList.addItem("Item #2");
		itemList.addItem("Item #3");
		super.addComponent(itemList);
		
		label = new Label(""+itemList, 5, 5);
		super.addComponent(label);
		canvas = new Canvas(800, 500);

		
		
		
		button = new Button(10, 30, "Browse");
		button.setSize(50, 20);
		addComponent(button);
		button.setClickListener((c, mouseButton) ->
		{
			Dialog.OpenFile dialog = new Dialog.OpenFile(this);
			dialog.setResponseHandler((success, file) ->
			{
				
					Picture picture = Picture.fromFile(file);
					canvas.setPicture(picture);
					//setCurrentLayout(layoutDraw);
					this.addComponent(canvas);
					return true;
		
				
			});
			openDialog(dialog);
        });
		

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
