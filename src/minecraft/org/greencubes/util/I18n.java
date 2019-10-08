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
			return ((long) Math.ceil(s)) + "�";
		s /= 60D;
		if(s <= 60)
			return ((long) Math.ceil(s)) + "�";
		return ((long) Math.ceil(s / 60)) + "�";
	}
	
	public static String getTimeString(long ms) {
		double s = ms / 1000D;
		StringBuffer sb = new StringBuffer();
		if(s > 3600) {
			int hours = (int) (s / 3600D);
			s -= hours * 3600;
			sb.append(hours).append(" �.");
		}
		if(s > 60) {
			int minutes = (int) (s / 60D);
			s -= minutes * 60;
			if(sb.length() > 0)
				sb.append(' ');
			sb.append(minutes).append(" �.");
		}
		if(s > 0) {
			int seconds = (int) s;
			s -= seconds;
			if(sb.length() > 0)
				sb.append(' ');
			sb.append(seconds).append(" �.");
		}
		if(sb.length() == 0 && s >= 0.1d)
			sb.append(String.format("%.1f", s)).append(" �.");
		if(sb.length() == 0)
			return "0 �.";
		return sb.toString();
	}
}
