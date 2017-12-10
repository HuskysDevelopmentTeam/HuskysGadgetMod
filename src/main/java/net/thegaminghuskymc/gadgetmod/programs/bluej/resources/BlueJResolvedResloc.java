package net.thegaminghuskymc.gadgetmod.programs.bluej.resources;

import net.minecraft.nbt.NBTTagCompound;

public interface BlueJResolvedResloc {

    // maybe more
    String name();

    boolean exists();

    void create();

    void mkdir();

    void delete();

    NBTTagCompound getData();

    void setData(NBTTagCompound data);

    boolean isFile();

    boolean isFolder();

    String[] listFiles();

    String[] listFolders();

    BlueJResolvedResloc getFile(String name);

}
