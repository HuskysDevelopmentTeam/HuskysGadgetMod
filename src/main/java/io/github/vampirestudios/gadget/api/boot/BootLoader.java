package io.github.vampirestudios.gadget.api.boot;

import io.github.vampirestudios.gadget.api.boot.interfaces.BootStages;

public class BootLoader {

    public BootStages getBootStage() {
        return BootStages.POST;
    }

}
