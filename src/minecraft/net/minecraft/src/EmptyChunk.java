// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Chunk, World, EnumSkyBlock, Entity, 
//            TileEntity, AxisAlignedBB

public class EmptyChunk extends Chunk {

	public EmptyChunk(World world, int i, int j) {
		super(world, i, j);
		neverSave = true;
	}

	public EmptyChunk(World world, byte abyte0[], int i, int j) {
		super(world, abyte0, i, j);
		neverSave = true;
	}

	@Override
	public boolean isAtLocation(int i, int j) {
		return i == xPosition && j == zPosition;
	}

	@Override
	public int getHeightValue(int i, int j) {
		return 0;
	}

	@Override
	public void func_1014_a() {
	}

	@Override
	public void generateHeightMap() {
	}

	@Override
	public void generateSkylightMap() {
	}

	@Override
	public void func_4143_d() {
	}

	@Override
	public int getBlockID(int i, int j, int k) {
		return 0;
	}

	@Override
	public boolean setBlockIDWithMetadata(int i, int j, int k, int l, int i1) {
		return true;
	}

	@Override
	public boolean setBlockID(int i, int j, int k, int l) {
		return true;
	}

	@Override
	public int getBlockMetadata(int i, int j, int k) {
		return 0;
	}

	@Override
	public boolean setBlockMetadata(int i, int j, int k, int l) {
		return false;
	}

	@Override
	public int getSavedLightValue(EnumSkyBlock enumskyblock, int i, int j, int k) {
		return 0;
	}

	@Override
	public void setLightValue(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
	}

	@Override
	public int getBlockLightValue(int i, int j, int k, int l) {
		return 0;
	}

	@Override
	public void addEntity(Entity entity) {
	}

	@Override
	public void removeEntity(Entity entity) {
	}

	@Override
	public void removeEntityAtIndex(Entity entity, int i) {
	}

	@Override
	public boolean canBlockSeeTheSky(int i, int j, int k) {
		return false;
	}

	@Override
	public TileEntity getChunkBlockTileEntity(int i, int j, int k) {
		return null;
	}

	@Override
	public void addTileEntity(TileEntity tileentity) {
	}

	@Override
	public void setChunkBlockTileEntity(int i, int j, int k, TileEntity tileentity) {
	}

	@Override
	public void removeChunkBlockTileEntity(int i, int j, int k) {
	}

	@Override
	public void onChunkLoad() {
	}

	@Override
	public void onChunkUnload() {
	}

	@Override
	public void setChunkModified() {
	}

	@Override
	public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB axisalignedbb, List list) {
	}

	@Override
	public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisalignedbb, List list) {
	}

	@Override
	public boolean needsSaving(boolean flag) {
		return false;
	}

	@Override
	public int setChunkData(byte abyte0[], int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = l - i;
		int i2 = i1 - j;
		int j2 = j1 - k;
		int k2 = l1 * i2 * j2;
		return k2 + (k2 / 2) * 3;
	}

	@Override
	public Random getRandomWithSeed(long l) {
		return new Random(worldObj.getWorldSeed() + (xPosition * xPosition * 0x4c1906) + (xPosition * 0x5ac0db) + (zPosition * zPosition) * 0x4307a7L + (zPosition * 0x5f24f) ^ l);
	}

	@Override
	public boolean isEmptyChunk() {
		return true;
	}
}
