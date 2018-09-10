package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOS;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;

public class Laptop extends BaseDevice {

    private static Laptop instance = new Laptop();

    public static final TaskBar taskBar = new TaskBar(instance);

    public Laptop() {
        super(new TileEntityLaptop(), 0, new NeonOS(taskBar));
    }

}
