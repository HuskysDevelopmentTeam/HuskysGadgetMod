package com.mrcrayfish.device;

import com.mrcrayfish.device.api.ApplicationManager;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.task.TaskManager;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.core.io.task.*;
import com.mrcrayfish.device.event.BankEvents;
import com.mrcrayfish.device.event.EmailEvents;
import com.mrcrayfish.device.gui.GuiHandler;
import com.mrcrayfish.device.init.DeviceBlocks;
import com.mrcrayfish.device.init.DeviceCrafting;
import com.mrcrayfish.device.init.DeviceItems;
import com.mrcrayfish.device.init.DeviceTileEntites;
import com.mrcrayfish.device.network.PacketHandler;
import com.mrcrayfish.device.programs.*;
import com.mrcrayfish.device.programs.auction.ApplicationMineBay;
import com.mrcrayfish.device.programs.auction.task.TaskAddAuction;
import com.mrcrayfish.device.programs.auction.task.TaskBuyItem;
import com.mrcrayfish.device.programs.auction.task.TaskGetAuctions;
import com.mrcrayfish.device.programs.email.ApplicationEmail;
import com.mrcrayfish.device.programs.email.task.*;
import com.mrcrayfish.device.programs.system.ApplicationBank;
import com.mrcrayfish.device.programs.system.ApplicationFileBrowser;
import com.mrcrayfish.device.programs.system.ApplicationSettings;
import com.mrcrayfish.device.programs.system.task.*;
import com.mrcrayfish.device.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION)
public class MrCrayfishDeviceMod 
{
	@Instance(Reference.MOD_ID)
	public static MrCrayfishDeviceMod instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabDevice = new DeviceTab("cdmTabDevice");

	private static Logger logger;

	public static final boolean DEVELOPER_MODE = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws LaunchException {

		if(DEVELOPER_MODE && !(Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
		{
			throw new LaunchException();
		}

		logger = event.getModLog();

		/* Block Registering */
		DeviceBlocks.init();
		DeviceBlocks.register();

		DeviceItems.init();
		DeviceItems.register();
		
		/* Packet Registering */
		PacketHandler.init();
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		/* Crafting Registering */
		DeviceCrafting.register();
		
		/* Tile Entity Registering */
		DeviceTileEntites.register();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new EmailEvents());
		MinecraftForge.EVENT_BUS.register(new BankEvents());

		registerApplications();

		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		proxy.postInit();
		
		
		
		
		
		
		
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_4.png"));
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_1.png"));
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_2.png"));
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_3.png"));
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_5.png"));
		Laptop.addWallpaper(new ResourceLocation("cdm:textures/gui/laptop_wallpaper_6.png"));
		
		
	}

	private void registerApplications()
	{
		// Applications (Both)
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "settings"), ApplicationSettings.class);
	//	ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bank"), ApplicationBank.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "file_browser"), ApplicationFileBrowser.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "note_stash"), ApplicationNotePad.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_painter"), ApplicationPixelPainter.class);
	//	ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "ender_mail"), ApplicationEmail.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "example"), ApplicationExample.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "icons"), ApplicationIcons.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "test"), ApplicationTest.class);
		// Core
		TaskManager.registerTask(TaskUpdateApplicationData.class);
		TaskManager.registerTask(TaskUpdateSystemData.class);

		//Bank
		TaskManager.registerTask(ApplicationBank.TaskDeposit.class);
		TaskManager.registerTask(ApplicationBank.TaskWithdraw.class);
		TaskManager.registerTask(TaskGetBalance.class);
		TaskManager.registerTask(TaskPay.class);
		TaskManager.registerTask(TaskAdd.class);
		TaskManager.registerTask(TaskRemove.class);

		//File Browser
		TaskManager.registerTask(TaskSendAction.class);
		TaskManager.registerTask(TaskSetupFileBrowser.class);
		TaskManager.registerTask(TaskGetFiles.class);
		TaskManager.registerTask(TaskGetStructure.class);
		TaskManager.registerTask(TaskGetMainDrive.class);

		//Ender Mail
		TaskManager.registerTask(TaskUpdateInbox.class);
		TaskManager.registerTask(TaskSendEmail.class);
		TaskManager.registerTask(TaskCheckEmailAccount.class);
		TaskManager.registerTask(TaskRegisterEmailAccount.class);
		TaskManager.registerTask(TaskDeleteEmail.class);
		TaskManager.registerTask(TaskViewEmail.class);

		if(!DEVELOPER_MODE)
		{
			// Applications (Normal)
			//ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "boat_racers"), ApplicationBoatRacers.class);
		//	ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "mine_bay"), ApplicationMineBay.class);

			// Tasks (Normal)
			TaskManager.registerTask(TaskAddAuction.class);
			TaskManager.registerTask(TaskGetAuctions.class);
			TaskManager.registerTask(TaskBuyItem.class);
		}
		else
		{
			// Applications (Developers)
			ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "example"), ApplicationExample.class);
			ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "icons"), ApplicationIcons.class);
		}
	}

	public static Logger getLogger()
	{
		return logger;
	}
}
