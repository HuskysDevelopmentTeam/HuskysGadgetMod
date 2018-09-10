package net.thegaminghuskymc.gadgetmod.api.operating_system;

import net.thegaminghuskymc.gadgetmod.core.TaskBar;

public interface OperatingSystem {

    String name();

    String version();

    TaskBar taskBar();

    int ram();

    int storage();

}
