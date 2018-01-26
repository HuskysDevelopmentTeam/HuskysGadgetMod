package net.thegaminghuskymc.gadgetmod;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.thegaminghuskymc.gadgetmod.api.app.component.ComboBox;
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
import net.thegaminghuskymc.gadgetmod.handler.PlayerEvents;
import net.thegaminghuskymc.gadgetmod.init.GadgetTileEntities;
import net.thegaminghuskymc.gadgetmod.init.RegistrationHandler;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationPixelShop;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskAddAuction;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskBuyItem;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskGetAuctions;
import net.thegaminghuskymc.gadgetmod.programs.email.task.*;
import net.thegaminghuskymc.gadgetmod.programs.system.task.*;
import net.thegaminghuskymc.gadgetmod.proxy.CommonProxy;
import net.thegaminghuskymc.gadgetmod.util.ItemUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION/*, dependencies = Reference.DEPENDENCE*/)
public class HuskyGadgetMod {

    public static final RemoteClassLoader classLoader = new RemoteClassLoader(HuskyGadgetMod.class.getClassLoader());
    @Instance(Reference.MOD_ID)
    public static HuskyGadgetMod instance;
    public static Gson gson;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs deviceBlocks = new DeviceTab("gadgetBlocks");
    public static CreativeTabs deviceItems = new DeviceTab("gadgetItems");
    public static CreativeTabs deviceDecoration = new DeviceTab("gadgetDecoration");

    public static boolean HUSKY_MODE;
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Keybinds.register();

        HUSKY_MODE = true;

        logger = event.getModLog();

        DeviceConfig.load(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new DeviceConfig());

        RegistrationHandler.init();

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        /* Tile Entity Registering */
        GadgetTileEntities.register();

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "seat"), EntitySeat.class, "Seat", 0, this, 80, 1, false);

        /* Packet Registering */
        PacketHandler.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new EmailEvents());
        MinecraftForge.EVENT_BUS.register(new BankEvents());

//        GadgetOreDictionary.init();

        registerTasks();

        proxy.init(event);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    private void registerTasks() {
        // Core
        TaskManager.registerTask(TaskUpdateApplicationData.class);
        TaskManager.registerTask(TaskPrint.class);
        TaskManager.registerTask(TaskUpdateSystemData.class);
        TaskManager.registerTask(TaskConnect.class);
        TaskManager.registerTask(TaskPing.class);
        TaskManager.registerTask(TaskGetDevices.class);

        //Bank
        TaskManager.registerTask(TaskDeposit.class);
        TaskManager.registerTask(TaskWithdraw.class);
        TaskManager.registerTask(TaskGetBalance.class);
        TaskManager.registerTask(TaskPay.class);
        TaskManager.registerTask(TaskAdd.class);
        TaskManager.registerTask(TaskRemove.class);

        TaskManager.registerTask(TaskAddAuction.class);
        TaskManager.registerTask(TaskGetAuctions.class);
        TaskManager.registerTask(TaskBuyItem.class);

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

        PrintingManager.registerPrint(new ResourceLocation(Reference.MOD_ID, "picture"), ApplicationPixelShop.PicturePrint.class);
    }

}
