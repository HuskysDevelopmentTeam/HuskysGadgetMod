package net.thegaminghuskymc.gadgetmod.api.app.emojie_packs;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.IIcon;

/**
 * Created by Timor Morrien
 */
@SuppressWarnings("unused")
public enum OtherEmojis implements IIcon {

    POOP,
    EYES,
    EYE,
    HEART,
    THUMB_UP,
    THUMB_DOWN,
    POINT_LEFT,
    POINT_RIGHT,
    POINT_UP,
    POINT_DOWN,
    NOSE,
    EAR,
    BRIEFCASE,
    PURSE,
    TOAST,
    GOGGLES,
    SUNGLASSES,
    CROWN,
    CD,
    UNI,
    CYLINDER,
    MICROPHONE_ONE,
    MICROPHONE_TWO,
    MONEY,
    COIN,
    EMERALD,
    CREDIT_CARD_BACK,
    CREDIT_CARD_FRONT,
    FIRE,
    LIGHT_BULB_ON,
    LIGHT_BULB_OFF,
    RAINBOW,
    SNOWMAN,
    NO_TEXTURE;

    private static final ResourceLocation ICON_ASSET = new ResourceLocation(Reference.MOD_ID, "textures/gui/icon_packs/other_emoji.png");

    private static final int ICON_SIZE = 10;
    private static final int GRID_SIZE = 20;

    @Override
    public ResourceLocation getIconAsset() {
        return ICON_ASSET;
    }

    @Override
    public int getIconSize() {
        return ICON_SIZE;
    }

    @Override
    public int getGridWidth() {
        return GRID_SIZE;
    }

    @Override
    public int getGridHeight() {
        return GRID_SIZE;
    }

    @Override
    public int getU() {
        return (ordinal() % GRID_SIZE) * ICON_SIZE;
    }

    @Override
    public int getV() {
        return (ordinal() / GRID_SIZE) * ICON_SIZE;
    }

}
