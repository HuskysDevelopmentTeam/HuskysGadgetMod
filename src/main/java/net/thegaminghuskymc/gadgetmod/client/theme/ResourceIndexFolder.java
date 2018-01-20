package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ResourceIndexFolder extends ThemeIndex {
    private final File baseDir;

    public ResourceIndexFolder(File folder) {
        this.baseDir = folder;
    }

    public File getFile(ThemeLocation location) {
        return new File(this.baseDir, location.toString().replace(':', '/'));
    }

    public File getPackMcmeta() {
        return new File(this.baseDir, "pack.mcmeta");
    }
}