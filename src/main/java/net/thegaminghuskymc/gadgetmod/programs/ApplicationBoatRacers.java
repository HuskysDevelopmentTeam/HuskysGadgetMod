package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceApplication;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.CheckBox;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.object.Game;
import net.thegaminghuskymc.gadgetmod.object.TileGrid;
import net.thegaminghuskymc.gadgetmod.object.tiles.Tile;

import javax.annotation.Nullable;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "boat_racer")
public class ApplicationBoatRacers extends Application {

    private static final ResourceLocation PIXEL_MAIL_BACKGROUND = new ResourceLocation("hgm", "textures/gui/pixel_mail_background.png");

	private Layout layoutLevelEditor, layoutMain;
	private Game game;
	private TileGrid tileGrid;
	private Label labelLayer;
	private Button btnNextLayer;
	private Button btnPrevLayer;
	private CheckBox checkBoxForeground;
	private CheckBox checkBoxBackground;
	private CheckBox checkBoxPlayer;

	public ApplicationBoatRacers() {
		this.setDefaultWidth(320);
		this.setDefaultHeight(160);
	}
	
	@Override
	public void init(@Nullable NBTTagCompound intent) {

	    layoutMain = new Layout(320, 160);
	    layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            int guiLeft = (width - x) / 2;
            int guiTop = (height - y) / 2;
            GuiInventory.drawEntityOnScreen(guiLeft + 8, guiTop + 95, 40, (float)(guiLeft + 88 - mouseX), (float)(guiTop + 45 - 30 - mouseY), Minecraft.getMinecraft().player);

            GuiInventory.drawEntityOnScreen(guiLeft + 238, guiTop + 95, 40, (float)(guiLeft + 88 + mouseX), (float)(guiTop + 45 - 30 - mouseY), Minecraft.getMinecraft().player);

            mc.getTextureManager().bindTexture(PIXEL_MAIL_BACKGROUND);
            RenderUtil.drawRectWithTexture(x, y, 0, 0, width, height, 640, 360, 640, 360);
        });

        Label title = new Label("Boat Racer", 80, 20);
        title.setScale(3);
        layoutMain.addComponent(title);

		layoutLevelEditor = new Layout(364, 178);

        try
        {
            game = new Game(4, 4, 256, 144);
            game.fill(Tile.grass);
            layoutLevelEditor.addComponent(game);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Button btnPlay = new Button(90, 60, "Play", Icons.PLAY);
        btnPlay.setSize(140, 20);
        btnPlay.setClickListener((mouseX, mouseY, mouseButton) -> {
            game.setEditorMode(false);
            game.setRenderPlayer(true);
        });
        layoutMain.addComponent(btnPlay);

        Button btnEdit = new Button(90, 90, "Edit", Icons.EDIT);
        btnEdit.setSize(140, 20);
        btnEdit.setClickListener((mouseX, mouseY, mouseButton) -> {
            game.setEditorMode(true);
            game.setRenderPlayer(false);
        });
        layoutMain.addComponent(btnEdit);
		
		tileGrid = new TileGrid(266, 3, game);
		layoutLevelEditor.addComponent(tileGrid);
		
		labelLayer = new Label("1", 280, 108);
		layoutLevelEditor.addComponent(labelLayer);
		
		btnNextLayer = new Button(266, 106, Icons.CHEVRON_RIGHT);
		btnNextLayer.setClickListener((mouseX, mouseY, mouseButton) ->
		{
            game.nextLayer();
            labelLayer.setText(Integer.toString(game.getCurrentLayer().layer + 1));
        });
		layoutLevelEditor.addComponent(btnNextLayer);
		
		btnPrevLayer = new Button(314, 106, Icons.CHEVRON_LEFT);
		btnPrevLayer.setClickListener((mouseX, mouseY, mouseButton) ->
		{
            game.prevLayer();
            labelLayer.setText(Integer.toString(game.getCurrentLayer().layer + 1));
        });
		layoutLevelEditor.addComponent(btnPrevLayer);
		
		checkBoxBackground = new CheckBox("Background", 3, 151);
		checkBoxBackground.setClickListener((mouseX, mouseY, mouseButton) -> game.setRenderBackground(checkBoxBackground.isSelected()));
		checkBoxBackground.setSelected(true);
		layoutLevelEditor.addComponent(checkBoxBackground);
		
		checkBoxForeground = new CheckBox("Foreground", 80, 151);
		checkBoxForeground.setClickListener((mouseX, mouseY, mouseButton) -> game.setRenderForeground(checkBoxForeground.isSelected()));
		checkBoxForeground.setSelected(true);
		layoutLevelEditor.addComponent(checkBoxForeground);
		
		checkBoxPlayer = new CheckBox("Player", 160, 151);
		checkBoxPlayer.setClickListener((mouseX, mouseY, mouseButton) -> game.setRenderPlayer(checkBoxPlayer.isSelected()));
		layoutLevelEditor.addComponent(checkBoxPlayer);
		
		setCurrentLayout(layoutMain);
	}

	@Override
	public void load(NBTTagCompound tagCompound) {

	}

	@Override
	public void save(NBTTagCompound tagCompound) {

	}

	
}
