package net.thegaminghuskymc.gadgetmod.client.theme;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.FileNotFoundException;

@SideOnly(Side.CLIENT)
public class ThemePackFileNotFoundException extends FileNotFoundException {
    public ThemePackFileNotFoundException(File resourcePack, String fileName) {
        super(String.format("'%s' in ThemePack '%s'", fileName, resourcePack));
    }
}