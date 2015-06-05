package net.minecraft.src;

import java.lang.reflect.Field;

public class TMIPrivateFields {
	public static Field lwjglMouseDWheel;
	public static Field lwjglMouseEventDWheel;

	public TMIPrivateFields() {
	}

	static {
		Field afield[] = (org.lwjgl.input.Mouse.class).getDeclaredFields();
		int i = afield.length;

		for(int j = 0; j < i; j++) {
			Field field = afield[j];

			if(field.getName().equals("event_dwheel")) {
				field.setAccessible(true);
				lwjglMouseEventDWheel = field;
				continue;
			}

			if(field.getName().equals("dwheel")) {
				field.setAccessible(true);
				lwjglMouseDWheel = field;
			}
		}
	}
}
