package io.github.vampirestudios.gadget.api.boot.interfaces;

public interface IBootLoader {

    void getBootableDisk();

    boolean isPoweredOn();

    boolean hasBootableDisk();

}
