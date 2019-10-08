/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.greencubes.util;

/**
 * All supported color values for chat
 */
public enum ChatColor {
	
	/**
	 * Represents black
	 */
	BLACK(0x0),
	/**
	 * Represents dark blue
	 */
	DARK_BLUE(0x1),
	/**
	 * Represents dark green
	 */
	DARK_GREEN(0x2),
	/**
	 * Represents dark blue (aqua)
	 */
	DARK_AQUA(0x3),
	/**
	 * Represents dark red
	 */
	DARK_RED(0x4),
	/**
	 * Represents dark purple
	 */
	DARK_PURPLE(0x5),
	/**
	 * Represents gold
	 */
	GOLD(0x6),
	/**
	 * Represents gray
	 */
	GRAY(0x7),
	/**
	 * Represents dark gray
	 */
	DARK_GRAY(0x8),
	/**
	 * Represents blue
	 */
	BLUE(0x9),
	/**
	 * Represents green
	 */
	GREEN(0xA),
	/**
	 * Represents aqua
	 */
	AQUA(0xB),
	/**
	 * Represents red
	 */
	RED(0xC),
	/**
	 * Represents light purple
	 */
	LIGHT_PURPLE(0xD),
	/**
	 * Represents yellow
	 */
	YELLOW(0xE),
	/**
	 * Represents white
	 */
	WHITE(0xF);
	
	public static final char COLOR_CODE = '\u00A7';
	
	private final int code;
	private final static ChatColor[] colors = new ChatColor[16];
	private final static int[] codeToRGB = new int[32];
	private final String stringValue;
	
	private ChatColor(int code) {
		this.code = code;
		stringValue = String.format("\u00A7%x", code);
	}
	
	/**
	 * Gets the data value associated with this color
	 *
	 * @return An integer value of this color code
	 */
	public int getCode() {
		return code;
	}
	
	public int getRGB() {
		return codeToRGB[code];
	}
	
	public int getRGBShadow() {
		return codeToRGB[code + 16];
	}
	
	@Override
	public String toString() {
		return stringValue;
	}
	
	/**
	 * Gets the color represented by the specified color code
	 *
	 * @param code Code to check
	 * @return Associative {@link Color} with the given code, or null if it doesn't exist
	 */
	public static ChatColor getByCode(final int code) {
		if(code < 0 || code > 15)
			return null;
		return colors[code];
	}
	
	/**
	 * Strips the given message of all color codes
	 *
	 * @param input String to strip of color
	 * @return A copy of the input string, without any coloring
	 */
	public static String stripColor(final String input) {
		if(input == null)
			return null;
		return input.replaceAll("(?i)\u00A7[0-F]", "");
	}
	
	public static StringBuilder disableColor(StringBuilder input) {
		for(int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			if(c == '\u00A7')
				input.setCharAt(i, '&');
		}
		return input;
	}
	
	public static int getRGBColor(String colorString) {
		if(colorString.length() == 9 && colorString.startsWith("r")) {
			String color = colorString.substring(3);
			return Integer.parseInt(color, 16);
		} else if(colorString.length() == 1) {
			ChatColor color = getByCode(Integer.parseInt(colorString, 16));
			if(color != null)
				return color.getRGB();
		}
		throw new NumberFormatException("Color " + colorString + " not identified!");
	}
	
	static {
		for(ChatColor color : ChatColor.values())
			colors[color.getCode()] = color;
		for(int j1 = 0; j1 < 32; j1++) {
			int i2 = (j1 >> 3 & 1) * 85;
			int l2 = (j1 >> 2 & 1) * 170 + i2;
			int j3 = (j1 >> 1 & 1) * 170 + i2;
			int k3 = (j1 >> 0 & 1) * 170 + i2;
			if(j1 == 6)
				l2 += 85;
			boolean flag1 = j1 >= 16;
			if(flag1) {
				l2 /= 8;
				j3 /= 8;
				k3 /= 8;
			}
			codeToRGB[j1] = (l2 << 16) | (j3 << 8) | k3;
		}
	}
}