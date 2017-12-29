package net.thegaminghuskymc.gadgetmod.programs.social_medias;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.listener.SlideListener;

public class ApplicationPixelBook extends Application {

    public ApplicationPixelBook() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init() {
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
