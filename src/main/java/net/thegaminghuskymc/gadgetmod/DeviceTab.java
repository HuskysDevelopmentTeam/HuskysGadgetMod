package net.thegaminghuskymc.gadgetmod;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class DeviceTab extends CreativeTabs {

    @SideOnly(Side.CLIENT)
    private String title = getTabLabel();
    private boolean hoveringButton = false;

    private ItemStack icon = ItemStack.EMPTY;
    private boolean displayRandom = true;
    private int tempIndex = 0;
    private ItemStack tempDisplayStack = ItemStack.EMPTY;

    public DeviceTab(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (this.displayRandom) {
            if (Minecraft.getSystemTime() % 120 == 0) {
                this.updateDisplayStack();
            }
            return this.tempDisplayStack;
        } else return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return hoveringButton ? title : getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    public void setTitle(String title) {
        this.title = title;
    }

    @SideOnly(Side.CLIENT)
    public void setHoveringButton(boolean hoveringButton) {
        this.hoveringButton = hoveringButton;
    }

    @SideOnly(Side.CLIENT)
    private void updateDisplayStack() {
        if (this.displayRandom) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            this.displayAllRelevantItems(itemStacks);
            this.tempDisplayStack = !itemStacks.isEmpty() ? itemStacks.get(tempIndex) : ItemStack.EMPTY;
            if (++tempIndex >= itemStacks.size()) tempIndex = 0;
        } else {
            if (this.icon.isEmpty()) {
                this.tempDisplayStack = new ItemStack(Items.DIAMOND);
            }
            this.tempDisplayStack = this.icon;
        }
    }

    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }

}
