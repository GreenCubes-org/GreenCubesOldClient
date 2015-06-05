// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            TileEntity, NBTTagCompound

public class TileEntitySign extends TileEntity {

	public String signText[] = {"", "", "", ""};
	public int lineBeingEdited;
	private boolean isEditable;
	public int textRenderList = -1;
	boolean compiled = false;

	public TileEntitySign() {
		lineBeingEdited = -1;
		isEditable = true;
	}

	@Override
	public void onInventoryChanged() {
		if(textRenderList != -1)
			GL11.glDeleteLists(textRenderList, 1);
		compiled = false;
		super.onInventoryChanged();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if(textRenderList != -1)
			GL11.glDeleteLists(textRenderList, 1);
		compiled = false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("Text1", signText[0]);
		nbttagcompound.setString("Text2", signText[1]);
		nbttagcompound.setString("Text3", signText[2]);
		nbttagcompound.setString("Text4", signText[3]);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		isEditable = false;
		super.readFromNBT(nbttagcompound);
		for(int i = 0; i < 4; i++) {
			signText[i] = nbttagcompound.getString((new StringBuilder()).append("Text").append(i + 1).toString());
			if(signText[i].length() > 15) {
				signText[i] = signText[i].substring(0, 15);
			}
		}

	}

	@Override
	protected void finalize() {
		if(textRenderList != -1)
			GL11.glDeleteLists(textRenderList, 1);
	}
}
