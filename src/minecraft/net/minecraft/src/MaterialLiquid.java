// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Material, MapColor

public class MaterialLiquid extends Material {

	public MaterialLiquid(MapColor mapcolor) {
		super(mapcolor);
		setReplaceable();
		setNoPushMobility();
	}

	@Override
	public boolean getIsLiquid() {
		return true;
	}

	@Override
	public boolean getIsSolid() {
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}
}
