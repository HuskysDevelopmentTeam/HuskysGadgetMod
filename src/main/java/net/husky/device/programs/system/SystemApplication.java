package net.husky.device.programs.system;

import net.husky.device.api.app.Application;
import net.husky.device.core.Laptop;

import javax.annotation.Nullable;

/**
 * Created by Casey on 03-Aug-17.
 */
public abstract class SystemApplication extends Application
{
    private Laptop laptop;

    SystemApplication() {}

    public void setLaptop(@Nullable Laptop laptop)
    {
        this.laptop = laptop;
    }

    @Nullable
    public Laptop getLaptop()
    {
        return laptop;
    }
}
