package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.util.IColored;

public class TileEntityGamingDesk extends TileEntitySync implements IColored {

    private EnumDyeColor color = EnumDyeColor.WHITE;

    private byte rotation;

    public void nextRotation() {
        rotation++;
        if (rotation > 7) {
            rotation = 0;
        }
        pipeline.setByte("rotation", rotation);
        sync();
    }

    public float getRotation() {
        return rotation * 45F;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("rotation", Constants.NBT.TAG_BYTE)) {
            rotation = compound.getByte("rotation");
        }
        if (compound.hasKey("color", Constants.NBT.TAG_BYTE)) {
            this.color = EnumDyeColor.byDyeDamage(compound.getByte("color"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("rotation", rotation);
        compound.setByte("color", (byte) color.getDyeDamage());
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("color", (byte) color.getDyeDamage());
        return tag;
    }

    @Override
    public EnumDyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(EnumDyeColor color) {
        this.color = color;
    }

}