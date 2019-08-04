package io.github.vampirestudios.gadget.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import io.github.vampirestudios.gadget.core.Console;
import io.github.vampirestudios.gadget.core.Desktop;
import io.github.vampirestudios.gadget.core.Laptop;
import io.github.vampirestudios.gadget.core.Server;
import io.github.vampirestudios.gadget.tileentity.TileEntityDesktop;
import io.github.vampirestudios.gadget.tileentity.TileEntityLaptop;
import io.github.vampirestudios.gadget.tileentity.TileEntityPlaystation4Pro;
import io.github.vampirestudios.gadget.tileentity.TileEntityServer;

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
        if(tileEntity instanceof TileEntityLaptop && validateTE(ID, tileEntity)) {
        	return new Laptop();
        }
        if(tileEntity instanceof TileEntityDesktop && validateTE(ID, tileEntity)) {
            return new Desktop();
        }
        if(tileEntity instanceof TileEntityServer && validateTE(ID, tileEntity)) {
            return new Server();
        }
        if(tileEntity instanceof TileEntityPlaystation4Pro && validateTE(ID, tileEntity)) {
            return new Console();
        }
        return null;
    }
    
    private boolean validateTE(int ID, TileEntity te) {
    	if(ID == Laptop.ID) {
    		return te instanceof TileEntityLaptop;
    	} else if(ID == Desktop.ID) {
    		return te instanceof TileEntityDesktop;
    	} else if(ID == Server.ID) {
    		return te instanceof TileEntityServer;
    	} else if(ID == Console.ID) {
    		return te instanceof TileEntityPlaystation4Pro;
    	}
    	return false;
    }
    
}
