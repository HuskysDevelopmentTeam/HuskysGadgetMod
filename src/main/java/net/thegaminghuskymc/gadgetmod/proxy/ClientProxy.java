package net.thegaminghuskymc.gadgetmod.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.thegaminghuskymc.gadgetmod.DeviceConfig;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.api.print.PrintingManager;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.tileentity.*;
import net.thegaminghuskymc.gadgetmod.tileentity.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaptop.class, new LaptopRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrinter.class, new PrinterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPaper.class, new PaperRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRouter.class, new RouterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScreen.class, new ScreenRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOfficeChair.class, new OfficeChairRenderer());

        if (HuskyGadgetMod.DEVELOPER_MODE) {
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/developer_wallpaper.png"));
        } else {
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_1.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_2.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_3.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_4.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_5.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_6.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_7.png"));
            Laptop.addWallpaper(new ResourceLocation(Reference.MOD_ID, "textures/gui/laptop_wallpaper_8.png"));
        }
    }

    @Override
    public void postInit() {
        generateIconAtlas();
    }

    private void generateIconAtlas() {
        final int ICON_SIZE = 14;
        int index = 0;

        BufferedImage atlas = new BufferedImage(ICON_SIZE * 16, ICON_SIZE * 16, BufferedImage.TYPE_INT_ARGB);
        Graphics g = atlas.createGraphics();

        try {
            BufferedImage icon = TextureUtil.readBufferedImage(ClientProxy.class.getResourceAsStream("/assets/" + Reference.MOD_ID + "/textures/icon/missing.png"));
            g.drawImage(icon, 0, 0, ICON_SIZE, ICON_SIZE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        index++;

        for (AppInfo info : ApplicationManager.getAvailableApps()) {
            ResourceLocation identifier = info.getId();
            String path = "/assets/" + identifier.getResourceDomain() + "/textures/icon/" + identifier.getResourcePath() + ".png";
            try {
                InputStream input = ClientProxy.class.getResourceAsStream(path);
                if (input != null) {
                    BufferedImage icon = TextureUtil.readBufferedImage(input);
                    if (icon.getWidth() != ICON_SIZE || icon.getHeight() != ICON_SIZE) {
                        HuskyGadgetMod.getLogger().error("Incorrect icon size for " + identifier.toString() + " (Must be 14 by 14 pixels)");
                        continue;
                    }
                    int iconU = (index % 16) * ICON_SIZE;
                    int iconV = (index / 16) * ICON_SIZE;
                    g.drawImage(icon, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    updateIcon(info, iconU, iconV);
                    index++;
                } else {
                    HuskyGadgetMod.getLogger().error("Missing icon for " + identifier.toString());
                }
            } catch (Exception e) {
                HuskyGadgetMod.getLogger().error("Unable to load icon for " + identifier.toString());
            }
        }

        g.dispose();
        Minecraft.getMinecraft().getTextureManager().loadTexture(Laptop.ICON_TEXTURES, new DynamicTexture(atlas));
    }

    private void updateIcon(AppInfo info, int iconU, int iconV) {
        ReflectionHelper.setPrivateValue(AppInfo.class, info, iconU, "iconU");
        ReflectionHelper.setPrivateValue(AppInfo.class, info, iconV, "iconV");
    }

    @Nullable
    @Override
    public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz) {
        if ("minecraft".equals(identifier.getResourceDomain())) {
            throw new IllegalArgumentException("Invalid identifier domain");
        }

        try {
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
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean registerPrint(ResourceLocation identifier, Class<? extends IPrint> classPrint)
    {
        try
        {
            Constructor<? extends IPrint> constructor = classPrint.getConstructor();
            IPrint print = constructor.newInstance();
            Class<? extends IPrint.Renderer> classRenderer = print.getRenderer();
            try
            {
                IPrint.Renderer renderer = classRenderer.newInstance();
                Map<String, IPrint.Renderer> idToRenderer = ReflectionHelper.getPrivateValue(PrintingManager.class, null, "registeredRenders");
                if(idToRenderer == null)
                {
                    idToRenderer = new HashMap<>();
                    ReflectionHelper.setPrivateValue(PrintingManager.class, null, idToRenderer, "registeredRenders");
                }
                idToRenderer.put(identifier.toString(), renderer);
            }
            catch(InstantiationException e)
            {
                HuskyGadgetMod.getLogger().error("The print renderer '" + classRenderer.getName() + "' is missing an empty constructor and could not be registered!");
                return false;
            }
            return true;
        }
        catch(Exception e)
        {
            HuskyGadgetMod.getLogger().error("The print '" + classPrint.getName() + "' is missing an empty constructor and could not be registered!");
        }
        return false;
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        allowedApps = null;
        DeviceConfig.restore();
    }

}
