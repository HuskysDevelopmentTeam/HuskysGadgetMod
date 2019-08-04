package io.github.vampirestudios.gadget.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import io.github.vampirestudios.gadget.tileentity.*;

public class GadgetTileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(TileEntityLaptop.class, "hgm:laptop");
        GameRegistry.registerTileEntity(TileEntityBaseDevice.class, "hgm:base_device");
        GameRegistry.registerTileEntity(TileEntityRouter.class, "hgm:router");
        GameRegistry.registerTileEntity(TileEntityPrinter.class, "hgm:printer");
        GameRegistry.registerTileEntity(TileEntityPaper.class, "hgm:printed_paper");
        GameRegistry.registerTileEntity(TileEntityDesktop.class, "hgm:desktop");
        GameRegistry.registerTileEntity(TileEntityEthernetWallOutlet.class, "hgm:ethernet_wall_outlet");
        GameRegistry.registerTileEntity(TileEntityMonitor.class, "hgm:monitor");
        GameRegistry.registerTileEntity(TileEntityRobot.class, "hgm:robot");
        GameRegistry.registerTileEntity(TileEntityScreen.class, "hgm:screen");
        GameRegistry.registerTileEntity(TileEntityServer.class, "hgm:server");
        GameRegistry.registerTileEntity(TileEntityServerRack.class, "hgm:server_rack");
        GameRegistry.registerTileEntity(TileEntityServerTerminal.class, "hgm:server_terminal");
        GameRegistry.registerTileEntity(TileEntityOfficeChair.class, "hgm:office_chair");

        GameRegistry.registerTileEntity(TileEntity3DPrinter.class, "hgm:3d_printer");
        GameRegistry.registerTileEntity(TileEntityBenchmarkStation.class, "hgm:benchmark_station");
        GameRegistry.registerTileEntity(TileEntityDrawingTablet.class, "hgm:drawing_tablet");
        GameRegistry.registerTileEntity(TileEntityMechanicalKeyboard.class, "hgm:mechanical_keyboard");
        GameRegistry.registerTileEntity(TileEntityRGBLights.class, "hgm:rgb_lights");
        GameRegistry.registerTileEntity(TileEntityRoofLights.class, "hgm:roof_lights");
        GameRegistry.registerTileEntity(TileEntitySecurityCamera.class, "hgm:security_camera");
        GameRegistry.registerTileEntity(TileEntitySoundwoofers.class, "hgm:soundwoofers");
        GameRegistry.registerTileEntity(TileEntityPlaystation4Pro.class, "hgm:playstation_4_pro");
        GameRegistry.registerTileEntity(TileEntityExternalHarddrive.class, "hgm:external_harddrive");
        GameRegistry.registerTileEntity(TileEntityEasterEgg.class, "hgm:easter_egg");
        GameRegistry.registerTileEntity(TileEntityGamingDesk.class, "hgm:gaming_desk");
    }

}
