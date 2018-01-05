package net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings;

import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;

import java.net.URL;
import java.util.ArrayList;

public class AppStoreAppInfo {

    public String name;
    public String shortDescription;
    public String description;
    public AppStoreCategories category;

    public ArrayList<URL> urls;
    public ArrayList<Class> classes = new ArrayList<>();

    public AppStoreAppInfo(String name, String shortDescription, String description, AppStoreCategories category, ArrayList<URL> urls) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.category = category;
        this.urls = urls;
    }

    public void loadClasses() throws ClassNotFoundException {
        for (URL url : urls) {
            classes.add(HuskyGadgetMod.classLoader.loadClass(url.toString()));
        }
    }

    public void reloadClasses() throws ClassNotFoundException {
        classes.clear();
        for (URL url : urls) {
            HuskyGadgetMod.classLoader.removeFromCache(url.toString());
            classes.add(HuskyGadgetMod.classLoader.loadClass(url.toString()));
        }
    }

}