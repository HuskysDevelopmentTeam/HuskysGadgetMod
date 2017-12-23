package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.core.images.Monitor;
import net.thegaminghuskymc.gadgetmod.util.Colorable;

/**
 * Author: MrCrayfish
 */
public class TileEntityMonitor extends TileEntitySync implements ITickable, Colorable {

    private EnumDyeColor color = EnumDyeColor.RED;

    private Monitor router;

    @SideOnly(Side.CLIENT)
    private int debugTimer;

    public Monitor getMonitor()
    {
        if(router == null)
        {
            router = new Monitor(pos);
            markDirty();
        }
        return router;
    }

    public void update()
    {
        if(!world.isRemote)
        {
            getMonitor().update(world);
        }
        else if(debugTimer > 0)
        {
            debugTimer--;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isDebug()
    {
        return debugTimer > 0;
    }

    @SideOnly(Side.CLIENT)
    public void setDebug()
    {
        if(debugTimer <= 0)
        {
            debugTimer = 1200;
        }
        else
        {
            debugTimer = 0;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("monitor", getMonitor().toTag(false));
        compound.setByte("color", (byte) color.getDyeDamage());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("monitor", Constants.NBT.TAG_COMPOUND))
        {
            router = Monitor.fromTag(pos, compound.getCompoundTag("monitor"));
        }
        if(compound.hasKey("color", Constants.NBT.TAG_BYTE)) {
            this.color = EnumDyeColor.byDyeDamage(compound.getByte("color"));
        }
    }

    @Override
    public NBTTagCompound writeSyncTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("color", (byte) color.getDyeDamage());
        return tag;
    }

    public void syncDevicesToClient()
    {
        pipeline.setTag("monitor", getMonitor().toTag(true));
        sync();
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

    @Override
    public void setColor(EnumDyeColor color) {
        this.color = color;
    }

    @Override
    public EnumDyeColor getColor() {
        return color;
    }

}