package net.husky.device.programs;

import net.husky.device.api.app.Application;
import net.husky.device.api.app.component.Label;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationHalloween extends Application {

    private Label happyHalloween;

    @Override
    public void init() {
        happyHalloween = new Label("Happy Halloween", 10, 10);
        super.addComponent(happyHalloween);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }


}
