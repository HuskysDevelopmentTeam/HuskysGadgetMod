package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumNvidiaGraphicCardLaptopNames implements IStringSerializable {

    GeForce_GTX_10_Series_Notebooks("16", 0),
    GeForce_GTX_965M("32", 1),
    GeForce_GTX_960M("64", 2),
    GeForce_GTX_950M("128", 3),
    GeForce_MX150("256", 4),
    GeForce_MX130("520", 5),
    GeForce_MX110("1000", 6);

    int ID;
    String name;

    EnumNvidiaGraphicCardLaptopNames(String name, int ID) {
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