package net.thegaminghuskymc.gadgetmod.programs.system;

import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

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
