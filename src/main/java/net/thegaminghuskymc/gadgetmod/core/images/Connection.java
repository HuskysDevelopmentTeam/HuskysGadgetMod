package net.thegaminghuskymc.gadgetmod.core.images;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityMonitor;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class Connection {
    private UUID monitorId;
    private BlockPos monitorPos;

    private Connection() {
    }

    public Connection(Monitor monitor) {
        this.monitorId = monitor.getId();
        this.monitorPos = monitor.getPos();
    }

    public static Connection fromTag(TileEntityDevice device, NBTTagCompound tag) {
        Connection connection = new Connection();
        connection.monitorId = UUID.fromString(tag.getString("id"));
        return connection;
    }

    public UUID getRouterId() {
        return monitorId;
    }

    @Nullable
    public BlockPos getRouterPos() {
        return monitorPos;
    }

    public void setRouterPos(BlockPos routerPos) {
        this.monitorPos = routerPos;
    }

    @Nullable
    public Monitor getRouter(World world) {
        if (monitorPos == null)
            return null;

        TileEntity tileEntity = world.getTileEntity(monitorPos);
        if (tileEntity instanceof TileEntityMonitor) {
            TileEntityMonitor router = (TileEntityMonitor) tileEntity;
            if (router.getMonitor().getId().equals(monitorId)) {
                return router.getMonitor();
            }
        }
        return null;
    }

    public boolean isConnected() {
        return monitorPos != null;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", monitorId.toString());
        return tag;
    }
}