package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityMonitor;

import javax.annotation.Nullable;

public class BlockMonitor extends BlockHorizontal implements ITileEntityProvider {

    public BlockMonitor() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
        this.setUnlocalizedName("monitor");
        this.setRegistryName(Reference.MOD_ID, "monitor");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMonitor();
    }

}