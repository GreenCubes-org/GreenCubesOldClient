// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            EntityRenderer, ModLoader

public class EntityRendererProxy extends EntityRenderer {

	private Minecraft game;

	public EntityRendererProxy(Minecraft minecraft) {
		super(minecraft);
		game = minecraft;
	}

	@Override
	public void updateCameraAndRender(float f) {
		super.updateCameraAndRender(f);
		ModLoader.OnTick(f, game);
	}
}
