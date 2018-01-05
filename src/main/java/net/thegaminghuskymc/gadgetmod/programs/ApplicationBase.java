package net.thegaminghuskymc.gadgetmod.programs;

import net.thegaminghuskymc.gadgetmod.api.app.Application;

import java.io.File;

public abstract class ApplicationBase extends Application {
	
	public File appDataDir;
	
	public File getAppDataDir() {
		return appDataDir;
	}
	
}
