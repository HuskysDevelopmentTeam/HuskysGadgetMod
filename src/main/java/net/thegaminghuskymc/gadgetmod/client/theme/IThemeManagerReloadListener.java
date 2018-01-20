package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IThemeManagerReloadListener {
    void onThemeManagerReload(IThemeManager resourceManager);
}