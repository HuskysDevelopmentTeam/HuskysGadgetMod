package io.github.vampirestudios.gadget.programs.system;

import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.core.BaseDevice;

import javax.annotation.Nullable;

/**
 * Created by Casey on 03-Aug-17.
 */
public abstract class SystemApplication extends Application {

    private BaseDevice laptop;

    protected SystemApplication() {

    }

    @Nullable
    public BaseDevice getLaptop() {
        return laptop;
    }

    public void setLaptop(@Nullable BaseDevice laptop) {
        this.laptop = laptop;
    }

}
