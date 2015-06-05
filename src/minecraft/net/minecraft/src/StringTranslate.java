// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class StringTranslate {

	private static StringTranslate instance = new StringTranslate();
	private Properties translateTable;

	private StringTranslate() {
		translateTable = new Properties();
		BufferedReader bufferedreader = null;
		try {
			bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.StringTranslate.class).getResourceAsStream("/lang/en_US.lang"), "UTF-8"));
			translateTable.load(bufferedreader);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} finally {
			try {
				bufferedreader.close();
			} catch (IOException e) {}
		}
	}
	
	public String translateIfExists(String s) {
		String s1 = translateTable.getProperty(s);
		return s1 != null && s1.trim().length() > 0 ? s1 : null;
	}

	public static StringTranslate getInstance() {
		return instance;
	}

	public String translateKey(String s) {
		return translateTable.getProperty(s, s);
	}

	public String translateKeyFormat(String s, Object aobj[]) {
		String s1 = translateTable.getProperty(s, s);
		return String.format(s1, aobj);
	}

	public String translateNamedKey(String s) {
		return translateTable.getProperty((new StringBuilder()).append(s).append(".name").toString(), "");
	}

}
