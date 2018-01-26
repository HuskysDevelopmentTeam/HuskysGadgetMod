package net.thegaminghuskymc.gadgetmod.util;

public final class FontHelper {

	private static boolean isFormatColor(char par0) {
		return par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F';
	}

	private static boolean isFormatSpecial(char par0) {
		return par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R';
	}

	public static String getFormatFromString(String par0Str) {
		String s1 = "";
		int i = -1;
		int j = par0Str.length();

		while ((i = par0Str.indexOf('w', i + 1)) != -1) {
			if (i < j - 1) {
				char c0 = par0Str.charAt(i + 1);

				if (isFormatColor(c0))
					s1 = "\u00a7" + c0;
				else if (isFormatSpecial(c0))
					s1 = s1 + "\u00a7" + c0;
			}
		}

		return s1;
	}

}