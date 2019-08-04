package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumComponents implements IStringSerializable {

    HARD_DRIVE("hard_drive", 0),
    SOUND_CARD("sound_card", 1),
    BATTERY("battery", 2),
    SCREEN("computer_screen", 3),
    CIRCUIT_BOARD("printed_circuit", 4),
    PLASTIC("plastic", 5),
    RAW_PLASTIC("raw_plastic", 6),
    PLASTIC_FRAME("plastic_frame", 7),
    WHEEL("wheel", 8),
    SOLID_STATE_DRIVE("solid_state_drive", 9),
    FLASH_CHIP("flash_chip", 10),
    PRINTER_CARTRIDGES("printer_cartridges", 11),
    MEMORY_CARD("memory_card", 12),
    PHONE_CAMERA("phone_camera", 13),
    PHONE_BATTERY("phone_battery", 14),
    PHONE_MOTHER_BOARD("phone_mother_board", 15),
    PHONE_FRAME("phone_frame", 16),
    BLUETOOTH_CARD("bluetooth_card", 17),
    BLUETOOTH_MODULE("bluetooth_module", 18),
    PHONE_SCREEN("phone_screen", 19);

    private static final EnumComponents[] META_LOOKUP = new EnumComponents[values().length];
    int ID;
    String name;

    EnumComponents(String name, int ID) {
        this.ID = ID;
        this.name = name;
    }

    public static EnumComponents byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    @Override
    public String getName() {
        return this.name.toLowerCase();
    }

    public int getID() {
        return ID;
    }

}