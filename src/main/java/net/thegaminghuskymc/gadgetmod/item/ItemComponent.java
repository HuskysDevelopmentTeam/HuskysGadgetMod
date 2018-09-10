package net.thegaminghuskymc.gadgetmod.item;

import net.hdt.huskylib2.item.ItemMod;
import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class ItemComponent extends ItemMod implements IHGMItem {

    public ItemComponent(String componentName) {
        super(componentName, MOD_ID);
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    public static Item getComponentFromName(String name) {
        return Item.getByNameOrId(MOD_ID + ":" + name);
    }

}
