package net.thegaminghuskymc.gadgetmod.item;

import net.hdt.huskylib2.item.ItemMod;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;

public class ItemIDCard extends ItemMod implements IHGMItem {

    public ItemIDCard() {
        super("id_card");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

}
