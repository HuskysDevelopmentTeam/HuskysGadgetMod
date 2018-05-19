package net.thegaminghuskymc.gadgetmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.thegaminghuskymc.gadgetmod.entity.EntitySeat;
import net.thegaminghuskymc.gadgetmod.gui.GuiHandler;
import net.thegaminghuskymc.gadgetmod.init.GadgetCrafting;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.init.GadgetTasks;
import net.thegaminghuskymc.gadgetmod.init.GadgetTileEntities;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION/*, dependencies = Reference.DEPENDENCE*/)
public class HuskyGadgetMod {

    @Instance(Reference.MOD_ID)
    public static HuskyGadgetMod instance;
    public static boolean hasAllApps = false, isInsider = false, isDeveloper = false, isAdmin = false, isServerAdmin = false, isArtist = true, likesSocialMedias = true;
    public static final RemoteClassLoader remoteClassLoader = new RemoteClassLoader(HuskyGadgetMod.class.getClassLoader());
    public static File modDataDir;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs deviceBlocks = new DeviceTab("gadgetBlocks");
    public static CreativeTabs deviceItems = new DeviceTab("gadgetItems");
    public static CreativeTabs deviceDecoration = new DeviceTab("gadgetDecoration");

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        Keybinds.register();
        logger = event.getModLog();
        DeviceConfig.load(event.getSuggestedConfigurationFile());
        GadgetItems.register();
        GadgetCrafting.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        GadgetTileEntities.register();
        GadgetTasks.register();
        PacketHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "seat"), EntitySeat.class, "Seat", 0, this, 80, 1, false);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
