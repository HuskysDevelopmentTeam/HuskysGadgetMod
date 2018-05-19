package net.thegaminghuskymc.gadgetmod.item;

import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.huskylib2.items.ItemMod;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class ItemIDCard extends ItemMod {

    public ItemIDCard() {
        super("id_card", MOD_ID);
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

}
