package net.thegaminghuskymc.gadgetmod.core.operation_systems;

import net.thegaminghuskymc.gadgetmod.api.operating_system.OperatingSystem;
import net.thegaminghuskymc.gadgetmod.core.TaskBar;
import net.thegaminghuskymc.gadgetmod.core.operation_systems.core_os.OSInfo;

public class PixelOS implements OperatingSystem {

    private TaskBar taskBar;

    public PixelOS(TaskBar taskBar) {
        this.taskBar = taskBar;
    }

    @Override
    public String name() {
        return "PixelOS";
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