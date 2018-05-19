package net.thegaminghuskymc.gadgetmod.programs.social_medias;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;

import javax.annotation.Nullable;
import java.awt.*;

public class ApplicationTest extends Application {

    public ApplicationTest() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    public void init(@Nullable NBTTagCompound intent) {

        Layout homeLayout = new Layout();

        Button btnBack = new Button(5, 5, Icons.ARROW_LEFT);
        btnBack.setClickListener((mouseX, mouseY, mouseButton) -> {
            setCurrentLayout(homeLayout);
        });

        Layout testLayout = new Layout();
        testLayout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(0, 0, 270, 140, Color.GREEN.getRGB());
        });
        testLayout.addComponent(btnBack);

        Button testButton = new Button(5, 5, Icons.CHECK);
        testLayout.addComponent(testButton);

        Button goToTestLayout = new Button(30, 30, Icons.CROSS);
        goToTestLayout.setClickListener((mouseX, mouseY, mouseButton) -> {
            setCurrentLayout(testLayout);
        });
        homeLayout.addComponent(goToTestLayout);

        setCurrentLayout(homeLayout);
    }

    public void load(NBTTagCompound tagCompound) {
    }

    public void save(NBTTagCompound tagCompound) {
    }
}
