package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class CUSettings {

	// NBT tag keys
	private static final String 	BAR_COLOR_NAME = "barColor",
									BACKGROUND_NAME = "background";
	
	// Some helping constants
	private static final int 		MIN_BAR_ALPHA = 0x7F,
									MAX_BAR_ALPHA = 0xF0;
	
	// Default values (defined to save space when writing to NBT)
	private static final int		DEFAULT_BAR_COLOR = 0xBFFF0000; 
	
	// CUSettings object dependent (tl;dr: non-static) stuff here
	private int barColor = DEFAULT_BAR_COLOR;
	
	public static CUSettings readFromNBT(NBTTagCompound nbt) {
		CUSettings settings = new CUSettings();
		if(nbt.hasKey(BAR_COLOR_NAME, Constants.NBT.TAG_INT)) {
			int color = nbt.getInteger(BAR_COLOR_NAME);
			// Alpha check (thanks to bitwise operators ^^)
			if(color >= MIN_BAR_ALPHA << 24 && color < (MAX_BAR_ALPHA)+1 << 24) {
				settings.barColor = color;
			}
		}
		return settings;
	}
	
	public NBTTagCompound writeToNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(this.barColor != DEFAULT_BAR_COLOR) {
			nbt.setInteger(BAR_COLOR_NAME, this.barColor);
		}
		return nbt;
	}
	
	int getBarColor() {
		return this.barColor;
	}
	
}