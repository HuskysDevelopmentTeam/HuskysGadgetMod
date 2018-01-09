package net.thegaminghuskymc.gadgetmod.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.util.Colorable;
import net.thegaminghuskymc.gadgetmod.util.TileEntityUtil;

import java.util.List;

public class TileEntityDesktop extends TileEntityDevice {

    @SideOnly(Side.CLIENT)
    public float rotation;
    @SideOnly(Side.CLIENT)
    public float prevRotation;
    public EnumDyeColor color = EnumDyeColor.RED;
    private String name = "Desktop";
    private boolean powered = false, online = false, connected = false, monitorConnected = false, doorOpen = false;
    private List<String> components;
    private NBTTagCompound applicationData;
    private NBTTagCompound systemData;
    private FileSystem fileSystem;
    @SideOnly(Side.CLIENT)
    private boolean hasExternalDrive;

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            prevRotation = rotation;
            if (rotation > 0) {
                rotation -= 10F;
            } else {
                if (rotation < 110) {
                    rotation += 10F;
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("device_name", Constants.NBT.TAG_STRING)) {
            this.name = compound.getString("device_name");
        }
        if (compound.hasKey("system_data", Constants.NBT.TAG_COMPOUND)) {
            this.systemData = compound.getCompoundTag("system_data");
        }
        if (compound.hasKey("application_data", Constants.NBT.TAG_COMPOUND)) {
            this.applicationData = compound.getCompoundTag("application_data");
        }
        if (compound.hasKey("has_external_drive")) {
            this.hasExternalDrive = compound.getBoolean("has_external_drive");
        }
        if (compound.hasKey("color", Constants.NBT.TAG_BYTE)) {
            this.color = EnumDyeColor.byDyeDamage(compound.getByte("color"));
        }
        if (compound.hasKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.hasKey("online")) {
            this.online = compound.getBoolean("online");
        }
        if (compound.hasKey("connected")) {
            this.connected = compound.getBoolean("connected");
        }
        if (compound.hasKey("monitorConnected")) {
            this.monitorConnected = compound.getBoolean("monitorConnected");
        }
        if (compound.hasKey("doorOpen")) {
            this.doorOpen = compound.getBoolean("doorOpen");
        }
        if (compound.hasKey("components")) {
            this.components = Lists.newArrayList();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("device_name", name);
        compound.setByte("color", (byte) color.getDyeDamage());

        if (systemData != null) {
            compound.setTag("system_data", systemData);
        }

        if (applicationData != null) {
            compound.setTag("application_data", applicationData);
        }

        if (fileSystem != null) {
            compound.setTag("file_system", fileSystem.toTag());
        }
        compound.setBoolean("powered", powered);
        compound.setBoolean("online", online);
        compound.setBoolean("connected", connected);
        compound.setBoolean("monitorConnected", monitorConnected);
        compound.setBoolean("doorOpen", doorOpen);
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("device_name", name);
        tag.setByte("color", (byte) color.getDyeDamage());
        tag.setBoolean("powered", powered);
        tag.setBoolean("online", powered);
        tag.setBoolean("connected", powered);
        tag.setBoolean("monitorConnected", monitorConnected);
        tag.setBoolean("doorOpen", doorOpen);
        return tag;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 16384;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public void powerUnpower() {
        powered = !powered;
        pipeline.setBoolean("powered", powered);
        sync();
    }

    public boolean isPowered() {
        return powered;
    }

    public void onlineOffline() {
        online = !online;
        pipeline.setBoolean("online", online);
        sync();
    }

    public boolean isOnline() {
        return online;
    }

    public void connectNotConnected() {
        connected = !connected;
        pipeline.setBoolean("connected", connected);
        sync();
    }

    public boolean isConnected() {
        return connected;
    }

    public void monitorConnectedMonitorNotConnected() {
        monitorConnected = !monitorConnected;
        pipeline.setBoolean("monitorConnected", monitorConnected);
        sync();
    }

    public boolean isMonitorConnected() {
        return monitorConnected;
    }

    public void doorOpenDoorClosed() {
        doorOpen = !doorOpen;
        pipeline.setBoolean("doorOpen", doorOpen);
        sync();
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public NBTTagCompound getApplicationData() {
        return applicationData != null ? applicationData : new NBTTagCompound();
    }

    public NBTTagCompound getSystemData() {
        return systemData != null ? systemData : new NBTTagCompound();
    }

    public void setSystemData(NBTTagCompound systemData) {
        this.systemData = systemData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public void setApplicationData(String appId, NBTTagCompound applicationData) {
        this.applicationData = applicationData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public boolean isExternalDriveAttached() {
        return hasExternalDrive;
    }

}