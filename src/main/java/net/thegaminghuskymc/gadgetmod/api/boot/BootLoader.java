package net.thegaminghuskymc.gadgetmod.api.boot;

import net.thegaminghuskymc.gadgetmod.api.boot.interfaces.BootStages;

public class BootLoader {

    public BootStages getBootStage() {
        return BootStages.POST;
    }

}
