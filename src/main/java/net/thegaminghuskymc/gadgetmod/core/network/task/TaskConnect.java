package net.thegaminghuskymc.gadgetmod.core.network.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.network.Router;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityRouter;

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
        nbt.setLong("routerPos", routerPos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player)
    {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("routerPos")));
        if(tileEntity instanceof TileEntityRouter)
        {
            TileEntityRouter tileEntityRouter = (TileEntityRouter) tileEntity;
            Router router = tileEntityRouter.getRouter();

            TileEntity tileEntity1 = world.getTileEntity(BlockPos.fromLong(nbt.getLong("devicePos")));
            if(tileEntity1 instanceof TileEntityDevice)
            {
                TileEntityDevice tileEntityDevice = (TileEntityDevice) tileEntity1;
                if(router.addDevice(tileEntityDevice))
                {
                    tileEntityDevice.connect(router);
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