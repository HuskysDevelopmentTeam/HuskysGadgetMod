package net.thegaminghuskymc.gadgetmod.programs.social_medias.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.programs.email.EmailManager;
import net.thegaminghuskymc.gadgetmod.programs.email.object.Email;

public class TaskSendMessage extends Task {
    private Email email;
    private String to;

    public TaskSendMessage() {
        super("send_message");
    }

    public TaskSendMessage(Email email, String to) {
        this();
        this.email = email;
        this.to = to;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        this.email.writeToNBT(nbt);
        nbt.setString("to", this.to);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        String name = EmailManager.INSTANCE.getName(player);
        if (name != null) {
            Email email = Email.readFromNBT(nbt);
            email.setAuthor(name);
            if (EmailManager.INSTANCE.addEmailToInbox(email, nbt.getString("to"))) {
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
