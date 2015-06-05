package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiOtherPlayerInventory extends GuiContainer {

	private final EntityOtherPlayerMP player;

	public GuiOtherPlayerInventory(EntityOtherPlayerMP el) {
		super(new ContainerEntityLiving(el));
		this.player = el;
		allowUserInput = true;
		xSize = 123;
		ySize = 46;
		//highlightItems = false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int k = mc.renderEngine.getTexture("/gc_images/gui/player_inv.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = guiLeft;
		int i1 = guiTop;
		drawRect(l, i1, xSize, ySize);
	}

	public void drawRect(int i, int j, int i1, int j1) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + j1, zLevel, 0, 1);
		tessellator.addVertexWithUV(i + i1, j + j1, zLevel, 1, 1);
		tessellator.addVertexWithUV(i + i1, j + 0, zLevel, 1, 0);
		tessellator.addVertexWithUV(i + 0, j + 0, zLevel, 0, 0);
		tessellator.draw();
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		mc.thePlayer.closeScreen();
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.renderString("Предметы на игроке", 5, 2, 0, false);
		fontRenderer.renderString(player.coloredName, 5, 12, 0, false);
	}

}
