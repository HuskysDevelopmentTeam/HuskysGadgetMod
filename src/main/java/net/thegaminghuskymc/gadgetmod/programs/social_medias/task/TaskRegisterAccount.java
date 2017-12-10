package net.thegaminghuskymc.gadgetmod.programs.social_medias.task;

import net.husky.device.programs.email.ApplicationEmail.EmailManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;

public class TaskRegisterAccount extends Task {
    private String name;

    public TaskRegisterAccount() {
        super("register_account");
    }

    public TaskRegisterAccount(String name) {
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
