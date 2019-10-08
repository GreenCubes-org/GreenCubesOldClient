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
package org.greencubes.items.unique;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.src.GCUtil;

import org.greencubes.util.HSVColor;
import org.greencubes.util.RGBUtils;
import gnu.trove.map.TFloatObjectMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.TFloatObjectHashMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum NamedColor {
	
	PINK(1, "Pink", "Розовый", 350, 25),
	CARMINE_RED(2, "Carmine red", "Карминовый", 347, 100),
	LILO(3, "lilo", "Лиловый", 344, 50),
	DEEP_PINK(4, "Deep pink", "Ярко-розовый", 328, 92),
	LIGHT_VIOLET(5, "light-violet", "Светло-пурпурный", 300, 50),
	MAGENTA(6, "Magenta", "Насыщенный пурпурный", 300, 100),
	LAVENDER(7, "Lavender", "Лавандовый", 290, 27),
	VIOLET(8, "Violet", "Насыщенный фиолетовый", 273, 100),
	LIGHT_BLUE(9, "light-blue", "Светло-синий", 240, 50),
	BLUE(10, "Blue", "Насыщенный синий", 240, 100),
	ROYAL_BLUE(11, "royal-blue", "Королевский синий", 230, 65),
	CORNFLOWER_BLUE(12, "Cornflower blue", "Васильковый", 219, 58),
	AZURE(13, "Azure", "Лазурный", 210, 100),
	CELESTE(14, "Celeste", "Небесный", 206, 50),
	LIGHT_LIGHT_BLUE(15, "Light blue", "Голубой", 195, 100),
	DIAMOND(16, "Diamond", "Алмазный", 190, 100),
	LIGHT_CYAN(17, "light-cyan", "Светло-бирюзовый", 180, 50),
	CYAN(18, "cyan", "Насыщенный бирюзовый", 180, 100),
	AQUA(19, "Aqua", "Цвет морской волны", 172, 90),
	AQUAMARINE(20, "Aquamarine", "Аквамариновый", 160, 50),
	MINT_GREEN(21, "Mint Green", "Мятный", 120, 40),
	LIGHT_GREEN(22, "light-green", "Светло-зеленый", 120, 50),
	GREEN(23, "Green", "Насыщенный зеленый", 120, 100),
	CHARTREUSE(24, "Chartreuse", "Салатовый ", 90, 100),
	GREEN_YELLOW(25, "Green-yellow", "Жёлто-зелёный", 84, 82),
	PISTACHIO(26, "pistachio", "Фисташковый", 82, 40),
	LIME(27, "Lime", "Цвет лайма", 72, 100),
	LIGHT_YELLOW(28, "light-yellow", "Светло-желтый", 60, 50),
	YELLOW(29, "Yellow", "Насыщенный жёлтый", 60, 100),
	CREAM(30, "Cream", "Кремовый", 57, 18),
	LEMON(31, "Lemon", "Лимонный", 55, 95),
	CORN(32, "Corn", "Кукурузный", 54, 62),
	BANANA_YELLOW(33, "Banana yellow", "Банановый", 51, 80),
	GOLD(34, "Gold", "Золотой", 51, 100),
	VANILLA(35, "Vanilla", "Ванильный", 48, 30),
	MUSTARD(36, "Mustard", "Горчичный", 47, 65),
	AMBER(37, "Amber", "Янтарный", 45, 100),
	PEACH(38, "Peach", "Персиковый", 39, 30),
	LIGHT_ORANGE(39, "Light-Orange", "Светло-оранжевый", 39, 100),
	MANDARIN(40, "Mandarin", "Мандариновый", 32, 100),
	PEACH_ORANGE(41, "Peach-orange", "Оранжево-персиковый", 30, 40),
	DARK_PEACH(42, "Dark Peach", "Тускло-персиковый", 28, 27),
	STRANGE(43, "Strange orange", "Странный рыжий", 21, 75),
	PUMPKIN(44, "Pumpkin", "Тыквенный", 24, 90),
	PINK_ORANGE(45, "Pink-orange", "Оранжево-розовый", 20, 60),
	CORAL(46, "Coral", "Коралловый", 16, 70),
	SALMON(47, "Salmon", "Лососёвый", 14, 60),
	TOMATO(48, "Tomato", "Томатный", 9, 72),
	SCARLET(49, "Scarlet", "Алый", 8, 100),
	BITTERSWEET(50, "Bittersweet", "Грейпфрутовый", 6, 62),
	WHITE(51, "White", "Белый", 0, 0),
	LIGHT_RED(52, "light-red", "Светло-красный", 0, 50),
	RED(53, "Red", "Насыщенный красный", 0, 100),
	CAT_ORANGE(54, "Cat orange", "Кошкин рыжий", 33, 66),
	PRESIAN_BLIE(55, "Persian blue", "Персидский синий", 264, 100),
	SPRING_BLIE(56, "Spring green", "Зеленая весна", 150, 100),
	BRIGHT_GREEN(57, "Bright-green", "Ярко-зеленый", 96, 100),
	AERO_GREEN(58, "Aero green", "Воздушный зеленый", 151, 20),
	GREENCUBES(59, "GreenCubes green", "Зеленый GreenCubes", 120, 70),
	NEOVENEZIAN_BLUE(60, "Neo-Venezian blue", "Нео-Венецианский синий", 225, 97),
	ORANGE(61, "Orange", "Насыщенный оранжевый", 30, 100),
	SNOWY(62, "Snowy", "Снежный", 233, 12, 7),
	SUNNY(63, "Sunny", "Солнечный", 55, 32, 7),
	FIERY(64, "Fiery", "Огненный", 42, 90, 7),
	;

	private static final TIntObjectMap<NamedColor> namedColors = new TIntObjectHashMap<NamedColor>();
	private static final TIntObjectMap<NamedColor> byId = new TIntObjectHashMap<NamedColor>();
	private static final TMap<String, NamedColor> byName = new THashMap<String, NamedColor>();
	public static NamedColor[] orderedColors;
	private static final TIntObjectMap<TFloatObjectMap<NamedColor>> colorsByHS = new TIntObjectHashMap<TFloatObjectMap<NamedColor>>();
	
	public final int id;
	public final String name;
	public final String translatedName;
	public final float h;
	public final int s;
	public final int rgb;
	
	private NamedColor(int id, String name, String translatedName, int h, int s) {
		this(id, name, translatedName, h, s, 6);
	}
	
	private NamedColor(int id, String name, String translatedName, int h, int s, int level) {
		this.name = name;
		this.id = id;
		this.translatedName = translatedName;
		this.h = GCUtil.roundFloat(h, Glowing.complexity[level]);
		this.s = s;
		this.rgb = new HSVColor(this.h / 360F, s / 100F, 1.0F).getRGB();
		int[] arr = RGBUtils.toIntArray(this.rgb);
		//System.out.println(translatedName + ": H: " + this.h + ", S: " + s + ", RGB: " + RGBUtils.toHexString(this.rgb) + "(" + arr[0] + ", " + arr[1] + ", " + arr[2] + ")");
	}
	
	public String getColoredTranslatedName() {
		StringBuilder sb = new StringBuilder();
		sb.append("\247rff");
		sb.append(RGBUtils.toHexString(rgb));
		sb.append(translatedName);
		sb.append(" (");
		int[] arr = RGBUtils.toIntArray(rgb);
		sb.append(arr[0]).append(", ").append(arr[1]).append(", ").append(arr[2]).append(")");
		return sb.toString();
	}
	
	public static NamedColor getByRGB(int rgb) {
		return namedColors.get(rgb);
	}
	
	public static NamedColor getById(int id) {
		return byId.get(id);
	}
	
	public static NamedColor getByHS(float h, int s) {
		if(s == 0)
			return WHITE;
		TFloatObjectMap<NamedColor> byH = colorsByHS.get(s);
		if(byH == null)
			return null;
		return byH.get(h);
	}
	
	public static NamedColor getByNameOrId(String str) {
		try {
			return byId.get(Integer.parseInt(str));
		} catch(NumberFormatException e) {
			return byName.get(str);
		}
	}
	
	static {
		orderedColors = new NamedColor[values().length];
		System.arraycopy(values(), 0, orderedColors, 0, orderedColors.length);
		for(NamedColor color : values()) {
			byName.put(color.name.toLowerCase(), color);
			byId.put(color.id, color);
			namedColors.put(color.rgb, color);
			TFloatObjectMap<NamedColor> byH = colorsByHS.get(color.s);
			if(byH == null) {
				byH = new TFloatObjectHashMap<NamedColor>();
				colorsByHS.put(color.s, byH);
			}
			byH.put(color.h, color);
		}
		Arrays.sort(orderedColors, new Comparator<NamedColor>() {
			@Override
			public int compare(NamedColor arg0, NamedColor arg1) {
				int i = Float.compare(arg0.h, arg1.h);
				if(i == 0)
					return (arg0.s < arg1.s) ? -1 : ((arg0.s == arg1.s) ? 0 : 1);//Integer.compare(arg0.s, arg1.s);
				return i;
			}
			
		});
	}
}
