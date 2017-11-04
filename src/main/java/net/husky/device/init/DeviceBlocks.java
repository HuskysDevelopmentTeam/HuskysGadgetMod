package net.husky.device.init;

import net.husky.device.block.BlockLaptop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DeviceBlocks 
{
	public static Block laptop;
	
	public static void init()
	{
		laptop = new BlockLaptop().setUnlocalizedName("laptop").setRegistryName("laptop");
	}
	
	public static void register()
	{
		registerBlock(laptop);
	}
	
	public static void registerBlock(Block block)
	{
		ForgeRegistries.BLOCKS.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(item);
	}
	
	public static void registerRenders() 
	{
		registerRender(laptop);
	}
	
	private static void registerRender(Block blockIn)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockIn), 0, new ModelResourceLocation(blockIn.getRegistryName(), "inventory"));
	}
}