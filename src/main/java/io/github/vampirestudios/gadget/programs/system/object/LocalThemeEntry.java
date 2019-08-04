package io.github.vampirestudios.gadget.programs.system.object;

import io.github.vampirestudios.gadget.object.ThemeInfo;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class LocalThemeEntry implements ThemeEntry
{
    private ThemeInfo info;

    public LocalThemeEntry(ThemeInfo info)
    {
        this.info = info;
    }

    @Override
    public String getName()
    {
        return info.getName();
    }

    @Override
    public String getCreator() {
        return info.getCreator();
    }

    @Override
    public String getDescription()
    {
        return info.getDescription();
    }

    @Nullable
    @Override
    public String getThemeVersion() {
        return info.getVersion();
    }

    @Nullable
    @Override
    public String getThemePreview() {
        return info.getIcon();
    }

    @Override
    public String getId() {
        return info.getId().toString();
    }

    @Override
    public String[] getScreenshots()
    {
        return info.getScreenshots();
    }

    public ThemeInfo getInfo()
    {
        return info;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof AppEntry)
        {
            return ((AppEntry) obj).getId().equals(getId());
        }
        return false;
    }
}