package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.block.Block;

/**
 * Author: MrCrayfish
 */
public class ItemDevice extends ItemColorable {
    public ItemDevice(Block block) {
        super(block);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean getShareTag() {
        return false;
    }
}
