package net.thegaminghuskymc.gadgetmod.client.theme;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.IMetadataSection;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class SimpleResource implements ITheme {

    private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
    private final String resourcePackName;
    private final ThemeLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final MetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;

    public SimpleResource(String resourcePackNameIn, ThemeLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, MetadataSerializer srMetadataSerializerIn)
    {
        this.resourcePackName = resourcePackNameIn;
        this.srResourceLocation = srResourceLocationIn;
        this.resourceInputStream = resourceInputStreamIn;
        this.mcmetaInputStream = mcmetaInputStreamIn;
        this.srMetadataSerializer = srMetadataSerializerIn;
    }

    public ThemeLocation getThemeLocation()
    {
        return this.srResourceLocation;
    }

    public InputStream getInputStream()
    {
        return this.resourceInputStream;
    }

    public boolean hasMetadata()
    {
        return this.mcmetaInputStream != null;
    }

    @Nullable
    public <T extends IMetadataSection> T getMetadata(String sectionName)
    {
        if (!this.hasMetadata())
        {
            return (T)null;
        }
        else
        {
            if (this.mcmetaJson == null && !this.mcmetaJsonChecked)
            {
                this.mcmetaJsonChecked = true;
                BufferedReader bufferedreader = null;

                try
                {
                    bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream, StandardCharsets.UTF_8));
                    this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
                }
                finally
                {
                    IOUtils.closeQuietly((Reader)bufferedreader);
                }
            }

            T t = (T)this.mapMetadataSections.get(sectionName);

            if (t == null)
            {
                t = this.srMetadataSerializer.parseMetadataSection(sectionName, this.mcmetaJson);
            }

            return t;
        }
    }

    public String getThemePackName()
    {
        return this.resourcePackName;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof SimpleResource))
        {
            return false;
        }
        else
        {
            SimpleResource simpleresource = (SimpleResource)p_equals_1_;

            if (this.srResourceLocation != null)
            {
                if (!this.srResourceLocation.equals(simpleresource.srResourceLocation))
                {
                    return false;
                }
            }
            else if (simpleresource.srResourceLocation != null)
            {
                return false;
            }

            if (this.resourcePackName != null)
            {
                if (!this.resourcePackName.equals(simpleresource.resourcePackName))
                {
                    return false;
                }
            }
            else if (simpleresource.resourcePackName != null)
            {
                return false;
            }

            return true;
        }
    }

    public int hashCode()
    {
        int i = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
        i = 31 * i + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
        return i;
    }

    public void close() throws IOException
    {
        this.resourceInputStream.close();

        if (this.mcmetaInputStream != null)
        {
            this.mcmetaInputStream.close();
        }
    }
}