package net.husky.device.init;

import net.husky.device.HuskyDeviceMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class DeviceItems
{
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

    public static void init()
    {
        flash_drive = new Item().setUnlocalizedName("flash_drive").setRegistryName("flash_drive").setCreativeTab(HuskyDeviceMod.tabDevice);
        /*apple_watch = new Item().setUnlocalizedName("apple_watch").setRegistryName("apple_watch").setCreativeTab(HuskyDeviceMod.tabDevice);
        iPad = new Item().setUnlocalizedName("iPad").setRegistryName("iPad").setCreativeTab(HuskyDeviceMod.tabDevice);
        iPhone = new Item().setUnlocalizedName("iPhone").setRegistryName("iPhone").setCreativeTab(HuskyDeviceMod.tabDevice);
        cpu = new Item().setUnlocalizedName("cpu").setRegistryName("cpu").setCreativeTab(HuskyDeviceMod.tabDevice);
        motherBoard = new Item().setUnlocalizedName("mother_board").setRegistryName("mother_board").setCreativeTab(HuskyDeviceMod.tabDevice);
        ramSticks = new Item().setUnlocalizedName("ram").setRegistryName("ram_sticks").setCreativeTab(HuskyDeviceMod.tabDevice);
        videoCard = new Item().setUnlocalizedName("video_card").setRegistryName("video_card").setCreativeTab(HuskyDeviceMod.tabDevice);
        soundCard = new Item().setUnlocalizedName("sound_card").setRegistryName("sound_card").setCreativeTab(HuskyDeviceMod.tabDevice);
        wifiCard = new Item().setUnlocalizedName("wifi_card").setRegistryName("wifi_card").setCreativeTab(HuskyDeviceMod.tabDevice);

        serverKey = new Item().setUnlocalizedName("server_key").setRegistryName("server_key").setCreativeTab(HuskyDeviceMod.tabDevice);
        networksCode = new Item().setUnlocalizedName("networks_code").setRegistryName("networks_code").setCreativeTab(HuskyDeviceMod.tabDevice);*/
    }

    public static void register()
    {
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

    public static void registerRenders()
    {
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

    private static void registerRender(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
