package net.thegaminghuskymc.gadgetmod.programs.system.admin;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.StandardLayout;

public class ApplicationServerAdmin extends SystemApplication {

    @Override
    public void init() {
        Layout mainMenu = new StandardLayout("Server Admin",300, 150, this, null);

        setCurrentLayout(mainMenu);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
