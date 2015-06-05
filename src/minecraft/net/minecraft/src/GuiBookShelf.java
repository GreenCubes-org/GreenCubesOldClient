package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

/**
 * GreenCubes
 * Books mod
 * @author Rena
 *
 */
public class GuiBookShelf extends GuiContainer {

	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows;

	public GuiBookShelf(Minecraft mc, boolean canEdit, InventoryPlayer player, IInventory iinventory1) {
		super(new ContainerBookShelf(mc, canEdit, player, iinventory1));
		inventoryRows = 0;
		upperChestInventory = player;
		lowerChestInventory = iinventory1;
		allowUserInput = false;
		char c = '\336';
		int i = c - 108;
		inventoryRows = iinventory1.getSizeInventory() / 9;
		ySize = i + inventoryRows * 18;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(lowerChestInventory.getInvName(), 8, 6, 0x404040);
		fontRenderer.drawString(upperChestInventory.getInvName(), 8, (ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int k = mc.renderEngine.getTexture("/gui/container.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(l, i1 + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}
}
