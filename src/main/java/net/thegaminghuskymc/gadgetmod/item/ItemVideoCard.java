package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemVideoCard extends Item {

    public ItemVideoCard() {
        this.setUnlocalizedName("video_card");
        this.setRegistryName(Reference.MOD_ID, "video_card");
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
    }
}
