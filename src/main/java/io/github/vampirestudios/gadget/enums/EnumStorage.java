package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumStorage implements IStringSerializable {

    SIXTEEN("16", 0),
    THIRTY_TWO("32", 1),
    SIXTY_FOUR("64", 2),
    ONE_TWENTY_EIGHT("128", 3),
    TWO_FIFTY_SIX("256", 4),
    FIVE_TWENTY("520", 5),
    ONE_THOUSAND("1000", 6),
    TWO_THOUSAND("2000", 7),
    THREE_THOUSAND("3000", 8),
    FOUR_THOUSAND("4000", 9),
    FIVE_THOUSAND("5000", 10),
    SIX_THOUSAND("6000", 11),
    SEVEN_THOUSAND("7000", 12),
    EIGHT_THOUSAND("8000", 13),
    NINE_THOUSAND("9000", 14),
    TEN_THOUSAND("10000", 15);

    int ID;
    String name;

    EnumStorage(String name, int ID) {
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