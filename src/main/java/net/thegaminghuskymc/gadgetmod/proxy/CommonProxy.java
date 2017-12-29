package net.thegaminghuskymc.gadgetmod.proxy;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.thegaminghuskymc.gadgetmod.Reference;
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

    public World getClientWorld()
    {
        return null;
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public boolean isSinglePlayer()
    {
        return false;
    }

    public boolean isDedicatedServer()
    {
        return true;
    }

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

    private static final List<String> IGNORE_SOUNDS;

    static
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("channel_news");
        builder.add("channel_sam_tabor");
        builder.add("channel_heman");
        builder.add("channel_switch");
        builder.add("channel_cooking");
        IGNORE_SOUNDS = builder.build();
    }

    @SubscribeEvent
    public void onMissingMap(RegistryEvent.MissingMappings<SoundEvent> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<SoundEvent> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_SOUNDS.contains(missing.key.getResourcePath().toString()))
            {
                missing.ignore();
            }
        }
    }

}
