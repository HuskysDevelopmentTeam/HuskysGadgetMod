package net.thegaminghuskymc.gadgetmod;

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
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.print.PrintingManager;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.core.io.task.*;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskGetDevices;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskPing;
import net.thegaminghuskymc.gadgetmod.core.print.task.TaskPrint;
import net.thegaminghuskymc.gadgetmod.entity.EntitySeat;
import net.thegaminghuskymc.gadgetmod.event.BankEvents;
import net.thegaminghuskymc.gadgetmod.event.EmailEvents;
import net.thegaminghuskymc.gadgetmod.gui.GuiHandler;
import net.thegaminghuskymc.gadgetmod.init.*;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.programs.*;
import net.thegaminghuskymc.gadgetmod.programs.auction.ApplicationMineBay;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskAddAuction;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskBuyItem;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskGetAuctions;
import net.thegaminghuskymc.gadgetmod.programs.email.ApplicationEmail;
import net.thegaminghuskymc.gadgetmod.programs.email.task.*;
import net.thegaminghuskymc.gadgetmod.programs.social_medias.*;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationBank;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationFileBrowser;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationSettings;
import net.thegaminghuskymc.gadgetmod.programs.system.task.*;
import net.thegaminghuskymc.gadgetmod.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION)
public class HuskyGadgetMod {

    @Instance(Reference.MOD_ID)
    public static HuskyGadgetMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs deviceBlocks = new DeviceTab("hdmTabDeviceBlocks");
    public static CreativeTabs deviceItems = new DeviceTab("hdmTabDeviceItems");
    public static CreativeTabs deviceDecoration = new DeviceTab("hdmTabDeviceDecoration");
    public static boolean DEVELOPER_MODE;
    public static boolean HUSKY_MODE;
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        DEVELOPER_MODE = false;
        HUSKY_MODE = true;

        logger = event.getModLog();

        DeviceConfig.load(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new DeviceConfig());

        RegistrationHandler.init();

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        /* Tile Entity Registering */
        GadgetTileEntities.register();

        EntityRegistry.registerModEntity(new ResourceLocation("hgmSeat"), EntitySeat.class, "hgmSeat", 0, this, 80, 1, false);

        /* Packet Registering */
        PacketHandler.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new EmailEvents());
        MinecraftForge.EVENT_BUS.register(new BankEvents());

        registerApplications();

        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    private void registerApplications() {
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
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "test"), ApplicationTest.class);

        // Core
        TaskManager.registerTask(TaskUpdateApplicationData.class);
        TaskManager.registerTask(TaskPrint.class);
        TaskManager.registerTask(TaskUpdateSystemData.class);
        TaskManager.registerTask(TaskConnect.class);
        TaskManager.registerTask(TaskPing.class);
        TaskManager.registerTask(TaskGetDevices.class);

        TaskManager.registerTask(net.thegaminghuskymc.gadgetmod.core.images.task.TaskConnect.class);
        TaskManager.registerTask(net.thegaminghuskymc.gadgetmod.core.images.task.TaskPing.class);
        TaskManager.registerTask(net.thegaminghuskymc.gadgetmod.core.images.task.TaskGetDevices.class);

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

        if (HUSKY_MODE) {
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craycord"), ApplicationDiscord.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craybook"), ApplicationFacebook.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "cray_plus"), ApplicationGooglePlus.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "twitter"), ApplicationTwitter.class);
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "craytube"), ApplicationYouTube.class);
        }

        if (!DEVELOPER_MODE) {
            // Tasks (Normal)
            TaskManager.registerTask(TaskAddAuction.class);
            TaskManager.registerTask(TaskGetAuctions.class);
            TaskManager.registerTask(TaskBuyItem.class);
        } else {
            // Applications (Developers)
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "example"), ApplicationExample.class);
        }
        PrintingManager.registerPrint(new ResourceLocation(Reference.MOD_ID, "picture"), ApplicationPixelPainter.PicturePrint.class);
    }
}
