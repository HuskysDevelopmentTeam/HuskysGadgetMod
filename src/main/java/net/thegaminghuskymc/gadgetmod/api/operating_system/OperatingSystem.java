package net.thegaminghuskymc.gadgetmod.api.operating_system;

import net.thegaminghuskymc.gadgetmod.core.TaskBar;

public interface OperatingSystem {

    String getOSName();

    String getOSVersion();

    TaskBar getTaskBar();

}
