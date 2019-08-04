package io.github.vampirestudios.gadget.core;

import io.github.vampirestudios.gadget.core.operation_systems.NeonOS;
import io.github.vampirestudios.gadget.tileentity.TileEntityLaptop;

public class Laptop extends BaseDevice {

    private static Laptop instance = new Laptop();

    public static final TaskBar taskBar = new TaskBar(instance);

    public Laptop() {
        super(new TileEntityLaptop(), 0, new NeonOS(taskBar));
    }

}
