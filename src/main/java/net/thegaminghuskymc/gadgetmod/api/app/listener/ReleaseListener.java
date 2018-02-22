package net.thegaminghuskymc.gadgetmod.api.app.listener;

import net.thegaminghuskymc.gadgetmod.api.app.Component;

/**
 * The release listener interface. Used for handling releasing
 * clicks on components.
 */
public interface ReleaseListener {
    /**
     * Called when a click on a component is released
     *
     * @param c           the component that was clicked
     * @param mouseButton the mouse button used to click
     */
    void onRelease(Component c, int mouseButton);
}
