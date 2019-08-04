package io.github.vampirestudios.gadget.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.init.GadgetItems;
import io.github.vampirestudios.gadget.item.ItemMotherBoard;

public class RecipeMotherboard extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public RecipeMotherboard() {
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "motherboard_components"));
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack motherboard = ItemStack.EMPTY;
        ItemStack component = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == GadgetItems.motherBoard) {
                    if (!motherboard.isEmpty())
                        return false;
                    motherboard = stack;
                } else if (stack.getItem() instanceof ItemMotherBoard.Component) {
                    if (!component.isEmpty())
                        return false;
                    component = stack;
                } else {
                    return false;
                }
            }
        }

        return !motherboard.isEmpty() && !component.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack motherboard = ItemStack.EMPTY;
        ItemStack component = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == GadgetItems.motherBoard) {
                    if (!motherboard.isEmpty())
                        return null;
                    motherboard = stack;
                } else if (stack.getItem() instanceof ItemMotherBoard.Component) {
                    if (!component.isEmpty())
                        return null;
                    component = stack;
                } else {
                    return null;
                }
            }
        }

        if (!motherboard.isEmpty() && !component.isEmpty()) {
            NBTTagCompound originalTag = motherboard.getTagCompound();
            if (originalTag != null && originalTag.hasKey("components", Constants.NBT.TAG_COMPOUND)) {
                NBTTagCompound tag = originalTag.getCompoundTag("components");
                if (tag.hasKey(component.getTranslationKey().substring(5), Constants.NBT.TAG_BYTE)) {
                    return null;
                }
            }

            ItemStack result = motherboard.copy();
            if (!result.hasTagCompound()) {
                result.setTagCompound(new NBTTagCompound());
            }

            NBTTagCompound itemTag = result.getTagCompound();
            if (itemTag != null) {
                if (!itemTag.hasKey("components", Constants.NBT.TAG_COMPOUND)) {
                    itemTag.setTag("components", new NBTTagCompound());
                }

                NBTTagCompound components = itemTag.getCompoundTag("components");
                components.setByte(component.getTranslationKey().substring(5), (byte) 0);
                return result;
            }
        }

        return null;
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