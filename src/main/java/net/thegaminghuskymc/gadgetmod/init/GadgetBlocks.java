package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.thegaminghuskymc.gadgetmod.block.*;
import net.thegaminghuskymc.gadgetmod.item.ItemColoredDevice;
import net.thegaminghuskymc.gadgetmod.item.ItemDevice;
import net.thegaminghuskymc.gadgetmod.item.ItemPaper;

public class GadgetBlocks {

    public static final Block LAPTOP;
    public static final Block ROUTER;
    public static final Block PRINTER;
    public static final Block PAPER;
    public static final Block SCREEN;
    public static final Block OFFICE_CHAIR;
    public static final Block MONITOR;
    public static final Block DESKTOP;
    public static final Block SERVER;
    public static final Block SERVER_TERMINAL;
    public static final Block ROBOT;
    public static final Block ETHERNET_WALL_OUTLET;
    public static final Block GAMING_DESK;
    public static final Block THREEDE_PRINTER;
    public static final Block BENCHMARK_STATION;
    public static final Block DRAWING_TABLET;
    public static final Block RGB_LIGHTS;
    public static final Block ROOF_LIGHTS;
    public static final Block SECURITY_CAMERA;
    public static final Block SOUNDWOOFERS;
    public static final Block PLAYSTATION_4_PRO;
    public static final Block EXTERNAL_HARDDRIVE;
    public static final Block EASTER_EGG;

    static {
        LAPTOP = new BlockLaptop();
        ROUTER = new BlockRouter();
        PRINTER = new BlockPrinter();
        PAPER = new BlockPaper();
        OFFICE_CHAIR = new BlockOfficeChair();
        SCREEN = new BlockScreen();
        MONITOR = new BlockMonitor();
        DESKTOP = new BlockDesktop();
        SERVER = new BlockServer();
        SERVER_TERMINAL = new BlockServerTerminal();
        ROBOT = new BlockRobot();
        ETHERNET_WALL_OUTLET = new BlockEthernetWallOutlet();
        GAMING_DESK = new BlockGamingDesk();
        THREEDE_PRINTER = new Block3DPrinter();
        BENCHMARK_STATION = new BlockBenchmarkStation();
        DRAWING_TABLET = new BlockDrawingTablet();
        RGB_LIGHTS = new BlockRGBLights();
        ROOF_LIGHTS = new BlockRoofLights();
        SECURITY_CAMERA = new BlockSecurityCamera();
        SOUNDWOOFERS = new BlockSoundwoofers();
        PLAYSTATION_4_PRO = new BlockPlaystation4Pro();
        EXTERNAL_HARDDRIVE = new BlockExternalHarddrive();
        EASTER_EGG = new BlockEasterEgg();
    }

    public static void register() {

        registerBlock(LAPTOP, new ItemColoredDevice(LAPTOP));
        registerBlock(ROUTER, new ItemColoredDevice(ROUTER));
        registerBlock(PRINTER, new ItemColoredDevice(PRINTER));
        registerBlock(PAPER, new ItemPaper(PAPER));
        registerBlock(SCREEN, new ItemColoredDevice(SCREEN));
        registerBlock(MONITOR, new ItemColoredDevice(MONITOR));
        registerBlock(DESKTOP, new ItemColoredDevice(DESKTOP));
        registerBlock(SERVER, new ItemDevice(SERVER));
        registerBlock(SERVER_TERMINAL, new ItemDevice(SERVER_TERMINAL));
        registerBlock(ROBOT, new ItemDevice(ROBOT));
        registerBlock(ETHERNET_WALL_OUTLET, new ItemColoredDevice(ETHERNET_WALL_OUTLET));
        registerBlock(OFFICE_CHAIR, new ItemColoredDevice(OFFICE_CHAIR));
        registerBlock(GAMING_DESK, new ItemColoredDevice(GAMING_DESK));
        registerBlock(THREEDE_PRINTER, new ItemColoredDevice(THREEDE_PRINTER));
        registerBlock(BENCHMARK_STATION, new ItemColoredDevice(BENCHMARK_STATION));
        registerBlock(DRAWING_TABLET, new ItemColoredDevice(DRAWING_TABLET));
        registerBlock(RGB_LIGHTS);
        registerBlock(ROOF_LIGHTS);
        registerBlock(PLAYSTATION_4_PRO, new ItemColoredDevice(PLAYSTATION_4_PRO));
        registerBlock(EXTERNAL_HARDDRIVE, new ItemColoredDevice(EXTERNAL_HARDDRIVE));
        registerBlock(EASTER_EGG);
    }

    private static void registerBlock(Block block) {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item) {
        if (block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }

}