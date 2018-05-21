package net.thegaminghuskymc.gadgetmod.proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.api.theme.Theme;
import net.thegaminghuskymc.gadgetmod.block.BlockPrinter;
import net.thegaminghuskymc.gadgetmod.gui.GadgetConfig;
import net.thegaminghuskymc.gadgetmod.init.GadgetApps;
import net.thegaminghuskymc.gadgetmod.init.GadgetThemes;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncApplications;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncConfig;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.object.ThemeInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public class CommonProxy {

    List<AppInfo> allowedApps;
    List<ThemeInfo> allowedThemes;

    @SubscribeEvent
    public void preInit(FMLPreInitializationEvent event) {
        GadgetConfig.preInit();
        GadgetThemes.init();
    }

    @SubscribeEvent
    public void init(FMLInitializationEvent event) {
        GadgetApps.init();
    }

    @SubscribeEvent
    public void postInit(FMLPostInitializationEvent event) {
    }

    public boolean registerPrint(ResourceLocation identifier, Class<? extends IPrint> classPrint) {
        return true;
    }

    @Nullable
    public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz) {
        if (allowedApps == null) {
            allowedApps = new ArrayList<>();
        }
        allowedApps.add(new AppInfo(identifier, SystemApplication.class.isAssignableFrom(clazz)));
        return null;
    }

    @Nullable
    public Theme registerTheme(ResourceLocation identifier) {
        if (allowedThemes == null) {
            allowedThemes = new ArrayList<>();
        }
        allowedThemes.add(new ThemeInfo(identifier));
        return null;
    }

    public boolean hasAllowedApplications() {
        return allowedApps != null;
    }

    public boolean hasAllowedThemes() {
        return allowedThemes != null;
    }

    public List<AppInfo> getAllowedApplications() {
        if (allowedApps == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedApps);
    }

    public List<ThemeInfo> getAllowedThemes() {
        if (allowedThemes == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedThemes);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (allowedApps != null) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncApplications(allowedApps), (EntityPlayerMP) event.player);
        }
        PacketHandler.INSTANCE.sendTo(new MessageSyncConfig(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.PAPER) {
            if (world.getBlockState(event.getPos()).getBlock() instanceof BlockPrinter) {
                event.setUseBlock(Event.Result.ALLOW);
            }
        }
    }

    public void showNotification(NBTTagCompound tag) {
    }

}
