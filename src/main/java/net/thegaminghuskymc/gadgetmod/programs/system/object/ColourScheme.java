package net.thegaminghuskymc.gadgetmod.programs.system.object;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import java.awt.*;

public class ColourScheme {

    private int textColour;
    private int textSecondaryColour;
    private int headerColour;
    private int backgroundColour;
    private int itemBackgroundColour;
    private int itemHighlightColour;
    private int buttonNormalColour;
    private int buttonHoveredColour;
    private int buttonDisabledColour;
    private int taskBarColour;
    private int mainApplicationBarColour;
    private int secondApplicationBarColour;

    public ColourScheme() {
        resetDefault();
    }

    public static ColourScheme fromTag(NBTTagCompound tag) {
        ColourScheme scheme = new ColourScheme();
        if (tag.hasKey("textColour", Constants.NBT.TAG_INT)) {
            scheme.textColour = tag.getInteger("textColour");
        }
        if (tag.hasKey("textSecondaryColour", Constants.NBT.TAG_INT)) {
            scheme.textSecondaryColour = tag.getInteger("textSecondaryColour");
        }
        if (tag.hasKey("headerColour", Constants.NBT.TAG_INT)) {
            scheme.headerColour = tag.getInteger("headerColour");
        }
        if (tag.hasKey("backgroundColour", Constants.NBT.TAG_INT)) {
            scheme.backgroundColour = tag.getInteger("backgroundColour");
        }
        if (tag.hasKey("itemBackgroundColour", Constants.NBT.TAG_INT)) {
            scheme.itemBackgroundColour = tag.getInteger("itemBackgroundColour");
        }
        if (tag.hasKey("itemHighlightColour", Constants.NBT.TAG_INT)) {
            scheme.itemHighlightColour = tag.getInteger("itemHighlightColour");
        }
        if (tag.hasKey("buttonNormalColour", Constants.NBT.TAG_INT)) {
            scheme.buttonNormalColour = tag.getInteger("buttonNormalColour");
        }
        if (tag.hasKey("buttonHoveredColour", Constants.NBT.TAG_INT)) {
            scheme.buttonHoveredColour = tag.getInteger("buttonHoveredColour");
        }
        if (tag.hasKey("buttonDisabledColour", Constants.NBT.TAG_INT)) {
            scheme.buttonDisabledColour = tag.getInteger("buttonDisabledColour");
        }
        if (tag.hasKey("taskBarColour", Constants.NBT.TAG_INT)) {
            scheme.taskBarColour = tag.getInteger("taskBarColour");
        }
        if (tag.hasKey("mainApplicationBarColour", Constants.NBT.TAG_INT)) {
            scheme.mainApplicationBarColour = tag.getInteger("mainApplicationBarColour");
        }
        if (tag.hasKey("secondApplicationBarColour", Constants.NBT.TAG_INT)) {
            scheme.secondApplicationBarColour = tag.getInteger("secondApplicationBarColour");
        }
        return scheme;
    }

    public int getTextColour() {
        return textColour;
    }

    public int getHeaderColour() {
        return headerColour;
    }

    public int getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public int getItemBackgroundColour() {
        return itemBackgroundColour;
    }

    public int getItemHighlightColour() {
        return itemHighlightColour;
    }

    public int getMainApplicationBarColour() {
        return mainApplicationBarColour;
    }

    public void setMainApplicationBarColour(int mainApplicationBarColour) {
        this.mainApplicationBarColour = mainApplicationBarColour;
    }

    public int getSecondApplicationBarColour() {
        return secondApplicationBarColour;
    }

    public void setSecondApplicationBarColour(int secondApplicationBarColour) {
        this.secondApplicationBarColour = secondApplicationBarColour;
    }

    public void setHeaderColour(int headerColour) {
        this.headerColour = headerColour;
    }

    public void setItemBackgroundColour(int itemBackgroundColour) {
        this.itemBackgroundColour = itemBackgroundColour;
    }

    public void setItemHighlightColour(int itemHighlightColour) {
        this.itemHighlightColour = itemHighlightColour;
    }

    public int getButtonNormalColour() {
        return buttonNormalColour;
    }

    public void setButtonNormalColour(int buttonNormalColour) {
        this.buttonNormalColour = buttonNormalColour;
    }

    public int getButtonHoveredColour() {
        return buttonHoveredColour;
    }

    public void setButtonHoveredColour(int buttonHoveredColour) {
        this.buttonHoveredColour = buttonHoveredColour;
    }

    public int getButtonDisabledColour() {
        return buttonDisabledColour;
    }

    public void setButtonDisabledColour(int buttonDisabledColour) {
        this.buttonDisabledColour = buttonDisabledColour;
    }

    private void resetDefault() {
        textColour = Color.decode("0xFFFFFF").getRGB();
        textSecondaryColour = Color.decode("0x9BEDF2").getRGB();
        headerColour = Color.decode("0x535861").getRGB();
        backgroundColour = Color.decode("0x3D4147").getRGB();
        itemBackgroundColour = Color.decode("0x9E9E9E").getRGB();
        itemHighlightColour = Color.decode("0x757575").getRGB();
        buttonNormalColour = Color.decode("0x535861").getRGB();
        buttonHoveredColour = Color.decode("0x535861").getRGB();
        buttonDisabledColour = Color.decode("0x535861").getRGB();
        taskBarColour = Color.decode("0x9E9E9E").getRGB();
        mainApplicationBarColour = Color.decode("0x9E9E9E").getRGB();
        secondApplicationBarColour = Color.decode("0x7F7F7F").getRGB();
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("textColour", textColour);
        tag.setInteger("textSecondaryColour", textSecondaryColour);
        tag.setInteger("headerColour", headerColour);
        tag.setInteger("backgroundColour", backgroundColour);
        tag.setInteger("itemBackgroundColour", itemBackgroundColour);
        tag.setInteger("itemHighlightColour", itemHighlightColour);
        tag.setInteger("buttonNormalColour", buttonNormalColour);
        tag.setInteger("buttonHoveredColour", buttonHoveredColour);
        tag.setInteger("buttonDisabledColour", buttonDisabledColour);
        tag.setInteger("taskBarColour", taskBarColour);
        tag.setInteger("mainApplicationBarColour", mainApplicationBarColour);
        tag.setInteger("secondApplicationBarColour", secondApplicationBarColour);

        return tag;
    }
}