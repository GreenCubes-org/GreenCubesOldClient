// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            MovingObjectPosition, SPCEntity

public class SPCObjectHit {

	public int blockx;
	public int blocky;
	public int blockz;
	public int sidehit;
	public SPCEntity entity;
	public MovingObjectPosition m;

	public SPCObjectHit(MovingObjectPosition movingobjectposition) {
		m = movingobjectposition;
		if(movingobjectposition == null) {
			blockx = -1;
			blocky = -1;
			blockz = -1;
			entity = null;
			sidehit = -1;
			return;
		}
		blockx = movingobjectposition.blockX;
		blocky = movingobjectposition.blockY;
		blockz = movingobjectposition.blockZ;
		sidehit = movingobjectposition.sideHit;
		if(movingobjectposition.entityHit != null) {
			entity = new SPCEntity(movingobjectposition.entityHit);
		}
	}

	public MovingObjectPosition getMOP() {
		return m;
	}
}
