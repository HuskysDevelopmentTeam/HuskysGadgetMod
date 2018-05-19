package net.thegaminghuskymc.gadgetmod.programs.system;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;

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
