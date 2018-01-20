package net.thegaminghuskymc.gadgetmod.client.theme;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.IMetadataSection;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class DefaultThemePack implements IThemePack {

    public static final Set<String> DEFAULT_RESOURCE_DOMAINS = ImmutableSet.<String>of("hgm");
    private final ThemeIndex resourceIndex;

    public DefaultThemePack(ThemeIndex resourceIndexIn) {
        this.resourceIndex = resourceIndexIn;
    }

    public InputStream getInputStream(ThemeLocation location) throws IOException {
        InputStream inputstream = this.getInputStreamAssets(location);

        if (inputstream != null) {
            return inputstream;
        } else {
            InputStream inputstream1 = this.getResourceStream(location);

            if (inputstream1 != null) {
                return inputstream1;
            } else {
                throw new FileNotFoundException(location.getThemePath());
            }
        }
    }

    @Nullable
    public InputStream getInputStreamAssets(ThemeLocation location) throws IOException, FileNotFoundException {
        File file1 = this.resourceIndex.getFile(location);
        return file1 != null && file1.isFile() ? new FileInputStream(file1) : null;
    }

    @Nullable
    private InputStream getResourceStream(ThemeLocation location) {
        String s = "/assets/" + location.getThemeDomain() + "/" + location.getThemePath();

        try {
            URL url = DefaultThemePack.class.getResource(s);
            return url != null && FolderThemePack.validatePath(new File(url.getFile()), s) ? DefaultThemePack.class.getResourceAsStream(s) : null;
        } catch (IOException var4) {
            return DefaultThemePack.class.getResourceAsStream(s);
        }
    }

    public boolean themeExists(ThemeLocation location) {
        return this.getResourceStream(location) != null || this.resourceIndex.isFileExisting(location);
    }

    public Set<String> getThemeDomains() {
        return DEFAULT_RESOURCE_DOMAINS;
    }

    @Nullable
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        try {
            InputStream inputstream = new FileInputStream(this.resourceIndex.getPackMcmeta());
            return (T) AbstractThemePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
        } catch (RuntimeException var4) {
            return (T) null;
        } catch (FileNotFoundException var5) {
            return (T) null;
        }
    }

    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(DefaultThemePack.class.getResourceAsStream("/" + (new ThemeLocation("pack.png")).getThemePath()));
    }

    public String getPackName() {
        return "Default";
    }
}