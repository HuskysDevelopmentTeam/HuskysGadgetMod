package io.github.vampirestudios.gadget.item;

import net.minecraft.item.Item;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.Reference;

public class ItemCD extends Item {

    public ItemCD() {
        this.setTranslationKey("cd");
        this.setRegistryName(Reference.MOD_ID, "cd");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
