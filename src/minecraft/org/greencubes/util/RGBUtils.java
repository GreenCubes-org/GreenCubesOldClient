package org.greencubes.util;

public class RGBUtils {

	public static int toRGB(int r, int g, int b) {
		return (r << 16) | (g << 8) | b;
	}
	
	public static String toHexString(int rgb) {
		return String.format("%06x", rgb & 0xFFFFFF);
	}
	
	public static int[] toIntArray(int rgb) {
		int[] ret = new int[3];
		ret[2] = rgb & 0xFF;
		ret[1] = (rgb >> 8) & 0xFF;
		ret[0] = (rgb >> 16) & 0xFF;
		return ret;
	}
}
