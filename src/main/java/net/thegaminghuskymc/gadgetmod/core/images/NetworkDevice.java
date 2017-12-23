package net.thegaminghuskymc.gadgetmod.core.images;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class NetworkDevice
{
    private Monitor monitor;
    private UUID id;
    private String name;
    private BlockPos pos;

    private NetworkDevice() {}

    public NetworkDevice(TileEntityDevice device, Monitor monitor)
    {
        this.monitor = monitor;
        this.id = device.getId();
        update(device);
    }

    public NetworkDevice(UUID id, String name, Monitor monitor)
    {
        this.id = id;
        this.name = name;
        this.monitor = monitor;
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Nullable
    public BlockPos getPos()
    {
        return pos;
    }

    public void setPos(BlockPos pos)
    {
        this.pos = pos;
    }

    public boolean isConnected(World world)
    {
        if(pos == null)
            return false;

        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityDevice)
        {
            TileEntityDevice device = (TileEntityDevice) tileEntity;
            Monitor monitor = device.getMonitor();
            return monitor != null && monitor.getId().equals(monitor.getId());
        }
        return false;
    }

    public void update(TileEntityDevice device)
    {
        name = device.getDeviceName();
        pos = device.getPos();
    }

    @Nullable
    public TileEntityDevice getDevice(World world)
    {
        if(pos == null)
            return null;

        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityDevice)
        {
            TileEntityDevice tileEntityDevice = (TileEntityDevice) tileEntity;
            if(tileEntityDevice.getId().equals(getId()))
            {
                return tileEntityDevice;
            }
        }
        return null;
    }

    public NBTTagCompound toTag(boolean includePos)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", id.toString());
        tag.setString("name", name);
        if(includePos && pos != null)
        {
            tag.setLong("pos", pos.toLong());
        }
        return tag;
    }

    public static NetworkDevice fromTag(NBTTagCompound tag)
    {
        NetworkDevice device = new NetworkDevice();
        device.id = UUID.fromString(tag.getString("id"));
        device.name = tag.getString("name");
        if(tag.hasKey("pos", Constants.NBT.TAG_LONG))
        {
            device.pos = BlockPos.fromLong(tag.getLong("pos"));
        }
        return device;
    }
}