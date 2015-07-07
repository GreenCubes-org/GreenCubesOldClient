package org.greencubes.util;

import net.minecraft.src.StringTranslate;

public class I18n {
	
	public static String get(String key) {
		return StringTranslate.getInstance().translateKey(key);
	}
	
	public static String get(String key, Object... format) {
		return StringTranslate.getInstance().translateKeyFormat(key, format);
	}
	
	public static String getMaxTimeString(long ms) {
		double s = ms / 1000D;
		if(s <= 60)
			return ((long) Math.ceil(s)) + "ñ";
		s /= 60D;
		if(s <= 60)
			return ((long) Math.ceil(s)) + "ì";
		return ((long) Math.ceil(s / 60)) + "÷";
	}
	
	public static String getTimeString(long ms) {
		double s = ms / 1000D;
		StringBuffer sb = new StringBuffer();
		if(s > 3600) {
			int hours = (int) (s / 3600D);
			s -= hours * 3600;
			sb.append(hours).append(" ÷.");
		}
		if(s > 60) {
			int minutes = (int) (s / 60D);
			s -= minutes * 60;
			if(sb.length() > 0)
				sb.append(' ');
			sb.append(minutes).append(" ì.");
		}
		if(s > 0) {
			int seconds = (int) s;
			s -= seconds;
			if(sb.length() > 0)
				sb.append(' ');
			sb.append(seconds).append(" ñ.");
		}
		if(sb.length() == 0 && s >= 0.1d)
			sb.append(String.format("%.1f", s)).append(" ñ.");
		if(sb.length() == 0)
			return "0 ñ.";
		return sb.toString();
	}
}
