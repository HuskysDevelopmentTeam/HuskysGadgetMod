package net.thegaminghuskymc.gadgetmod.programs.social_medias.layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.ScrollableLayout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

import java.awt.*;


public class LayoutProfile extends ScrollableLayout {

    private String nickname, username, bio, posts, followers, following;
    
    public LayoutProfile(Application application, String nickname, String username, String bio, String posts, String followers, String following) {
        super(270, 140, 140);
        this.nickname = nickname;
        this.username = username;
        this.bio = bio;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
    }

    @Override
    public void init() {
        Image profilePicture = new Image(15, 25, 40, 40, "https://cdn.discordapp.com/avatars/280431747437559808/aa317bc6b28f973e4cfd444a6a26db63.png?size=128");
        this.addComponent(profilePicture);

        Label labelNick = new Label(nickname, 15, 69);
        this.addComponent(labelNick);

        Label labelUsername = new Label("@" + username, 12, 79);
        this.addComponent(labelUsername);

        Label labelBio = new Label(bio, 5, 92);
        labelBio.setAlignment(3);
        this.addComponent(labelBio);

        Label labelTweets = new Label("Clucks: " + posts, 3, 105);
        this.addComponent(labelTweets);

        Label labelFollowing = new Label("Following: " + following, 3, 117);
        this.addComponent(labelFollowing);

        Label labelFollowers = new Label("Followers: " + followers, 3, 129);
        this.addComponent(labelFollowers);

        Button home = new Button(3, 4, 42, 17, "Home", Icons.HOME);
        home.setToolTip("Home", "The Home Page");
        this.addComponent(home);

        Button profile = new Button(43 * 5, 4, 50, 16, "Profile", Icons.USER);
        profile.setToolTip("Profile ", "Your Profile");
        this.addComponent(profile);

    }

    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getBackgroundColour());
        Gui.drawRect(x, y + 15, x + width, y + height, color.brighter().brighter().getRGB());
        Gui.drawRect(x + 70, y, x + 220, y + height, color.getRGB());
        Gui.drawRect(x, y, x + width, y + 27, color.getRGB());
        super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
    }
    
}
