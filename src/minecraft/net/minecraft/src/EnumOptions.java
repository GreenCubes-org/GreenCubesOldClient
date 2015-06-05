// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

public enum EnumOptions {
	MUSIC("options.music", true, false), SOUND("options.sound", true, false), INVERT_MOUSE("options.invertMouse", false, true), SENSITIVITY("options.sensitivity", true, false), FOV("options.fov", true, false), GAMMA("options.gamma", true, false), RENDER_DISTANCE("options.renderDistance", false, false), VIEW_BOBBING("options.viewBobbing", false, true), ANAGLYPH("options.anaglyph", false, true), ADVANCED_OPENGL("options.advancedOpengl", false, true), FRAMERATE_LIMIT("options.framerateLimit", false, false), DIFFICULTY("options.difficulty", false, false), GRAPHICS("options.graphics", false, false), AMBIENT_OCCLUSION("options.ao", false, true), GUI_SCALE("options.guiScale", false, false), RENDER_CLOUDS("options.renderClouds", false, true), PARTICLES("options.particles", false, false), USE_FULLSCREEN("options.fullscreen", false, true), ENABLE_VSYNC("options.vsync", false, true), OCC("options.occ", true, false), REPLAY("options.replay", false, true), ;

	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	private static final EnumOptions allOptions[]; /* synthetic field */

	public static EnumOptions getEnumOptions(int i) {
		EnumOptions aenumoptions[] = values();
		int j = aenumoptions.length;
		for(int k = 0; k < j; k++) {
			EnumOptions enumoptions = aenumoptions[k];
			if(enumoptions.returnEnumOrdinal() == i) {
				return enumoptions;
			}
		}

		return null;
	}

	private EnumOptions(String s1, boolean flag, boolean flag1) {
		enumString = s1;
		enumFloat = flag;
		enumBoolean = flag1;
	}

	public boolean getEnumFloat() {
		return enumFloat;
	}

	public boolean getEnumBoolean() {
		return enumBoolean;
	}

	public int returnEnumOrdinal() {
		return ordinal();
	}

	public String getEnumString() {
		return enumString;
	}

	static {
		allOptions = (new EnumOptions[]{MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES});
	}
}
