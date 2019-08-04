package io.github.vampirestudios.gadget.programs.gitweb.module;

import net.minecraft.item.ItemStack;
import io.github.vampirestudios.gadget.programs.gitweb.component.container.ContainerBox;
import io.github.vampirestudios.gadget.programs.gitweb.component.container.CraftingBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class CraftingModule extends ContainerModule
{
    @Override
    public String[] getOptionalData()
    {
        List<String> optionalData = new ArrayList<>();
        optionalData.addAll(Arrays.asList(super.getOptionalData()));
        for(int i = 0; i < 9; i++)
        {
            optionalData.add("slot-" + (i + 1));
        }
        optionalData.add("slot-result");
        return optionalData.toArray(new String[0]);
    }

    @Override
    public int getHeight()
    {
        return CraftingBox.HEIGHT;
    }

    @Override
    public ContainerBox createContainer(Map<String, String> data)
    {
        ItemStack[] ingredient = new ItemStack[9];
        Arrays.fill(ingredient, ItemStack.EMPTY);
        for(int i = 0; i < ingredient.length; i++)
        {
            ingredient[i] = getItem(data, "slot-" + (i + 1));
        }
        ItemStack result = getItem(data, "slot-result");
        return new CraftingBox(ingredient, result);
    }
}
