package io.github.vampirestudios.gadget.programs.system.object;


import io.github.vampirestudios.gadget.api.AppInfo;

/**
 * Author: MrCrayfish
 */
public class LocalAppEntry implements AppEntry
{
    private AppInfo info;

    public LocalAppEntry(AppInfo info)
    {
        this.info = info;
    }

    @Override
    public String getId()
    {
        return info.getId().toString();
    }

    @Override
    public String getName()
    {
        return info.getName();
    }

    @Override
    public String getAuthor()
    {
        return info.getAuthor();
    }

    @Override
    public String getDescription()
    {
        return info.getDescription();
    }

    @Override
    public String getVersion()
    {
        return info.getVersion();
    }

    @Override
    public String getIcon()
    {
        return info.getIcon();
    }

    @Override
    public String[] getScreenshots()
    {
        return info.getScreenshots();
    }

    public AppInfo getInfo()
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