package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.util.TileEntityUtil;

public class TileEntityDesktop extends TileEntityDevice implements ITickable
{
    private String name = "Desktop";
    private boolean powered = false;

    private NBTTagCompound applicationData;
    private NBTTagCompound systemData;
    private FileSystem fileSystem;

    @SideOnly(Side.CLIENT)
    public float rotation;

    @SideOnly(Side.CLIENT)
    public float prevRotation;

    @SideOnly(Side.CLIENT)
    private boolean hasExternalDrive;

    @Override
    public String getDeviceName()
    {
        return name;
    }

    @Override
    public void update()
    {
        super.update();
        if(world.isRemote)
        {
            prevRotation = rotation;
            if(rotation > 0) {
                rotation -= 10F;
            }
            else
            {
                if(rotation < 110)
                {
                    rotation += 10F;
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("powered"))
        {
            this.powered = compound.getBoolean("powered");
        }
        if(compound.hasKey("device_name", Constants.NBT.TAG_STRING))
        {
            this.name = compound.getString("device_name");
        }
        if(compound.hasKey("system_data", Constants.NBT.TAG_COMPOUND))
        {
            this.systemData = compound.getCompoundTag("system_data");
        }
        if(compound.hasKey("application_data", Constants.NBT.TAG_COMPOUND))
        {
            this.applicationData = compound.getCompoundTag("application_data");
        }
        if(compound.hasKey("file_system"))
        {
            this.fileSystem = new FileSystem(this, compound.getCompoundTag("file_system"));
        }
        if(compound.hasKey("has_external_drive"))
        {
            this.hasExternalDrive = compound.getBoolean("has_external_drive");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("powered", powered);
        compound.setString("device_name", name);

        if(systemData != null)
        {
            compound.setTag("system_data", systemData);
        }

        if(applicationData != null)
        {
            compound.setTag("application_data", applicationData);
        }

        if(fileSystem != null)
        {
            compound.setTag("file_system", fileSystem.toTag());
        }
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("powered", powered);
        tag.setString("device_name", name);
        tag.setBoolean("has_external_drive", getFileSystem().getAttachedDrive() != null);
        return tag;
    }

    @Override
    public double getMaxRenderDistanceSquared()
    {
        return 16384;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

    public void powerUnpower()
    {
        powered = !powered;
        pipeline.setBoolean("powered", powered);
        sync();
    }

    public boolean isPowered()
    {
        return powered;
    }

    public NBTTagCompound getApplicationData()
    {
        return applicationData != null ? applicationData : new NBTTagCompound();
    }

    public NBTTagCompound getSystemData()
    {
        return systemData != null ? systemData : new NBTTagCompound();
    }

    public FileSystem getFileSystem()
    {
        if(fileSystem == null)
        {
            fileSystem = new FileSystem(this, new NBTTagCompound());
        }
        return fileSystem;
    }

    public void setApplicationData(String appId, NBTTagCompound applicationData)
    {
        this.applicationData = applicationData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public void setSystemData(NBTTagCompound systemData)
    {
        this.systemData = systemData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public boolean isExternalDriveAttached()
    {
        return hasExternalDrive;
    }
}