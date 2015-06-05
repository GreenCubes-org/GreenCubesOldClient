// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, BiomeDecorator, IBlockAccess, WorldChunkManager, 
//            ColorizerGrass, ColorizerFoliage, WorldGenerator

public class BiomeGenSwamp extends BiomeGenBase {

	protected BiomeGenSwamp(int i) {
		super(i);
		biomeDecorator.treesPerChunk = 2;
		biomeDecorator.flowersPerChunk = -999;
		biomeDecorator.deadBushPerChunk = 1;
		biomeDecorator.mushroomsPerChunk = 8;
		biomeDecorator.reedsPerChunk = 10;
		biomeDecorator.clayPerChunk = 1;
		biomeDecorator.waterlilyPerChunk = 4;
		field_40256_A = 0xe0ff70;
	}

	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random random) {
		return worldGenSwamp;
	}

	@Override
	public int getBiomeGrassColor() {
		double d = temperature < 0.0f ? 0.0f : temperature > 1.0f ? 1.0f : temperature;
		double d1 = rainfall < 0.0f ? 0.0f : rainfall > 1.0f ? 1.0f : rainfall;
		return ((ColorizerGrass.getGrassColor(d, d1) & 0xfefefe) + 0x4e0e4e) / 2;
	}

	@Override
	public int getBiomeFoliageColor() {
		double d = temperature < 0.0f ? 0.0f : temperature > 1.0f ? 1.0f : temperature;
		double d1 = rainfall < 0.0f ? 0.0f : rainfall > 1.0f ? 1.0f : rainfall;
		return ((ColorizerFoliage.getFoliageColor(d, d1) & 0xfefefe) + 0x4e0e4e) / 2;
	}
}
