package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.TextField;

public class ApplicationPixelBrowser extends Application {

    private TextField textField;

    public ApplicationPixelBrowser() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init() {
        textField = new TextField(5, 20, 260);
        textField.setPlaceholder("URL");
        super.addComponent(textField);

        Button user = new Button(10, 10, Icons.USER);
        super.addComponent(user);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
