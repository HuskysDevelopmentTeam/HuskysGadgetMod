package net.thegaminghuskymc.gadgetmod.init;

import net.husky.device.HuskyGadgetMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class DeviceItems {
    public static Item flash_drive;
    public static Item apple_watch;
    public static Item iPad;
    public static Item iPhone;

    public static Item cpu;
    public static Item motherBoard;
    public static Item ramSticks;
    public static Item videoCard;
    public static Item soundCard;
    public static Item wifiCard;

    public static Item serverKey;
    public static Item networksCode;

    public static void init() {
        flash_drive = new Item().setUnlocalizedName("flash_drive").setRegistryName("flash_drive").setCreativeTab(HuskyGadgetMod.tabDevice);
        /*apple_watch = new Item().setUnlocalizedName("apple_watch").setRegistryName("apple_watch").setCreativeTab(HuskyGadgetMod.tabDevice);
        iPad = new Item().setUnlocalizedName("iPad").setRegistryName("iPad").setCreativeTab(HuskyGadgetMod.tabDevice);
        iPhone = new Item().setUnlocalizedName("iPhone").setRegistryName("iPhone").setCreativeTab(HuskyGadgetMod.tabDevice);
        cpu = new Item().setUnlocalizedName("cpu").setRegistryName("cpu").setCreativeTab(HuskyGadgetMod.tabDevice);
        motherBoard = new Item().setUnlocalizedName("mother_board").setRegistryName("mother_board").setCreativeTab(HuskyGadgetMod.tabDevice);
        ramSticks = new Item().setUnlocalizedName("ram").setRegistryName("ram_sticks").setCreativeTab(HuskyGadgetMod.tabDevice);
        videoCard = new Item().setUnlocalizedName("video_card").setRegistryName("video_card").setCreativeTab(HuskyGadgetMod.tabDevice);
        soundCard = new Item().setUnlocalizedName("sound_card").setRegistryName("sound_card").setCreativeTab(HuskyGadgetMod.tabDevice);
        wifiCard = new Item().setUnlocalizedName("wifi_card").setRegistryName("wifi_card").setCreativeTab(HuskyGadgetMod.tabDevice);

        serverKey = new Item().setUnlocalizedName("server_key").setRegistryName("server_key").setCreativeTab(HuskyGadgetMod.tabDevice);
        networksCode = new Item().setUnlocalizedName("networks_code").setRegistryName("networks_code").setCreativeTab(HuskyGadgetMod.tabDevice);*/
    }

    public static void register() {
        registerItem(flash_drive);
        /*registerItem(apple_watch);
        registerItem(iPad);
        registerItem(iPhone);
        registerItem(cpu);
        registerItem(motherBoard);
        registerItem(ramSticks);
        registerItem(videoCard);
        registerItem(soundCard);
        registerItem(wifiCard);*/
    }

    public static void registerItem(Item item) {
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerRenders() {
        registerRender(flash_drive);
//        registerRender(apple_watch);
//        registerRender(iPad);
//        registerRender(iPhone);
//        registerRender(cpu);
//        registerRender(motherBoard);
//        registerRender(ramSticks);
//        registerRender(videoCard);
//        registerRender(soundCard);
//        registerRender(wifiCard);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
