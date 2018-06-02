package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.hdt.huskylib2.blocks.BlockFacing;

public class BlockGreenScreen extends BlockFacing {

    public BlockGreenScreen() {
        super(Material.ANVIL, Reference.MOD_ID, "green_screen");
        this.setCreativeTab(HuskyGadgetMod.deviceBlocks);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public String getPrefix() {
        return Reference.MOD_ID;
    }

}
