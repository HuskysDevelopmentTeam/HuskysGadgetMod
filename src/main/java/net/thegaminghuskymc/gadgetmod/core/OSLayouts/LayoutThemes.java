package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.client.theme.ITheme;
import net.thegaminghuskymc.gadgetmod.client.theme.IThemeManager;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;

import java.io.IOException;

public class LayoutThemes extends Layout {

    IThemeManager manager;

    public LayoutThemes() {}

    @Override
    public void init() {
        ItemList<ITheme> availableThemes = new ItemList<>(10, 10, 60, 4);
        try {
            availableThemes.setItems(manager.getAllThemes(new ThemeLocation("textures/themes/")));
        } catch (IOException io) {

        }
        availableThemes.setListItemRenderer(new ListItemRenderer<ITheme>(30) {
            @Override
            public void render(ITheme iTheme, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour() : Laptop.getSystem().getSettings().getColourScheme().getItemHighlightColour());

                GlStateManager.color(1.0F, 1.0F, 1.0F);
            }
        });
        this.addComponent(availableThemes);

        Label test = new Label("Test", 30, 30);
        this.addComponent(test);
    }

}
