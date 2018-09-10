package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOS;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDesktop;

public class Desktop extends BaseDevice {

    private static Desktop instance = new Desktop();

    private static final TaskBar taskBar = new TaskBar(instance);

    public Desktop() {
        super(new TileEntityDesktop(),1, new NeonOS(taskBar));
    }

}
