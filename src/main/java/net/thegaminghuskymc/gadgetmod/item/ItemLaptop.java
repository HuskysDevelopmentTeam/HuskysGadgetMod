package net.thegaminghuskymc.gadgetmod.item;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemLaptop extends ItemBlock implements SubItems {
	
    public ItemLaptop(Block block) {
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
    	NonNullList<ResourceLocation> models = NonNullList.<ResourceLocation>withSize(16, new ResourceLocation(Reference.MOD_ID, "laptop"));
    	return models;
    }
    
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    	if(isInCreativeTab(tab)) {
    		for(int i = 0; i < 16; i++) {
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
        } catch(Exception e) { }
        String colorName = color.getName().replace("_", " ");
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + TextFormatting.BOLD.toString() + tf.toString() + colorName);
    }
    
}
