package net.minecraft.src;

import java.lang.reflect.*;
import net.minecraft.client.Minecraft;

public class TMICompatibility {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private static boolean prevZanEnabled = false;

	public TMICompatibility() {
	}

	public static void disableCompetingMods() {
		prevZanEnabled = setZanMinimapEnabled(false);
	}

	public static void restoreCompetingMods() {
		setZanMinimapEnabled(prevZanEnabled);
	}

	public static boolean setZanMinimapEnabled(boolean flag) {
		try {
			boolean flag1 = true;
			Class class1 = Class.forName("mod_ZanMinimap");
			Field field = class1.getField("instance");
			Object obj = field.get(null);
			Field afield[] = class1.getDeclaredFields();
			Field afield1[] = afield;
			int i = afield1.length;

			for(int j = 0; j < i; j++) {
				Field field1 = afield1[j];

				if(field1.getName().equals("hide")) {
					field1.setAccessible(true);
					flag1 = !field1.getBoolean(obj);
					field1.setBoolean(obj, !flag);
				}
			}

			return flag1;
		} catch (ClassNotFoundException classnotfoundexception) {
		} catch (IllegalAccessException illegalaccessexception) {
			System.out.println("IllegalAccessException in setZanMinimapEnabled.");
		} catch (NoSuchFieldException nosuchfieldexception) {
			System.out.println("NoSuchFieldException in setZanMinimapEnabled.");
		}

		return true;
	}

	public static boolean callConvenientInventoryHandler(int i, int j, boolean flag, Minecraft minecraft, Container container) {
		try {
			Class class1 = Class.forName("ConvenientInventory");
			Class aclass[] = {Integer.TYPE, Integer.TYPE, Boolean.TYPE, net.minecraft.client.Minecraft.class, net.minecraft.src.Container.class};
			Method method = class1.getMethod("mod_convenientInventory_handleClickOnSlot", aclass);
			Object aobj[] = {Integer.valueOf(i), Integer.valueOf(j), Boolean.valueOf(flag), minecraft, container};
			method.invoke(null, aobj);
			return true;
		} catch (ClassNotFoundException classnotfoundexception) {
			return false;
		} catch (NoSuchMethodException nosuchmethodexception) {
			return false;
		} catch (InvocationTargetException invocationtargetexception) {
			System.out.println((new StringBuilder()).append("callConvenientInventoryHandler: ").append(invocationtargetexception.getCause()).toString());
			return false;
		} catch (IllegalAccessException illegalaccessexception) {
			System.out.println((new StringBuilder()).append("callConvenientInventoryHandler: ").append(illegalaccessexception).toString());
		}

		return false;
	}
}
