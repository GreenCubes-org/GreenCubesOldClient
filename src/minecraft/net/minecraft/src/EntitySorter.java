// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Comparator;

// Referenced classes of package net.minecraft.src:
//            Entity, WorldRenderer

public class EntitySorter implements Comparator<WorldRenderer> {

	private double entityPosX;
	private double entityPosY;
	private double entityPosZ;

	public EntitySorter(Entity entity) {
		entityPosX = -entity.posX;
		entityPosY = -entity.posY;
		entityPosZ = -entity.posZ;
	}

	public int sortByDistanceToEntity(WorldRenderer worldrenderer, WorldRenderer worldrenderer1) {
		double d = worldrenderer.posXPlus + entityPosX;
		double d1 = worldrenderer.posYPlus + entityPosY;
		double d2 = worldrenderer.posZPlus + entityPosZ;
		double d3 = worldrenderer1.posXPlus + entityPosX;
		double d4 = worldrenderer1.posYPlus + entityPosY;
		double d5 = worldrenderer1.posZPlus + entityPosZ;
		return (int) (((d * d + d1 * d1 + d2 * d2) - (d3 * d3 + d4 * d4 + d5 * d5)) * 1024D);
	}

	@Override
	public int compare(WorldRenderer obj, WorldRenderer obj1) {
		return sortByDistanceToEntity(obj, obj1);
	}
}
