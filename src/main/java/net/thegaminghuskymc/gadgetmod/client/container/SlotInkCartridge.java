package net.thegaminghuskymc.gadgetmod.client.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.thegaminghuskymc.gadgetmod.enums.EnumComponents;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.item.ItemComponent;

public class SlotInkCartridge extends Slot {
    public SlotInkCartridge(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
    }

    public static boolean isCartridge(ItemStack stack) {
        return stack.getItem() == ItemComponent.getComponentFromName("printer_cartridges");
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) {
        return isCartridge(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return isCartridge(stack) ? 1 : super.getItemStackLimit(stack);
    }
}