// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;

// Referenced classes of package net.minecraft.src:
//            SPCWorldInterface, World, SPCPoint, WorldInfo, 
//            SaveHandler, ISaveHandler

public class SPCWorld implements SPCWorldInterface {

	private World world;
	private WorldInfo wi;

	public SPCWorld(World world1) {
		world = world1;
		wi = world1.worldInfo;
	}

	@Override
	public int getBlock(SPCPoint spcpoint, int i) {
		return world.getBlockId(spcpoint.ix, spcpoint.iy, spcpoint.iz);
	}

	@Override
	public int getMetadata(SPCPoint spcpoint, int i) {
		return world.getBlockMetadata(spcpoint.ix, spcpoint.iy, spcpoint.iz);
	}

	@Override
	public SPCPoint getSpawn() {
		return new SPCPoint(wi.getSpawnX(), wi.getSpawnY(), wi.getSpawnZ());
	}

	@Override
	public long getTime() {
		return wi.getWorldTime();
	}

	@Override
	public File getWorldDir() {
		ISaveHandler isavehandler = world.saveHandler;
		if(isavehandler instanceof SaveHandler) {
			return ((SaveHandler) isavehandler).getSaveDirectory();
		} else {
			return new File("");
		}
	}

	@Override
	public void setBlock(SPCPoint spcpoint, int i) {
		world.setBlock(spcpoint.ix, spcpoint.iy, spcpoint.iz, i);
	}

	@Override
	public void setBlockWithNotify(SPCPoint spcpoint, int i) {
		world.setBlockWithNotify(spcpoint.ix, spcpoint.iy, spcpoint.iz, i);
	}

	@Override
	public void setMetadata(SPCPoint spcpoint, int i) {
		world.setBlockMetadata(spcpoint.ix, spcpoint.iy, spcpoint.iz, i);
	}

	@Override
	public void setMetadataWithNotify(SPCPoint spcpoint, int i) {
		world.setBlockMetadataWithNotify(spcpoint.ix, spcpoint.iy, spcpoint.iz, i);
	}

	@Override
	public void setSpawn(SPCPoint spcpoint) {
		wi.setSpawn(spcpoint.ix, spcpoint.iy, spcpoint.iz);
	}

	@Override
	public void setTime(long l) {
		wi.setWorldTime(l);
	}
}
