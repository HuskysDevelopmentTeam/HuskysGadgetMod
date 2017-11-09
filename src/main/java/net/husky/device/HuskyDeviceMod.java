package net.husky.device;

import net.husky.device.api.ApplicationManager;
import net.husky.device.api.task.TaskManager;
import net.husky.device.core.io.task.*;
import net.husky.device.event.BankEvents;
import net.husky.device.event.EmailEvents;
import net.husky.device.gui.GuiHandler;
import net.husky.device.init.DeviceBlocks;
import net.husky.device.init.DeviceCrafting;
import net.husky.device.init.DeviceItems;
import net.husky.device.init.DeviceTileEntites;
import net.husky.device.network.PacketHandler;
import net.husky.device.programs.*;
import net.husky.device.programs.auction.ApplicationMineBay;
import net.husky.device.programs.auction.task.TaskAddAuction;
import net.husky.device.programs.auction.task.TaskBuyItem;
import net.husky.device.programs.auction.task.TaskGetAuctions;
import net.husky.device.programs.email.ApplicationEmail;
import net.husky.device.programs.email.task.*;
import net.husky.device.programs.social_medias.*;
import net.husky.device.programs.system.*;
import net.husky.device.programs.system.task.*;
import net.husky.device.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
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

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.ModVersion, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION)
public class HuskyDeviceMod
{
	@Instance(Reference.MOD_ID)
	public static HuskyDeviceMod instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabDevice = new DeviceTab("hdmTabDevice");

	private static Logger logger;

	public static boolean DEVELOPER_MODE;
	public static boolean HUSKY_MODE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	    DEVELOPER_MODE = false;
	    HUSKY_MODE = true;

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
	}

	private void registerApplications()
	{
		// Applications (Both)
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "settings"), ApplicationSettings.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bank"), ApplicationBank.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "file_browser"), ApplicationFileBrowser.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "note_stash"), ApplicationNoteStash.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_painter"), ApplicationPixelPainter.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "ender_mail"), ApplicationEmail.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "app_store"), ApplicationAppStore.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "boat_racers"), ApplicationBoatRacers.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "mine_bay"), ApplicationMineBay.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "icons"), ApplicationIcons.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bluej"), ApplicationBlueJ.class);

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

		if(HUSKY_MODE) {
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craycord"), ApplicationDiscord.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craybook"), ApplicationFacebook.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "cray_plus"), ApplicationGooglePlus.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "twitter"), ApplicationTwitter.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craytube"), ApplicationYouTube.class);
        }

		if(!DEVELOPER_MODE)
		{
			// Tasks (Normal)
			TaskManager.registerTask(TaskAddAuction.class);
			TaskManager.registerTask(TaskGetAuctions.class);
			TaskManager.registerTask(TaskBuyItem.class);
		}
	}

	public static Logger getLogger()
	{
		return logger;
	}
}
