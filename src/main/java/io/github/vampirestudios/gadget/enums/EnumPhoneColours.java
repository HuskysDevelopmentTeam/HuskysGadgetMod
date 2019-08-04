package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumPhoneColours implements IStringSerializable {

    WHITE("white", 0),
    SILVER("silver", 1),
    BLACK("black", 2);

    private static final EnumPhoneColours[] META_LOOKUP = new EnumPhoneColours[values().length];
    int ID;
    String name;

    EnumPhoneColours(String name, int ID) {
        this.ID = ID;
        this.name = name;
    }

    public static EnumPhoneColours byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    @Override
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

}