package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.EnumDyeColor;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.hdt.huskylib2.items.ItemColored;

public class ItemColoredComponent extends ItemColored {

    public ItemColoredComponent(String componentName, EnumDyeColor color) {
        super(Reference.MOD_ID, componentName, color);
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    @Override
    public String getPrefix() {
        return Reference.MOD_ID;
    }

}
