package net.thegaminghuskymc.gadgetmod.enums;

import net.minecraft.util.IStringSerializable;

public enum Enum_Intel_i3_Names implements IStringSerializable {

    i3_8100("i3_8100", 0, 6, 4, 4),
    i3_8350K("i3_8350K", 1, 8, 4, 4),
    i3_7100H("i3_7100H", 2, 3, 2, 4),
    i3_7100("i3_7100", 3, 3, 2, 4),
    i3_7300T("i3_7300T", 4, 4, 2, 4),
    i3_7300("i3_7300", 5, 4, 2, 4),
    i3_7100T("i3_7100T", 6, 3, 2, 4),
    i3_7320("i3_7320", 7, 4, 2, 4),
    i3_7167U("i3_7167U", 8, 3, 2, 4),
    i3_7350K("i3_7350K", 9, 4, 2, 4),
    i3_7100U("i3_7100U", 10, 3, 2, 4),
    i3_6098P("i3_6098P", 11, 3, 2, 4),
    i3_6100U("i3_6100U", 12, 3, 2, 4),
    i3_6100H("i3_6100H", 13, 3, 2, 4),
    i3_6300T("i3_6300T", 14, 4, 2, 4),
    i3_6100("i3_6100", 15, 3, 2, 4);

    int ID, MB_cache, cores, threads;
    String name;

    Enum_Intel_i3_Names(String name, int ID, int MB_cache, int cores, int threads) {
        this.ID = ID;
        this.MB_cache = MB_cache;
        this.cores = cores;
        this.threads = threads;
        this.name = name;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getMB_cache() {
        return MB_cache;
    }

    public void setMB_cache(int MB_cache) {
        this.MB_cache = MB_cache;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}