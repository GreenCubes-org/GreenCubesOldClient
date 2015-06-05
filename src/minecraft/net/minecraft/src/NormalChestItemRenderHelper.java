// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            TileEntityChest, TileEntityRenderer, Block

public class NormalChestItemRenderHelper {

	public static NormalChestItemRenderHelper instance = new NormalChestItemRenderHelper();
	private TileEntityNormalChest field_35610_b;

	public NormalChestItemRenderHelper() {
		field_35610_b = new TileEntityNormalChest();
	}

	public void renderInInventory(Block block, int i, float f) {
		field_35610_b.field_35146_o = block;
		TileEntityRenderer.instance.renderTileEntityAt(field_35610_b, 0.0D, 0.0D, 0.0D, 0.0F);
		field_35610_b.field_35146_o = null;
	}

}
