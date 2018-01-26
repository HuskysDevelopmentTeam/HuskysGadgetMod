package net.thegaminghuskymc.gadgetmod.util;

import net.minecraft.entity.Entity;

public final class MathHelper {
	public static float pointDistanceSpace(double x1, double y1, double z1, double x2, double y2, double z2) {
		return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
	}

	public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.subtract(entityVector);

		if(finalVector.mag() > 1)
			finalVector = finalVector.normalize();

		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

	private static final String[] ORDINAL_SUFFIXES = new String[]{ "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	public static String numberToOrdinal(int i) {
		return i % 100 == 11 || i % 100 == 12 || i % 100 == 13 ? i + "th" : i + ORDINAL_SUFFIXES[i % 10];
	}

}
