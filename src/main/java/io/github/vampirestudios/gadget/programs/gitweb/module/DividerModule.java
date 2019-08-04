package io.github.vampirestudios.gadget.programs.gitweb.module;

import io.github.vampirestudios.gadget.api.app.Layout;
import net.minecraft.client.gui.Gui;
import io.github.vampirestudios.gadget.programs.gitweb.component.GitWebFrame;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class DividerModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "size" };
    }

    @Override
    public String[] getOptionalData()
    {
        return new String[] { "color" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        return Math.max(0, Integer.parseInt(data.get("size")));
    }

    @Override
    public void generate(GitWebFrame frame, Layout layout, int width, Map<String, String> data)
    {
        if(data.containsKey("color"))
        {
            int color = Integer.parseInt(data.get("color"));
            layout.setBackground((gui, mc, x, y, width1, height, mouseX, mouseY, windowActive) ->
            {
                Gui.drawRect(x, y, x + width1, y + height, color);
            });
        }
    }
}
