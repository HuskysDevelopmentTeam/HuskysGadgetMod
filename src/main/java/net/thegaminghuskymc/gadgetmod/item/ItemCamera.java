package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemCamera extends Item {

    public ItemCamera() {
        this.setUnlocalizedName("camera");
        this.setRegistryName(Reference.MOD_ID, "camera");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
