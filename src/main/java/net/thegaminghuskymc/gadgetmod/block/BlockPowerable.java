package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityPowerable;

import javax.annotation.Nullable;

public class BlockPowerable extends BlockDevice {

    protected BlockPowerable() {
        super(Material.ANVIL);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return null;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        super.removedByPlayer(state, world, pos, player, willHarvest);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityPowerable) {
            TileEntityPowerable externalHarddrive = (TileEntityPowerable) tileEntity;

            NBTTagCompound tileEntityTag = new NBTTagCompound();
            externalHarddrive.writeToNBT(tileEntityTag);
            tileEntityTag.removeTag("powered");
            tileEntityTag.removeTag("online");
            tileEntityTag.removeTag("connected");

            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.getItemFromBlock(this), 1);
            drop.setTagCompound(compound);
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
}
