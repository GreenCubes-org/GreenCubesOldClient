// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiContainer, ContainerBrewingStand, FontRenderer, RenderEngine, 
//            TileEntityBrewingStand, InventoryPlayer

public class GuiBrewingStand extends GuiContainer {

	private TileEntityBrewingStand field_40217_h;

	public GuiBrewingStand(InventoryPlayer inventoryplayer, TileEntityBrewingStand tileentitybrewingstand) {
		super(new ContainerBrewingStand(inventoryplayer, tileentitybrewingstand));
		field_40217_h = tileentitybrewingstand;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// GreenCubes start
		fontRenderer.drawString("�������� ������", 56, 6, 0x404040);
		fontRenderer.drawString("���������", 8, (ySize - 96) + 2, 0x404040);
		// GreenCubes end
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int k = mc.renderEngine.getTexture("/gui/alchemy.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int j1 = field_40217_h.func_40053_g();
		if(j1 > 0) {
			int k1 = (int) (28F * (1.0F - j1 / 600F));
			if(k1 > 0) {
				drawTexturedModalRect(l + 97, i1 + 16, 176, 0, 9, k1);
			}
			int l1 = (j1 / 2) % 7;
			switch(l1) {
			case 6: // '\006'
				k1 = 0;
				break;

			case 5: // '\005'
				k1 = 6;
				break;

			case 4: // '\004'
				k1 = 11;
				break;

			case 3: // '\003'
				k1 = 16;
				break;

			case 2: // '\002'
				k1 = 20;
				break;

			case 1: // '\001'
				k1 = 24;
				break;

			case 0: // '\0'
				k1 = 29;
				break;
			}
			if(k1 > 0) {
				drawTexturedModalRect(l + 65, (i1 + 14 + 29) - k1, 185, 29 - k1, 12, k1);
			}
		}
	}
}
