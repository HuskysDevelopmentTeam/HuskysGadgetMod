package net.thegaminghuskymc.gadgetmod.init;

import net.thegaminghuskymc.gadgetmod.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GadgetTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntityLaptop.class, "hgm:laptop");
        GameRegistry.registerTileEntity(TileEntityRouter.class, "hgm:router");
        GameRegistry.registerTileEntity(TileEntityPrinter.class, "hgm:printer");
        GameRegistry.registerTileEntity(TileEntityPaper.class, "hgm:printed_paper");
        GameRegistry.registerTileEntity(TileEntityDesktop.class, "hgm:desktop");
        GameRegistry.registerTileEntity(TileEntityEthernetWallOutlet.class, "hgm:ethernet_wall_outlet");
        GameRegistry.registerTileEntity(TileEntityMonitor.class, "hgm:monitor");
        GameRegistry.registerTileEntity(TileEntityRobot.class, "hgm:robot");
        GameRegistry.registerTileEntity(TileEntityScreen.class, "hgm:screen");
        GameRegistry.registerTileEntity(TileEntityServer.class, "hgm:server");
        GameRegistry.registerTileEntity(TileEntityServerTerminal.class, "hgm:server_terminal");
        GameRegistry.registerTileEntity(TileEntityOfficeChair.class, "hgm:office_chair");
    }
}
