package net.minecraft.src;

import static net.minecraft.src.GreenTextures.glassHalfTexture;
import static net.minecraft.src.GreenTextures.glassStepTexture;

import org.greencubes.util.HSVColor;

public class BlockColoredHalfBlockGlass extends BlockHalfBlockGlass {
	
	public final int[][] colors;
	private int currentPass;
	private boolean renderingItem = false;

	public BlockColoredHalfBlockGlass(int id, Block original, int[][] colors) {
		super(id, glassStepTexture, glassHalfTexture, original);
		this.colors = colors;
		for(int i = 0; i < colors.length; ++i) {
			HSVColor hsv = HSVColor.fromRGB(colors[i][1]);
			hsv = new HSVColor(hsv.h, Math.min(1.0f, hsv.s / 1.75f), Math.min(1.0f, hsv.v * 1.75f));
			colors[i][1] = hsv.getRGB();
		}
		multipassBlocks[id] = true;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return super.getBlockTextureFromSideAndMetadata(i, this.renderingItem ? 0 : j);
	}
	
	@Override
	public boolean setUpPass(int pass) {
		this.currentPass = pass;
		this.renderingItem = false;
		return pass == 0 || pass == 2;
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		super.setBlockBoundsForItemRender();
		this.currentPass = 0;
		this.renderingItem = true;
	}
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int i) {
		if(currentPass == 2)
			return colors[(iblockaccess.getBlockMetadata(x, y, z) >> 2) % colors.length][0];
		return super.getBlockTexture(iblockaccess, x, y, z, i);
	}
	
	@Override
	public int getRenderColor(int i) {
		return currentPass == 2 ? 0xffffff : colors[i % colors.length][1];
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return currentPass == 2 ? 0xffffff : getRenderColor(iblockaccess.getBlockMetadata(i, j, k) >> 2);
	}
	
	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemHalfBlock(blockID - 256) {
				@Override
				public int getPlacedBlockMetadata(int i) {
					return i;
				}
				
				@Override
				public String getItemNameIS(ItemStack itemstack) {
					return (new StringBuilder()).append(super.getItemName()).append(".").append(ItemDye.dyeColorNames[BlockWool.getBlockFromDye(itemstack.getItemDamage())]).toString();
				}
			};
		super.init();
	}

}
