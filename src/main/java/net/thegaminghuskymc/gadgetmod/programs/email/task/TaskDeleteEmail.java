package net.thegaminghuskymc.gadgetmod.programs.email.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceTask;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.programs.email.EmailManager;
import net.thegaminghuskymc.gadgetmod.programs.email.object.Email;

import java.util.List;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@DeviceTask(modId = MOD_ID, taskId = "delete_email")
public class TaskDeleteEmail extends Task {

    private int index;

    public TaskDeleteEmail() {}

    public TaskDeleteEmail(int index) {
        this();
        this.index = index;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setInteger("Index", this.index);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        List<Email> emails = EmailManager.INSTANCE.getEmailsForAccount(player);
        if (emails != null) {
            int index = nbt.getInteger("Index");
            if (index >= 0 && index < emails.size()) {
                emails.remove(index);
                this.setSuccessful();
            }
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {
    }
}
