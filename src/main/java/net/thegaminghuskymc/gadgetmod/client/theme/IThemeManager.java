package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public interface IThemeManager {

    Set<String> getThemeDomains();

    ITheme getTheme(ThemeLocation location) throws IOException;

    /**
     * Gets all versions of the resource identified by {@code location}. The list is ordered by resource pack priority
     * from lowest to highest.
     */
    List<ITheme> getAllThemes(ThemeLocation location) throws IOException;
}