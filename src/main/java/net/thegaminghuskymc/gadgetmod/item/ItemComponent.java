package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.huskylib2.lib.items.ItemMod;

public class ItemComponent extends ItemMod {

    public ItemComponent(String componentName) {
        super(componentName);
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    public static Item getComponentFromName(String name) {
        return Item.getByNameOrId(name);
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
