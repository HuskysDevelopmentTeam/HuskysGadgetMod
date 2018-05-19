package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.huskylib2.items.ItemMod;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class ItemComponent extends ItemMod {

    public ItemComponent(String componentName) {
        super(componentName, MOD_ID);
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    public static Item getComponentFromName(String name) {
        return Item.getByNameOrId(MOD_ID + ":" + name);
    }

}
