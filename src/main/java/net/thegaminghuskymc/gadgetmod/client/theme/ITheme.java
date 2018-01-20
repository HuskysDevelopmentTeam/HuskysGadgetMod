package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.IMetadataSection;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.InputStream;

@SideOnly(Side.CLIENT)
public interface ITheme extends Closeable {

    ThemeLocation getThemeLocation();

    InputStream getInputStream();

    boolean hasMetadata();

    @Nullable
    <T extends IMetadataSection> T getMetadata(String sectionName);

    String getThemePackName();

}