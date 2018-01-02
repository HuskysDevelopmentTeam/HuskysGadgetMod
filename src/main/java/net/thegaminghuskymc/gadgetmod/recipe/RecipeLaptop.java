package net.thegaminghuskymc.gadgetmod.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;

public class RecipeLaptop extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public RecipeLaptop() {
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "laptop"));
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack redstone = ItemStack.EMPTY;
        ItemStack iron_ingot = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.REDSTONE) {
                    if (!redstone.isEmpty()) return false;
                    redstone = stack;
                }

                if (stack.getItem() == Items.IRON_INGOT) {
                    if (!iron_ingot.isEmpty()) return false;
                    iron_ingot = stack;
                }
            }
        }

        return !redstone.isEmpty() && !iron_ingot.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack laptop = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Item.getItemFromBlock(GadgetBlocks.LAPTOP)) {
                    if (!laptop.isEmpty()) return ItemStack.EMPTY;
                    laptop = stack;
                }
            }
        }

        if (!laptop.isEmpty() && laptop.hasTagCompound()) {
            ItemStack result = new ItemStack(GadgetBlocks.LAPTOP);

            NBTTagCompound tag = laptop.getTagCompound();
            if (!tag.hasKey("open")) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("device_name", Constants.NBT.TAG_STRING)) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("system_data", Constants.NBT.TAG_COMPOUND)) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("application_data", Constants.NBT.TAG_COMPOUND)) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("file_system")) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("has_external_drive")) {
                return ItemStack.EMPTY;
            }

            if (!tag.hasKey("color", Constants.NBT.TAG_BYTE)) {
                return ItemStack.EMPTY;
            }

            result.setTagCompound(tag);

            return result;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean isDynamic() {
        return true;
    }
}
