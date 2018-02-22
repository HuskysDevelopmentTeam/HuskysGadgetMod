package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.block.*;
import net.thegaminghuskymc.gadgetmod.item.ItemPaper;

import java.util.EnumMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class GadgetBlocks {

    public static Block gaming_chairs;
    public static Block laptops;
    public static Block routers;
    public static Block printers;
    public static Block monitors;
    public static Block ethernet_wall_outlets;
    public static Block robots;
    public static Block gaming_desks;
    public static Block benchmark_stations;
    public static Block external_harddrives;
    public static Block playstation_4_pros;
    public static Block threede_printers;
    public static Block desktops;

    public static final Block PAPER;
    public static final Block SCREEN;
    public static final Block SERVER;
    public static final Block SERVER_TERMINAL;
    public static Block DRAWING_TABLET;
    public static final Block RGB_LIGHTS;
    public static final Block ROOF_LIGHTS;
    public static final Block SECURITY_CAMERA;
    public static final Block SOUNDWOOFERS;
    public static final Block EASTER_EGG;

    static {

        for (EnumDyeColor color : EnumDyeColor.values()) {
            gaming_chairs = new BlockOfficeChair(color);
            laptops = new BlockLaptop(color);
            routers = new BlockRouter(color);
            printers = new BlockPrinter(color);
            monitors = new BlockMonitor(color);
            ethernet_wall_outlets = new BlockEthernetWallOutlet(color);
            robots = new BlockRobot(color);
            gaming_desks = new BlockGamingDesk(color);
            benchmark_stations = new BlockBenchmarkStation(color);
            external_harddrives = new BlockExternalHarddrive(color);
            playstation_4_pros = new BlockPlaystation4Pro(color);
            threede_printers = new Block3DPrinter(color);
            desktops = new BlockDesktop(color);
            DRAWING_TABLET = new BlockDrawingTablet(color);
        }

        PAPER = new BlockPaper();
        SCREEN = new BlockScreen();
        SERVER = new BlockServer();
        SERVER_TERMINAL = new BlockServerTerminal();
        RGB_LIGHTS = new BlockRGBLights();
        ROOF_LIGHTS = new BlockRoofLights();
        SECURITY_CAMERA = new BlockSecurityCamera();
        SOUNDWOOFERS = new BlockSoundwoofers();
        EASTER_EGG = new BlockEasterEgg();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
    }

}