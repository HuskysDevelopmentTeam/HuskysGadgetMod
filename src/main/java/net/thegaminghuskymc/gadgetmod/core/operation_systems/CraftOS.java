package net.thegaminghuskymc.gadgetmod.core.operation_systems;

import net.thegaminghuskymc.gadgetmod.api.operating_system.OperatingSystem;
import net.thegaminghuskymc.gadgetmod.core.TaskBar;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

public class CraftOS implements OperatingSystem {

    @Override
    public String getOSName() {
        return "CraftOS";
    }

    @Override
    public String getOSVersion() {
        return "0.0.1";
    }

    @Override
    public TaskBar getTaskBar() {
        Laptop laptop = new Laptop();
        return laptop.getTaskBar();
    }

}
