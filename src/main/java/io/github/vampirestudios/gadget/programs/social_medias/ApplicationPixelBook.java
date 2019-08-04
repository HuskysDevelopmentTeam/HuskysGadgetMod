package io.github.vampirestudios.gadget.programs.social_medias;

import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.annontation.DeviceApplication;
import io.github.vampirestudios.gadget.api.app.component.Button;

import javax.annotation.Nullable;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "pixel_book")
public class ApplicationPixelBook extends Application {

    public ApplicationPixelBook() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        Button button = new Button(getWidth() / 2, getHeight() / 2, "Travel to the Earth's Core and see your Long Lost Great Grandpa");
        super.addComponent(button);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
