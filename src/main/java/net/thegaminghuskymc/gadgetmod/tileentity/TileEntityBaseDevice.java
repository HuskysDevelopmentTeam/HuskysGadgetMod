package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.util.TileEntityUtil;

public class TileEntityBaseDevice extends TileEntityNetworkDevice.Colored {

    private String deviceName;
    @SideOnly(Side.CLIENT)
    public float rotation;
    @SideOnly(Side.CLIENT)
    public float prevRotation;
    private boolean powered = false, powerConnected = false, wifiConnected = false;
    private NBTTagCompound applicationData;
    private NBTTagCompound systemData;
    private FileSystem fileSystem;
    @SideOnly(Side.CLIENT)
    private boolean hasExternalDrive;

    @SideOnly(Side.CLIENT)
    private EnumDyeColor externalDriveColor;

    public TileEntityBaseDevice(String deviceName, boolean isLaptop) {
        this.deviceName = deviceName;
    }

    @Override
    public String getDeviceName() {
        return deviceName;
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
        if (this.systemData != null && this.systemData.hasKey("boottimer") && this.systemData.hasKey("bootmode")) {
            BaseDevice.BootMode bootmode = BaseDevice.BootMode.getBootMode(this.systemData.getInteger("bootmode"));
            if (bootmode != null && bootmode != BaseDevice.BootMode.NOTHING) {
                int boottimer = Math.max(this.systemData.getInteger("boottimer") - 1, 0);
                if (boottimer == 0) {
                    bootmode = bootmode == BaseDevice.BootMode.BOOTING ? BaseDevice.BootMode.NOTHING : null;
                    this.systemData.setInteger("bootmode", BaseDevice.BootMode.ordinal(bootmode));
                }
                this.systemData.setInteger("boottimer", boottimer);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("device_name", Constants.NBT.TAG_STRING)) {
            this.deviceName = compound.getString("device_name");
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
        if (compound.hasKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if(compound.hasKey("powerConnected")) {
            this.powerConnected = compound.getBoolean("powerConnected");
        }
        if(compound.hasKey("wifiConnected")) {
            this.wifiConnected = compound.getBoolean("wifiConnected");
        }
        if (compound.hasKey("file_system")) {
            this.fileSystem = new FileSystem(this, compound.getCompoundTag("file_system"));
        }
        if (compound.hasKey("external_drive_color", Constants.NBT.TAG_BYTE)) {
            this.externalDriveColor = null;
            if (compound.getByte("external_drive_color") != -1) {
                this.externalDriveColor = EnumDyeColor.byMetadata(compound.getByte("external_drive_color"));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("device_name", deviceName);

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
        compound.setBoolean("wifiConnected", wifiConnected);
        compound.setBoolean("powerConnected", powerConnected);
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("device_name", deviceName);
        tag.setBoolean("powered", powered);
        tag.setBoolean("wifiConnected", wifiConnected);
        tag.setBoolean("powerConnected", powerConnected);
        tag.setTag("system_data", getSystemData());

        if (getFileSystem().getAttachedDrive() != null) {
            tag.setByte("external_drive_color", (byte) getFileSystem().getAttachedDriveColor().getMetadata());
        } else {
            tag.setByte("external_drive_color", (byte) -1);
        }
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

    public boolean isPowerConnected() {
        return powerConnected;
    }

    public boolean isWifiConnected() {
        return wifiConnected;
    }

    public void wifiOnlineOffline() {
        wifiConnected = !wifiConnected;
        pipeline.setBoolean("wifiConnected", wifiConnected);
        sync();
    }

    public void powerOnOff() {
        powerConnected = !powerConnected;
        pipeline.setBoolean("powerConnected", powerConnected);
        sync();
    }

    public NBTTagCompound getApplicationData() {
        return applicationData != null ? applicationData : new NBTTagCompound();
    }

    public NBTTagCompound getSystemData() {
        if(systemData == null)
        {
            systemData = new NBTTagCompound();
        }
        return systemData;
    }

    public EnumDyeColor getExternalDriveColor() {
        return externalDriveColor;
    }

    public void setSystemData(NBTTagCompound systemData) {
        this.systemData = systemData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public FileSystem getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new FileSystem(this, new NBTTagCompound());
        }
        return fileSystem;
    }

    public void setApplicationData(String appID, NBTTagCompound applicationData) {
        this.applicationData = applicationData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public boolean isExternalDriveAttached() {
        return hasExternalDrive;
    }

}
