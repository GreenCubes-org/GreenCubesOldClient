// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.util.Properties;

public class Settings extends Properties {

	private static final long serialVersionUID = 1L;
	private File settings;

	public Settings() {
	}

	public Settings(File file) {
		this(file, true);
	}

	public Settings(File file, boolean flag) {
		settings = file;
		if(flag) {
			load(file);
		}
	}

	public void set(String s, boolean flag) {
		setProperty(s, (new Boolean(flag)).toString());
	}

	public boolean getBoolean(String s, boolean flag) {
		String s1 = getProperty(s);
		try {
			return s1 != null && !s1.trim().equalsIgnoreCase("") ? (new Boolean(s1)).booleanValue() : flag;
		} catch (Exception exception) {
			return flag;
		}
	}

	public void set(String s, int i) {
		setProperty(s, (new Integer(i)).toString());
	}

	public int getInteger(String s, int i) {
		String s1 = getProperty(s);
		try {
			return isEmpty(s1) ? i : (new Integer(s1)).intValue();
		} catch (NumberFormatException numberformatexception) {
			return i;
		}
	}

	public void set(String s, char c) {
		setProperty(s, (new Character(c)).toString());
	}

	public char getCharacter(String s, char c) {
		String s1 = getProperty(s);
		try {
			return isEmpty(s1) ? c : s1.charAt(0);
		} catch (NumberFormatException numberformatexception) {
			return c;
		}
	}

	public void set(String s, double d) {
		setProperty(s, (new Double(d)).toString());
	}

	public double getDouble(String s, double d) {
		String s1 = getProperty(s);
		try {
			return isEmpty(s1) ? d : (new Double(s1)).doubleValue();
		} catch (NumberFormatException numberformatexception) {
			return d;
		}
	}

	public void set(String s, float f) {
		setProperty(s, (new Float(f)).toString());
	}

	public float getFloat(String s, float f) {
		String s1 = getProperty(s);
		try {
			return isEmpty(s1) ? f : (new Float(s1)).floatValue();
		} catch (NumberFormatException numberformatexception) {
			return f;
		}
	}

	public void set(String s, String s1) {
		setProperty(s, s1);
	}

	public String getString(String s, String s1) {
		String s2 = getProperty(s);
		return isEmpty(s2) ? s1 : s2;
	}

	public boolean save() {
		return save("");
	}

	public boolean save(String s) {
		return save(settings, s);
	}

	public boolean save(File file, String s) {
		if(file == null || file.isDirectory()) {
			return false;
		}
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			super.store(fileoutputstream, s);
			fileoutputstream.close();
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean load() {
		return load(settings);
	}

	public boolean load(File file) {
		if(file == null || file.isDirectory()) {
			return false;
		}
		try {
			if(!file.exists()) {
				file.createNewFile();
				return true;
			} else {
				super.load(new FileInputStream(file));
				return true;
			}
		} catch (Exception exception) {
			return false;
		}
	}

	public File getFile() {
		return settings;
	}

	public void setFile(File file) {
		settings = file;
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().equalsIgnoreCase("");
	}
}
