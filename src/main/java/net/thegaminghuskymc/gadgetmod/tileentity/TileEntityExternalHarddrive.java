package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.util.Colorable;

public class TileEntityExternalHarddrive extends TileEntityDevice{

    @SideOnly(Side.CLIENT)
    public float rotation;
    @SideOnly(Side.CLIENT)
    public float prevRotation;
    private String name = "External Harddrive";
    private EnumDyeColor color = EnumDyeColor.RED;
    private boolean
            connected = false,
            online = false,
            powered = false;

    @Override
    public void update() {
        if (world.isRemote) {
            prevRotation = rotation;
            if (rotation > 0) {
                rotation -= 10F;
            } else if (rotation < 110) {
                rotation += 10F;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (compound.hasKey("connected")) {
            this.connected = compound.getBoolean("connected");
        }
        if (compound.hasKey("online")) {
            this.online = compound.getBoolean("online");
        }
        if (compound.hasKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.hasKey("device_name", Constants.NBT.TAG_STRING)) {
            this.name = compound.getString("device_name");
        }
        compound.setByte("color", (byte) color.getDyeDamage());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.setBoolean("connected", connected);
        compound.setBoolean("online", online);
        compound.setBoolean("powered", powered);
        compound.setString("device_name", name);
        if (compound.hasKey("color", Constants.NBT.TAG_BYTE)) {
            this.color = EnumDyeColor.byDyeDamage(compound.getByte("color"));
        }
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("connected", connected);
        tag.setBoolean("online", online);
        tag.setBoolean("powered", powered);
        tag.setString("device_name", name);
        tag.setByte("color", (byte) color.getDyeDamage());
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

    public void onlineOffline() {
        online = !online;
        pipeline.setBoolean("online", online);
        sync();
    }

    public boolean isOnline() {
        return online;
    }

    public void poweredUnpowered() {
        powered = !powered;
        pipeline.setBoolean("powered", powered);
        sync();
    }

    public boolean isPowered() {
        return online;
    }

    public void connectedNotConnected() {
        connected = !connected;
        pipeline.setBoolean("connected", connected);
        sync();
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

}