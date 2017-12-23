package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;
import net.thegaminghuskymc.gadgetmod.enums.EnumStorage;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class GadgetOreDictionary {

	public static void init() {
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("laptopGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.LAPTOP, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("printerGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.PRINTER, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("routerGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.ROUTER, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("monitorGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.MONITOR, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("officeChairGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.OFFICE_CHAIR, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("screenGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.SCREEN, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("desktopGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.DESKTOP, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("ethernetWallOutletGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.ETHERNET_WALL_OUTLET, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("gamingDeskGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.GAMING_DESK, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("robotGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.ROBOT, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("serverGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.SERVER, 1, meta)));
		IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("serverTerminalGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
				new ItemStack(GadgetBlocks.SERVER_TERMINAL, 1, meta)));

        IntStream.range(0, EnumDyeColor.values().length).forEach(meta -> registerWithHandlers("flashDriveGadget" + StringUtils.capitalize(EnumDyeColor.values()[meta].getName()),
                new ItemStack(GadgetItems.flash_drive, 1, meta)));
        IntStream.range(0, EnumStorage.values().length).forEach(meta -> registerWithHandlers("ramGadget" + StringUtils.capitalize(EnumStorage.values()[meta].getName()),
                new ItemStack(GadgetItems.ramSticks, 1, meta)));
	}

	private static void registerWithHandlers(String oreName, ItemStack stack) {
		OreDictionary.registerOre(oreName, stack);
		OreDictionary.doesOreNameExist(oreName);
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", stack);
	}

}
