package net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings;

import com.google.gson.annotations.SerializedName;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.util.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AppStoreAppInfo {

    public String name;
    @SerializedName("short_description")
    public String shortDescription;
    public String description;
    public AppStoreCategories category;

    public ArrayList<URL> urls;
    public ArrayList<String> jars;

    private transient LinkedHashSet<Class> classes = new LinkedHashSet<>();
    private transient LinkedHashSet<Class> jarClasses = new LinkedHashSet<>();

    public AppStoreAppInfo(String name, String shortDescription, String description, AppStoreCategories category, ArrayList<URL> urls, ArrayList<String> jars) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.category = category;
        this.urls = urls;
        this.jars = jars;
    }

    public void loadClasses() throws ClassNotFoundException {
        for (URL url : urls) {
            classes.add(HuskyGadgetMod.classLoader.loadClass(url.toString()));
        }
        for (String jar : jars) {
            jarClasses.addAll(Utils.loadAllClassesFromJar(jar));
        }
    }

    public void reloadClasses() throws ClassNotFoundException {
        classes.clear();
        for (URL url : urls) {
            HuskyGadgetMod.classLoader.removeFromCache(url.toString());
            classes.add(HuskyGadgetMod.classLoader.loadClass(url.toString()));
        }
    }

    public LinkedHashSet<Class> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return name + ":" + shortDescription + ":" + category;
    }
}