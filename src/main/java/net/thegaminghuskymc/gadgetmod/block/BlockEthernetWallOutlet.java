package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.object.Bounds;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityEthernetWallOutlet;
import net.thegaminghuskymc.gadgetmod.util.CollisionHelper;
import net.thegaminghuskymc.gadgetmod.util.Colorable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockEthernetWallOutlet extends BlockDevice implements ITileEntityProvider {

    private static final Bounds BODY_BOUNDS = new Bounds(0.0, 0.0, 0.0, 1.0, 1.0, 2.0);
    private static final AxisAlignedBB BODY_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, BODY_BOUNDS);
    private static final AxisAlignedBB[] BODY_BOUNDING_BOX = {BODY_BOX_SOUTH, BODY_BOX_WEST, BODY_BOX_NORTH, BODY_BOX_EAST};

    private static final Bounds SELECTION_BOUNDS = new Bounds(0.0, 0.0, 0.0, 1.0, 1.0, 2.0);
    private static final AxisAlignedBB SELECTION_BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, SELECTION_BOUNDS);
    private static final AxisAlignedBB[] SELECTION_BOUNDING_BOX = {SELECTION_BOUNDING_BOX_SOUTH, SELECTION_BOUNDING_BOX_WEST, SELECTION_BOUNDING_BOX_NORTH, SELECTION_BOUNDING_BOX_EAST};

    public BlockEthernetWallOutlet() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.deviceDecoration);
        this.setUnlocalizedName("ethernet_wall_outlet");
        this.setRegistryName(Reference.MOD_ID, "ethernet_wall_outlet");
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityEthernetWallOutlet) {
            TileEntityEthernetWallOutlet externalHarddrive = (TileEntityEthernetWallOutlet) tileEntity;

            NBTTagCompound tileEntityTag = new NBTTagCompound();
            externalHarddrive.writeToNBT(tileEntityTag);
            tileEntityTag.removeTag("x");
            tileEntityTag.removeTag("y");
            tileEntityTag.removeTag("z");
            tileEntityTag.removeTag("id");
            tileEntityTag.removeTag("color");

            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.getItemFromBlock(this));
            drop.setTagCompound(compound);

            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return SELECTION_BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        EnumFacing facing = state.getValue(FACING);
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, BODY_BOUNDING_BOX[facing.getHorizontalIndex()]);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEthernetWallOutlet();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }
}