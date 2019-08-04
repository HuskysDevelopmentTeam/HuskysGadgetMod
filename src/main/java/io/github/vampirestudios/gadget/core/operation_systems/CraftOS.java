package io.github.vampirestudios.gadget.core.operation_systems;

import io.github.vampirestudios.gadget.api.operating_system.OperatingSystem;
import io.github.vampirestudios.gadget.core.TaskBar;
import io.github.vampirestudios.gadget.core.operation_systems.core_os.OSInfo;

public class CraftOS implements OperatingSystem {

    private TaskBar taskBar;

    public CraftOS(TaskBar taskBar) {
        this.taskBar = taskBar;
    }

    @Override
    public String name() {
        return "CraftOS";
    }

    @Override
    public String version() {
        return "0.0.1";
    }

    @Override
    public TaskBar taskBar() {
        return taskBar;
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
