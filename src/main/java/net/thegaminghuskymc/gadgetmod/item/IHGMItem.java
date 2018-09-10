package net.thegaminghuskymc.gadgetmod.item;

import net.hdt.huskylib2.interf.IVariantHolder;
import net.thegaminghuskymc.gadgetmod.Reference;

public interface IHGMItem extends IVariantHolder {

    @Override
    default String getModNamespace() {
        return Reference.MOD_ID;
    }

}