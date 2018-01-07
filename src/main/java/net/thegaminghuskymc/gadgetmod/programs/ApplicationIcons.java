package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;

import java.awt.*;

public class ApplicationIcons extends Application {

    public ApplicationIcons() {
        this.setDefaultWidth(278);
        this.setDefaultHeight(230);
    }

    @Override
    public void init() {
        /*for (Icons icon : Icons.values()) {
            int posX = (icon.ordinal() % 15) * 18;
            int posY = (icon.ordinal() / 15) * 18;
            Button button = new Button(5 + posX, 5 + posY, icon);
            button.setToolTip("Icon", icon.name());
            super.addComponent(button);
        }*/

        ItemList<Icons> itemListIcons = new ItemList<>(5, 5, 200, 6, true);

        for (Icons icon : Icons.values()) {
            itemListIcons.addItem(icon);
        }

        itemListIcons.setListItemRenderer(new ListItemRenderer<Icons>(20) {
            @Override
            public void render(Icons icons, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(icons.getIconAsset());
                int size = icons.getIconSize();
                int assetWidth = icons.getGridWidth() * size;
                int assetHeight = icons.getGridHeight() * size;
                RenderUtil.drawRectWithTexture(x + 5, y + 5, icons.getU(), icons.getV(), size, size, size, size, assetWidth, assetHeight);
                RenderUtil.drawStringClipped(I18n.format(icons.toString().replace("_", " ")), x + 20, y + 6, itemListIcons.getWidth(), Color.WHITE.getRGB(), true);
            }
        });

        super.addComponent(itemListIcons);

    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
