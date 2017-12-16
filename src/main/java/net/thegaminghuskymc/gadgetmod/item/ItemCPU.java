package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemCPU extends Item {

    public ItemCPU() {
        this.setUnlocalizedName("cpu");
        this.setRegistryName(Reference.MOD_ID, "cpu");
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
    }
}
