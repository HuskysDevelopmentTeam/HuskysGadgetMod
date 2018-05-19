package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.thegaminghuskymc.gadgetmod.enums.EnumComponents;
import net.thegaminghuskymc.gadgetmod.item.*;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GadgetItems {

    public static ItemComponent components;
    public static ItemFlashDrive flash_drives;

    public static final ItemPixelWatch pixel_watch;
    public static final ItemPixelTab pixel_pad;
    public static final ItemPixelPhone pixel_phone;

    public static final ItemMotherBoard motherBoard;
    public static final ItemMotherBoard.Component cpu, ramSticks, wifiCard, gpu;

    public static final Item ethernet_cable;
    public static final Item hdmi_cable;
    public static final Item usb_cable;

    public static final Item easter_egg;
    public static final Item wiiu_gamepad;

    public static final Item idCard;

    static {

        for(EnumDyeColor color : EnumDyeColor.values()) {
            flash_drives = new ItemFlashDrive(color);
        }
        for (EnumComponents type : EnumComponents.values()) {
            components = new ItemComponent(type.getName());
        }

        pixel_watch = new ItemPixelWatch();
        pixel_pad = new ItemPixelTab();
        pixel_phone = new ItemPixelPhone();
        cpu = new ItemMotherBoard.Component("cpu");
        motherBoard = new ItemMotherBoard();
        ramSticks = new ItemMotherBoard.Component("ram");
        wifiCard = new ItemMotherBoard.Component("wifi");
        ethernet_cable = new ItemEthernetCable();
        hdmi_cable = new ItemHDMICable();
        usb_cable = new ItemUSBCable();
        easter_egg = new ItemEasterEgg();
        wiiu_gamepad = new ItemWiiUGamepad();
        gpu = new ItemMotherBoard.Component("gpu");
        idCard = new ItemIDCard();
    }

    public static void register() {
//        register(pixel_watch);
        register(pixel_pad);
        register(pixel_phone);
//        register(cpu);
        register(motherBoard);
//        register(ramSticks);
//        register(gpu);
//        register(wifiCard);
        register(ethernet_cable);
        register(hdmi_cable);
        register(usb_cable);
        register(easter_egg);
        register(wiiu_gamepad);
    }

    private static void register(Item item) {
        RegistrationHandler.Items.add(item);
    }

}
