package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.programs.system.component.FileBrowser;

import javax.annotation.Nullable;

public class ApplicationFileBrowser extends SystemApplication {

    public ApplicationFileBrowser() {
        this.setDefaultWidth(225);
        this.setDefaultHeight(145);
    }

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        FileBrowser browser = new FileBrowser(0, 0, this, FileBrowser.Mode.FULL);
        browser.openFolder(FileSystem.DIR_HOME);
        this.addComponent(browser);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
