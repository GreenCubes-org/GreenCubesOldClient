package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class BlockMysteryChest extends BlockNormalChest {

	public BlockMysteryChest(int i, String texture) {
		super(i, texture);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		EntityMysteryChestFXGC fx = new EntityMysteryChestFXGC(world, i, j, k);
		Minecraft.theMinecraft.effectRenderer.addEffect(fx);
		fx = new EntityMysteryChestFXGC(world, i, j, k);
		Minecraft.theMinecraft.effectRenderer.addEffect(fx);
		fx = new EntityMysteryChestFXGC(world, i, j, k);
		Minecraft.theMinecraft.effectRenderer.addEffect(fx);
	}

}
