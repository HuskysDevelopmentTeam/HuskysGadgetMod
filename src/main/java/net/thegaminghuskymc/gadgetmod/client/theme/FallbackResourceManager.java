package net.thegaminghuskymc.gadgetmod.client.theme;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class FallbackResourceManager implements IThemeManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * All resource packs for this domain, as ordered by the options menu. Packs later in the list have higher priority
     * than those earlier in the list.
     */
    protected final List<IThemePack> resourcePacks = Lists.newArrayList();
    private final MetadataSerializer frmMetadataSerializer;

    public FallbackResourceManager(MetadataSerializer frmMetadataSerializerIn)
    {
        this.frmMetadataSerializer = frmMetadataSerializerIn;
    }

    public void addThemePack(IThemePack resourcePack)
    {
        this.resourcePacks.add(resourcePack);
    }

    public Set<String> getThemeDomains()
    {
        return Collections.emptySet();
    }

    public ITheme getTheme(ThemeLocation location) throws IOException
    {
        this.checkThemePath(location);
        IThemePack iresourcepack = null;
        ThemeLocation resourcelocation = getLocationMcmeta(location);

        for (int i = this.resourcePacks.size() - 1; i >= 0; --i)
        {
            IThemePack iresourcepack1 = this.resourcePacks.get(i);

            if (iresourcepack == null && iresourcepack1.themeExists(resourcelocation))
            {
                iresourcepack = iresourcepack1;
            }

            if (iresourcepack1.themeExists(location))
            {
                InputStream inputstream = null;

                if (iresourcepack != null)
                {
                    inputstream = this.getInputStream(resourcelocation, iresourcepack);
                }

                return new SimpleResource(iresourcepack1.getPackName(), location, this.getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
            }
        }

        throw new FileNotFoundException(location.toString());
    }

    @SuppressWarnings("resource")
    protected InputStream getInputStream(ThemeLocation location, IThemePack resourcePack) throws IOException
    {
        InputStream inputstream = resourcePack.getInputStream(location);
        return (LOGGER.isDebugEnabled() ? new FallbackResourceManager.InputStreamLeakedResourceLogger(inputstream, location, resourcePack.getPackName()) : inputstream);
    }

    private void checkThemePath(ThemeLocation p_188552_1_) throws IOException
    {
        if (p_188552_1_.getThemePath().contains(".."))
        {
            throw new IOException("Invalid relative path to resource: " + p_188552_1_);
        }
    }

    /**
     * Gets all versions of the resource identified by {@code location}. The list is ordered by resource pack priority
     * from lowest to highest.
     */
    public List<ITheme> getAllThemes(ThemeLocation location) throws IOException
    {
        this.checkThemePath(location);
        List<ITheme> list = Lists.newArrayList();
        ThemeLocation resourcelocation = getLocationMcmeta(location);

        for (IThemePack iresourcepack : this.resourcePacks)
        {
            if (iresourcepack.themeExists(location))
            {
                InputStream inputstream = iresourcepack.themeExists(resourcelocation) ? this.getInputStream(resourcelocation, iresourcepack) : null;
                list.add(new SimpleResource(iresourcepack.getPackName(), location, this.getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
            }
        }

        if (list.isEmpty())
        {
            throw new FileNotFoundException(location.toString());
        }
        else
        {
            return list;
        }
    }

    static ThemeLocation getLocationMcmeta(ThemeLocation location)
    {
        return new ThemeLocation(location.getThemeDomain(), location.getThemePath() + ".mcmeta");
    }

    @SideOnly(Side.CLIENT)
    static class InputStreamLeakedResourceLogger extends InputStream
        {
            private final InputStream inputStream;
            private final String message;
            private boolean isClosed;

            public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ThemeLocation location, String resourcePack)
            {
                this.inputStream = p_i46093_1_;
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
                this.message = "Leaked theme: '" + location + "' loaded from pack: '" + resourcePack + "'\n" + bytearrayoutputstream;
            }

            public void close() throws IOException
            {
                this.inputStream.close();
                this.isClosed = true;
            }

            protected void finalize() throws Throwable
            {
                if (!this.isClosed)
                {
                    FallbackResourceManager.LOGGER.warn(this.message);
                }

                super.finalize();
            }

            public int read() throws IOException
            {
                return this.inputStream.read();
            }
        }
}