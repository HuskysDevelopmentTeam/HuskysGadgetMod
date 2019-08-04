//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.gadget.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockFacing extends BlockModContainer {
    public static final PropertyDirection FACING;

    public BlockFacing(String name, Material materialIn) {
        super(name, materialIn);
        this.setDefaultState(this.makeDefaultState());
    }

    public IBlockState makeDefaultState() {
        return this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState thisState) {
        if (!worldIn.isRemote) {
            IBlockState state = worldIn.getBlockState(pos.north());
            IBlockState state1 = worldIn.getBlockState(pos.south());
            IBlockState state2 = worldIn.getBlockState(pos.west());
            IBlockState state3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = thisState.getValue(FACING);
            if (enumfacing == EnumFacing.NORTH && state.isFullBlock() && !state1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && state1.isFullBlock() && !state.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && state2.isFullBlock() && !state3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && state3.isFullBlock() && !state2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, thisState.withProperty(FACING, enumfacing), 2);
        }

    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.fromAngle(meta);
        if (enumfacing.getAxis() == Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    static {
        FACING = PropertyDirection.create("facing", Plane.HORIZONTAL);
    }
}
