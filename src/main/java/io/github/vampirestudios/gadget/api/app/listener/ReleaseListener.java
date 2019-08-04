package io.github.vampirestudios.gadget.api.app.listener;

import io.github.vampirestudios.gadget.api.app.Component;

/**
 * The release listener interface. Used for handling releasing
 * clicks on components.
 */
public interface ReleaseListener {
    /**
     * Called when a click on a component is released
     *
     * @param mouseButton the mouse button used to click
     */
    void onRelease(int mouseX, int mouseY, int mouseButton);
}
