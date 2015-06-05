// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, BlockLeaves, 
//            EntityPlayer

public class BlockLog extends Block {
	public static int jungleSide = ModLoader.addOverride("/terrain.png", "/gc_images/junglelogside.png");
	public static int jungleTop = ModLoader.addOverride("/terrain.png", "/gc_images/junglelogtop.png");
	public static int redwoodTop = ModLoader.addOverride("/terrain.png", "/gc_images/redwoodlogtop.png");
	public static int birchTop = ModLoader.addOverride("/terrain.png", "/gc_images/birchlogtop.png");

	protected BlockLog(int i) {
		super(i, Material.wood);
		blockIndexInTexture = 20;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public int getRenderType() {
		return 31;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.wood.blockID;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		byte byte0 = 4;
		int l = byte0 + 1;
		if(world.checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l)) {
			for(int i1 = -byte0; i1 <= byte0; i1++) {
				for(int j1 = -byte0; j1 <= byte0; j1++) {
					for(int k1 = -byte0; k1 <= byte0; k1++) {
						int l1 = world.getBlockId(i + i1, j + j1, k + k1);
						if(l1 != Block.leaves.blockID) {
							continue;
						}
						int i2 = world.getBlockMetadata(i + i1, j + j1, k + k1);
						if((i2 & 8) == 0) {
							world.setBlockMetadata(i + i1, j + j1, k + k1, i2 | 8);
						}
					}

				}

			}

		}
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		if(world.multiplayerWorld)
			return;
		int n = world.getBlockMetadata(i, j, k) & 0x3;
		int l = BlockPistonBase.determineOrientation(world, i, j, k, (EntityPlayer) entityliving);
		int d = 0;

		switch(l) {
		case 2:
		case 3:
			d = 8;
			break;
		case 4:
		case 5:
			d = 4;
			break;
		case 0:
		case 1:
			d = 0;
		}
		world.setBlockMetadataWithNotify(i, j, k, n | d);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int data) {
		int rot = data & 0xC;
		int type = data & 0x3;

		if((rot == 0) && ((side == 1) || (side == 0))) {
			if(type == 0)
				return 21;
			if(type == 1)
				return redwoodTop;
			if(type == 2)
				return birchTop;
			if(type == 3)
				return jungleTop;
			return 21;
		}

		if((rot == 4) && ((side == 5) || (side == 4))) {
			if(type == 0)
				return 21;
			if(type == 1)
				return redwoodTop;
			if(type == 2)
				return birchTop;
			if(type == 3)
				return jungleTop;
			return 21;
		}
		if((rot == 8) && ((side == 2) || (side == 3))) {
			if(type == 0)
				return 21;
			if(type == 1)
				return redwoodTop;
			if(type == 2)
				return birchTop;
			if(type == 3)
				return jungleTop;
			return 21;
		}

		if(type == 1)
			return 116;
		if(type == 2)
			return 117;
		if(type == 3)
			return jungleSide;

		return 20;
	}

	@Override
	protected int damageDropped(int i) {
		return i & 0x3;
	}
}
