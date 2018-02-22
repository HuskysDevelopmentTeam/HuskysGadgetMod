package net.thegaminghuskymc.gadgetmod.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.item.ItemComponent;

import java.util.Objects;

public class RecipeLaptop extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private static String color;
    private static final String name = "laptop_" + color;

    public RecipeLaptop(String color) {
        RecipeLaptop.color = color;
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if (inv.getStackInSlot(0).isEmpty() || inv.getStackInSlot(0).getItem() != ItemComponent.getComponentFromName("plastic_frame"))
            return false;

        if (inv.getStackInSlot(1).isEmpty() || inv.getStackInSlot(1).getItem() != ItemComponent.getComponentFromName("computer_screen"))
            return false;

        if (inv.getStackInSlot(2).isEmpty() || inv.getStackInSlot(2).getItem() != ItemComponent.getComponentFromName("plastic_frame"))
            return false;

        if (inv.getStackInSlot(3).isEmpty() || inv.getStackInSlot(3).getItem() != ItemComponent.getComponentFromName("battery"))
            return false;

        if (inv.getStackInSlot(4).isEmpty() || inv.getStackInSlot(4).getItem() != GadgetItems.motherBoard)
            return false;

        if (inv.getStackInSlot(5).isEmpty() || inv.getStackInSlot(5).getItem() != ItemComponent.getComponentFromName("hard_drive"))
            return false;

        if (inv.getStackInSlot(6).isEmpty() || inv.getStackInSlot(6).getItem() != ItemComponent.getComponentFromName("plastic_frame"))
            return false;

        if (inv.getStackInSlot(7).isEmpty() || inv.getStackInSlot(7).getItem() != Items.DYE)
            return false;

        if (inv.getStackInSlot(8).isEmpty() || inv.getStackInSlot(8).getItem() != ItemComponent.getComponentFromName("plastic_frame"))
            return false;

        ItemStack motherboard = inv.getStackInSlot(4);
        NBTTagCompound tag = motherboard.getTagCompound();
        if (tag != null) {
            NBTTagCompound components = tag.getCompoundTag("components");
            return components.hasKey("cpu") && components.hasKey("ram") && components.hasKey("gpu") && components.hasKey("wifi");
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack dye = inv.getStackInSlot(7);
        return new ItemStack(Objects.requireNonNull(Block.getBlockFromName(name)), 1, 15 - dye.getMetadata());
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}