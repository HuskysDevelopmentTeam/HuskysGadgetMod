package net.thegaminghuskymc.gadgetmod.api.app;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class CurrencySets {

    static ArrayList<Item> foodArray = new ArrayList<Item>();
    private static ItemStack[] moddedFood = new ItemStack[foodArray.size()];

    static {
        for (ResourceLocation rl : Item.REGISTRY.getKeys()) {
            Item i = Item.REGISTRY.getObject(rl);
            if (i instanceof ItemFood) {
                foodArray.add(i);
            }
        }

        moddedFood = foodArray.toArray(moddedFood);
    }

    private ItemStack[] emerals = new ItemStack[]{
            new ItemStack(Items.EMERALD),
            new ItemStack(Blocks.EMERALD_BLOCK)
    };
    private ItemStack defaultCurrency = getEmerals()[0];
    private ItemStack[] diamond = new ItemStack[]{
            new ItemStack(Items.DIAMOND),
            new ItemStack(Blocks.DIAMOND_BLOCK)
    };

    private ItemStack[] gold = new ItemStack[]{
            new ItemStack(Items.GOLD_INGOT),
            new ItemStack(Blocks.GOLD_BLOCK)
    };

    private ItemStack[] iron = new ItemStack[]{
            new ItemStack(Items.IRON_INGOT),
            new ItemStack(Blocks.IRON_BLOCK)
    };

    private ItemStack[] food = new ItemStack[]{
            new ItemStack(Items.APPLE),
            new ItemStack(Items.COOKIE),
            new ItemStack(Items.POTATO),
            new ItemStack(Items.MELON),
            new ItemStack(Items.FISH),
            new ItemStack(Items.MUSHROOM_STEW),
            new ItemStack(Items.BREAD),
            new ItemStack(Items.PORKCHOP),
            new ItemStack(Items.COOKED_BEEF),
            new ItemStack(Items.COOKED_FISH),
            new ItemStack(Items.COOKED_CHICKEN),
            new ItemStack(Items.COOKED_PORKCHOP),
            new ItemStack(Items.COOKED_MUTTON),
            new ItemStack(Items.COOKED_RABBIT),
            new ItemStack(Items.GOLDEN_APPLE),
            new ItemStack(Items.CAKE),
            new ItemStack(Items.BEEF),
            new ItemStack(Items.BEETROOT),
            new ItemStack(Items.RABBIT),
            new ItemStack(Items.CHICKEN),
            new ItemStack(Items.RABBIT_STEW),
            new ItemStack(Items.BAKED_POTATO)
    };

    public ItemStack[] getModdedFood() {
        return moddedFood;
    }

    public ItemStack[] getDiamond() {
        return diamond;
    }

    public ItemStack[] getEmerals() {
        return emerals;
    }

    public ItemStack[] getFood() {
        return food;
    }

    public ItemStack[] getGold() {
        return gold;
    }

    public ItemStack[] getIron() {
        return iron;
    }

    public ItemStack getDefaultCurrency() {
        return defaultCurrency;
    }

    public ItemStack setDefaultCurrency(ItemStack defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
        return defaultCurrency;
    }
}