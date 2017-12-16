package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.object.Bounds;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityScreen;
import net.thegaminghuskymc.gadgetmod.util.CollisionHelper;
import net.thegaminghuskymc.gadgetmod.util.TileEntityUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockScreen extends BlockHorizontal implements ITileEntityProvider {

    private static final PropertyEnum TYPE = PropertyEnum.create("type", Type.class);

    private static final Bounds BODY_BOUNDS = new Bounds(4 * 0.0625, 0.0, 2 * 0.0625, 12 * 0.0625, 2 * 0.0625, 14 * 0.0625);
    private static final AxisAlignedBB BODY_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, BODY_BOUNDS);
    private static final AxisAlignedBB BODY_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, BODY_BOUNDS);
    private static final AxisAlignedBB[] BODY_BOUNDING_BOX = { BODY_BOX_SOUTH, BODY_BOX_WEST, BODY_BOX_NORTH, BODY_BOX_EAST };

    private static final Bounds SELECTION_BOUNDS = new Bounds(3 * 0.0625, 0.0, 1 * 0.0625, 13 * 0.0625, 3 * 0.0625, 15 * 0.0625);
    private static final AxisAlignedBB SELECTION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, SELECTION_BOUNDS);
    private static final AxisAlignedBB SELECTION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, SELECTION_BOUNDS);
    private static final AxisAlignedBB[] SELECTION_BOUNDING_BOX = { SELECTION_BOX_SOUTH, SELECTION_BOX_WEST, SELECTION_BOX_NORTH, SELECTION_BOX_EAST };

    public BlockScreen() {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.LONELY));
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
        this.setUnlocalizedName("screen");
        this.setRegistryName(Reference.MOD_ID, "screen");
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SELECTION_BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, BODY_BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()]);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityScreen)
        {
            TileEntityScreen laptop = (TileEntityScreen) tileEntity;

            NBTTagCompound tileEntityTag = new NBTTagCompound();
            laptop.writeToNBT(tileEntityTag);
            tileEntityTag.removeTag("x");
            tileEntityTag.removeTag("y");
            tileEntityTag.removeTag("z");
            tileEntityTag.removeTag("id");

            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.getItemFromBlock(this));
            drop.setTagCompound(compound);

            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(TYPE, Type.LONELY);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityScreen();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(TYPE, Type.LONELY);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    public enum Type implements IStringSerializable
    {
        CORNER_TOP_LEFT, CORNER_TOP_RIGHT, CORNER_BOTTOM_LEFT, CORNER_BOTTOM_RIGHT, MIDDLE_BOTTOM, MIDDLE_TOP, MIDDLE_LEFT, MIDDLE_RIGHT, MIDDLE, LONELY;

        @Override
        public String getName()
        {
            return name().toLowerCase();
        }

    }

}
