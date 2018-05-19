package net.thegaminghuskymc.gadgetmod.programs.system.admin;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.StandardLayout;

import javax.annotation.Nullable;

public class ApplicationServerAdmin extends SystemApplication {

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        Layout mainMenu = new StandardLayout("Main",300, 150, this, null);
        ((StandardLayout) mainMenu).setIcon(Icons.DATABASE);

        mainMenu.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(0, 0, getWidth(), 20, BaseDevice.getSystem().getSettings().getColourScheme().getHeaderColour()));

        setCurrentLayout(mainMenu);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
