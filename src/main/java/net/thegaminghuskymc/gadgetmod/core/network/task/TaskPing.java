package net.thegaminghuskymc.gadgetmod.core.network.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceTask;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityNetworkDevice;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@DeviceTask(modId = MOD_ID, taskId = "ping")
public class TaskPing extends Task {
    private BlockPos sourceDevicePos;
    private int strength;

    public TaskPing() {}

    public TaskPing(BlockPos sourceDevicePos) {
        this();
        this.sourceDevicePos = sourceDevicePos;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("sourceDevicePos", sourceDevicePos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("sourceDevicePos")));
        if (tileEntity instanceof TileEntityNetworkDevice) {
            TileEntityNetworkDevice TileEntityNetworkDevice = (TileEntityNetworkDevice) tileEntity;
            if (TileEntityNetworkDevice.isConnected()) {
                this.strength = TileEntityNetworkDevice.getSignalStrength();
                this.setSuccessful();
            }
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        if (this.isSucessful()) {
            nbt.setInteger("strength", strength);
        }
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}
