package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceApplication;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;

import javax.annotation.Nullable;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "test")
public class ApplicationTest extends Application {

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        Button confirmation = new Button(5, 5, Icons.CHECK);
        confirmation.setToolTip("Confirmation", "This will show a fake confirmation");
        confirmation.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                Dialog.Confirmation dialog = new Dialog.Confirmation("Test");
                dialog.setPositiveText("Override");
                openDialog(dialog);
            }
        });
        super.addComponent(confirmation);

        Button message = new Button(30, 5, Icons.CHAT);
        message.setToolTip("Message", "This will show a message");
        message.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                openDialog(new Dialog.Message("Test"));
            }
        });
        super.addComponent(message);

        Button input = new Button(55, 5, Icons.RENAME);
        input.setToolTip("Input", "This will let you write in a text field");
        input.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                openDialog(new Dialog.Input("Test"));
            }
        });
        super.addComponent(input);

        Button openFile = new Button(80, 5, Icons.LOAD);
        openFile.setToolTip("Open File", "This will open a file");
        openFile.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                openDialog(new Dialog.OpenFile(this));
            }
        });
        super.addComponent(openFile);

        Button saveFile = new Button(105, 5, Icons.EXPORT);
        saveFile.setToolTip("Save File", "This will save a custom file for this program");
        saveFile.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                openDialog(new Dialog.SaveFile(this, new NBTTagCompound()));
            }
        });
        super.addComponent(saveFile);

        this.setResizable(true);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}