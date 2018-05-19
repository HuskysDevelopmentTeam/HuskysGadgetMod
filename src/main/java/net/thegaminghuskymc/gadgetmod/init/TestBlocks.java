package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.block.Block;
import net.thegaminghuskymc.gadgetmod.registry.RegisterBlock;
import net.thegaminghuskymc.gadgetmod.registry.RegisterItem;

public class TestBlocks {

    @RegisterBlock(registryName = "test_block")
    @RegisterItem(registryName = "test_block")
    public static Block test_block;

}
