package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.Reference;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

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
        NonNullList<ResourceLocation> models = NonNullList.withSize(16, new ResourceLocation(this.block.getRegistryName().toString()));
        return models;
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
        EnumDyeColor color = EnumDyeColor.byMetadata(stack.getMetadata());
        TextFormatting tf = TextFormatting.WHITE;
        try {
            Field f = EnumDyeColor.class.getDeclaredField("chatColor");
            f.setAccessible(true);
            tf = (TextFormatting) f.get(color == EnumDyeColor.MAGENTA ? EnumDyeColor.PINK : color);
        } catch (Exception e) {
        }
        String colorName = color.getName().replace("_", " ");
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + TextFormatting.BOLD + tf.toString() + colorName);
    }
}