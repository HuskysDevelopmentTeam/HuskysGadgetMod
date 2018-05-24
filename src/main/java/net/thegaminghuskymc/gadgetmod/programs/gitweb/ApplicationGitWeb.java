package net.thegaminghuskymc.gadgetmod.programs.gitweb;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceApplication;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.Spinner;
import net.thegaminghuskymc.gadgetmod.api.app.component.TextField;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.component.GitWebFrame;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.layout.TextLayout;
import net.thegaminghuskymc.gadgetmod.programs.system.layout.StandardLayout;

import javax.annotation.Nullable;
import java.awt.*;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

/**
 * The Device Mod implementations of an internet browser. Originally created by MinecraftDoodler.
 * Licensed under GPL 3.0
 */
@DeviceApplication(modId = MOD_ID, appId = "gitweb")
public class ApplicationGitWeb extends Application
{
    private Layout layoutBrowser;
    private Layout layoutPref;

    private Button btnSearch;
    private Button btnHome;
    private Button btnSettings;

    private GitWebFrame webFrame;
    private TextField textFieldAddress;
    private Spinner spinnerLoading;
    private TextLayout scrollable;

    @Override
    public void init(@Nullable NBTTagCompound intent)
    {
        layoutBrowser = new StandardLayout("GitWeb", 362, 240, this, null);
        layoutBrowser.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            Gui.drawRect(x, y + 21, x + width, y + 164, color.getRGB());
        });

        layoutPref = new Layout(200, 120);

        textFieldAddress = new TextField(2, 2, 304);
        textFieldAddress.setPlaceholder("Enter Address");
        textFieldAddress.setKeyListener(c ->
        {
            if(c == '\r')
            {
                webFrame.loadWebsite(this.getAddress());
                return false;
            }
            return true;
        });
        layoutBrowser.addComponent(textFieldAddress);

        spinnerLoading = new Spinner(291, 4);
        spinnerLoading.setVisible(false);
        layoutBrowser.addComponent(spinnerLoading);

        btnSearch = new Button(308, 2, 16, 16, Icons.ARROW_RIGHT);
        btnSearch.setToolTip("Refresh", "Loads the entered address.");
        btnSearch.setClickListener((mouseX, mouseY, mouseButton) -> webFrame.loadWebsite(this.getAddress()));
        layoutBrowser.addComponent(btnSearch);

        btnHome = new Button(326, 2, 16, 16, Icons.HOME);
        btnHome.setToolTip("Home", "Loads page set in settings.");
        btnHome.setClickListener((mouseX, mouseY, mouseButton) -> webFrame.loadWebsite("welcome.official"));
        layoutBrowser.addComponent(btnHome);

        btnSettings = new Button(344, 2, 16, 16, Icons.WRENCH);
        btnSettings.setToolTip("Settings", "Change your preferences.");
        btnSettings.setClickListener((mouseX, mouseY, mouseButton) -> this.setCurrentLayout(layoutPref));
        layoutBrowser.addComponent(btnSettings);

        webFrame = new GitWebFrame(this, 0, 21, 362, 143);
        webFrame.loadWebsite("welcome.official");
        webFrame.setLoadingCallback((s, success) ->
        {
            spinnerLoading.setVisible(true);
            textFieldAddress.setFocused(false);
            textFieldAddress.setEditable(false);
            textFieldAddress.setText(s);
            btnSearch.setEnabled(false);
        });
        webFrame.setLoadedCallback((s, success) ->
        {
            spinnerLoading.setVisible(false);
            textFieldAddress.setEditable(true);
            btnSearch.setEnabled(true);
        });
        layoutBrowser.addComponent(webFrame);

        this.setCurrentLayout(layoutBrowser);
    }

    @Override
    public void handleKeyTyped(char character, int code)
    {
        super.handleKeyTyped(character, code);
    }

    private String getAddress()
    {
        return textFieldAddress.getText().replace("\\s+", "");
    }

    @Override
    public void load(NBTTagCompound tag) {}

    @Override
    public void save(NBTTagCompound tag) {}
}
