package net.thegaminghuskymc.gadgetmod.programs.system.object;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class RemoteEntry implements AppEntry
{
    public String app_id;
    public String app_name;
    public String app_author;
    public String app_description;
    public int app_screenshots;
    public String project_id;

    @Override
    public String getId()
    {
        return app_id;
    }

    @Override
    public String getName()
    {
        return app_name;
    }

    @Override
    public String getAuthor()
    {
        return app_author;
    }

    @Override
    public String getDescription()
    {
        return app_description;
    }

    @Override
    @Nullable
    public String getVersion()
    {
        return null;
    }

    @Override
    public String getIcon()
    {
        return null;
    }

    @Override
    public String[] getScreenshots()
    {
        return null;
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