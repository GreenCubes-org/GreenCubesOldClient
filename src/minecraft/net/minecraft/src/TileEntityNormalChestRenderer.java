// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            TileEntitySpecialRenderer, ModelChest, ModelLargeChest, TileEntityChest, 
//            BlockChest, ModelRenderer, TileEntity

public class TileEntityNormalChestRenderer extends TileEntitySpecialRenderer {

	private ModelChest field_35377_b;

	public TileEntityNormalChestRenderer() {
		field_35377_b = new ModelChest();
	}

	public void func_35376_a(TileEntityNormalChest tileentitychest, double d, double d1, double d2, float f) {
		int i;
		if(tileentitychest.worldObj == null) {
			i = 0;
		} else {
			i = tileentitychest.getBlockMetadata();
		}
		ModelChest modelchest = field_35377_b;
		try {
			bindTextureByName(tileentitychest.getTexture());
		} catch(NullPointerException e) {
			System.err.println("Lost NormalChest while rendering");
		}
		GL11.glPushMatrix();
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) d, (float) d1 + 1.0F, (float) d2 + 1.0F);
		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int j = 0;
		if(i == 2) {
			j = 180;
		}
		if(i == 3) {
			j = 0;
		}
		if(i == 4) {
			j = 90;
		}
		if(i == 5) {
			j = -90;
		}
		GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		modelchest.func_35402_a();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		func_35376_a((TileEntityNormalChest) tileentity, d, d1, d2, f);
	}
}
