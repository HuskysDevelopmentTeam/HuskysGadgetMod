package net.husky.device.programs;

import net.husky.device.api.app.Application;
import net.husky.device.api.app.Icons;
import net.husky.device.api.app.component.*;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationChrome extends Application {

    private TextField textField;

    public ApplicationChrome()
    {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init()
    {
        textField = new TextField(5, 5, 260);
        textField.setPlaceholder("URL");
        super.addComponent(textField);

        Button user = new Button(10, 10, Icons.USER);
        super.addComponent(user);
    }

    @Override
    public void load(NBTTagCompound tagCompound)
    {

    }

    @Override
    public void save(NBTTagCompound tagCompound)
    {

    }

}
