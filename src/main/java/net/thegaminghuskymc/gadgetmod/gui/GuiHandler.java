package net.thegaminghuskymc.gadgetmod.gui;

import net.thegaminghuskymc.gadgetmod.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Laptop.ID) {
            TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof TileEntityLaptop) {
                TileEntityLaptop laptop = (TileEntityLaptop) tileEntity;
                return new Laptop(laptop);
            }
        }
        return null;
    }
}
