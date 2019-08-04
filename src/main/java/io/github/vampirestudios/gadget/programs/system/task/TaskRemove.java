package io.github.vampirestudios.gadget.programs.system.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import io.github.vampirestudios.gadget.api.task.Task;
import io.github.vampirestudios.gadget.api.utils.BankUtil;
import io.github.vampirestudios.gadget.programs.system.object.Account;

public class TaskRemove extends Task {
    private int amount;

    public TaskRemove() {
        super("remove");
    }

    public TaskRemove(int amount) {
        this();
        this.amount = amount;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setInteger("amount", this.amount);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        this.amount = nbt.getInteger("amount");
        Account sender = BankUtil.INSTANCE.getAccount(player);
        if (sender.hasAmount(amount)) {
            sender.remove(amount);
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        if (isSucessful()) {
            nbt.setInteger("balance", this.amount);
        }
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {
    }
}
