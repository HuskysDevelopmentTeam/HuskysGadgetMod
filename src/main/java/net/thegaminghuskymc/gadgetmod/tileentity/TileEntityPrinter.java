package net.thegaminghuskymc.gadgetmod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.DeviceConfig;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.block.BlockPrinter;
import net.thegaminghuskymc.gadgetmod.client.container.ContainerPrinter;
import net.thegaminghuskymc.gadgetmod.client.container.SlotInkCartridge;
import net.thegaminghuskymc.gadgetmod.enums.EnumComponents;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.init.GadgetSounds;
import net.thegaminghuskymc.gadgetmod.item.ItemComponent;
import net.thegaminghuskymc.gadgetmod.util.CollisionHelper;

import java.util.ArrayDeque;
import java.util.Deque;

import static net.thegaminghuskymc.gadgetmod.tileentity.TileEntityPrinter.State.*;

public class TileEntityPrinter extends TileEntityNetworkDevice.Colored implements ISidedInventory {
    private static final int[] SLOTS = new int[]{0};
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
    private State state = IDLE;
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private NonNullList<ItemStack> printerItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int printTime;
    private int totalPrintTime;
    private int inkLevels;
    private String furnaceCustomName;
    private Deque<IPrint> printQueue = new ArrayDeque<>();
    private IPrint currentPrint;
    private int remainingPrintTime;
    private int paperCount = 0;

    public static void registerFixesFurnace(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityPrinter.class, "Items"));
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.printerItemStacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.printerItemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.printerItemStacks.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.printerItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.printerItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.printerItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.printerItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.totalPrintTime = this.getTotalPrintTime();
            this.printTime = 0;
            this.markDirty();
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container.printer";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_) {
        this.furnaceCustomName = p_145951_1_;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (remainingPrintTime > 0) {
                if (remainingPrintTime % 20 == 0 || state == LOADING_PAPER) {
                    pipeline.setInteger("remainingPrintTime", remainingPrintTime);
                    sync();
                    if (remainingPrintTime != 0 && state == PRINTING) {
                        world.playSound(null, pos, GadgetSounds.PRINTER_PRINTING, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    }
                }
                remainingPrintTime--;
            } else {
                setState(state.next());
            }
        }

        if (state == IDLE && remainingPrintTime == 0 && currentPrint != null) {
            if (!world.isRemote) {
                IBlockState state = world.getBlockState(pos);
                double[] fixedPosition = CollisionHelper.fixRotation(state.getValue(BlockPrinter.FACING), 0.15, 0.5, 0.15, 0.5);
                EntityItem entity = new EntityItem(world, pos.getX() + fixedPosition[0], pos.getY() + 0.0625, pos.getZ() + fixedPosition[1], IPrint.generateItem(currentPrint));
                entity.motionX = 0;
                entity.motionY = 0;
                entity.motionZ = 0;
                world.spawnEntity(entity);
            }
            currentPrint = null;
        }

        if (state == IDLE && currentPrint == null && !printQueue.isEmpty() && paperCount > 0) {
            print(printQueue.poll());
        }
    }

    @Override
    public String getDeviceName() {
        return "Printer";
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("currentPrint", Constants.NBT.TAG_COMPOUND)) {
            currentPrint = IPrint.loadFromTag(compound.getCompoundTag("currentPrint"));
        }
        if (compound.hasKey("totalPrintTime", Constants.NBT.TAG_INT)) {
            totalPrintTime = compound.getInteger("totalPrintTime");
        }
        if (compound.hasKey("remainingPrintTime", Constants.NBT.TAG_INT)) {
            remainingPrintTime = compound.getInteger("remainingPrintTime");
        }
        if (compound.hasKey("state", Constants.NBT.TAG_INT)) {
            state = State.values()[compound.getInteger("state")];
        }
        if (compound.hasKey("paperCount", Constants.NBT.TAG_INT)) {
            paperCount = compound.getInteger("paperCount");
        }
        if (compound.hasKey("queue", Constants.NBT.TAG_LIST)) {
            printQueue.clear();
            NBTTagList queue = compound.getTagList("queue", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < queue.tagCount(); i++) {
                IPrint print = IPrint.loadFromTag(queue.getCompoundTagAt(i));
                printQueue.offer(print);
            }
        }
        this.printerItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.printerItemStacks);
        this.printTime = compound.getInteger("printTime");
        this.totalPrintTime = compound.getInteger("printTimeTotal");
        this.inkLevels = compound.getInteger("inkLevels");

        if (compound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("totalPrintTime", totalPrintTime);
        compound.setInteger("remainingPrintTime", remainingPrintTime);
        compound.setInteger("state", state.ordinal());
        compound.setInteger("paperCount", paperCount);
        if (currentPrint != null) {
            compound.setTag("currentPrint", IPrint.writeToTag(currentPrint));
        }
        if (!printQueue.isEmpty()) {
            NBTTagList queue = new NBTTagList();
            printQueue.forEach(print -> {
                queue.appendTag(IPrint.writeToTag(print));
            });
            compound.setTag("queue", queue);
        }
        compound.setInteger("printTime", (short) this.printTime);
        compound.setInteger("inkLevels", (short) this.inkLevels);
        ItemStackHelper.saveAllItems(compound, this.printerItemStacks);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.furnaceCustomName);
        }
        return compound;
    }

    @Override
    public NBTTagCompound writeSyncTag() {
        NBTTagCompound tag = super.writeSyncTag();
        tag.setInteger("paperCount", paperCount);
        return tag;
    }

    public void setState(State newState) {
        if (newState == null)
            return;

        state = newState;
        if (state == PRINTING) {
            if (DeviceConfig.isOverridePrintSpeed()) {
                remainingPrintTime = DeviceConfig.getCustomPrintSpeed() * 20;
            } else {
                remainingPrintTime = currentPrint.speed() * 20;
            }
        } else {
            remainingPrintTime = state.animationTime;
        }
        totalPrintTime = remainingPrintTime;

        pipeline.setInteger("state", state.ordinal());
        pipeline.setInteger("totalPrintTime", totalPrintTime);
        pipeline.setInteger("remainingPrintTime", remainingPrintTime);
        sync();
    }

    public void addToQueue(IPrint print) {
        printQueue.offer(print);
    }

    private void print(IPrint print) {
        world.playSound(null, pos, GadgetSounds.PRINTER_LOADING_PAPER, SoundCategory.BLOCKS, 0.5F, 1.0F);

        setState(LOADING_PAPER);
        currentPrint = print;
        paperCount--;

        pipeline.setInteger("paperCount", paperCount);
        pipeline.setTag("currentPrint", IPrint.writeToTag(currentPrint));
        sync();
    }

    public boolean isLoading() {
        return state == LOADING_PAPER;
    }

    public boolean isPrinting() {
        return state == PRINTING;
    }

    public int getTotalPrintTime() {
        return totalPrintTime;
    }

    public int getRemainingPrintTime() {
        return remainingPrintTime;
    }

    public boolean addPaper(ItemStack stack, boolean addAll) {
        if (!stack.isEmpty() && stack.getItem() == Items.PAPER && paperCount < DeviceConfig.getMaxPaperCount()) {
            if (!addAll) {
                paperCount++;
                stack.shrink(1);
            } else {
                paperCount += stack.getCount();
                stack.setCount(Math.max(0, paperCount - 64));
                paperCount = Math.min(64, paperCount);
            }
            pipeline.setInteger("paperCount", paperCount);
            sync();
            world.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    public boolean hasPaper() {
        return paperCount > 0;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public IPrint getPrint() {
        return currentPrint;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.printerItemStacks.get(1);
            return SlotInkCartridge.isCartridge(stack) && itemstack.getItem() != ItemComponent.getComponentFromName("printer_cartridges");
        }
    }

    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return SLOTS;
        } else {
            return null;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    public String getGuiID() {
        return "hgm:printer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerPrinter(playerInventory, this);
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.printTime;
            case 1:
                return this.totalPrintTime;
            case 2:
                return this.inkLevels;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.printTime = value;
                break;
            case 1:
                this.totalPrintTime = value;
                break;
            case 2:
                this.inkLevels = value;
        }
    }

    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        this.printerItemStacks.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

    public enum State {
        LOADING_PAPER(30), PRINTING(0), IDLE(0);

        final int animationTime;

        State(int time) {
            this.animationTime = time;
        }

        public State next() {
            if (ordinal() + 1 >= values().length)
                return null;
            return values()[ordinal() + 1];
        }
    }
}