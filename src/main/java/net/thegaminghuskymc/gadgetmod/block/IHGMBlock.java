package net.thegaminghuskymc.gadgetmod.block;

import net.hdt.huskylib2.interf.IModBlock;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.thegaminghuskymc.gadgetmod.Reference;

public interface IHGMBlock extends IModBlock {

    @Override
    default String getModNamespace() {
        return Reference.MOD_ID;
    }

    @Override
    default EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

}
