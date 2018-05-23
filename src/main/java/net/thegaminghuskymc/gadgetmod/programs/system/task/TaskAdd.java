package net.thegaminghuskymc.gadgetmod.programs.system.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceTask;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.api.utils.BankUtil;
import net.thegaminghuskymc.gadgetmod.programs.system.object.Account;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@DeviceTask(modId = MOD_ID, taskId = "bank_add")
public class TaskAdd extends Task {
    private int amount;

    public TaskAdd() {}

    public TaskAdd(int amount) {
        this();
        this.amount = amount;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setInteger("amount", this.amount);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        int amount = nbt.getInteger("amount");
        Account sender = BankUtil.INSTANCE.getAccount(player);
        sender.add(amount);
        this.setSuccessful();
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        nbt.setInteger("balance", this.amount);
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {
    }
}
