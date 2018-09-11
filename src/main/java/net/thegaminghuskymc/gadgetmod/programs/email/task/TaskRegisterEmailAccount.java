package net.thegaminghuskymc.gadgetmod.programs.email.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.programs.email.EmailManager;

public class TaskRegisterEmailAccount extends Task {
    private String name;

    public TaskRegisterEmailAccount() {
        super("register_email_account");
    }

    public TaskRegisterEmailAccount(String name) {
        this();
        this.name = name;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setString("AccountName", this.name);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        if (EmailManager.INSTANCE.addAccount(player, nbt.getString("AccountName"))) {
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {
    }

}
