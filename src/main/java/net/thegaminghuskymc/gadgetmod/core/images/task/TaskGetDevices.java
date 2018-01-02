package net.thegaminghuskymc.gadgetmod.core.images.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.images.Monitor;
import net.thegaminghuskymc.gadgetmod.core.images.NetworkDevice;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;

import java.util.Collection;

public class TaskGetDevices extends Task {
    private BlockPos devicePos;
    private Class<? extends TileEntityDevice> targetDeviceClass;

    private Collection<NetworkDevice> foundDevices;

    private TaskGetDevices() {
        super("get_connected_devices");
    }

    public TaskGetDevices(BlockPos devicePos) {
        this();
        this.devicePos = devicePos;
    }

    public TaskGetDevices(BlockPos devicePos, Class<? extends TileEntityDevice> targetDeviceClass) {
        this();
        this.devicePos = devicePos;
        this.targetDeviceClass = targetDeviceClass;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("devicePos", devicePos.toLong());
        if (targetDeviceClass != null) {
            nbt.setString("targetClass", targetDeviceClass.getName());
        }
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        BlockPos devicePos = BlockPos.fromLong(nbt.getLong("devicePos"));
        Class targetDeviceClass = null;
        try {
            Class targetClass = Class.forName(nbt.getString("targetClass"));
            if (TileEntityDevice.class.isAssignableFrom(targetClass)) {
                targetDeviceClass = targetClass;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TileEntity tileEntity = world.getTileEntity(devicePos);
        if (tileEntity instanceof TileEntityDevice) {
            TileEntityDevice tileEntityDevice = (TileEntityDevice) tileEntity;
            if (tileEntityDevice.isConnected()) {
                Monitor monitor = tileEntityDevice.getMonitor();
                if (monitor != null) {
                    if (targetDeviceClass != null) {
                        foundDevices = monitor.getConnectedDevices(world, targetDeviceClass);
                    } else {
                        foundDevices = monitor.getConnectedDevices(world);
                    }
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        if (this.isSucessful()) {
            NBTTagList deviceList = new NBTTagList();
            foundDevices.forEach(device -> deviceList.appendTag(device.toTag(true)));
            nbt.setTag("network_devices", deviceList);
        }
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}