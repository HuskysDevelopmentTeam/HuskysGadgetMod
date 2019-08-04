package io.github.vampirestudios.gadget.core.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import io.github.vampirestudios.gadget.tileentity.TileEntityNetworkDevice;

import javax.annotation.Nullable;
import java.util.UUID;

public class NetworkDevice {
    private Router router;
    private UUID id;
    private String name;
    private BlockPos pos;

    private NetworkDevice() {
    }

    public NetworkDevice(TileEntityNetworkDevice device, Router router) {
        this.router = router;
        this.id = device.getId();
        update(device);
    }

    public NetworkDevice(UUID id, String name, Router router) {
        this.id = id;
        this.name = name;
        this.router = router;
    }

    public static NetworkDevice fromTag(NBTTagCompound tag) {
        NetworkDevice device = new NetworkDevice();
        device.id = UUID.fromString(tag.getString("id"));
        device.name = tag.getString("name");
        if (tag.hasKey("pos", Constants.NBT.TAG_LONG)) {
            device.pos = BlockPos.fromLong(tag.getLong("pos"));
        }
        return device;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public boolean isConnected(World world) {
        if (pos == null)
            return false;

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityNetworkDevice) {
            TileEntityNetworkDevice device = (TileEntityNetworkDevice) tileEntity;
            Router router = device.getRouter();
            return router != null && router.getId().equals(router.getId());
        }
        return false;
    }

    public void update(TileEntityNetworkDevice device) {
        name = device.getDeviceName();
        pos = device.getPos();
    }

    @Nullable
    public TileEntityNetworkDevice getDevice(World world) {
        if (pos == null)
            return null;

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityNetworkDevice) {
            TileEntityNetworkDevice TileEntityNetworkDevice = (TileEntityNetworkDevice) tileEntity;
            if (TileEntityNetworkDevice.getId().equals(getId())) {
                return TileEntityNetworkDevice;
            }
        }
        return null;
    }

    public NBTTagCompound toTag(boolean includePos) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", id.toString());
        tag.setString("name", name);
        if (includePos && pos != null) {
            tag.setLong("pos", pos.toLong());
        }
        return tag;
    }
}