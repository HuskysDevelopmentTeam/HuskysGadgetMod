package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.util.ItemUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemInternalHarddrive extends Item implements SubItems {

    public ItemInternalHarddrive() {
        this.setUnlocalizedName("internal_harddrive");
        this.setRegistryName(Reference.MOD_ID, "internal_harddrive");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (EnumDyeColor color : EnumDyeColor.values()) {
                items.add(new ItemStack(this, 1, color.getMetadata()));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        ItemUtils.addInformation(stack, tooltip);
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        return ItemUtils.getModels(Objects.requireNonNull(getRegistryName()));
    }
}
