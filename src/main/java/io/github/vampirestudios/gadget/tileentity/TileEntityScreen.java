package io.github.vampirestudios.gadget.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import io.github.vampirestudios.gadget.api.print.IPrint;
import io.github.vampirestudios.gadget.util.IColored;

import javax.annotation.Nullable;

public class TileEntityScreen extends TileEntitySync implements IColored {

    private EnumDyeColor color = EnumDyeColor.WHITE;

    private IPrint print;
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

    @Nullable
    public IPrint getPrint() {
        return print;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("print", Constants.NBT.TAG_COMPOUND)) {
            print = IPrint.loadFromTag(compound.getCompoundTag("print"));
        }
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
        if (print != null) {
            compound.setTag("print", IPrint.writeToTag(print));
        }
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
