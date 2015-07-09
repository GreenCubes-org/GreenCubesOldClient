// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.iterator.TIntObjectIterator;

import net.minecraft.client.Minecraft;

import org.greencubes.gui.FancyGUI;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiContainer, EntityPlayer, AchievementList, PlayerController, 
//            GuiContainerCreative, EntityPlayerSP, FontRenderer, RenderEngine, 
//            RenderHelper, RenderManager, GuiButton, GuiAchievements, 
//            GuiStats, PotionEffect, Potion, StatCollector

public class GuiInventory extends GuiContainer {

	private float xSize_lo;
	private float ySize_lo;

	public GuiInventory(EntityPlayer entityplayer) {
		super(entityplayer.inventorySlots);
		allowUserInput = true;
		//entityplayer.addStat(AchievementList.openInventory, 1);
	}

	@Override
	public void updateScreen() {
		if(mc.playerController.isInCreativeMode()) {
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		}
	}

	@Override
	public void initGui() {
		controlList.clear();
		if(mc.playerController.isInCreativeMode()) {
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		} else {
			super.initGui();
			if(!mc.thePlayer.func_40118_aO().isEmpty()) {
				guiLeft = 160 + (width - xSize - 200) / 2;
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if(inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 2).getHasStack()) {
			int k = GreenTextures.trashcan;
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
			drawTexturedModalRectItem(133, 62, (k % 16) * 16, (k / 16) * 16, 16, 16);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		fontRenderer.drawString("Крафт", 95, 14, 0x404040);
		displayDebuffEffects(mouseX, mouseY);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int k = mc.renderEngine.getTexture("/gui/inventory.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = guiLeft;
		int i1 = guiTop;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glPushMatrix();
		GL11.glTranslatef(l + 67, i1 + 75, 50F);
		float f1 = 30F;
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f2 = mc.thePlayer.renderYawOffset;
		float f3 = mc.thePlayer.rotationYaw;
		float f4 = mc.thePlayer.rotationPitch;
		float f5 = (l + 51) - xSize_lo;
		float f6 = ((i1 + 75) - 50) - ySize_lo;
		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float) Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
		mc.thePlayer.renderYawOffset = (float) Math.atan(f5 / 40F) * 20F;
		mc.thePlayer.rotationYaw = (float) Math.atan(f5 / 40F) * 40F;
		mc.thePlayer.rotationPitch = -(float) Math.atan(f6 / 40F) * 20F;
		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		mc.thePlayer.renderYawOffset = f2;
		mc.thePlayer.rotationYaw = f3;
		mc.thePlayer.rotationPitch = f4;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 0) {
			mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
		}
		if(guibutton.id == 1) {
			mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
		}
	}

	private void displayDebuffEffects(int mouseX, int mouseY) {
		int i = - 19;
		int j = + 2;
		if(mc.thePlayer.activeBuffs.size() > 0) {
			int n = 0;
			boolean renderTooltip = false;
			TIntObjectIterator<BuffActive> iterator = mc.thePlayer.activeBuffs.iterator();
			while(iterator.hasNext()) {
				iterator.advance();
				BuffActive buff = iterator.value();
				if(buff != null && buff.buff != null && buff.buff.getTextureFramed() != null) {
					Minecraft.theMinecraft.ingameGUI.renderBuff(buff, i, j + n * 19);
					if(mouseX > i && mouseX < i + 19 && mouseY > j + n * 19 && mouseY < j + n * 19 + 19) {
						renderTooltip = true;
					}
					n++;
				}
			}
			
			if(renderTooltip) {
				n = 0;
				iterator = mc.thePlayer.activeBuffs.iterator();
				while(iterator.hasNext()) {
					iterator.advance();
					BuffActive buff = iterator.value();
					if(buff != null && buff.buff != null && buff.buff.getTextureFramed() != null) {
						if(mouseX > i && mouseX < i + 19 && mouseY > j + n * 19 && mouseY < j + n * 19 + 19) {
							FancyGUI.getInstance().renderScaledTooltip(mouseX, mouseY, buff.buff.getBuffDescription(buff).split("\n"), width - guiLeft, height - guiTop, 4);
						}
						n++;
					}
				}
			}
			GL11.glColor4f(1, 1, 1, 1);
		}

	}
}
