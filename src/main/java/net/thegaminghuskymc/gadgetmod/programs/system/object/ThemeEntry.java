package net.thegaminghuskymc.gadgetmod.programs.system.object;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface ThemeEntry {

    String getId();
    String getName();
    String getCreator();
    String getDescription();
    @Nullable String getThemeVersion();
    @Nullable String getThemePreview();
    @Nullable String[] getScreenshots();

}