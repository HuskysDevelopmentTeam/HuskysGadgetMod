package net.thegaminghuskymc.gadgetmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.Console;
import net.thegaminghuskymc.gadgetmod.core.Desktop;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.Server;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBaseDevice;
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
    	TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));     
        if(tileEntity instanceof TileEntityBaseDevice && validateTE(ID, tileEntity)) {
        	return new BaseDevice((TileEntityBaseDevice)tileEntity, 0, null);
        }
        return null;
    }
    
    private boolean validateTE(int ID, TileEntity te) {
    	if(ID == Laptop.ID) {
    		return te instanceof TileEntityLaptop;
    	}/* else if(ID == Desktop.ID) {
    		//return te instanceof TileEntityDesktop;
    	} else if(ID == Server.ID) {
    		//return te instanceof TileEntityServer;
    	} else if(ID == Console.ID) {
    		//return te instanceof TileEntityConsole;
    	}*/
    	return false;
    }
    
}
