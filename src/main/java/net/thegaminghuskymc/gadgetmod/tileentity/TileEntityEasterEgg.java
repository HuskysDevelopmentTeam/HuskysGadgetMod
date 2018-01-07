package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityEasterEgg extends TileEntity {

    private int color0, color1;

    public TileEntityEasterEgg(Random r) {
        if (r != null) {
            this.color0 = r.nextInt(0xFFFFFF);
            this.color1 = r.nextInt(0xFFFFFF);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        this.writeColorsToNBT(compound);
        return compound;
    }

    public NBTTagCompound writeColorsToNBT(NBTTagCompound compound) {
        for (int i = 0; i < 2; i++) {
            compound.setInteger("color" + i, this.getColor(i));
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readColorsFromNBT(compound);
    }

    public void readColorsFromNBT(NBTTagCompound compound) {
        for (int i = 0; i < 2; i++) {
            if (compound.hasKey("color" + i)) {
                this.setColor(i, compound.getInteger("color" + i));
            }
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeColorsToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readColorsFromNBT(pkt.getNbtCompound());
    }

    public int getColor(int index) {
        return index == 0 ? color0 : (index == 1 ? color1 : 0xFFFFFF);
    }

    public void setColor(int index, int color) {
        if (index == 0) {
            this.color0 = color;
        } else if (index == 1) {
            this.color1 = color;
        }
    }

}
