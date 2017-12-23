package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemMotherBoard extends Item {

    public ItemMotherBoard() {
        this.setUnlocalizedName("mother_board");
        this.setRegistryName(Reference.MOD_ID, "mother_board");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
