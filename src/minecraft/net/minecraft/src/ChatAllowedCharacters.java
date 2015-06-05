// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAllowedCharacters {

	public static final String allowedCharacters = getAllowedCharacters();
	public static final char allowedCharactersArray[] = {'/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};

	public ChatAllowedCharacters() {
	}

	private static String getAllowedCharacters() {
		String s = "";
		try {
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.ChatAllowedCharacters.class).getResourceAsStream("/font.txt"), "UTF-8"));
			String s1 = "";
			do {
				String s2;
				if((s2 = bufferedreader.readLine()) == null) {
					break;
				}
				if(!s2.startsWith("#")) {
					s = (new StringBuilder()).append(s).append(s2).toString();
				}
			} while(true);
			bufferedreader.close();
		} catch (Exception exception) {
		}
		return s;
	}

	public static final boolean isAllowedCharacter(char par0) {
		return par0 != 167 && (allowedCharacters.indexOf(par0) >= 0 || par0 > 32);
	}

	/**
	 * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
	 */
	public static String filerAllowedCharacters(String par0Str) {
		StringBuilder var1 = new StringBuilder();
		char[] var2 = par0Str.toCharArray();
		int var3 = var2.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			char var5 = var2[var4];

			if(isAllowedCharacter(var5)) {
				var1.append(var5);
			}
		}

		return var1.toString();
	}

}
