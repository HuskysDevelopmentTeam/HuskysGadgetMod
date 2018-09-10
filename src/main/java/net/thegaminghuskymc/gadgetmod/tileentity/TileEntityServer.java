package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityServer extends TileEntityBaseDevice {

    @SideOnly(Side.CLIENT)
    public float rotation;
    private boolean
            inServerRack = false,
            connected = false;

    public TileEntityServer() {
        super("Server", false);
    }

    @Override
    public void update() {
        if (world.isRemote) {
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
        if (compound.hasKey("inServerRack")) {
            this.inServerRack = compound.getBoolean("inServerRack");
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.setBoolean("connected", connected);
        compound.setBoolean("inServerRack", inServerRack);
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("connected", connected);
        tag.setBoolean("inServerRack", inServerRack);
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

    public boolean isInServerRack() {
        return inServerRack;
    }

    public void connectedNotConnected() {
        connected = !connected;
        pipeline.setBoolean("connected", connected);
        sync();
    }

    public boolean isConnected() {
        return connected;
    }

}