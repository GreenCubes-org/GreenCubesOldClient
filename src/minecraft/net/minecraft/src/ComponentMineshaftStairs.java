// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            StructureComponent, StructureBoundingBox, StructureMineshaftPieces, World

public class ComponentMineshaftStairs extends StructureComponent {

	public ComponentMineshaftStairs(int i, Random random, StructureBoundingBox structureboundingbox, int j) {
		super(i);
		coordBaseMode = j;
		boundingBox = structureboundingbox;
	}

	public static StructureBoundingBox func_35027_a(List list, Random random, int i, int j, int k, int l) {
		StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j - 5, k, i, j + 2, k);
		switch(l) {
		case 2: // '\002'
			structureboundingbox.maxX = i + 2;
			structureboundingbox.minZ = k - 8;
			break;

		case 0: // '\0'
			structureboundingbox.maxX = i + 2;
			structureboundingbox.maxZ = k + 8;
			break;

		case 1: // '\001'
			structureboundingbox.minX = i - 8;
			structureboundingbox.maxZ = k + 2;
			break;

		case 3: // '\003'
			structureboundingbox.maxX = i + 8;
			structureboundingbox.maxZ = k + 2;
			break;
		}
		if(StructureComponent.getIntersectingStructureComponent(list, structureboundingbox) != null) {
			return null;
		} else {
			return structureboundingbox;
		}
	}

	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random random) {
		int i = func_35012_c();
		switch(coordBaseMode) {
		case 2: // '\002'
			StructureMineshaftPieces.getNextComponent(structurecomponent, list, random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, i);
			break;

		case 0: // '\0'
			StructureMineshaftPieces.getNextComponent(structurecomponent, list, random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
			break;

		case 1: // '\001'
			StructureMineshaftPieces.getNextComponent(structurecomponent, list, random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, i);
			break;

		case 3: // '\003'
			StructureMineshaftPieces.getNextComponent(structurecomponent, list, random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, i);
			break;
		}
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox) {
		if(isLiquidInStructureBoundingBox(world, structureboundingbox)) {
			return false;
		}
		fillWithBlocks(world, structureboundingbox, 0, 5, 0, 2, 7, 1, 0, 0, false);
		fillWithBlocks(world, structureboundingbox, 0, 0, 7, 2, 2, 8, 0, 0, false);
		for(int i = 0; i < 5; i++) {
			fillWithBlocks(world, structureboundingbox, 0, 5 - i - (i >= 4 ? 0 : 1), 2 + i, 2, 7 - i, 2 + i, 0, 0, false);
		}

		return true;
	}
}
