// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, EntitySnowman, 
//            EntityLiving, MathHelper

public class BlockPumpkin extends Block {

	private boolean blockType;

	protected BlockPumpkin(int i, int j, boolean flag) {
		super(i, Material.pumpkin);
		blockIndexInTexture = j;
		setTickOnLoad(true);
		blockType = flag;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1) {
			return blockIndexInTexture;
		}
		if(i == 0) {
			return blockIndexInTexture;
		}
		int k = blockIndexInTexture + 1 + 16;
		if(blockType) {
			k++;
		}
		if(j == 2 && i == 2) {
			return k;
		}
		if(j == 3 && i == 5) {
			return k;
		}
		if(j == 0 && i == 3) {
			return k;
		}
		if(j == 1 && i == 4) {
			return k;
		} else {
			return blockIndexInTexture + 16;
		}
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1) {
			return blockIndexInTexture;
		}
		if(i == 0) {
			return blockIndexInTexture;
		}
		if(i == 3) {
			return blockIndexInTexture + 1 + 16;
		} else {
			return blockIndexInTexture + 16;
		}
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		if(world.getBlockId(i, j - 1, k) == Block.blockSnow.blockID && world.getBlockId(i, j - 2, k) == Block.blockSnow.blockID) {
			if(!world.multiplayerWorld) {
				world.setBlockWithNotify(i, j, k, 0);
				world.setBlockWithNotify(i, j - 1, k, 0);
				world.setBlockWithNotify(i, j - 2, k, 0);
				EntitySnowman entitysnowman = new EntitySnowman(world);
				entitysnowman.setLocationAndAngles(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
				world.entityJoinedWorld(entitysnowman);
			}
			for(int l = 0; l < 120; l++) {
				world.spawnParticle("snowshovel", i + world.rand.nextDouble(), (j - 2) + world.rand.nextDouble() * 2.5D, k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
			}

		}
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l);
	}
}
