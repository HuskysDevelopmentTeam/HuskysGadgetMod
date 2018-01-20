package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBenchmarkStation;

import javax.annotation.Nullable;

public class BlockBenchmarkStation extends BlockDevice.Colored {

    public BlockBenchmarkStation() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.deviceBlocks);
        this.setUnlocalizedName("benchmark_station");
        this.setRegistryName(Reference.MOD_ID, "benchmark_station");
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {
        return new TileEntityBenchmarkStation();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }
}