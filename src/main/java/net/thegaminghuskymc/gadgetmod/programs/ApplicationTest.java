package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskGetDevices;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityPrinter;

import java.awt.*;


/**
 * Author: MrCrayfish
 */
public class ApplicationTest extends Application
{
    @Override
    public void init()
    {
        Button button = new Button(5, 5, "Print", Icons.PRINTER);
        button.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                int[] pixels = {Color.RED.getRGB(), Color.DARK_GRAY.getRGB(), Color.YELLOW.getRGB(), Color.MAGENTA.getRGB()};
                Dialog.Print dialog = new Dialog.Print(new ApplicationPixelPainter.PicturePrint("Test", pixels, 2));
                openDialog(dialog);
            }
        });
        super.addComponent(button);

        Button getDevices = new Button(50, 5, "Get Devices", Icons.NETWORK_DRIVE);
        button.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                Task task = new TaskGetDevices(Laptop.getPos(), TileEntityPrinter.class);
                task.setCallback((tagCompound, success) ->
                {
                    if(success)
                    {
                        System.out.println(tagCompound);
                    }
                });
                TaskManager.sendTask(task);
            }
        });
        super.addComponent(getDevices);
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
