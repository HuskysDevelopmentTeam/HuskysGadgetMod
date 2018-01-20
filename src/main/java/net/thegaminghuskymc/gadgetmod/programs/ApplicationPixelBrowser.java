package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.component.TextField;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import java.awt.*;

public class ApplicationPixelBrowser extends Application {

    public ApplicationPixelBrowser() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    private static Layout createDropdown(Application application) {

        Layout layout = new Layout.Context(100, 100);
        layout.yPosition = 70;
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));

        ItemList<Button> itemListRouters = new ItemList<>(5, 5, 90, 4);
        itemListRouters.setListItemRenderer(new ListItemRenderer<Button>(16) {
            @Override
            public void render(Button button, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, "Router", x + 16, y + 4, Color.WHITE.getRGB());

                BlockPos pos = Minecraft.getMinecraft().player.getPosition();
                BlockPos laptopPos = Laptop.getPos();
                double distance = Math.sqrt(pos.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
                if (distance > 20) {
                    Icons.WIFI_LOW.draw(mc, x + 3, y + 3);
                } else if (distance > 10) {
                    Icons.WIFI_MED.draw(mc, x + 3, y + 3);
                } else {
                    Icons.WIFI_HIGH.draw(mc, x + 3, y + 3);
                }
            }
        });
        /*itemListRouters.setListItemRenderer(new ListItemRenderer<BlockPos>(16) {
            @Override
            public void render(BlockPos blockPos, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, "Router", x + 16, y + 4, Color.WHITE.getRGB());

                BlockPos laptopPos = Laptop.getPos();
                double distance = Math.sqrt(blockPos.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
                if(distance > 20) {
                    Icons.WIFI_LOW.draw(mc, x + 3, y + 3);
                }
                else if(distance > 10) {
                    Icons.WIFI_MED.draw(mc, x + 3, y + 3);
                }
                else {
                    Icons.WIFI_HIGH.draw(mc, x + 3, y + 3);
                }
            }
        });*/
        /*itemListRouters.sortBy((o1, o2) -> {
            BlockPos laptopPos = Laptop.getPos();
            double distance1 = Math.sqrt(o1.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            double distance2 = Math.sqrt(o2.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            return Double.compare(distance1, distance2);
        });*/
        layout.addComponent(itemListRouters);

        return layout;
    }

    @Override
    public void init() {
        TextField textField = new TextField(5, 5, 200);
        textField.setPlaceholder("URL");
        super.addComponent(textField);

        Button user = new Button(210, 5, Icons.USER);
        super.addComponent(user);

        Button dropDown = new Button(240, 5, Icons.DOTS_HORIZONTAL);
        dropDown.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (Laptop.getSystem().hasContext()) {
                Laptop.getSystem().closeContext();
            } else {
                Laptop.getSystem().openContext(createDropdown(this), mouseX - 100, mouseY + 5);
            }
        });
        super.addComponent(dropDown);

    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
