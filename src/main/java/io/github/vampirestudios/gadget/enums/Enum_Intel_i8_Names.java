package io.github.vampirestudios.gadget.enums;

import net.minecraft.util.IStringSerializable;

public enum Enum_Intel_i8_Names implements IStringSerializable {

    INTEL_i7_8700("i7_8700", 0, 12, 6, 12),
    INTEL_i7_8700K("i7_8700K", 1, 12, 6, 12),
    INTEL_i7_8550U("i7_8550U", 2, 8, 4, 8),
    INTEL_i7_8650U("i7_8650U", 3, 8, 4, 8),
    INTEL_i7_7920HQ("i7_7920HQ", 4, 8, 4, 8),
    INTEL_i7_77000T("i7_77000T", 5, 8, 4, 8),
    INTEL_i7_7700HQ("i7_7700HQ", 6, 6, 4, 8),
    INTEL_i7_7660U("i7_7660U", 7, 4, 2, 4),
    INTEL_i7_7820HK("i7_7820HK", 8, 8, 4, 8),
    INTEL_i7_7700("i7_7700", 9, 8, 4, 8),
    INTEL_i7_7650U("i7_7650U", 10, 4, 2, 4),
    INTEL_i7_7700K("i7_7700K", 11, 8, 4, 8),
    INTEL_i7_7567U("i7_7567U", 12, 4, 2, 4),
    INTEL_i7_7820HQ("i7_7820HQ", 13, 8, 4, 8),
    INTEL_i7_7500U("i7_7500U", 14, 4, 2, 4),
    INTEL_i7_6785R("i7_6785R", 15, 8, 4, 8);

    int ID, MB_cache, cores, threads;
    String name;

    Enum_Intel_i8_Names(String name, int ID, int MB_cache, int cores, int threads) {
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