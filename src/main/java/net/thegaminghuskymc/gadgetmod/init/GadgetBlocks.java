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

    public static BlockOfficeChair[] gaming_chairs = new BlockOfficeChair[16];
    public static Block[] laptops = new Block[16];
    public static Block[] routers = new Block[16];
    public static Block[] printers = new Block[16];
    public static Block[] monitors = new Block[16];
    public static Block[] ethernet_wall_outlets = new Block[16];
    public static Block[] robots = new Block[16];
    public static Block[] gaming_desks = new Block[16];
    public static Block[] benchmark_stations = new Block[16];
    public static Block[] external_harddrives = new Block[16];
    public static Block[] playstation_4_pros = new Block[16];
    public static Block[] threede_printers = new Block[16];
    public static Block[] desktops = new Block[16];
    public static Block[] drawing_tablets = new Block[16];

    public static final Block PAPER;
    public static final Block SCREEN;
    public static final Block server, serverRack, serverTerminal;
    public static final Block RGB_LIGHTS;
    public static final Block ROOF_LIGHTS;
    public static final Block SECURITY_CAMERA;
    public static final Block SOUNDWOOFERS;
    public static final Block EASTER_EGG;
    public static final Block securityFence, securityGate, cardScanner, securityLaser;

    static {

        for (EnumDyeColor color : EnumDyeColor.values()) {
            gaming_chairs[color.getMetadata()] = new BlockOfficeChair(color);
            laptops[color.getMetadata()] = new BlockLaptop(color);
            routers[color.getMetadata()] = new BlockRouter(color);
            printers[color.getMetadata()] = new BlockPrinter(color);
            monitors[color.getMetadata()] = new BlockMonitor(color);
            ethernet_wall_outlets[color.getMetadata()] = new BlockEthernetWallOutlet(color);
            robots[color.getMetadata()] = new BlockRobot(color);
            gaming_desks[color.getMetadata()] = new BlockGamingDesk(color);
            benchmark_stations[color.getMetadata()] = new BlockBenchmarkStation(color);
            external_harddrives[color.getMetadata()] = new BlockExternalHarddrive(color);
            playstation_4_pros[color.getMetadata()] = new BlockPlaystation4Pro(color);
            threede_printers[color.getMetadata()] = new Block3DPrinter(color);
            desktops[color.getMetadata()] = new BlockDesktop(color);
            drawing_tablets[color.getMetadata()] = new BlockDrawingTablet(color);
        }

        PAPER = new BlockPaper();
        SCREEN = new BlockScreen();
        server = new BlockServer();
        serverRack = new BlockServerRack();
        serverTerminal = new BlockServerTerminal();
        RGB_LIGHTS = new BlockRGBLights();
        ROOF_LIGHTS = new BlockRoofLights();
        SECURITY_CAMERA = new BlockSecurityCamera();
        SOUNDWOOFERS = new BlockSoundwoofers();
        EASTER_EGG = new BlockEasterEgg();
        securityFence = new BlockElectricSecurityFence();
        securityGate = new BlockElectricSecurityGate();
        cardScanner = new BlockCardScanner();
        securityLaser = new BlockSecurityLaser();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
    }

}