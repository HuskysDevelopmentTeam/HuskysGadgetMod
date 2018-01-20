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
public class LegacyV2Adapter implements IThemePack {
    private final IThemePack pack;

    public LegacyV2Adapter(IThemePack packIn) {
        this.pack = packIn;
    }

    public InputStream getInputStream(ThemeLocation location) throws IOException {
        return this.pack.getInputStream(this.fudgePath(location));
    }

    private ThemeLocation fudgePath(ThemeLocation p_191382_1_) {
        String s = p_191382_1_.getThemePath();

        if (!"lang/swg_de.lang".equals(s) && s.startsWith("lang/") && s.endsWith(".lang")) {
            int i = s.indexOf(95);

            if (i != -1) {
                final String s1 = s.substring(0, i + 1) + s.substring(i + 1, s.indexOf(46, i)).toUpperCase() + ".lang";
                return new ThemeLocation(p_191382_1_.getThemeDomain(), "") {
                    public String getThemeDomains() {
                        return s1;
                    }
                };
            }
        }

        return p_191382_1_;
    }

    public boolean themeExists(ThemeLocation location) {
        return this.pack.themeExists(this.fudgePath(location));
    }

    public Set<String> getThemeDomains() {
        return this.pack.getThemeDomains();
    }

    @Nullable
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        return (T) this.pack.getPackMetadata(metadataSerializer, metadataSectionName);
    }

    public BufferedImage getPackImage() throws IOException {
        return this.pack.getPackImage();
    }

    public String getPackName() {
        return this.pack.getPackName();
    }
}