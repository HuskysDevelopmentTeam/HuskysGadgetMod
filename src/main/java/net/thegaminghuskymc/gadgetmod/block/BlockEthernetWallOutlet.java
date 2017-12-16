package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

import javax.annotation.Nullable;

public class BlockEthernetWallOutlet extends BlockHorizontal implements ITileEntityProvider {

    public BlockEthernetWallOutlet() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
        this.setUnlocalizedName("ethernet_wall_outlet");
        this.setRegistryName(Reference.MOD_ID, "ethernet_wall_outlet");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

}