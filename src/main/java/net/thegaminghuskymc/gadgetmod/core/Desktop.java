package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOS;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBaseDevice;

public class Desktop extends BaseDevice {

    public Desktop() {
        super(new TileEntityBaseDevice("Desktop"),1, new NeonOS());
    }

}
