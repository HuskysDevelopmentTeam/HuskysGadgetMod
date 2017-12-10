package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;

/**
 * Author: MrCrayfish
 */
public class ApplicationIcons extends Application {
    public ApplicationIcons() {
        this.setDefaultWidth(278);
        this.setDefaultHeight(150);
    }

    @Override
    public void init() {
        for (Icons icon : Icons.values()) {
            int posX = (icon.ordinal() % 15) * 18;
            int posY = (icon.ordinal() / 15) * 18;
            Button button = new Button(5 + posX, 5 + posY, icon);
            button.setToolTip("Icon", icon.name());
            super.addComponent(button);
        }
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }
}
