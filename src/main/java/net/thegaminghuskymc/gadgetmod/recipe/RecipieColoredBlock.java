package net.thegaminghuskymc.gadgetmod.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;

public class RecipieColoredBlock extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private Block block;

    public RecipieColoredBlock() {
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "cut_paper"));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv
     * @param worldIn
     */
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack iron = ItemStack.EMPTY;
        ItemStack redstone = ItemStack.EMPTY;
        ItemStack enderpearl = ItemStack.EMPTY;
        ItemStack wool = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.IRON_INGOT) {
                    if (!iron.isEmpty()) return false;
                    iron = stack;
                }

                if (stack.getItem() == Items.REDSTONE) {
                    if (!redstone.isEmpty()) return false;
                    redstone = stack;
                }

                if (stack.getItem() == Items.ENDER_PEARL) {
                    if (!enderpearl.isEmpty()) return false;
                    enderpearl = stack;
                }

                if (stack.getItem() == Item.getItemFromBlock(Blocks.WOOL)) {
                    if (!wool.isEmpty()) return false;
                    wool = stack;
                }
            }
        }

        return !iron.isEmpty() && !redstone.isEmpty() && !enderpearl.isEmpty() && !wool.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv
     */
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

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param width
     * @param height
     */
    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return null;
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public String getGroup() {
        return null;
    }
}
