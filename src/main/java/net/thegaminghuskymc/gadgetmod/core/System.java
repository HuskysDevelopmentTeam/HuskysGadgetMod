package net.thegaminghuskymc.gadgetmod.core;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.core.OSLayouts.LayoutStartMenu;

/**
 * Created by Casey on 07-Aug-17.
 */
public interface System {
    void openContext(Layout layout, int x, int y);

    boolean hasContext();

    void closeContext();

    /**
     * Gets the system settings
     *
     * @return the system settings
     */
    Settings getSettings();

    Layout getContext();

}
