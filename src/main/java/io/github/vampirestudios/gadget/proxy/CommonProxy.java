package io.github.vampirestudios.gadget.proxy;

import io.github.vampirestudios.gadget.api.AppInfo;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.print.IPrint;
import io.github.vampirestudios.gadget.api.theme.Theme;
import io.github.vampirestudios.gadget.init.GadgetBlocks;
import io.github.vampirestudios.gadget.network.PacketHandler;
import io.github.vampirestudios.gadget.network.task.MessageSyncApplications;
import io.github.vampirestudios.gadget.network.task.MessageSyncConfig;
import io.github.vampirestudios.gadget.object.ThemeInfo;
import io.github.vampirestudios.gadget.programs.system.SystemApplication;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonProxy {

    private List<ThemeInfo> allowedThemes;
    List<AppInfo> allowedApps;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Nullable
    public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz) {
        if(allowedApps == null) {
            allowedApps = new ArrayList<>();
        }
        if(SystemApplication.class.isAssignableFrom(clazz)) {
            allowedApps.add(new AppInfo(identifier, true));
        }
        else {
            allowedApps.add(new AppInfo(identifier, false));
        }
        return null;
    }

    public boolean registerPrint(ResourceLocation identifier, Class<? extends IPrint> classPrint)
    {
        return true;
    }

    public boolean hasAllowedApplications()
    {
        return allowedApps != null;
    }

    public List<AppInfo> getAllowedApplications()
    {
        if(allowedApps == null)
        {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedApps);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(allowedApps != null) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncApplications(allowedApps), (EntityPlayerMP) event.player);
        }
        PacketHandler.INSTANCE.sendTo(new MessageSyncConfig(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.PAPER) {
            for(EnumDyeColor color : EnumDyeColor.values()) {
                if(world.getBlockState(event.getPos()).getBlock() == GadgetBlocks.printers[color.getMetadata()]) {
                    event.setUseBlock(Event.Result.ALLOW);
                }
            }
        }
    }

    public void showNotification(NBTTagCompound tag) {}

    @Nullable
    public Theme registerTheme(ResourceLocation identifier) {
        if (allowedThemes == null) {
            allowedThemes = new ArrayList<>();
        }
        allowedThemes.add(new ThemeInfo(identifier));
        return null;
    }

    public boolean hasAllowedThemes() {
        return allowedThemes != null;
    }

    public List<ThemeInfo> getAllowedThemes() {
        if (allowedThemes == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedThemes);
    }

}
