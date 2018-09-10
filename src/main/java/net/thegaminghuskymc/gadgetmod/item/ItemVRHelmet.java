package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemVRHelmet extends Item {

    public ItemVRHelmet() {
        this.setTranslationKey("vr_helmet");
        this.setRegistryName(Reference.MOD_ID, "vr_helmet");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {

        if (entity.getArmorInventoryList().equals(1)) {
            setContainerItem(this);
            return true;
        }

        return false;
    }

}
