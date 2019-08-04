package io.github.vampirestudios.gadget.programs.system;

import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.component.Label;

import javax.annotation.Nullable;

public class ApplicationUserInfo extends Application {

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        Label test = new Label("Test", 50, 50);
        super.addComponent(test);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
