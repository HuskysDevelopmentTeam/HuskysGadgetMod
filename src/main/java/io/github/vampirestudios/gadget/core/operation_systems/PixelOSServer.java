package io.github.vampirestudios.gadget.core.operation_systems;

import io.github.vampirestudios.gadget.api.operating_system.OperatingSystem;
import io.github.vampirestudios.gadget.core.Laptop;
import io.github.vampirestudios.gadget.core.TaskBar;
import io.github.vampirestudios.gadget.core.operation_systems.core_os.OSInfo;

public class PixelOSServer implements OperatingSystem {

    @Override
    public String name() {
        return "PixelOS Server";
    }

    @Override
    public String version() {
        return "0.0.1";
    }

    @Override
    public TaskBar taskBar() {
        Laptop laptop = new Laptop();
        return laptop.getTaskBar();
    }

    @Override
    public int ram() {
        return OSInfo.BASIC_RAM;
    }

    @Override
    public int storage() {
        return OSInfo.BASIC_STORAGE;
    }

}