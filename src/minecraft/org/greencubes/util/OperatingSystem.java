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

import java.io.File;

public enum OperatingSystem {
	
	//@formatter:off
	LINUX("linux", new String[]{"linux", "unix"}),
	WINDOWS("windows", new String[]{"win"}),
	OSX("osx", new String[]{"mac"}),
	UNKNOWN("unknown", new String[0]);
	//@fomatter: on
	
	private final String[] aliases;
	public final String name;

	private OperatingSystem(String name, String[] aliases) {
		this.aliases = aliases;
		this.name = name;
	}

	public static String getJavaExecutable(boolean forceConsole) {
		String separator = System.getProperty("file.separator");
		String path = System.getProperty("java.home") + separator + "bin" + separator;
		if(getCurrentPlatform() == WINDOWS) {
			if(!forceConsole && new File(path + "javaw.exe").isFile())
				return path + "javaw.exe";
			return path + "java.exe";
		}
		return path + "java";
	}

	public static OperatingSystem getCurrentPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		for(OperatingSystem os : values()) {
			for(String alias : os.aliases) {
				if(osName.contains(alias))
					return os;
			}
		}
		return UNKNOWN;
	}
}