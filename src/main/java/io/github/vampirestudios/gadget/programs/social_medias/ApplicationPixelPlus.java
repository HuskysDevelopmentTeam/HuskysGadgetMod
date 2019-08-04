package io.github.vampirestudios.gadget.programs.social_medias;

import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.annontation.DeviceApplication;

import javax.annotation.Nullable;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "pixel_plus")
public class ApplicationPixelPlus extends Application {

    public ApplicationPixelPlus() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init(@Nullable NBTTagCompound intent) {

    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
