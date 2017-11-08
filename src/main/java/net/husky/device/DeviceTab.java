package net.husky.device;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.husky.device.init.DeviceBlocks;

public class DeviceTab extends CreativeTabs 
{
	public DeviceTab(String label) 
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(DeviceBlocks.laptop);
	}
}
