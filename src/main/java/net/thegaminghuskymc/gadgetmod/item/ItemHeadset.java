package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.util.ItemUtils;
import net.hdt.huskylib2.items.ItemColored;

import java.util.Objects;

public class ItemHeadset extends ItemColored implements SubItems {

    public ItemHeadset(EnumDyeColor color) {
        super(Reference.MOD_ID, "headset", color);
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {

        if (entity.getArmorInventoryList().equals(1)) {
            setContainerItem(this);
            return true;
        }

        return false;
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        return ItemUtils.getModels(Objects.requireNonNull(getRegistryName()));
    }
}
