package net.thegaminghuskymc.gadgetmod.programs.social_medias;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;

import java.awt.*;

public class ApplicationCackler extends Application {

    public ApplicationCackler() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

    @Override
    public void init() {
        
        Layout layoutMain = new Layout(270, 140);

        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y + 15, x + width, y + height, new Color(89, 100, 101, 255).getRGB());

            Gui.drawRect(x + 70, y, x + 220, y + height, new Color(70, 78, 79, 255).getRGB());

            Gui.drawRect(x, y, x + width, y + 27, new Color(61, 67, 68, 255).getRGB());
        });

        Label labelCackler = new Label("Cackler", 15, 3);
        layoutMain.addComponent(labelCackler);

        Image profilePicture = new Image(15, 25, 40, 40, "https://cdn.discordapp.com/avatars/235792215736188928/445367d691b718e48bcb208e626a76a7.png?size=2048");
        layoutMain.addComponent(profilePicture);

        Label labelNick = new Label("MRC", 15, 69);
        layoutMain.addComponent(labelNick);

        Label labelUsername = new Label("@MRC", 12, 79);
        layoutMain.addComponent(labelUsername);

        Label labelBio = new Label("This is a bio", 5, 92);
        labelBio.setAlignment(3);
        layoutMain.addComponent(labelBio);

        Label labelTweets = new Label("Clucks: 100", 3, 105);
        layoutMain.addComponent(labelTweets);

        Label labelFollowing = new Label("Following: 10", 3, 117);
        layoutMain.addComponent(labelFollowing);

        Label labelFollowers = new Label("Followers: 19", 3, 129);
        layoutMain.addComponent(labelFollowers);

        setCurrentLayout(layoutMain);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}