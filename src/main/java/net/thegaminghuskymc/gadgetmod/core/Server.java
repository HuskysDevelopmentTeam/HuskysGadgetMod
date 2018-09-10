package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOSServer;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityServer;

public class Server extends BaseDevice {

    public Server() {
        super(new TileEntityServer(), 2, new NeonOSServer());
    }

}
