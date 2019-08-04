package io.github.vampirestudios.gadget.api.app.emojie_packs;

import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.api.app.IIcon;

public enum EmojiesMRC implements IIcon {

    SMILE,
    SUSPICIOUS,
    SICK,
    WOW,
    ONE_HUNDRED,
    DOG,
    CAT,
    ANGER,
    KAWAII,
    EXPLANATION_MARK_RED,
    EXPLANATION_MARK_BLUE,
    EGGPLANT,
    THUMBS_UP,
    THUMBS_DOWN,
    GIRL,
    BOY,
    MAGIC_8_BALL,
    LANDSCAPE,
    SMILING_SUN,
    SKY,
    MRC,
    NIGHT_SKY,
    BED,
    ANGRY_FACE,
    CRYING,
    X_MAS_TREE,
    X_MAS_HAT,
    ZE_BLACK_HOLE,
    ROBOT_DERP,
    ROBOT_NORMAL;


    private static final ResourceLocation ICON_ASSET = new ResourceLocation(Reference.MOD_ID, "textures/gui/icon_packs/emojies.png");

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
