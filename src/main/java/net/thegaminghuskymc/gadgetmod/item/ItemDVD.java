package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemDVD extends Item {

    public ItemDVD() {
        this.setTranslationKey("dvd");
        this.setRegistryName(Reference.MOD_ID, "dvd");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
