package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.IMetadataSection;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@SideOnly(Side.CLIENT)
public interface IThemePack {
    InputStream getInputStream(ThemeLocation location) throws IOException;

    boolean themeExists(ThemeLocation location);

    Set<String> getThemeDomains();

    @Nullable
    <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException;

    BufferedImage getPackImage() throws IOException;

    String getPackName();
}