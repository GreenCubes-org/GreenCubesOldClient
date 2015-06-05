// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            WorldClient

class WorldBlockPositionType {

	public int posX;
	public int posY;
	public int posZ;
	public int acceptCountdown;
	public int blockID;
	public int metadata;
	public int newId;
	public int newData;
	final WorldClient worldClient; /* synthetic field */

	public WorldBlockPositionType(WorldClient worldclient, int i, int j, int k, int l, int i1, int newId, int newData) {
		worldClient = worldclient;
		posX = i;
		posY = j;
		posZ = k;
		acceptCountdown = 80;
		blockID = l;
		metadata = i1;
	}
}
