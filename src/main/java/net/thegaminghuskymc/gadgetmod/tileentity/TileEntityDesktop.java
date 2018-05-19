package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDesktop extends TileEntityBaseDevice {

    @SideOnly(Side.CLIENT)
    public float rotation;
    @SideOnly(Side.CLIENT)
    public float prevRotation;
    private boolean psuInstalled = false, psuPowered = false, monitorConnected = false;

    public TileEntityDesktop() {
        super("Desktop");
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
        if (compound.hasKey("psuInstalled")) {
            this.psuInstalled = compound.getBoolean("psuInstalled");
        }
        if (compound.hasKey("psuPowered")) {
            this.psuPowered = compound.getBoolean("psuPowered");
        }
        if (compound.hasKey("monitorConnected")) {
            this.monitorConnected = compound.getBoolean("monitorConnected");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("psuInstalled", psuInstalled);
        compound.setBoolean("psuPowered", psuPowered);
        compound.setBoolean("monitorConnected", monitorConnected);
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("psuInstalled", psuInstalled);
        tag.setBoolean("psuPowered", psuPowered);
        tag.setBoolean("monitorConnected", monitorConnected);
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

    public void monitorConnectedMonitorNotConnected() {
        monitorConnected = !monitorConnected;
        pipeline.setBoolean("monitorConnected", monitorConnected);
        sync();
    }

    public boolean isMonitorConnected() {
        return monitorConnected;
    }

}