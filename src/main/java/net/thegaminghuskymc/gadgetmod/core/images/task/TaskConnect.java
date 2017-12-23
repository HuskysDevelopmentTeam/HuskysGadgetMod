package net.thegaminghuskymc.gadgetmod.core.images.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.images.Monitor;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityMonitor;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityMonitorOld;

/**
 * Author: MrCrayfish
 */
public class TaskConnect extends Task
{
    private BlockPos devicePos;
    private BlockPos routerPos;

    public TaskConnect()
    {
        super("connect");
    }

    public TaskConnect(BlockPos devicePos, BlockPos routerPos)
    {
        this();
        this.devicePos = devicePos;
        this.routerPos = routerPos;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt)
    {
        nbt.setLong("devicePos", devicePos.toLong());
        nbt.setLong("monitorPos", routerPos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player)
    {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("monitorPos")));
        if(tileEntity instanceof TileEntityMonitor)
        {
            TileEntityMonitor tileEntityRouter = (TileEntityMonitor) tileEntity;
            Monitor monitor = tileEntityRouter.getMonitor();

            TileEntity tileEntity1 = world.getTileEntity(BlockPos.fromLong(nbt.getLong("devicePos")));
            if(tileEntity1 instanceof TileEntityDevice)
            {
                TileEntityDevice tileEntityDevice = (TileEntityDevice) tileEntity1;
                if(monitor.addDevice(tileEntityDevice))
                {
                    tileEntityDevice.connect(monitor);
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt)
    {

    }

    @Override
    public void processResponse(NBTTagCompound nbt)
    {

    }
}