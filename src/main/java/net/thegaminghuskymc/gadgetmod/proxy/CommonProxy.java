package net.thegaminghuskymc.gadgetmod.proxy;

import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncApplications;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncConfig;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonProxy {

    List<AppInfo> allowedApps;

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void init() {
    }

    public void postInit() {
    }

    public boolean registerPrint(ResourceLocation identifier, Class<? extends IPrint> classPrint)
    {
        return true;
    }

    @Nullable
    public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz) {
        if(allowedApps == null)
        {
            allowedApps = new ArrayList<>();
        }
        allowedApps.add(new AppInfo(identifier));
        return null;
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
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(allowedApps != null)
        {
            PacketHandler.INSTANCE.sendTo(new MessageSyncApplications(allowedApps), (EntityPlayerMP) event.player);
        }
        PacketHandler.INSTANCE.sendTo(new MessageSyncConfig(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.PAPER)
        {
            if(world.getBlockState(event.getPos()).getBlock() == GadgetBlocks.PRINTER)
            {
                event.setUseBlock(Event.Result.ALLOW);
            }
        }
    }

}
