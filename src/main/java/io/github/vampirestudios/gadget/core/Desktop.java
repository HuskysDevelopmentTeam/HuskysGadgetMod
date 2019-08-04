package io.github.vampirestudios.gadget.core;

import io.github.vampirestudios.gadget.core.operation_systems.NeonOS;
import io.github.vampirestudios.gadget.tileentity.TileEntityDesktop;

public class Desktop extends BaseDevice {

    private static Desktop instance = new Desktop();

    private static final TaskBar taskBar = new TaskBar(instance);

    public Desktop() {
        super(new TileEntityDesktop(),1, new NeonOS(taskBar));
    }

}
