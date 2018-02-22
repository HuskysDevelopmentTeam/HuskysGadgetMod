package net.thegaminghuskymc.gadgetmod.api.app.listener;

/**
 * The change listener interface. Used for handling value
 * changing in components
 */
public interface ChangeListener<T> {
    /**
     * Called when the value is changed
     *
     * @param oldValue the old value
     * @param newValue the new value
     */
    void onChange(T oldValue, T newValue);
}
