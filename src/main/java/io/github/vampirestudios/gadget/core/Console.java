package io.github.vampirestudios.gadget.core;

import io.github.vampirestudios.gadget.core.operation_systems.NeonOSConsole;
import io.github.vampirestudios.gadget.tileentity.TileEntityBaseDevice;

public class Console extends BaseDevice {

    public Console() {
        super(new TileEntityBaseDevice("Console", false), 3, new NeonOSConsole());
    }

}
