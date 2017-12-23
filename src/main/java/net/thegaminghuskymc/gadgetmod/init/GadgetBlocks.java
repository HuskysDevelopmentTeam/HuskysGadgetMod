package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.thegaminghuskymc.gadgetmod.block.*;
import net.thegaminghuskymc.gadgetmod.item.ItemColorable;
import net.thegaminghuskymc.gadgetmod.item.ItemDevice;
import net.thegaminghuskymc.gadgetmod.item.ItemPaper;

public class GadgetBlocks {

    public static final Block LAPTOP;
    public static final Block ROUTER;
    public static final Block PRINTER;
    public static final Block PAPER;
    public static final Block SCREEN;
    public static final Block OFFICE_CHAIR;
    public static Block TV;
    public static final Block MONITOR;
    public static final Block DESKTOP;
    public static final Block SERVER;
    public static final Block SERVER_TERMINAL;
    public static final Block ROBOT;
    public static final Block ETHERNET_WALL_OUTLET;
    public static final Block GAMING_DESK;

    static
    {
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
    }

    public static void register()
    {
        registerBlock(LAPTOP, new ItemDevice(LAPTOP));
        registerBlock(ROUTER, new ItemDevice(ROUTER));
        registerBlock(PRINTER, new ItemDevice(PRINTER));
        registerBlock(PAPER, new ItemPaper(PAPER));
        registerBlock(SCREEN, new ItemDevice(SCREEN));
        registerBlock(MONITOR, new ItemDevice(MONITOR));
        registerBlock(DESKTOP, new ItemDevice(DESKTOP));
        registerBlock(SERVER, new ItemDevice(SERVER));
        registerBlock(SERVER_TERMINAL, new ItemDevice(SERVER_TERMINAL));
        registerBlock(ROBOT, new ItemDevice(ROBOT));
        registerBlock(ETHERNET_WALL_OUTLET, new ItemColorable(ETHERNET_WALL_OUTLET));
        registerBlock(OFFICE_CHAIR, new ItemColorable(OFFICE_CHAIR));
        registerBlock(GAMING_DESK, new ItemColorable(GAMING_DESK));
    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }

}