package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thegaminghuskymc.gadgetmod.block.*;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GadgetBlocks {

    public static BlockOfficeChair[] gaming_chairs = new BlockOfficeChair[16];
    public static BlockLaptop[] laptops = new BlockLaptop[16];
    public static BlockRouter[] routers = new BlockRouter[16];
    public static BlockPrinter[] printers = new BlockPrinter[16];
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

    // Needed but boring stuff
    public static final Block paper, screen;

    // Server Stuff
    public static final Block server, serverRack, serverTerminal;

    // Lights
    public static final Block rgbLights, roofLights;

    // Fun Stuff
    public static final Block easter_egg;

    // Other computer stuff
    private static final Block desktopCase;

    // Security Stuff
    private static final Block securityFence, securityGate, cardScanner, securityLaser, securityCamera;

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
//            benchmark_stations[color.getMetadata()] = new BlockBenchmarkStation(color);
            external_harddrives[color.getMetadata()] = new BlockExternalHarddrive(color);
//            playstation_4_pros[color.getMetadata()] = new BlockPlaystation4Pro(color);
//            threede_printers[color.getMetadata()] = new Block3DPrinter(color);
            desktops[color.getMetadata()] = new BlockDesktop(color);
//            drawing_tablets[color.getMetadata()] = new BlockDrawingTablet(color);
        }

        paper = new BlockPaper();
        screen = new BlockScreen();
        server = new BlockServer();
        serverRack = new BlockServerRack();
        serverTerminal = new BlockServerTerminal();
        rgbLights = new BlockRGBLights();
        roofLights = new BlockRoofLights();
        securityCamera = new BlockSecurityCamera();
        easter_egg = new BlockEasterEgg();
        securityFence = new BlockElectricSecurityFence();
        securityGate = new BlockElectricSecurityGate();
        cardScanner = new BlockCardScanner();
        securityLaser = new BlockSecurityLaser();
        desktopCase = new BlockDesktopCase();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

}