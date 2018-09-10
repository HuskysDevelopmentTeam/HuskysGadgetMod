package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOSConsole;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBaseDevice;

public class Console extends BaseDevice {

    public Console() {
        super(new TileEntityBaseDevice("Console", false), 3, new NeonOSConsole());
    }

}
