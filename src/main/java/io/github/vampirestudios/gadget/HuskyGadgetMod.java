package io.github.vampirestudios.gadget;

import io.github.vampirestudios.gadget.init.*;
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
import io.github.vampirestudios.gadget.entity.EntitySeat;
import io.github.vampirestudios.gadget.event.BankEvents;
import io.github.vampirestudios.gadget.event.EmailEvents;
import io.github.vampirestudios.gadget.gui.GuiHandler;
import io.github.vampirestudios.gadget.network.PacketHandler;
import io.github.vampirestudios.gadget.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION/*, dependencies = Reference.DEPENDENCE*/)
public class HuskyGadgetMod {

    @Instance(Reference.MOD_ID)
    public static HuskyGadgetMod instance;
    public static boolean hasAllApps = false, isInsider = false, isDeveloper = false, isAdmin = false, isServerAdmin = false, isArtist = true, likesSocialMedias = true;
    public static final RemoteClassLoader remoteClassLoader = new RemoteClassLoader(HuskyGadgetMod.class.getClassLoader());

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs deviceBlocks = new DeviceTab("gadgetBlocks");
    public static CreativeTabs deviceItems = new DeviceTab("gadgetItems");
    public static CreativeTabs deviceDecoration = new DeviceTab("gadgetDecoration");

    private static Logger logger;

    public static Logger logger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        logger = event.getModLog();

        GadgetBlocks.register();
        GadgetItems.register();
        GadgetCrafting.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GadgetTileEntities.register();

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "seat"), EntitySeat.class, "Seat", 0, this, 80, 1, false);

        /* Packet Registering */
        PacketHandler.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new EmailEvents());
        MinecraftForge.EVENT_BUS.register(new BankEvents());

        GadgetApps.init();
        GadgetTasks.register();

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
