package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

/**
 * Author: MrCrayfish
 */
public class ItemFlashDrive extends Item
{
    public ItemFlashDrive()
    {
        this.setUnlocalizedName("flash_drive");
        this.setRegistryName(Reference.MOD_ID, "flash_drive");
        this.setCreativeTab(HuskyGadgetMod.tabDevice);
    }
}
