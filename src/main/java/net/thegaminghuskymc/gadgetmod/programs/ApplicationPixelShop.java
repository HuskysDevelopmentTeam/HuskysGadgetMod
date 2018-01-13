package net.thegaminghuskymc.gadgetmod.programs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.*;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.component.TextField;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.io.File;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.object.Canvas;
import net.thegaminghuskymc.gadgetmod.object.Picture;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Objects;

public class ApplicationPixelShop extends Application {

    private static final ResourceLocation PIXEL_PAINTER_ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/pixel_shop.png");

    private static final Color ITEM_BACKGROUND = new Color(170, 176, 194);
    private static final Color ITEM_SELECTED = new Color(200, 176, 174);
    private static final Color AUTHOR_TEXT = new Color(114, 120, 138);

    /* Main Menu */
    private Layout layoutMainMenu;

    /* New Picture */
    private Layout layoutNewPicture;
    private TextField fieldName;
    private TextField fieldAuthor;

    /* Load Picture */
    private Layout layoutLoadPicture;
    private ItemList<Picture> listPictures;
    private Button btnLoadSavedPicture;
    private Button btnDeleteSavedPicture;

    /* Drawing */
    private Layout layoutDraw;
    private Canvas canvas;
    private CheckBox displayGrid;

    private ComboBox.Custom<Integer> colourPicker;

    @Override
    public void init() {

        /* Main Menu */
        layoutMainMenu = new Layout(150, 150);

        Image logo = new Image(40, 5, 68, 68, info.getIconU(), info.getIconV(), 14, 14, Laptop.ICON_TEXTURES);
        layoutMainMenu.addComponent(logo);

        Label labelLogo = new Label("Huskydobe PixelShop", 19, 85);
        layoutMainMenu.addComponent(labelLogo);

        Button btnNewPicture = new Button(5, 100, "New");
        btnNewPicture.setSize(140, 20);
        btnNewPicture.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutNewPicture));
        layoutMainMenu.addComponent(btnNewPicture);

        Button btnLoadPicture = new Button(5, 125, "Load");
        btnLoadPicture.setSize(140, 20);
        btnLoadPicture.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutLoadPicture));
        layoutMainMenu.addComponent(btnLoadPicture);


        /* New Picture */

        layoutNewPicture = new Layout(180, 65);

        Label labelName = new Label("Name", 5, 5);
        layoutNewPicture.addComponent(labelName);

        fieldName = new TextField(5, 15, 100);
        layoutNewPicture.addComponent(fieldName);

        Label labelAuthor = new Label("Author", 5, 35);
        layoutNewPicture.addComponent(labelAuthor);

        fieldAuthor = new TextField(5, 45, 100);
        layoutNewPicture.addComponent(fieldAuthor);

        Label labelSize = new Label("Size", 110, 5);
        layoutNewPicture.addComponent(labelSize);

        RadioGroup sizeGroup = new RadioGroup();

        CheckBox checkBox16x = new CheckBox("16x", 110, 17);
        checkBox16x.setSelected(true);
        checkBox16x.setRadioGroup(sizeGroup);
        layoutNewPicture.addComponent(checkBox16x);

        CheckBox checkBox32x = new CheckBox("32x", 145, 17);
        checkBox32x.setRadioGroup(sizeGroup);
        layoutNewPicture.addComponent(checkBox32x);

        Button btnCreatePicture = new Button(110, 40, "Create");
        btnCreatePicture.setSize(65, 20);
        btnCreatePicture.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            setCurrentLayout(layoutDraw);
            canvas.createPicture(fieldName.getText(), fieldAuthor.getText(), checkBox16x.isSelected() ? Picture.Size.X16 : Picture.Size.X32);
        });
        layoutNewPicture.addComponent(btnCreatePicture);


        /* Load Picture */

        layoutLoadPicture = new Layout(165, 116);
        layoutLoadPicture.setInitListener(() ->
        {
            listPictures.removeAll();
            FileSystem.getApplicationFolder(this, (folder, success) ->
            {
                if (success) {
                    Objects.requireNonNull(folder).search(file -> file.isForApplication(this)).forEach(file ->
                    {
                        Picture picture = Picture.fromFile(file);
                        listPictures.addItem(picture);
                    });
                }
            });
        });

        listPictures = new ItemList<>(5, 5, 100, 5);
        listPictures.setListItemRenderer(new ListItemRenderer<Picture>(20) {
            @Override
            public void render(Picture picture, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? ITEM_SELECTED.getRGB() : ITEM_BACKGROUND.getRGB());
                mc.fontRenderer.drawString(picture.getName(), x + 2, y + 2, Color.WHITE.getRGB(), false);
                mc.fontRenderer.drawString(picture.getAuthor(), x + 2, y + 11, AUTHOR_TEXT.getRGB(), false);
            }
        });
        listPictures.setItemClickListener((picture, index, mouseButton) ->
        {
            if (mouseButton == 0) {
                btnLoadSavedPicture.setEnabled(true);
                btnDeleteSavedPicture.setEnabled(true);
            }
        });
        layoutLoadPicture.addComponent(listPictures);

        btnLoadSavedPicture = new Button(110, 5, "Load");
        btnLoadSavedPicture.setSize(50, 20);
        btnLoadSavedPicture.setEnabled(false);
        btnLoadSavedPicture.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (listPictures.getSelectedIndex() != -1) {
                canvas.setPicture(listPictures.getSelectedItem());
                setCurrentLayout(layoutDraw);
            }
        });
        layoutLoadPicture.addComponent(btnLoadSavedPicture);

        Button btnBrowseSavedPicture = new Button(110, 30, "Browse");
        btnBrowseSavedPicture.setSize(50, 20);
        btnBrowseSavedPicture.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            Dialog.OpenFile dialog = new Dialog.OpenFile(this);
            dialog.setResponseHandler((success, file) ->
            {
                if (file.isForApplication(this)) {
                    Picture picture = Picture.fromFile(file);
                    canvas.setPicture(picture);
                    setCurrentLayout(layoutDraw);
                    return true;
                } else {
                    Dialog.Message dialog2 = new Dialog.Message("Invalid file for Pixel Painter");
                    openDialog(dialog2);
                }
                return false;
            });
            openDialog(dialog);
        });
        layoutLoadPicture.addComponent(btnBrowseSavedPicture);

        btnDeleteSavedPicture = new Button(110, 55, "Delete");
        btnDeleteSavedPicture.setSize(50, 20);
        btnDeleteSavedPicture.setEnabled(false);
        btnDeleteSavedPicture.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (listPictures.getSelectedIndex() != -1) {
                Picture picture = listPictures.getSelectedItem();
                File file = picture.getSource();
                if (file != null) {
                    file.delete((o, success) ->
                    {
                        if (success) {
                            listPictures.removeItem(listPictures.getSelectedIndex());
                            btnDeleteSavedPicture.setEnabled(false);
                            btnLoadSavedPicture.setEnabled(false);
                        }
                    });
                }
            }
        });
        layoutLoadPicture.addComponent(btnDeleteSavedPicture);

        Button btnBackSavedPicture = new Button(110, 80, "Back");
        btnBackSavedPicture.setSize(50, 20);
        btnBackSavedPicture.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutMainMenu));
        layoutLoadPicture.addComponent(btnBackSavedPicture);


        /* Drawing */
        layoutDraw = new Layout(353, 280);

        canvas = new Canvas(30, 5);
        layoutDraw.addComponent(canvas);

        RadioGroup toolGroup = new RadioGroup();

        Layout layoutTools = new Layout(25, 164);
        layoutTools.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> Gui.drawRect(x, y, x + width, y + height, new Color(153, 146, 146, 255).getRGB()));
        layoutDraw.addComponent(layoutTools);

        Layout layoutColours = new Layout(100, 164);
        layoutColours.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> Gui.drawRect(x, y, x + width, y + height, new Color(153, 146, 146, 255).getRGB()));
        layoutColours.left = 180;
        layoutDraw.addComponent(layoutColours);

        ButtonToggle btnPencil = new ButtonToggle(5, 5, Icons.EDIT);
        btnPencil.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.PENCIL));
        btnPencil.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnPencil);

        ButtonToggle btnBucket = new ButtonToggle(5, 24, PIXEL_PAINTER_ICONS, 10, 0, 10, 10);
        btnBucket.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.BUCKET));
        btnBucket.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnBucket);

        ButtonToggle btnEraser = new ButtonToggle(5, 43, PIXEL_PAINTER_ICONS, 20, 0, 10, 10);
        btnEraser.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.ERASER));
        btnEraser.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnEraser);

        /*ButtonToggle btnBlur = new ButtonToggle(5, 62, PIXEL_PAINTER_ICONS, 30, 0, 10, 10);
        btnBlur.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.BLUR));
        btnBlur.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnBlur);

        ButtonToggle btnMagicWand = new ButtonToggle(5, 81, PIXEL_PAINTER_ICONS, 40, 0, 10, 10);
        btnMagicWand.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.MAGIC_WAND));
        btnMagicWand.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnMagicWand);

        ButtonToggle btnMove = new ButtonToggle(5, 100, Icons.EXPAND);
        btnMove.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.MOVE));
        btnMove.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnMove);

        ButtonToggle btnPen = new ButtonToggle(5, 119, PIXEL_PAINTER_ICONS, 60, 0, 10, 10);
        btnPen.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.PEN));
        btnPen.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnPen);

        ButtonToggle btnSmudge = new ButtonToggle(5, 138, PIXEL_PAINTER_ICONS, 70, 0, 10, 10);
        btnSmudge.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setCurrentTool(Canvas.SMUDGE));
        btnSmudge.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnSmudge);*/

        ButtonToggle btnEyeDropper = new ButtonToggle(5, 62, Icons.EYE_DROPPER);
        btnEyeDropper.setClickListener((mouseX, mouseY, mouseButton) -> {
            canvas.setCurrentTool(Canvas.EYE_DROPPER);
            Color color = new Color(canvas.getCurrentColour());
            colourPicker.setValue(color.getRGB());
        });
        btnEyeDropper.setRadioGroup(toolGroup);
        layoutTools.addComponent(btnEyeDropper);

        Button btnCancel = new Button(5, 81, Icons.CROSS);
        btnCancel.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (canvas.isExistingImage())
                setCurrentLayout(layoutLoadPicture);
            else
                setCurrentLayout(layoutMainMenu);
            canvas.clear();
        });
        layoutTools.addComponent(btnCancel);

        Button btnSave = new Button(162, 5, Icons.SAVE);
        btnSave.setClickListener((mouseX, mouseY, mouseButton) -> {
            canvas.picture.pixels = canvas.copyPixels();

            NBTTagCompound pictureTag = new NBTTagCompound();
            canvas.picture.writeToNBT(pictureTag);

            if (canvas.isExistingImage()) {
                File file = canvas.picture.getSource();
                if (file != null) {
                    file.setData(pictureTag, (response, success) ->
                    {
                        if (Objects.requireNonNull(response).getStatus() == FileSystem.Status.SUCCESSFUL) {
                            canvas.clear();
                            setCurrentLayout(layoutLoadPicture);
                        }
                    });
                }
            } else {
                Dialog.SaveFile dialog = new Dialog.SaveFile(ApplicationPixelShop.this, pictureTag);
                dialog.setResponseHandler((success, file) ->
                {
                    if (success) {
                        canvas.clear();
                        setCurrentLayout(layoutLoadPicture);
                        return true;
                    }
                    return false;
                });
                openDialog(dialog);
            }
        });
        layoutDraw.addComponent(btnSave);

        Button button = new Button(162, 22, Icons.PRINTER);
        button.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                Dialog.Print dialog = new Dialog.Print(new PicturePrint(canvas.picture.getName(), canvas.getPixels(), canvas.picture.getWidth()));
                openDialog(dialog);
            }
        });
        layoutDraw.addComponent(button);

        colourPicker = new ComboBox.Custom<>(5, 5, 50, 100, 100);
        colourPicker.setValue(Color.RED.getRGB());
        colourPicker.setItemRenderer(new ItemRenderer<Integer>() {
            @Override
            public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height) {
                if (integer != null) {
                    Gui.drawRect(x, y, x + width, y + height, integer);
                }
            }
        });
        colourPicker.setChangeListener((oldValue, newValue) ->
        {
            canvas.setColour(colourPicker.getValue());
        });

        Layout layout = colourPicker.getLayout();

        Palette palette = new Palette(5, 5, colourPicker);
        layout.addComponent(palette);

        layoutColours.addComponent(colourPicker);

        Component colourDisplay = new Component(58, 15) {
            @Override
            public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
                drawRect(xPosition, yPosition, xPosition + 50, yPosition + 20, Color.DARK_GRAY.getRGB());
                drawRect(xPosition + 1, yPosition + 1, xPosition + 49, yPosition + 19, canvas.getCurrentColour());
            }
        };
        layoutColours.addComponent(colourDisplay);

        displayGrid = new CheckBox("Grid", 30, 140);
        displayGrid.setClickListener((mouseX, mouseY, mouseButton) -> canvas.setShowGrid(displayGrid.isSelected()));
        layoutDraw.addComponent(displayGrid);

        setCurrentLayout(layoutMainMenu);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

    @Override
    public void onClose() {
        super.onClose();
        listPictures.removeAll();
    }

    public static class PicturePrint implements IPrint {
        private String name;
        private int[] pixels;
        private int resolution;
        private boolean cut;

        public PicturePrint() { }
        
        public PicturePrint(String name, int[] pixels, int resolution) {
            this.name = name;
            this.pixels = pixels;
            this.resolution = resolution;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int speed() {
            return resolution;
        }

        @Override
        public boolean requiresColor() {
            for (int pixel : pixels) {
                int r = (pixel >> 16 & 255);
                int g = (pixel >> 8 & 255);
                int b = (pixel & 255);
                if (r != g || r != b) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public NBTTagCompound toTag() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("name", name);
            tag.setIntArray("pixels", pixels);
            tag.setInteger("resolution", resolution);
            if (cut) tag.setBoolean("cut", true);
            return tag;
        }

        @Override
        public void fromTag(NBTTagCompound tag) {
            name = tag.getString("name");
            pixels = tag.getIntArray("pixels");
            resolution = tag.getInteger("resolution");
            cut = tag.getBoolean("cut");
        }

        @Override
        public Class<? extends IPrint.Renderer> getRenderer() {
            return PictureRenderer.class;
        }
    }

    public static class PictureRenderer implements IPrint.Renderer {
        static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/model/paper.png");

        @Override
        public boolean render(NBTTagCompound data) {
            if (data.hasKey("pixels", Constants.NBT.TAG_INT_ARRAY) && data.hasKey("resolution", Constants.NBT.TAG_INT)) {
                int[] pixels = data.getIntArray("pixels");
                int resolution = data.getInteger("resolution");
                boolean cut = data.getBoolean("cut");

                if (pixels.length != resolution * resolution)
                    return false;

                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GlStateManager.disableLighting();
                GlStateManager.rotate(180, 0, 1, 0);

                // This is for the paper background
                if (!cut) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
                    RenderUtil.drawRectWithTexture(-1, 0, 0, 0, 1, 1, resolution, resolution, resolution, resolution);
                }

                // This creates an flipped copy of the pixel array
                // as it otherwise would be mirrored
                int[] pixels2 = new int[pixels.length];
                for (int i = 0; i < resolution; i++) {
                    for (int j = 0; j < resolution; j++) {
                        pixels2[resolution - i - 1 + (resolution - j - 1) * resolution] = pixels[i + j * resolution];
                    }
                }

                // Creating a DynamicTexture to represent the picture
                DynamicTexture texture = new DynamicTexture(resolution, resolution);
                // This is actually more efficient than providing an BufferedImage
                // as BIs can lead to a memory leak or similar
                try {
                    Field textureDataField = texture.getClass().getDeclaredField("dynamicTextureData");
                    textureDataField.setAccessible(true);
                    textureDataField.set(texture, pixels2);
                    texture.updateDynamicTexture();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                // Rendering the texture
                GlStateManager.bindTexture(texture.getGlTextureId());
                RenderUtil.drawRectWithTexture(-1, 0, 0, 0, 1, 1, resolution, resolution, resolution, resolution);
                GlStateManager.deleteTexture(texture.getGlTextureId());

                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                return true;
            }
            return false;
        }
    }
}
