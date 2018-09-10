package net.thegaminghuskymc.gadgetmod.core.operation_systems;

import net.thegaminghuskymc.gadgetmod.api.operating_system.OperatingSystem;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.TaskBar;
import net.thegaminghuskymc.gadgetmod.core.operation_systems.core_os.OSInfo;

public class PixelOSConsole implements OperatingSystem {

    @Override
    public String name() {
        return "PixelOS Console";
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