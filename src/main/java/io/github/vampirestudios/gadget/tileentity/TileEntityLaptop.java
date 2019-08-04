package io.github.vampirestudios.gadget.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLaptop extends TileEntityBaseDevice {

    private static final int OPENED_ANGLE = 102;

    private boolean open = false, powered = false, hasBattery = false;

    @SideOnly(Side.CLIENT)
    private int rotation;

    @SideOnly(Side.CLIENT)
    private int prevRotation;

    public TileEntityLaptop() {
        super("Laptop", true);
    }

    @Override
    public void update() {
        super.update();
        if (world.isRemote) {
            prevRotation = rotation;
            if (!open) {
                if (rotation > 0) {
                    rotation -= 10F;
                }
            } else {
                if (rotation < OPENED_ANGLE) {
                    rotation += 10F;
                }
            }
            //System.out.println(this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("open")) {
            this.open = compound.getBoolean("open");
        }
        if (compound.hasKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.hasKey("hasBattery")) {
            this.hasBattery = compound.getBoolean("hasBattery");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("open", open);
        compound.setBoolean("powered", powered);
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = super.writeSyncTag();
        tag.setBoolean("open", open);
        tag.setBoolean("powered", powered);
        tag.setBoolean("hasBattery", hasBattery);
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

    public void openClose() {
        open = !open;
        pipeline.setBoolean("open", open);
        sync();
    }

    public void powerUnpower() {
        powered = !powered;
        pipeline.setBoolean("powered", powered);
        sync();
    }

    public void hasBatteryHasNotBattery() {
        hasBattery = !hasBattery;
        pipeline.setBoolean("hasBattery", hasBattery);
        sync();
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isPowered() {
        return powered;
    }

    public boolean hasBattery() {
        return hasBattery;
    }

    @SideOnly(Side.CLIENT)
    public float getScreenAngle(float partialTicks) {
        return -OPENED_ANGLE * ((prevRotation + (rotation - prevRotation) * partialTicks) / OPENED_ANGLE);
    }

}
