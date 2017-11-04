package net.husky.device.api;

import net.husky.device.HuskyDeviceMod;
import net.husky.device.api.app.Application;
import net.husky.device.object.AppInfo;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ApplicationManager
{
	private static final Map<ResourceLocation, AppInfo> APP_INFO = new HashMap<>();

	/**
	 * Registers an application into the application list
	 *
	 * The identifier parameter is simply just an id for the application.
	 * <p>
	 * Example: {@code new ResourceLocation("modid:appid");}
	 *
	 * @param identifier the
	 * @param clazz
	 */
	@Nullable
	public static Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz)
	{
		Application application = HuskyDeviceMod.proxy.registerApplication(identifier, clazz);
		if(application != null)
		{
			APP_INFO.put(identifier, application.getInfo());
			return application;
		}
		return null;
	}

	/**
	 * Get all applications that are registered. Please note
	 * that this list is read only and cannot be modified.
	 *
	 * @return the application list
	 */
	public static Collection<AppInfo> getAvailableApps()
	{
		return APP_INFO.values();
	}

	@Nullable
	public static AppInfo getApplication(String appId)
	{
		return APP_INFO.get(new ResourceLocation(appId.replace(".", ":")));
	}
}
