package net.husky.device.proxy;

import net.husky.device.HuskyDeviceMod;
import net.husky.device.Reference;
import net.husky.device.api.ApplicationManager;
import net.husky.device.api.app.Application;
import net.husky.device.core.Laptop;
import net.husky.device.init.DeviceBlocks;
import net.husky.device.init.DeviceItems;
import net.husky.device.object.AppInfo;
import net.husky.device.tileentity.TileEntityLaptop;
import net.husky.device.tileentity.render.LaptopRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        DeviceBlocks.registerRenders();
        DeviceItems.registerRenders();
    }

    @Override
    public void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaptop.class, new LaptopRenderer());

        if(HuskyDeviceMod.DEVELOPER_MODE)
        {
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/developer_wallpaper.png"));
        }
        else
        {
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_1.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_2.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_3.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_4.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_5.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_6.png"));
        }
    }

    @Override
    public void postInit()
    {
        generateIconAtlas();
    }

    public void generateIconAtlas()
    {
        final int ICON_SIZE = 14;
        int index = 0;

        BufferedImage atlas = new BufferedImage(ICON_SIZE * 16, ICON_SIZE * 16, BufferedImage.TYPE_INT_ARGB);
        Graphics g = atlas.createGraphics();

        try
        {
            BufferedImage icon = TextureUtil.readBufferedImage(ClientProxy.class.getResourceAsStream("/assets/" + Reference.MOD_ID + "/textures/icon/missing.png"));
            g.drawImage(icon, 0, 0, ICON_SIZE, ICON_SIZE, null);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        index++;

        for(AppInfo info : ApplicationManager.getAvailableApps())
        {
            ResourceLocation identifier = info.getId();
            String path = "/assets/" + identifier.getResourceDomain() + "/textures/icon/" + identifier.getResourcePath() + ".png";
            try
            {
                InputStream input = ClientProxy.class.getResourceAsStream(path);
                if(input != null)
                {
                    BufferedImage icon = TextureUtil.readBufferedImage(input);
                    if(icon.getWidth() != ICON_SIZE || icon.getHeight() != ICON_SIZE)
                    {
                        HuskyDeviceMod.getLogger().error("Incorrect icon size for " + identifier.toString() + " (Must be 14 by 14 pixels)");
                        continue;
                    }
                    int iconU = (index % 16) * ICON_SIZE;
                    int iconV = (index / 16) * ICON_SIZE;
                    g.drawImage(icon, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    updateIcon(info, iconU, iconV);
                    index++;
                }
                else
                {
                    HuskyDeviceMod.getLogger().error("Missing icon for " + identifier.toString());
                }
            }
            catch(Exception e)
            {
                HuskyDeviceMod.getLogger().error("Unable to load icon for " + identifier.toString());
            }
        }

        g.dispose();
        Minecraft.getMinecraft().getTextureManager().loadTexture(Laptop.ICON_TEXTURES, new DynamicTexture(atlas));
    }

    private void updateIcon(AppInfo info, int iconU, int iconV)
    {
        ReflectionHelper.setPrivateValue(AppInfo.class, info, iconU, "iconU");
        ReflectionHelper.setPrivateValue(AppInfo.class, info, iconV, "iconV");
    }

    @Nullable
    @Override
    public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz)
    {
        if("minecraft".equals(identifier.getResourceDomain()))
        {
            throw new IllegalArgumentException("Invalid identifier domain");
        }

        try
        {
            Application application = clazz.newInstance();
            java.util.List<Application> APPS = ReflectionHelper.getPrivateValue(Laptop.class, null, "APPLICATIONS");
            APPS.add(application);

            AppInfo info = new AppInfo(identifier);

            Field field = Application.class.getDeclaredField("info");
            field.setAccessible(true);

            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(application, info);

            return application;
        }
        catch(InstantiationException | IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        allowedApps = null;
    }
}
