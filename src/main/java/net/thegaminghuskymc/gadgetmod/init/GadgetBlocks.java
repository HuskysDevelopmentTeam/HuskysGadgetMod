package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.thegaminghuskymc.gadgetmod.block.*;
import net.thegaminghuskymc.gadgetmod.item.ItemLaptop;
import net.thegaminghuskymc.gadgetmod.item.ItemPaper;

public class GadgetBlocks {

    public static final Block LAPTOP;
    public static final Block ROUTER;
    public static final Block PRINTER;
    public static final Block PAPER;
    public static final Block SCREEN;
    public static Block TV;
    public static Block MONITOR;
    public static Block DESKTOP;
    public static Block SERVER;
    public static Block SERVER_TERMINAL;
    public static Block ROBOT;
    public static Block ETHERNET_WALL_OUTLET;

    static
    {
        LAPTOP = new BlockLaptop();
        ROUTER = new BlockRouter();
        PRINTER = new BlockPrinter();
        PAPER = new BlockPaper();
        SCREEN = new BlockScreen();
        MONITOR = new BlockMonitor();
        DESKTOP = new BlockDesktop();
        SERVER = new BlockServer();
        SERVER_TERMINAL = new BlockServerTerminal();
        ROBOT = new BlockRobot();
        ETHERNET_WALL_OUTLET = new BlockEthernetWallOutlet();
    }

    public static void register()
    {
        registerBlock(LAPTOP, new ItemLaptop(LAPTOP));
        registerBlock(ROUTER);
        registerBlock(PRINTER);
        registerBlock(PAPER, new ItemPaper(PAPER));
        registerBlock(SCREEN);
        registerBlock(MONITOR);
        registerBlock(DESKTOP);
        registerBlock(SERVER);
        registerBlock(SERVER_TERMINAL);
        registerBlock(ROBOT);
        registerBlock(ETHERNET_WALL_OUTLET);
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