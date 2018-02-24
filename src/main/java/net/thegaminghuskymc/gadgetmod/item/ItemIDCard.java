package net.thegaminghuskymc.gadgetmod.item;

import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.huskylib2.lib.items.ItemMod;

public class ItemIDCard extends ItemMod {

    public ItemIDCard() {
        super("id_card");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    @Override
    public String getPrefix() {
        return Reference.MOD_ID;
    }

    @Override
    public String getModNamespace() {
        return Reference.MOD_ID;
    }
}
