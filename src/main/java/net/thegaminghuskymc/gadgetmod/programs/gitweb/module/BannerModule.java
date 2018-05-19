package net.thegaminghuskymc.gadgetmod.programs.gitweb.module;

import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.component.GitWebFrame;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class BannerModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "image" };
    }

    @Override
    public String[] getOptionalData()
    {
        return new String[] { "text" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        return 50;
    }

    @Override
    public void generate(GitWebFrame frame, Layout layout, int width, Map<String, String> data)
    {
        int height = calculateHeight(data, width);
        Image image = new Image(0, 0, width, height);
        image.setImage(data.get("image"));
        layout.addComponent(image);

        if(data.containsKey("text"))
        {
            String s = GitWebFrame.parseFormatting(data.get("text"));
            Label label = new Label(s, width / 2, (height - 18) / 2);
            label.setAlignment(Component.ALIGN_CENTER);
            label.setScale(2.0F);
            layout.addComponent(label);
        }
    }
}
