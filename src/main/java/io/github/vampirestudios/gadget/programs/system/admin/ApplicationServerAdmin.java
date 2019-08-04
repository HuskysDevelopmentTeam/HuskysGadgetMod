package io.github.vampirestudios.gadget.programs.system.admin;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.emojie_packs.Icons;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.programs.system.SystemApplication;
import io.github.vampirestudios.gadget.programs.system.layout.StandardLayout;

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
