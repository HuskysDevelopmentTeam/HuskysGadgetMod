package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityServer;

import javax.annotation.Nullable;

public class BlockServer extends BlockHorizontal implements ITileEntityProvider {

    public BlockServer() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
        this.setUnlocalizedName("server");
        this.setRegistryName(Reference.MOD_ID, "server");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityServer();
    }

}