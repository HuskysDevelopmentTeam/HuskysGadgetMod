package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemRamSticks extends Item {

    public ItemRamSticks() {
        this.setUnlocalizedName("ram");
        this.setRegistryName(Reference.MOD_ID, "ram_stick");
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
    }
}
