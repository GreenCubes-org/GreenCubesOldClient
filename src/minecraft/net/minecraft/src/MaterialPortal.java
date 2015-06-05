// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Material, MapColor

public class MaterialPortal extends Material {

	public MaterialPortal(MapColor mapcolor) {
		super(mapcolor);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean notTransparent() {
		return false;
	}

	@Override
	public boolean getIsSolid() {
		return false;
	}
}
