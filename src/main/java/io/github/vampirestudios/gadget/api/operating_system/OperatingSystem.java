package io.github.vampirestudios.gadget.api.operating_system;

import io.github.vampirestudios.gadget.core.TaskBar;

public interface OperatingSystem {

    String name();

    String version();

    TaskBar taskBar();

    int ram();

    int storage();

}
