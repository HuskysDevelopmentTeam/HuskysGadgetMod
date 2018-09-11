package net.thegaminghuskymc.gadgetmod.api;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceApplication;
import net.thegaminghuskymc.gadgetmod.programs.system.SystemApplication;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ApplicationManager
{
	private static final Map<ResourceLocation, AppInfo> APP_INFO_MAP = new HashMap<>();
	private static List<AppInfo> whitelistedApps;

	private ApplicationManager() {}

	public static void init(ASMDataTable table)
	{
		Set<ASMDataTable.ASMData> apps = table.getAll(DeviceApplication.class.getCanonicalName());
		apps.forEach(asmData ->
		{
			try
			{
				Class clazz = Class.forName(asmData.getClassName());
				if(Application.class.isAssignableFrom(clazz))
				{
					DeviceApplication deviceApp = (DeviceApplication) clazz.getDeclaredAnnotation(DeviceApplication.class);
					ResourceLocation identifier = new ResourceLocation(deviceApp.modId(), deviceApp.appId());

					if(!APP_INFO_MAP.containsKey(identifier))
					{
						AppInfo info = new AppInfo(identifier, SystemApplication.class.isAssignableFrom(clazz));
						APP_INFO_MAP.put(identifier, info);
					}
					else
					{
						throw new RuntimeException(String.format("Class %s has an error", clazz.getCanonicalName()));
					}
				}
				else
				{
					throw new ClassCastException("The class " + clazz.getCanonicalName() + " is annotated with DeviceApplication but does not extend Application!");
				}
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		});
	}

	/**
	 * Get all applications that are registered. The returned collection does not include system
	 * applications, see {@link #getSystemApplications()} or {@link #getAllApplications()}. Please
	 * note that this list is read only and cannot be modified.
	 *
	 * @return the application list
	 */
	public static List<AppInfo> getAvailableApplications()
	{
		final Predicate<AppInfo> FILTER = info -> !info.isSystemApp() && isApplicationWhitelisted(info);
		return APP_INFO_MAP.values().stream().filter(FILTER).collect(Collectors.toList());
	}

	public static List<AppInfo> getSystemApplications()
	{
		return APP_INFO_MAP.values().stream().filter(AppInfo::isSystemApp).collect(Collectors.toList());
	}

	public static List<AppInfo> getAllApplications()
	{
		return new ArrayList<>(APP_INFO_MAP.values());
	}

	@Nullable
	public static AppInfo getApplication(String appId)
	{
		return APP_INFO_MAP.get(new ResourceLocation(appId.replace(".", ":")));
	}

	public static boolean isApplicationWhitelisted(AppInfo info)
	{
		return info.isSystemApp() || whitelistedApps == null || whitelistedApps.contains(info);
	}
}