package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemWiFiCard extends Item {

    public ItemWiFiCard() {
        this.setUnlocalizedName("wifi_card");
        this.setRegistryName(Reference.MOD_ID, "wifi_card");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
