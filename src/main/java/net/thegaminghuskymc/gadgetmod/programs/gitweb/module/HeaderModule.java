package net.thegaminghuskymc.gadgetmod.programs.gitweb.module;

import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.component.GitWebFrame;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class HeaderModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "text" };
    }

    @Override
    public String[] getOptionalData()
    {
        return new String[] { "scale", "padding", "align" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        if(data.containsKey("scale"))
        {
            return Integer.parseInt(data.get("scale")) * BaseDevice.fontRenderer.FONT_HEIGHT + 10;
        }
        return BaseDevice.fontRenderer.FONT_HEIGHT + (data.containsKey("padding") ? Integer.parseInt(data.get("padding")) : 5) * 2;
    }

    @Override
    public void generate(GitWebFrame frame, Layout layout, int width, Map<String, String> data)
    {
        int padding = (data.containsKey("padding") ? Integer.parseInt(data.get("padding")) : 5);
        String s = GitWebFrame.parseFormatting(data.get("text"));
        Label label = new Label(s, width / 2, padding);
        label.setAlignment(Component.ALIGN_CENTER);

        int scale = 1;
        if(data.containsKey("scale"))
        {
            scale = Integer.parseInt(data.get("scale"));
        }
        label.setScale(scale);

        String align = data.getOrDefault("align", "center");
        if("left".equals(align))
        {
            label.left = padding;
            label.setAlignment(Component.ALIGN_LEFT);
        }
        else if("right".equals(align))
        {
            label.left = width - padding;
            label.setAlignment(Component.ALIGN_RIGHT);
        }

        layout.addComponent(label);
    }
}
