package net.thegaminghuskymc.gadgetmod.programs;

import net.husky.device.api.app.*;
import net.husky.device.api.app.component.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.listener.ClickListener;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.io.File;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.object.Canvas;
import net.thegaminghuskymc.gadgetmod.object.Picture;

import java.awt.*;

public class ApplicationPixelPainter extends Application {
    private static final ResourceLocation PIXEL_PAINTER_ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/pixel_painter.png");

    private static final Color ITEM_BACKGROUND = new Color(170, 176, 194);
    private static final Color ITEM_SELECTED = new Color(200, 176, 174);
    private static final Color AUTHOR_TEXT = new Color(114, 120, 138);

    /* Main Menu */
    private Layout layoutMainMenu;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Image logo;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label labelLogo;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnNewPicture;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnLoadPicture;

    /* New Picture */
    private Layout layoutNewPicture;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label labelName;
    private net.thegaminghuskymc.gadgetmod.api.app.component.TextField fieldName;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label labelAuthor;
    private net.thegaminghuskymc.gadgetmod.api.app.component.TextField fieldAuthor;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Label labelSize;
    private CheckBox checkBox16x;
    private CheckBox checkBox32x;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnCreatePicture;

    /* Load Picture */
    private Layout layoutLoadPicture;
    private ItemList<Picture> listPictures;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnLoadSavedPicture;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnBrowseSavedPicture;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnDeleteSavedPicture;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnBackSavedPicture;

    /* Drawing */
    private Layout layoutDraw;
    private net.thegaminghuskymc.gadgetmod.object.Canvas canvas;
    private ButtonToggle btnPencil;
    private ButtonToggle btnBucket;
    private ButtonToggle btnEraser;
    private ButtonToggle btnEyeDropper;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnCancel;
    private net.thegaminghuskymc.gadgetmod.api.app.component.Button btnSave;
    private net.thegaminghuskymc.gadgetmod.api.app.Component colourDisplay;
    private CheckBox displayGrid;

    private ComboBox.Custom<Integer> colourPicker;

    @Override
    public void init() {
        /* Main Menu */
        layoutMainMenu = new Layout(100, 100);

        logo = new net.thegaminghuskymc.gadgetmod.api.app.component.Image(35, 5, 28, 28, info.getIconU(), info.getIconV(), 14, 14, Laptop.ICON_TEXTURES);
        layoutMainMenu.addComponent(logo);

        labelLogo = new net.thegaminghuskymc.gadgetmod.api.app.component.Label("Pixel Painter", 19, 35);
        layoutMainMenu.addComponent(labelLogo);

        btnNewPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(5, 50, "New");
        btnNewPicture.setSize(90, 20);
        btnNewPicture.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                setCurrentLayout(layoutNewPicture);
            }
        });
        layoutMainMenu.addComponent(btnNewPicture);

        btnLoadPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(5, 75, "Load");
        btnLoadPicture.setSize(90, 20);
        btnLoadPicture.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                setCurrentLayout(layoutLoadPicture);
            }
        });
        layoutMainMenu.addComponent(btnLoadPicture);


        /* New Picture */

        layoutNewPicture = new Layout(180, 65);

        labelName = new net.thegaminghuskymc.gadgetmod.api.app.component.Label("Name", 5, 5);
        layoutNewPicture.addComponent(labelName);

        fieldName = new net.thegaminghuskymc.gadgetmod.api.app.component.TextField(5, 15, 100);
        layoutNewPicture.addComponent(fieldName);

        labelAuthor = new net.thegaminghuskymc.gadgetmod.api.app.component.Label("Author", 5, 35);
        layoutNewPicture.addComponent(labelAuthor);

        fieldAuthor = new net.thegaminghuskymc.gadgetmod.api.app.component.TextField(5, 45, 100);
        layoutNewPicture.addComponent(fieldAuthor);

        labelSize = new net.thegaminghuskymc.gadgetmod.api.app.component.Label("Size", 110, 5);
        layoutNewPicture.addComponent(labelSize);

        RadioGroup sizeGroup = new RadioGroup();

        checkBox16x = new CheckBox("16x", 110, 17);
        checkBox16x.setSelected(true);
        checkBox16x.setRadioGroup(sizeGroup);
        layoutNewPicture.addComponent(checkBox16x);

        checkBox32x = new CheckBox("32x", 145, 17);
        checkBox32x.setRadioGroup(sizeGroup);
        layoutNewPicture.addComponent(checkBox32x);

        btnCreatePicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(110, 40, "Create");
        btnCreatePicture.setSize(65, 20);
        btnCreatePicture.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                setCurrentLayout(layoutDraw);
                canvas.createPicture(fieldName.getText(), fieldAuthor.getText(), checkBox16x.isSelected() ? Picture.Size.X16 : Picture.Size.X32);
            }
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
                    folder.search(file -> file.isForApplication(this)).forEach(file ->
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

        btnLoadSavedPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(110, 5, "Load");
        btnLoadSavedPicture.setSize(50, 20);
        btnLoadSavedPicture.setEnabled(false);
        btnLoadSavedPicture.setClickListener((c, mouseButton) ->
        {
            if (listPictures.getSelectedIndex() != -1) {
                canvas.setPicture(listPictures.getSelectedItem());
                setCurrentLayout(layoutDraw);
            }
        });
        layoutLoadPicture.addComponent(btnLoadSavedPicture);

        btnBrowseSavedPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(110, 30, "Browse");
        btnBrowseSavedPicture.setSize(50, 20);
        btnBrowseSavedPicture.setClickListener((c, mouseButton) ->
        {
            net.thegaminghuskymc.gadgetmod.api.app.Dialog.OpenFile dialog = new net.thegaminghuskymc.gadgetmod.api.app.Dialog.OpenFile(this);
            dialog.setResponseHandler((success, file) ->
            {
                if (file.isForApplication(this)) {
                    Picture picture = Picture.fromFile(file);
                    canvas.setPicture(picture);
                    setCurrentLayout(layoutDraw);
                    return true;
                } else {
                    net.thegaminghuskymc.gadgetmod.api.app.Dialog.Message dialog2 = new net.thegaminghuskymc.gadgetmod.api.app.Dialog.Message("Invalid file for Pixel Painter");
                    openDialog(dialog2);
                }
                return false;
            });
            openDialog(dialog);
        });
        layoutLoadPicture.addComponent(btnBrowseSavedPicture);

        btnDeleteSavedPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(110, 55, "Delete");
        btnDeleteSavedPicture.setSize(50, 20);
        btnDeleteSavedPicture.setEnabled(false);
        btnDeleteSavedPicture.setClickListener((c, mouseButton) ->
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
                        } else {
                            //TODO error dialog
                        }
                    });
                } else {
                    //TODO error dialog
                }
            }
        });
        layoutLoadPicture.addComponent(btnDeleteSavedPicture);

        btnBackSavedPicture = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(110, 80, "Back");
        btnBackSavedPicture.setSize(50, 20);
        btnBackSavedPicture.setClickListener((c, mouseButton) -> setCurrentLayout(layoutMainMenu));
        layoutLoadPicture.addComponent(btnBackSavedPicture);


        /* Drawing */

        layoutDraw = new Layout(213, 140);

        canvas = new net.thegaminghuskymc.gadgetmod.object.Canvas(5, 5);
        layoutDraw.addComponent(canvas);

        RadioGroup toolGroup = new RadioGroup();

        btnPencil = new ButtonToggle(138, 5, PIXEL_PAINTER_ICONS, 0, 0, 10, 10);
        btnPencil.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                canvas.setCurrentTool(net.thegaminghuskymc.gadgetmod.object.Canvas.PENCIL);
            }
        });
        btnPencil.setRadioGroup(toolGroup);
        layoutDraw.addComponent(btnPencil);

        btnBucket = new ButtonToggle(138, 24, PIXEL_PAINTER_ICONS, 10, 0, 10, 10);
        btnBucket.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                canvas.setCurrentTool(net.thegaminghuskymc.gadgetmod.object.Canvas.BUCKET);
            }
        });
        btnBucket.setRadioGroup(toolGroup);
        layoutDraw.addComponent(btnBucket);

        btnEraser = new ButtonToggle(138, 43, PIXEL_PAINTER_ICONS, 20, 0, 10, 10);
        btnEraser.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                canvas.setCurrentTool(net.thegaminghuskymc.gadgetmod.object.Canvas.ERASER);
            }
        });
        btnEraser.setRadioGroup(toolGroup);
        layoutDraw.addComponent(btnEraser);

        btnEyeDropper = new ButtonToggle(138, 62, PIXEL_PAINTER_ICONS, 30, 0, 10, 10);
        btnEyeDropper.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                canvas.setCurrentTool(Canvas.EYE_DROPPER);
                Color color = new Color(canvas.getCurrentColour());
                colourPicker.setValue(color.getRGB());
            }
        });
        btnEyeDropper.setRadioGroup(toolGroup);
        layoutDraw.addComponent(btnEyeDropper);

        btnCancel = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(138, 100, PIXEL_PAINTER_ICONS, 50, 0, 10, 10);
        btnCancel.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                if (canvas.isExistingImage())
                    setCurrentLayout(layoutLoadPicture);
                else
                    setCurrentLayout(layoutMainMenu);
                canvas.clear();
            }
        });
        layoutDraw.addComponent(btnCancel);

        btnSave = new net.thegaminghuskymc.gadgetmod.api.app.component.Button(138, 119, PIXEL_PAINTER_ICONS, 40, 0, 10, 10);
        btnSave.setClickListener(new ClickListener() {
            @Override
            public void onClick(net.thegaminghuskymc.gadgetmod.api.app.Component c, int mouseButton) {
                canvas.picture.pixels = canvas.copyPixels();

                NBTTagCompound pictureTag = new NBTTagCompound();
                canvas.picture.writeToNBT(pictureTag);

                if (canvas.isExistingImage()) {
                    File file = canvas.picture.getSource();
                    if (file != null) {
                        file.setData(pictureTag, (response, success) ->
                        {
                            if (response.getStatus() == FileSystem.Status.SUCCESSFUL) {
                                canvas.clear();
                                setCurrentLayout(layoutLoadPicture);
                            } else {
                                //TODO error dialog
                            }
                        });
                    }
                } else {
                    net.thegaminghuskymc.gadgetmod.api.app.Dialog.SaveFile dialog = new net.thegaminghuskymc.gadgetmod.api.app.Dialog.SaveFile(ApplicationPixelPainter.this, pictureTag);
                    dialog.setResponseHandler((success, file) ->
                    {
                        if (success) {
                            canvas.clear();
                            setCurrentLayout(layoutLoadPicture);
                            return true;
                        } else {
                            //TODO error dialog
                        }
                        return false;
                    });
                    openDialog(dialog);
                }
            }
        });
        layoutDraw.addComponent(btnSave);

        colourPicker = new ComboBox.Custom<>(159, 26, 50, 100, 100);
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

        layoutDraw.addComponent(colourPicker);

        colourDisplay = new net.thegaminghuskymc.gadgetmod.api.app.Component(158, 5) {
            @Override
            public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
                drawRect(xPosition, yPosition, xPosition + 50, yPosition + 20, Color.DARK_GRAY.getRGB());
                drawRect(xPosition + 1, yPosition + 1, xPosition + 49, yPosition + 19, canvas.getCurrentColour());
            }
        };
        layoutDraw.addComponent(colourDisplay);

        displayGrid = new CheckBox("Grid", 166, 120);
        displayGrid.setClickListener(new ClickListener() {
            @Override
            public void onClick(Component c, int mouseButton) {
                canvas.setShowGrid(displayGrid.isSelected());
            }
        });
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
}
