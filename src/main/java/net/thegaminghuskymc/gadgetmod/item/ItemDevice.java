package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class ItemDevice extends ItemBlock {

    public ItemDevice(Block block) {
        super(block);
        this.setMaxStackSize(1);
    }

    //This method is still bugged due to Forge.
    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        NBTTagCompound tag = new NBTTagCompound();
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("display", Constants.NBT.TAG_COMPOUND))
        {
            tag.setTag("display", stack.getTagCompound().getTag("display"));
        }
        return tag;
    }

}
