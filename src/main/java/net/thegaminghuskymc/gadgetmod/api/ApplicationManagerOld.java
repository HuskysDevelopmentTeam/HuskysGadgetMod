package net.thegaminghuskymc.gadgetmod.api;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Application;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Manages all the applications that can be added to the {@link net.thegaminghuskymc.gadgetmod.core.BaseDevice} in the device mod. Can be used to register an application with {@link #registerApplication(ResourceLocation, Class)} or to get certain applications.
 * 
 * <br>
 * </br>
 * 
 * <b>Author: MrCrayfish</b>
 */
public final class ApplicationManagerOld
{
	private static final Map<ResourceLocation, AppInfo> APP_INFO = new HashMap<>();

	private ApplicationManagerOld()
	{
	}

	/**
	 * Registers an application into the application list
	 * <p>
	 * Example: {@code new ResourceLocation("modid", "appid");}
	 *
	 * @param identifier
	 *            simply just an id for the application.
	 * @param clazz
	 *            The class of the application
	 */
	@Nullable
	public static Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz)
	{
		Application application = HuskyGadgetMod.proxy.registerApplication(identifier, clazz);
		if (application != null)
		{
			APP_INFO.put(identifier, application.getInfo());
			return application;
		}
		return null;
	}

	/**
	 * Get all applications that are registered. The returned collection does not include system applications, see {@link #getSystemApplications()} or {@link #getAllApplications()}. Please note that this list is read only and cannot be modified.
	 *
	 * @return the application list
	 */
	public static List<AppInfo> getAvailableApplications()
	{
		final Predicate<AppInfo> FILTER = info -> !info.isSystemApp() && (!HuskyGadgetMod.proxy.hasAllowedApplications() || HuskyGadgetMod.proxy.getAllowedApplications().contains(info));
		return APP_INFO.values().stream().filter(FILTER).collect(Collectors.toList());
	}

	/**
	 * Get all applications that are registered as system apps. The returned collection only includes system applications. Please note that this list is read only and cannot be modified.
	 *
	 * @return the application list
	 */
	public static List<AppInfo> getSystemApplications()
	{
		return APP_INFO.values().stream().filter(AppInfo::isSystemApp).collect(Collectors.toList());
	}

	/**
	 * Get all applications. Please note that this list is read only and cannot be modified.
	 *
	 * @return the application list
	 */
	public static List<AppInfo> getAllApplications()
	{
		return new ArrayList<>(APP_INFO.values());
	}

	/**
	 * Checks all the apps registered for a matching app or null if one was not found.
	 * 
	 * @param appId
	 *            The id of the app. Example {@code #getApplication("modid.appid")}
	 * @return The application info that was found with that id or null of there was not one registered to that id
	 */
	@Nullable
	public static AppInfo getApplication(String appId)
	{
		return APP_INFO.get(new ResourceLocation(appId.replace(".", ":")));
	}
}