package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemiPhone extends Item {

    public ItemiPhone() {
        this.setUnlocalizedName("iPhone");
        this.setRegistryName(Reference.MOD_ID,"iphone");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
