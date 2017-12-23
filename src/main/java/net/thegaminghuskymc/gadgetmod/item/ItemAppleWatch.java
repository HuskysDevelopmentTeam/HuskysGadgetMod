package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemAppleWatch extends Item {

    public ItemAppleWatch() {
        this.setUnlocalizedName("apple_watch");
        this.setRegistryName(Reference.MOD_ID, "apple_watch");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
