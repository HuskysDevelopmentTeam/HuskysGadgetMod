package net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.thegaminghuskymc.gadgetmod.util.RenderHelper;

import java.util.ArrayList;

public class AppData {
	
	private static void registerGames(AppData product) {
		if (!foodList.contains(product)) foodList.add(product);
	}

	private static void registerToolApps(AppData product) {
		if (!armorList.contains(product)) armorList.add(product);
	}
	
	private static void registerEducationApps(AppData product) {
		if (!toolList.contains(product)) toolList.add(product);
	}

    private static void registerEntertainmentApps(AppData product) {
        if (!foodList.contains(product)) foodList.add(product);
    }

    private static void registerSportApps(AppData product) {
        if (!armorList.contains(product)) armorList.add(product);
    }

    private static void registerVRApps(AppData product) {
        if (!toolList.contains(product)) toolList.add(product);
    }

    private static void registerFinanceApps(AppData product) {
        if (!foodList.contains(product)) foodList.add(product);
    }

    private static void registerMultiplayerApps(AppData product) {
        if (!armorList.contains(product)) armorList.add(product);
    }

    private static void registerShoppingApps(AppData product) {
        if (!toolList.contains(product)) toolList.add(product);
    }

    private static void registerSocialApps(AppData product) {
        if (!toolList.contains(product)) toolList.add(product);
    }
	
//	-----------------------------------------------------------------------
	
	// FOOD
	public static ArrayList<AppData> foodList = new ArrayList();
	
	public static void registerFoodProducts() {

	}
	
//	-----------------------------------------------------------------------
	
	public static ArrayList<AppData> armorList = new ArrayList();

	public static void registerArmorProducts() {

	}
//	-----------------------------------------------------------------------
	
	// TOOLS
	public static ArrayList<AppData> toolList = new ArrayList();
	
	public static void registerToolProducts() {

	}
	
//	-----------------------------------------------------------------------

	private int price;
	private Item product;
	private String name;
	private ItemStack stackProduct;
	private int qty;
	private int meta;
	private boolean isStack;
	
	private AppData(int price, Item product, String name) {
		this(price, product, 1, 0, name, false);
	}
	
	private AppData(int price, Item product, int qty, int meta, String name, boolean isStack) {
		this.price = price;
		this.stackProduct = new ItemStack(product, qty, meta);
		this.name = name;
		this.isStack = isStack;
	}
	
	public AppData setQty(int qty) {
		this.qty = qty;
		return this;
	}
	
	public String getUnlocalizedName() {
		return RenderHelper.unlocaliseName("appStore." + name);
	}
	
	public int getPrice() {
		return price;
	}

	public Item getProduct() {
		return product;
	}
	
	public ItemStack getStackProduct() {
		return stackProduct;
	}
	
	public int getQty() {
		return qty;
	}
	
	public int getMeta() {
		return meta;
	}
}