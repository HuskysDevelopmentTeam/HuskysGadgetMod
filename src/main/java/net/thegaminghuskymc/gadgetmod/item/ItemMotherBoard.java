package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMotherBoard extends Item {

    public ItemMotherBoard() {
        this.setTranslationKey("mother_board");
        this.setRegistryName(Reference.MOD_ID, "mother_board");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tag = stack.getTagCompound();
        if (!GuiScreen.isShiftKeyDown()) {
            tooltip.add("CPU: " + getComponentStatus(tag, "cpu"));
            tooltip.add("RAM: " + getComponentStatus(tag, "ram"));
            tooltip.add("GPU: " + getComponentStatus(tag, "gpu"));
            tooltip.add("WIFI: " + getComponentStatus(tag, "wifi"));
            tooltip.add(TextFormatting.YELLOW + "Hold shift for help");
        } else {
            tooltip.add("To add the required components");
            tooltip.add("place the motherboard and the");
            tooltip.add("corresponding component into a");
            tooltip.add("crafting table to combine them.");
        }
    }

    private String getComponentStatus(NBTTagCompound tag, String component) {
        if (tag != null && tag.hasKey("components", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound components = tag.getCompoundTag("components");
            if (components.hasKey(component, Constants.NBT.TAG_BYTE)) {
                return TextFormatting.GREEN + "Added";
            }
        }
        return TextFormatting.RED + "Missing";
    }

    public static class Component extends ItemComponent {
        public Component(String id) {
            super(id);
        }
    }

    public static class ColoredComponent extends ItemColoredComponent {
        public ColoredComponent(String id, EnumDyeColor color) {
            super(id, color);
        }
    }

}
