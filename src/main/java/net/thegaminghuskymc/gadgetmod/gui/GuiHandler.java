package net.thegaminghuskymc.gadgetmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.thegaminghuskymc.gadgetmod.core.Console;
import net.thegaminghuskymc.gadgetmod.core.Desktop;
import net.thegaminghuskymc.gadgetmod.core.Server;
import net.thegaminghuskymc.gadgetmod.core.TestLaptop;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_ELECTRONIC_TABLE = 0;
    public static final int GUI_ELECTRONIC_CRAFTER = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == TestLaptop.ID) {
            TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof TileEntityLaptop) {
                return new TestLaptop();
            }
        }
        if (ID == Desktop.ID) {
            TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof TileEntityLaptop) {
                return new Desktop();
            }
        }
        if (ID == Server.ID) {
            TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof TileEntityLaptop) {
                return new Server();
            }
        }
        if (ID == Console.ID) {
            TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof TileEntityLaptop) {
                return new Console();
            }
        }
        return null;
    }
}
