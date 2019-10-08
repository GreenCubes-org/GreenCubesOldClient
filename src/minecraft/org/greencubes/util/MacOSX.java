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

import java.awt.Image;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.List;

public class MacOSX {
	
	public static void setIcons(List<? extends Image> icons) {
		try {
			Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
			Method m = applicationClass.getDeclaredMethod("getApplication");
			Object application = m.invoke(null);
			m = application.getClass().getDeclaredMethod("setDockIconImage", Image.class);
			m.invoke(application, icons.get(icons.size() - 1));
		} catch(Exception e) {
		}
	}
	
	public static void setTitle(String title) {
		try {
			Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
			Method m = applicationClass.getDeclaredMethod("getApplication");
			Object application = m.invoke(null);
			m = application.getClass().getDeclaredMethod("setDockIconImage", String.class);
			m.invoke(application, title);
		} catch(Exception e) {
		}
	}
	
	public static boolean hasRetinaDisplay() {
		Object obj = Toolkit.getDefaultToolkit().getDesktopProperty("apple.awt.contentScaleFactor");
		if(obj instanceof Number) {
			Number f = (Number) obj;
			int scale = f.intValue();
			return scale == 2; // 1 indicates a regular mac display.
		}
		return false;
	}
	
}
