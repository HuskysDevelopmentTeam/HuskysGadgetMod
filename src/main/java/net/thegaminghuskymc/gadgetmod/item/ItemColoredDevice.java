package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.util.ItemUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemColoredDevice extends ItemDevice implements SubItems {

    public ItemColoredDevice(Block block) {
        super(block);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean getShareTag() {
        return false;
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        return ItemUtils.getModels(Objects.requireNonNull(this.block.getRegistryName()));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < 16; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        ItemUtils.addInformation(stack, tooltip);
    }
}