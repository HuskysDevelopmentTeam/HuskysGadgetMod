package net.thegaminghuskymc.gadgetmod.init;

import net.husky.device.Reference;
import net.husky.device.tileentity.TileEntityLaptop;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DeviceTileEntites {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntityLaptop.class, Reference.MOD_ID + "laptop");
    }
}
