package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.entity.EntitySeat;
import net.thegaminghuskymc.gadgetmod.util.IColored;

import java.util.List;

public class TileEntityOfficeChair extends TileEntitySync implements IColored {
    private EnumDyeColor color = EnumDyeColor.RED;

    @Override
    public EnumDyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(EnumDyeColor color) {
        this.color = color;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("color", Constants.NBT.TAG_BYTE)) {
            color = EnumDyeColor.byMetadata(compound.getByte("color"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("color", (byte) color.getMetadata());
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("color", (byte) color.getMetadata());
        return tag;
    }

    @SideOnly(Side.CLIENT)
    public float getRotation() {
        List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(pos));
        if (!seats.isEmpty()) {
            EntitySeat seat = seats.get(0);
            if (seat.getControllingPassenger() != null) {
                if (seat.getControllingPassenger() instanceof EntityLivingBase) {
                    EntityLivingBase living = (EntityLivingBase) seat.getControllingPassenger();
                    living.renderYawOffset = living.rotationYaw;
                    living.prevRenderYawOffset = living.rotationYaw;
                    return living.rotationYaw;
                }
                return seat.getControllingPassenger().rotationYaw;
            }
        }
        return getBlockMetadata() * 90F + 180F;
    }
}