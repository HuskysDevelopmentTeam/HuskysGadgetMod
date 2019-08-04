package io.github.vampirestudios.gadget.programs.email.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import io.github.vampirestudios.gadget.api.task.Task;
import io.github.vampirestudios.gadget.programs.email.EmailManager;
import io.github.vampirestudios.gadget.programs.email.object.Email;

import java.util.List;

public class TaskViewEmail extends Task {
    private int index;

    public TaskViewEmail() {
        super("view_email");
    }

    public TaskViewEmail(int index) {
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
                emails.get(index).setRead(true);
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
