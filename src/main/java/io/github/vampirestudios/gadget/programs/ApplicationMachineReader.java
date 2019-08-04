package io.github.vampirestudios.gadget.programs;

import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.annontation.DeviceApplication;
import io.github.vampirestudios.gadget.api.app.component.Label;

import javax.annotation.Nullable;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "machine_reader")
public class ApplicationMachineReader extends Application {

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
