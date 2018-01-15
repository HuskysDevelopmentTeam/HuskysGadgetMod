package net.thegaminghuskymc.gadgetmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.object.Bounds;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityMonitor;
import net.thegaminghuskymc.gadgetmod.util.CollisionHelper;
import net.thegaminghuskymc.gadgetmod.util.IColored;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockMonitor extends BlockDevice.Colored {

    private static final Bounds FEET_BOUNDS = new Bounds(5 * 0.0625, 0.0, 1 * 0.0625, 14 * 0.0625, 5 * 0.0625, 15 * 0.0625);
    private static final AxisAlignedBB FEET_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, FEET_BOUNDS);
    private static final AxisAlignedBB FEET_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, FEET_BOUNDS);
    private static final AxisAlignedBB FEET_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, FEET_BOUNDS);
    private static final AxisAlignedBB FEET_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, FEET_BOUNDS);
    private static final AxisAlignedBB[] FEET_BOUNDING_BOX = {FEET_BOX_SOUTH, FEET_BOX_WEST, FEET_BOX_NORTH, FEET_BOX_EAST};

    private static final Bounds SCREEN_BOUNDS = new Bounds(0.5 * 0.0625, 0.0, 3.5 * 0.0625, 5 * 0.0625, 1 * 0.0625, 12.5 * 0.0625);
    private static final AxisAlignedBB SCREEN_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, SCREEN_BOUNDS);
    private static final AxisAlignedBB SCREEN_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, SCREEN_BOUNDS);
    private static final AxisAlignedBB SCREEN_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, SCREEN_BOUNDS);
    private static final AxisAlignedBB SCREEN_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, SCREEN_BOUNDS);
    private static final AxisAlignedBB[] SCREEN_BOUNDING_BOX = {SCREEN_BOX_SOUTH, SCREEN_BOX_WEST, SCREEN_BOX_NORTH, SCREEN_BOX_EAST};

    private static final AxisAlignedBB SELECTION_BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

    public BlockMonitor() {
        super(Material.ANVIL);
        this.setCreativeTab(HuskyGadgetMod.deviceDecoration);
        this.setUnlocalizedName("monitor");
        this.setRegistryName(Reference.MOD_ID, "monitor");
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityMonitor();
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SELECTION_BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        EnumFacing facing = state.getValue(FACING);
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, FEET_BOUNDING_BOX[facing.getHorizontalIndex()]);
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, SCREEN_BOUNDING_BOX[facing.getHorizontalIndex()]);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMonitor) {
            TileEntityMonitor monitor = (TileEntityMonitor) tileEntity;

            if (playerIn.isSneaking()) {
                if (!worldIn.isRemote) {
                    monitor.powerUnpower();
                }
            } else {
                if (monitor.isPowered() && worldIn.isRemote) {
                    if(monitor.isComputerConnected()) {
                        playerIn.openGui(HuskyGadgetMod.instance, Laptop.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMonitor) {
            TileEntityMonitor monitor = (TileEntityMonitor) tileEntity;

            NBTTagCompound tileEntityTag = new NBTTagCompound();
            monitor.writeToNBT(tileEntityTag);
            tileEntityTag.removeTag("x");
            tileEntityTag.removeTag("y");
            tileEntityTag.removeTag("z");
            tileEntityTag.removeTag("id");
            byte color = tileEntityTag.getByte("color");
            tileEntityTag.removeTag("color");
            tileEntityTag.removeTag("powered");
            tileEntityTag.removeTag("connected");

            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.getItemFromBlock(this));
            drop.setItemDamage(15 - color);
            drop.setTagCompound(compound);

            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }
}