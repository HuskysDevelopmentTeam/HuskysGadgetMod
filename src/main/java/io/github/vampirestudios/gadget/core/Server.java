package io.github.vampirestudios.gadget.core;

import io.github.vampirestudios.gadget.core.operation_systems.NeonOSServer;
import io.github.vampirestudios.gadget.tileentity.TileEntityServer;

public class Server extends BaseDevice {

    public Server() {
        super(new TileEntityServer(), 2, new NeonOSServer());
    }

}
