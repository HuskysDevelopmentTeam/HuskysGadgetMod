package net.thegaminghuskymc.gadgetmod.proxy;

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
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.api.theme.Theme;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;
import net.thegaminghuskymc.gadgetmod.network.PacketHandler;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncApplications;
import net.thegaminghuskymc.gadgetmod.network.task.MessageSyncConfig;
import net.thegaminghuskymc.gadgetmod.object.ThemeInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonProxy {

    private List<ThemeInfo> allowedThemes;

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

    public boolean registerPrint(ResourceLocation identifier, Class<? extends IPrint> classPrint)
    {
        return true;
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PacketHandler.INSTANCE.sendTo(new MessageSyncApplications(ApplicationManager.getAvailableApplications()), (EntityPlayerMP) event.player);
        PacketHandler.INSTANCE.sendTo(new MessageSyncConfig(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.PAPER)
        {
            for(EnumDyeColor color : EnumDyeColor.values()) {
                if(world.getBlockState(event.getPos()).getBlock() == GadgetBlocks.printers[color.getMetadata()])
                {
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
