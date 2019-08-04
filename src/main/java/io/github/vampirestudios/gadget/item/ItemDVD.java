package io.github.vampirestudios.gadget.item;

import net.minecraft.item.Item;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.Reference;

public class ItemDVD extends Item {

    public ItemDVD() {
        this.setTranslationKey("dvd");
        this.setRegistryName(Reference.MOD_ID, "dvd");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
