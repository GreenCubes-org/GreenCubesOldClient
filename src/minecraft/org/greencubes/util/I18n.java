package org.greencubes.util;

import net.minecraft.src.StringTranslate;

public class I18n {
	
	public static String get(String key) {
		return StringTranslate.getInstance().translateKey(key);
	}
	
	public static String get(String key, Object... format) {
		return StringTranslate.getInstance().translateKeyFormat(key, format);
	}
	
}
