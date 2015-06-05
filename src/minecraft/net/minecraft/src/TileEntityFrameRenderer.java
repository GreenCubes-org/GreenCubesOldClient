// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

// Referenced classes of package net.minecraft.src:
//            TileEntitySpecialRenderer, ModelChest, ModelLargeChest, TileEntityChest, 
//            BlockChest, ModelRenderer, TileEntity

public class TileEntityFrameRenderer extends TileEntitySpecialRenderer {


	public TileEntityFrameRenderer() {
	}

	public void func_35376_a(TileEntityFrame tileentitychest, double d, double d1, double d2, float f) {
		if(tileentitychest.worldObj == null)
			return;
		ItemStack item = tileentitychest.getStackInSlot(0);
		if(item == null)
			return;
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		int x = tileentitychest.xCoord;
		int y = tileentitychest.yCoord;
		int z = tileentitychest.zCoord;
		BlockFace attachedFace = BlockOnWall.getAttachedSide(tileentitychest.worldObj.getBlockMetadata(x, y, z));
		switch(attachedFace) {
		case NORTH:
			GL11.glTranslatef(- 0.5f / 16f + 1f - 1f / 128f, 1f / 16f, 3f / 16f);
			GL11.glRotatef(90f, 0, 1, 0);
			break;
		case EAST:
			GL11.glTranslatef(- 3f / 16f + 1f, 1f / 16f, - 0.5f / 16f + 1f - 1f / 128f);
			break;
		case SOUTH:
			GL11.glTranslatef(0.5f / 16f + 1f / 128f, 1f / 16f, - 3f / 16f + 1f);
			GL11.glRotatef(-90f, 0, 1, 0);
			break;
		default:
			GL11.glTranslatef(3f / 16f, 1f / 16f, + 0.5f / 16f + 1f / 128f);
			GL11.glRotatef(180f, 0, 1, 0);
			break;
		}
		GL11.glTranslatef(0f, 0.75f, 0f);
		GL11.glScalef(10f / 16f, 10f / 16f, 0.625f);
		GL11.glScalef(1f / 1.5f, 1f / 1.5f, 1f / 1.5f);
		GL11.glScalef(-1.5f / 16f, -1.5f / 16f, 1f);
		RenderItem.getInstance().renderItemInWorld(Minecraft.theMinecraft.renderEngine, item);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		func_35376_a((TileEntityFrame) tileentity, d, d1, d2, f);
	}
}
