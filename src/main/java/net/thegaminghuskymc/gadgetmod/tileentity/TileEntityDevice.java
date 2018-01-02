package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.DeviceConfig;
import net.thegaminghuskymc.gadgetmod.core.images.Monitor;
import net.thegaminghuskymc.gadgetmod.core.network.Connection;
import net.thegaminghuskymc.gadgetmod.core.network.Router;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public abstract class TileEntityDevice extends TileEntitySync implements ITickable {
    private int counter;
    private UUID deviceId;
    private Connection connection;
    private net.thegaminghuskymc.gadgetmod.core.images.Connection connectionMonitor;

    @Override
    public void update() {
        if (world.isRemote)
            return;

        if (connection != null && connection.getRouterPos() != null) {
            if (++counter >= DeviceConfig.getBeaconInterval() * 2) {
                connection.setRouterPos(null);
            }
        }
    }

    public void connect(Router router) {
        if (router == null) {
            if (connection != null) {
                Router connectedRouter = connection.getRouter(world);
                if (connectedRouter != null) {
                    connectedRouter.removeDevice(this);
                }
            }
            connection = null;
            return;
        }
        connection = new Connection(router);
        counter = 0;
        this.markDirty();
    }

    public void connect(Monitor monitor) {
        if (monitor == null) {
            if (connection != null) {
                Monitor connectedRouter = connectionMonitor.getRouter(world);
                if (connectedRouter != null) {
                    connectedRouter.removeDevice(this);
                }
            }
            connection = null;
            return;
        }
        connectionMonitor = new net.thegaminghuskymc.gadgetmod.core.images.Connection(monitor);
        counter = 0;
        this.markDirty();
    }

    public Connection getConnection() {
        return connection;
    }

    @Nullable
    public Router getRouter() {
        return connection != null ? connection.getRouter(world) : null;
    }

    @Nullable
    public Monitor getMonitor() {
        return connectionMonitor != null ? connectionMonitor.getRouter(world) : null;
    }

    public final UUID getId() {
        if (deviceId == null) {
            deviceId = UUID.randomUUID();
        }
        return deviceId;
    }

    public abstract String getDeviceName();

    public boolean isConnected() {
        return connection != null && connection.isConnected() && connectionMonitor != null && connectionMonitor.isConnected();
    }

    public boolean receiveBeacon(Router router) {
        if (connection.getRouterId().equals(router.getId())) {
            connection.setRouterPos(router.getPos());
            counter = 0;
            return true;
        }
        return false;
    }

    public int getRouterSignalStrength() {
        BlockPos routerPos = connection.getRouterPos();
        if (routerPos != null) {
            double distance = Math.sqrt(pos.distanceSqToCenter(routerPos.getX() + 0.5, routerPos.getY() + 0.5, routerPos.getZ() + 0.5));
            double level = DeviceConfig.getSignalRange() / 3.0;
            return distance > level * 2 ? 2 : distance > level ? 1 : 0;
        }
        return -1;
    }

    public boolean receiveBeacon(Monitor monitor) {
        if (connection.getRouterId().equals(monitor.getId())) {
            connection.setRouterPos(monitor.getPos());
            counter = 0;
            return true;
        }
        return false;
    }

    public int getSignalStrength() {
        BlockPos routerPos = connection.getRouterPos();
        if (routerPos != null) {
            double distance = Math.sqrt(pos.distanceSqToCenter(routerPos.getX() + 0.5, routerPos.getY() + 0.5, routerPos.getZ() + 0.5));
            double level = DeviceConfig.getSignalRange() / 3.0;
            return distance > level * 2 ? 2 : distance > level ? 1 : 0;
        }
        return -1;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("deviceId", getId().toString());

        if (connection != null) {
            compound.setTag("connection", connection.toTag());
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("deviceId", Constants.NBT.TAG_STRING)) {
            deviceId = UUID.fromString(compound.getString("deviceId"));
        }
        if (compound.hasKey("connection", Constants.NBT.TAG_COMPOUND)) {
            connection = Connection.fromTag(this, compound.getCompoundTag("connection"));
        }
    }
}