package net.thegaminghuskymc.gadgetmod.programs.social_medias;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.listener.ClickListener;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import java.awt.*;

public class ApplicationFlameChat extends Application {

    int activeTab = 0;
    /*private Dialog nameAndInfo;
    private Dialog profile;
    private Layout search;
    private Layout searchBar;
    private Layout usersDM;
    private Layout userInfo;*/
    private Button[] serversButtons = new Button[5];
    private Layout[] servers = new Layout[5];
    private Icons[] stacks = new Icons[]{
            Icons.GAME_CONTROLLER,
            Icons.PICTURE,
            Icons.COMPUTER,
            Icons.BOOK_OPEN
    };
    private String[] names = new String[]{
            "Gaming Channel",
            "Photography Channel",
            "Tech Server",
            "Book Channel"
    };
    private String[] tooltips = new String[]{
            "We are gamers from the whole world!",
            "This server is for people that like to take photos!",
            "We love tech and can help you with whatever you need help with",
            "We are a server with people that like to read books and book writers"
    };

    private Icons[] stacks2 = new Icons[]{
            Icons.EDIT,
            Icons.DATABASE,
            Icons.HELP
    };
    private String[] names2 = new String[]{
            "Art Channel",
            "Flame Chat Developers",
            "Flame Chat Help"
    };
    private String[] tooltips2 = new String[]{
            "We are a community that loves to make art both traditional and digital",
            "In this server you can meet the developers of Flame Chat and you can also talk to them",
            "Here you can get help with Flame Chat if you have anything that you need help with"
    };

    @Override
    public void init() {

        Layout layoutMain = new Layout(270, 140);

        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y, x + width, y + height, new Color(22, 23, 25, 255).getRGB());

            Gui.drawRect(x + 60, y, x + width, y + height, new Color(32, 34, 37, 255).getRGB());
        });

        Button DMButton = new Button(20, 6, Icons.COMMUNITY);
        layoutMain.addComponent(DMButton);

        for (int i = 0; i < 4; i++) {
            servers[i] = layoutMain;
            serversButtons[i] = new Button(3, 27 + (i * 24), 23, 23, stacks[i]);
            serversButtons[i].setToolTip(names[i], tooltips[i]);
            servers[i].addComponent(serversButtons[i]);
        }

        for (int i = 0; i < 3; i++) {
            servers[i] = layoutMain;
            serversButtons[i] = new Button(28, 27 + (i * 24), 23, 23, stacks2[i]);
            serversButtons[i].setToolTip(names2[i], tooltips2[i]);
            servers[i].addComponent(serversButtons[i]);
        }

        Button createServer = new Button(10, 123, Icons.PLUS);
        createServer.setToolTip("Create Server", "This will create a server");
        layoutMain.addComponent(createServer);

        Button joinServer = new Button(27, 123, Icons.IMPORT);
        joinServer.setToolTip("Join Server", "You will have to get an invite link to join a server");
        layoutMain.addComponent(joinServer);

        Button menuButton = new Button(15, 123, Icons.CHECK);
        menuButton.setClickListener((mouseX, mouseY, mouseButton) -> {

        });

        this.setCurrentLayout(layoutMain);

    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        servers[activeTab].render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }


}
