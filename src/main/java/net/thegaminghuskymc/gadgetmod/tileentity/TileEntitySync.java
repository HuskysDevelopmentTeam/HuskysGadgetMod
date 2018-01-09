package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.thegaminghuskymc.gadgetmod.util.TileEntityUtil;

/**
 * Author: MrCrayfish
 */
public abstract class TileEntitySync extends TileEntity {
    protected NBTTagCompound pipeline = new NBTTagCompound();

    public void sync() {
        TileEntityUtil.markBlockForUpdate(world, pos);
        markDirty();
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        if (!pipeline.hasNoTags()) {
            NBTTagCompound updateTag = super.writeToNBT(pipeline);
            pipeline = new NBTTagCompound();
            return updateTag;
        }
        return super.writeToNBT(writeSyncTag());
    }

    public abstract NBTTagCompound writeSyncTag();

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    public NBTTagCompound getPipeline() {
        return pipeline;
    }
}