package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityEthernetWallOutlet extends TileEntityDevice {

    private String name = "Ethernet Wall Outlet";
    private boolean isRouterConnected = false;
    private boolean isDeviceConnected = false;

    @SideOnly(Side.CLIENT)
    public float rotation;

    @SideOnly(Side.CLIENT)
    public float prevRotation;

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        return null;
    }
}
