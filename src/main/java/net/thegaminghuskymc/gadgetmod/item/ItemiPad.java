package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemiPad extends Item {

    public ItemiPad() {
        this.setUnlocalizedName("iPad");
        this.setRegistryName(Reference.MOD_ID, "ipad");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
