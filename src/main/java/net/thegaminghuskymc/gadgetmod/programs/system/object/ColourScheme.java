package net.thegaminghuskymc.gadgetmod.programs.system.object;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import java.awt.*;

/**
 * Author: MrCrayfish
 */
public class ColourScheme {

    public int textColour;
    public int textSecondaryColour;
    public int headerColour;
    public int backgroundColour;
    public int backgroundSecondaryColour;
    public int itemBackgroundColour;
    public int itemHighlightColour;
    public int buttonNormalColour;
    public int buttonHoveredColour;
    public int buttonDisabledColour;
    public int taskBarColour;
    public int applicationBarColour;

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
        if (tag.hasKey("backgroundSecondaryColour", Constants.NBT.TAG_INT)) {
            scheme.backgroundSecondaryColour = tag.getInteger("backgroundSecondaryColour");
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
        if (tag.hasKey("applicationBarColour", Constants.NBT.TAG_INT)) {
            scheme.applicationBarColour = tag.getInteger("applicationBarColour");
        }
        return scheme;
    }

    public int getTextColour() {
        return textColour;
    }

    public void setTextColour(int textColour) {
        this.textColour = textColour;
    }

    public int getTextSecondaryColour() {
        return textSecondaryColour;
    }

    public void setTextSecondaryColour(int textSecondaryColour) {
        this.textSecondaryColour = textSecondaryColour;
    }

    public int getHeaderColour() {
        return headerColour;
    }

    public void setHeaderColour(int headerColour) {
        this.headerColour = headerColour;
    }

    public int getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public int getBackgroundSecondaryColour() {
        return backgroundSecondaryColour;
    }

    public void setBackgroundSecondaryColour(int backgroundSecondaryColour) {
        this.backgroundSecondaryColour = backgroundSecondaryColour;
    }

    public int getItemBackgroundColour() {
        return itemBackgroundColour;
    }

    public void setItemBackgroundColour(int itemBackgroundColour) {
        this.itemBackgroundColour = itemBackgroundColour;
    }

    public int getItemHighlightColour() {
        return itemHighlightColour;
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

    public int getTaskBarColour() {
        return taskBarColour;
    }

    public void setTaskBarColour(int taskBarColour) {
        this.taskBarColour = taskBarColour;
    }

    public int getApplicationBarColour() {
        return applicationBarColour;
    }

    public void setApplicationBarColour(int applicationBarColour) {
        this.applicationBarColour = applicationBarColour;
    }

    public void resetDefault() {
        textColour = Color.decode("0xFFFFFF").getRGB();
        textSecondaryColour = Color.decode("0x9BEDF2").getRGB();
        headerColour = Color.decode("0x535861").getRGB();
        backgroundColour = Color.decode("0x535861").getRGB();
        backgroundSecondaryColour = 0;
        itemBackgroundColour = Color.decode("0x9E9E9E").getRGB();
        itemHighlightColour = Color.decode("0x757575").getRGB();

        buttonNormalColour = Color.decode("0x535861").getRGB();
        buttonHoveredColour = Color.decode("0x535861").getRGB();
        buttonDisabledColour = Color.decode("0x535861").getRGB();
        taskBarColour = Color.decode("0x9E9E9E").getRGB();
        applicationBarColour = Color.decode("0x757575").getRGB();
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("textColour", textColour);
        tag.setInteger("textSecondaryColour", textSecondaryColour);
        tag.setInteger("headerColour", headerColour);
        tag.setInteger("backgroundColour", backgroundColour);
        tag.setInteger("backgroundSecondaryColour", backgroundSecondaryColour);
        tag.setInteger("itemBackgroundColour", itemBackgroundColour);
        tag.setInteger("itemHighlightColour", itemHighlightColour);

        tag.setInteger("buttonNormalColour", buttonNormalColour);
        tag.setInteger("buttonHoveredColour", buttonHoveredColour);
        tag.setInteger("buttonDisabledColour", buttonDisabledColour);
        tag.setInteger("taskBarColour", taskBarColour);
        tag.setInteger("applicationBarColour", applicationBarColour);

        return tag;
    }
}