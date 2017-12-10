package net.thegaminghuskymc.gadgetmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.thegaminghuskymc.gadgetmod.init.DeviceBlocks;

public class DeviceTab extends CreativeTabs {
    public DeviceTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(DeviceBlocks.laptop);
    }
}
