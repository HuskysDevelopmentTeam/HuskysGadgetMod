package net.husky.device.programs.system;

import net.husky.device.api.app.Application;
import net.husky.device.core.Laptop;
import net.husky.device.core.NeonOS;

import javax.annotation.Nullable;

/**
 * Created by Casey on 03-Aug-17.
 */
public abstract class SystemApplication extends Application
{
    private NeonOS OS;

    SystemApplication() {}

    public NeonOS getOS() {
        return OS;
    }

    public void setOS(NeonOS OS) {
        this.OS = OS;
    }
}
