package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemSoundCard extends Item {

    public ItemSoundCard() {
        this.setUnlocalizedName("sound_card");
        this.setRegistryName(Reference.MOD_ID, "sound_card");
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
    }
}
