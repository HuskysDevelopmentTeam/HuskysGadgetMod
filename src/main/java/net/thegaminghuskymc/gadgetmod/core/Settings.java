package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.programs.system.object.ColourScheme;

public class Settings {

    private static boolean showAllApps = false;

    private ColourScheme colourScheme = new ColourScheme();

    public static boolean isShowAllApps() {
        return Settings.showAllApps;
    }

    public static Settings fromTag(NBTTagCompound tag) {
        showAllApps = tag.getBoolean("showAllApps");

        Settings settings = new Settings();
        settings.colourScheme = ColourScheme.fromTag(tag.getCompoundTag("colourScheme"));
        return settings;
    }

    public ColourScheme getColourScheme() {
        return colourScheme;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("showAllApps", showAllApps);
        tag.setTag("colourScheme", colourScheme.toTag());
        return tag;
    }
}