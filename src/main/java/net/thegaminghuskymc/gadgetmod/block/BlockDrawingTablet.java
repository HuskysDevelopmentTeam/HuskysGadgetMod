package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDrawingTablet;
import net.thegaminghuskymc.gadgetmod.util.Colorable;

import javax.annotation.Nullable;

public class BlockDrawingTablet extends BlockDevice implements ITileEntityProvider {

    public BlockDrawingTablet() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.deviceDecoration);
        this.setUnlocalizedName("drawing_tablet");
        this.setRegistryName(Reference.MOD_ID, "drawing_tablet");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDrawingTablet();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof Colorable) {
            Colorable colorable = (Colorable) tileEntity;
            state = state.withProperty(BlockColored.COLOR, colorable.getColor());
        }
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }
}