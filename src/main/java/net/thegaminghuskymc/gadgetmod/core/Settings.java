package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;

public class Settings {

    private static boolean showAllApps = false;

    private ColourScheme colourScheme = new ColourScheme();

    private String hasWallpaperOrColor;

    public static boolean isShowAllApps() {
        return Settings.showAllApps;
    }

    public static void setShowAllApps(boolean showAllApps) {
        Settings.showAllApps = showAllApps;
    }

    public String hasWallpaperOrColor() {
        return hasWallpaperOrColor;
    }

    public void setHasWallpaperOrColor(String hasWallpaperOrColor) {
        this.hasWallpaperOrColor = hasWallpaperOrColor;
    }

    public static Settings fromTag(NBTTagCompound tag) {
        showAllApps = tag.getBoolean("showAllApps");

        Settings settings = new Settings();
        settings.colourScheme = ColourScheme.fromTag(tag.getCompoundTag("colourScheme"));
        settings.hasWallpaperOrColor = "Wallpaper";
        return settings;
    }

    public ColourScheme getColourScheme() {
        return colourScheme;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("showAllApps", showAllApps);
        tag.setTag("colourScheme", colourScheme.toTag());
        tag.setString("wallpaperOrColor", hasWallpaperOrColor);
        return tag;
    }
}