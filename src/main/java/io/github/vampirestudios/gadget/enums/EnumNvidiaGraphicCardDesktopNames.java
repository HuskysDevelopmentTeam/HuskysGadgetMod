package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumNvidiaGraphicCardDesktopNames implements IStringSerializable {

    GeForce_GTX_1050("16", 0),
    GeForce_GTX_1050TI("32", 1),
    GeForce_GTX_1060("64", 2),
    GeForce_GTX_1070("128", 3),
    GeForce_GTX_1070TI("256", 4),
    GeForce_GTX_1080("520", 5),
    GeForce_GTX_1080TI("1000", 6),
    GeForce_TITAN_Xp("520", 7),
    GeForce_TITAN_X("1000", 8);

    int ID;
    String name;

    EnumNvidiaGraphicCardDesktopNames(String name, int ID) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}