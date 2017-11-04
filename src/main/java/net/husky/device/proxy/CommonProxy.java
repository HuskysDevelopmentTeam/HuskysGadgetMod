package net.husky.device.proxy;

import net.husky.device.api.app.Application;
import net.husky.device.network.PacketHandler;
import net.husky.device.network.task.MessageSyncApplications;
import net.husky.device.object.AppInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonProxy
{
	protected List<AppInfo> allowedApps;

	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init() {}

	public void postInit() {}

	@Nullable
	public Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz)
	{
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
	}
}
