package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemServerKey extends Item {

    public ItemServerKey() {
        this.setUnlocalizedName("server_key");
        this.setRegistryName(Reference.MOD_ID, "server_key");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
