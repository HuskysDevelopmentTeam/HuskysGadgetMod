package net.thegaminghuskymc.gadgetmod.api.app;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.AppInfo;
import net.thegaminghuskymc.gadgetmod.core.Settings;

import javax.annotation.Nullable;
import java.util.Collection;

public interface System
{
    /**
     * Open a context on the screen
     */
    void openContext(Layout layout, int x, int y);

    /**
     * Checks if the system has a context open
     *
     * @return has a context open
     */
    boolean hasContext();

    /**
     * Closes the current context on screen
     */
    void closeContext();

    /**
     * Gets the system settings
     *
     * @return the system settings
     */
    Settings getSettings();

    void openApplication(AppInfo info);

    void openApplication(AppInfo info, @Nullable NBTTagCompound intentTag);

    void closeApplication(AppInfo info);

    Collection<AppInfo> getInstalledApplications();
}