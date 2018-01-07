package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemCD extends Item {

    public ItemCD() {
        this.setUnlocalizedName("cd");
        this.setRegistryName(Reference.MOD_ID, "cd");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
