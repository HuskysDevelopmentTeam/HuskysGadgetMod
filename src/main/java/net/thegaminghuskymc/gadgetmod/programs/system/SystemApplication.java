package net.thegaminghuskymc.gadgetmod.programs.system;

import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import javax.annotation.Nullable;

/**
 * Created by Casey on 03-Aug-17.
 */
public abstract class SystemApplication extends Application {

    private Laptop laptop;

    protected SystemApplication() {

    }

    @Nullable
    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(@Nullable Laptop laptop) {
        this.laptop = laptop;
    }

}
