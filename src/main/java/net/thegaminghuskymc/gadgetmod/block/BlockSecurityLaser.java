package net.thegaminghuskymc.gadgetmod.block;

import net.hdt.huskylib2.block.BlockFacing;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.DamageSourceFence;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.GadgetSounds;
import net.thegaminghuskymc.gadgetmod.object.Bounds;
import net.thegaminghuskymc.gadgetmod.util.CollisionHelper;

import java.util.List;

public class BlockSecurityLaser extends BlockFacing implements ITickable, IHGMBlock {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625);

    private static final AxisAlignedBB COLLISION_BOX = CollisionHelper.getBlockBounds(EnumFacing.NORTH, new Bounds(0.5625, 0.0, 0.4375, 1.0, 1.0, 0.5625));

    public DamageSource electricFence = new DamageSourceFence("laser");

    public BlockSecurityLaser() {
        super("laser", Material.IRON);
        this.setHardness(1.0F);
        this.setLightLevel(0.2F);
    }

    @Override
    public String getPrefix() {
        return Reference.MOD_ID;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
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
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (!(entity instanceof EntityItem) && !entity.getName().equals("unknown"))
        {
            if (entity instanceof EntityCreeper)
            {
                EntityCreeper creeper = (EntityCreeper) entity;
                EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false);
                if(!creeper.getPowered())
                {
                    creeper.setFire(1);
                    creeper.onStruckByLightning(lightning);
                }
            }
            else if (entity instanceof EntityPlayer)
            {
                if (!((EntityPlayer) entity).capabilities.isCreativeMode)
                {
                    entity.attackEntityFrom(this.electricFence, (int) 2.0F);
                    world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, GadgetSounds.ZAP, SoundCategory.BLOCKS, 0.2F, 1.0F);

                    this.sparkle(world, pos);
                }
            }
            else
            {
                entity.attackEntityFrom(this.electricFence, (int) 2.0F);
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, GadgetSounds.ZAP, SoundCategory.BLOCKS, 0.2F, 1.0F);
                this.sparkle(world, pos);
            }
        }
    }

    @Override
    public void update() {
        Minecraft.getMinecraft().world.playSound(null, Minecraft.getMinecraft().player.posX + 0.5D, Minecraft.getMinecraft().player.posY + 0.5D, Minecraft.getMinecraft().player.posZ + 0.5D, GadgetSounds.LASER, SoundCategory.BLOCKS, 0.2F, 1.0F);
    }

    private void sparkle(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double d1 = (pos.getX() + RANDOM.nextFloat());
            double d2 = (pos.getY() + RANDOM.nextFloat());
            double d3 = (pos.getZ() + RANDOM.nextFloat());

            if (l == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                d2 = (pos.getY() + 1) + d0;
            }

            if (l == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube(state))
            {
                d2 = (pos.getY()) - d0;
            }

            if (l == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube(state))
            {
                d3 = (pos.getZ() + 1) + d0;
            }

            if (l == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube(state))
            {
                d3 = (pos.getZ()) - d0;
            }

            if (l == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube(state))
            {
                d1 = (pos.getX() + 1) + d0;
            }

            if (l == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube(state))
            {
                d1 = (pos.getX()) - d0;
            }

            if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
