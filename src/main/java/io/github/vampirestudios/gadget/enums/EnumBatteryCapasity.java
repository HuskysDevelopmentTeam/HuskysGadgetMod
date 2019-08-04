package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumBatteryCapasity implements IStringSerializable {

    ;

    int ID;
    String name;

    EnumBatteryCapasity(String name, int ID) {
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