package net.husky.device.block;

import net.husky.device.MrCrayfishDeviceMod;
import net.husky.device.core.Laptop;
import net.husky.device.init.DeviceItems;
import net.husky.device.tileentity.TileEntityLaptop;
import net.husky.device.util.TileEntityUtil;
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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLaptop extends BlockHorizontal implements ITileEntityProvider
{
	public static final PropertyEnum TYPE = PropertyEnum.create("type", Type.class);

    private static final AxisAlignedBB COLLISION_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.6, 1.0);

    public BlockLaptop()
    {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.BASE));
        this.setCreativeTab(MrCrayfishDeviceMod.tabDevice);
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
		return COLLISION_BOX;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		return state.withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntityLaptop)
		{
			TileEntityLaptop laptop = (TileEntityLaptop) tileEntity;

			if(playerIn.isSneaking())
			{
				if(!worldIn.isRemote)
				{
					laptop.openClose();
				}
			}
			else
			{
				if(side == state.getValue(FACING).rotateYCCW())
				{
					ItemStack heldItem = playerIn.getHeldItem(hand);
					if(!heldItem.isEmpty() && heldItem.getItem() == DeviceItems.flash_drive)
					{
						if(!worldIn.isRemote)
						{
							if(laptop.getFileSystem().setAttachedDrive(heldItem.copy()))
							{
								heldItem.shrink(1);
								TileEntityUtil.markBlockForUpdate(worldIn, pos);
							}
							else
							{
								playerIn.sendMessage(new TextComponentString("No more available USB slots!"));
							}
						}
						return true;
					}

					else if(!heldItem.isEmpty() && heldItem.getItem() == DeviceItems.apple_watch)
                    {
                        if(!worldIn.isRemote)
                        {
                            if(laptop.getFileSystem().setAttachedDrive(heldItem.copy()))
                            {
                                heldItem.shrink(1);
                                TileEntityUtil.markBlockForUpdate(worldIn, pos);
                            }
                            else
                            {
                                playerIn.sendMessage(new TextComponentString("No more available USB slots!"));
                            }
                        }
                        return true;
                    }

                    else if(!heldItem.isEmpty() && heldItem.getItem() == DeviceItems.iPad)
                    {
                        if(!worldIn.isRemote)
                        {
                            if(laptop.getFileSystem().setAttachedDrive(heldItem.copy()))
                            {
                                heldItem.shrink(1);
                                TileEntityUtil.markBlockForUpdate(worldIn, pos);
                            }
                            else
                            {
                                playerIn.sendMessage(new TextComponentString("No more available USB slots!"));
                            }
                        }
                        return true;
                    }

                    else if(!heldItem.isEmpty() && heldItem.getItem() == DeviceItems.iPhone)
                    {
                        if(!worldIn.isRemote)
                        {
                            if(laptop.getFileSystem().setAttachedDrive(heldItem.copy()))
                            {
                                heldItem.shrink(1);
                                TileEntityUtil.markBlockForUpdate(worldIn, pos);
                            }
                            else
                            {
                                playerIn.sendMessage(new TextComponentString("No more available USB slots!"));
                            }
                        }
                        return true;
                    }

					if(!worldIn.isRemote)
					{
						ItemStack stack = laptop.getFileSystem().removeAttachedDrive();
						if(stack != null)
						{
							BlockPos summonPos = pos.offset(state.getValue(FACING).rotateYCCW());
							worldIn.spawnEntity(new EntityItem(worldIn, summonPos.getX() + 0.5, summonPos.getY(), summonPos.getZ() + 0.5, stack));
							TileEntityUtil.markBlockForUpdate(worldIn, pos);
						}
					}
					return true;
				}

				if(laptop.open && worldIn.isRemote)
				{
					playerIn.openGui(MrCrayfishDeviceMod.instance, Laptop.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(TYPE, Type.BASE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLaptop();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(TYPE, Type.BASE);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, TYPE);
	}

	public static enum Type implements IStringSerializable
	{
		BASE, SCREEN;

		@Override
		public String getName()
		{
			return name().toLowerCase();
		}

	}
}