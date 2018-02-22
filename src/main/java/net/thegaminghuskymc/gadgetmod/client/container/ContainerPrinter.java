package net.thegaminghuskymc.gadgetmod.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

public class ContainerPrinter extends Container {

    private final IInventory tilePrinter;
    private int printTime, totalPrintTime, inkLevels;

    public ContainerPrinter(InventoryPlayer playerInventory, IInventory printerInventory) {
        this.tilePrinter = printerInventory;

        for (int ink = 0; ink < 9; ink++) {
            this.addSlotToContainer(new SlotInkCartridge(printerInventory, ink, 8 + ink * 18, 17));
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tilePrinter);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener icontainerlistener : this.listeners) {
            if (this.printTime != this.tilePrinter.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tilePrinter.getField(2));
            }

            if (this.inkLevels != this.tilePrinter.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tilePrinter.getField(0));
            }

            if (this.totalPrintTime != this.tilePrinter.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tilePrinter.getField(3));
            }
        }

        this.printTime = this.tilePrinter.getField(2);
        this.inkLevels = this.tilePrinter.getField(0);
        this.totalPrintTime = this.tilePrinter.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tilePrinter.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tilePrinter.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

}
