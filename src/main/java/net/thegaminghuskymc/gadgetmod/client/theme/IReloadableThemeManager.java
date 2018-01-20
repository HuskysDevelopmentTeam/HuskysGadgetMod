package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public interface IReloadableThemeManager extends IThemeManager {

    /**
     * Releases all current theme packs, loads the given list, then triggers all listeners
     */
    void reloadResources(List<IThemePack> resourcesPacksList);

    /**
     * Registers a listener to be invoked every time the resource manager reloads. NOTE: The listener is immediately
     * invoked once when it is registered.
     */
    void registerReloadListener(IThemeManagerReloadListener reloadListener);
}