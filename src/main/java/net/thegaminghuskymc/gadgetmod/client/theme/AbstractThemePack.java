package net.thegaminghuskymc.gadgetmod.client.theme;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.IMetadataSection;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

@SideOnly(Side.CLIENT)
public abstract class AbstractThemePack implements IThemePack {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final File resourcePackFile;

    public AbstractThemePack(File resourcePackFileIn) {
        this.resourcePackFile = resourcePackFileIn;
    }

    private static String locationToName(ThemeLocation location) {
        return String.format("%s/%s/%s", "assets", location.getThemeDomain(), location.getThemePath());
    }

    protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }

    static <T extends IMetadataSection> T readMetadata(MetadataSerializer metadataSerializer, InputStream p_110596_1_, String sectionName) {
        JsonObject jsonobject = null;
        BufferedReader bufferedreader = null;

        try {
            bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, StandardCharsets.UTF_8));
            jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
        } catch (RuntimeException runtimeexception) {
            throw new JsonParseException(runtimeexception);
        } finally {
            IOUtils.closeQuietly((Reader) bufferedreader);
        }

        return (T) metadataSerializer.parseMetadataSection(sectionName, jsonobject);
    }

    public InputStream getInputStream(ThemeLocation location) throws IOException {
        return this.getInputStreamByName(locationToName(location));
    }

    public boolean themeExists(ThemeLocation location) {
        return this.hasThemeName(locationToName(location));
    }

    protected abstract InputStream getInputStreamByName(String name) throws IOException;

    protected abstract boolean hasThemeName(String name);

    protected void logNameNotLowercase(String name) {
        LOGGER.warn("ThemePack: ignored non-lowercase namespace: {} in {}", name, this.resourcePackFile);
    }

    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        return (T) readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), metadataSectionName);
    }

    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
    }

    public String getPackName() {
        return this.resourcePackFile.getName();
    }
}