package net.thegaminghuskymc.gadgetmod.programs.social_medias;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.OtherEmojis;
import net.thegaminghuskymc.gadgetmod.programs.social_medias.layouts.LayoutProfile;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.HomePageLayout;

import javax.annotation.Nullable;
import java.awt.*;

public class ApplicationCackler extends Application {

    public ApplicationCackler() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init(@Nullable NBTTagCompound intent) {

        Layout defaultLayout = new Layout(270, 140);

        HomePageLayout layoutMain = new HomePageLayout(defaultLayout.width, defaultLayout.height, this, defaultLayout);

        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y + 15, x + width, y + height, new Color(89, 100, 101, 255).getRGB());

            Gui.drawRect(x + 70, y, x + 220, y + height, new Color(70, 78, 79, 255).getRGB());

            Gui.drawRect(x, y, x + width, y + 27, new Color(61, 67, 68, 255).getRGB());
        });

        Label labelCackler = new Label("Cackler", 15, 3);
        layoutMain.addComponent(labelCackler);

        LayoutProfile profile = new LayoutProfile(this, "_Zontic", "_Zontic", "A kid with no computer", "10 000 000", "69 000 000", "46 000 000");

        Button button = new Button(10, 10, "Profile", OtherEmojis.POOP);
        button.setClickListener((mouseX, mouseY, mouseButton) ->
                setCurrentLayout(profile));
        layoutMain.addComponent(button);

        setCurrentLayout(layoutMain);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}