package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityRobot;

import javax.annotation.Nullable;

public class BlockRobot extends BlockHorizontal implements ITileEntityProvider {

    public BlockRobot() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
        this.setUnlocalizedName("robot");
        this.setRegistryName(Reference.MOD_ID, "robot");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRobot();
    }

}