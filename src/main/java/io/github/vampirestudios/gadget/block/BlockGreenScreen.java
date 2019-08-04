package io.github.vampirestudios.gadget.block;

import io.github.vampirestudios.gadget.HuskyGadgetMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockGreenScreen extends BlockFacing {

    public BlockGreenScreen() {
        super("green_screen", Material.CARPET);
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

}
