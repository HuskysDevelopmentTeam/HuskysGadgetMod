package io.github.vampirestudios.gadget.item;

import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemIDCard extends Item {

    public ItemIDCard() {
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "id_card"));
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

}
