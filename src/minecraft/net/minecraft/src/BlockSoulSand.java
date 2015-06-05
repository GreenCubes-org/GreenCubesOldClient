// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material, AxisAlignedBB, Entity, 
//            World

public class BlockSoulSand extends Block {

	public BlockSoulSand(int i, int j) {
		super(i, j, Material.sand);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		entity.motionX *= 0.40000000000000002D;
		entity.motionZ *= 0.40000000000000002D;
	}
}
