package net.minecraft.src;

import org.greencubes.util.HSVColor;

public class BlockColoredPane extends BlockPane {
	
	public final int[][] colors;
	private int currentPass;
	private int lastRenderDamage;

	public BlockColoredPane(int id, int texture, int sideTexture, int cutTexture, Material material, boolean flag, int[][] colors) {
		super(id, texture, sideTexture, cutTexture, material, flag);
		this.colors = colors;
		for(int i = 0; i < colors.length; ++i) {
			HSVColor hsv = HSVColor.fromRGB(colors[i][1]);
			hsv = new HSVColor(hsv.h, Math.min(1.0f, hsv.s / 1.75f), Math.min(1.0f, hsv.v * 1.75f));
			colors[i][1] = hsv.getRGB();
		}
		multipassBlocks[id] = true;
	}

	@Override
	public boolean setUpPass(int pass) {
		this.currentPass = pass;
		return pass == 0 || pass == 2;
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		super.setBlockBoundsForItemRender();
		currentPass = 0;
	}
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int i) {
		if(currentPass == 2) {
			lastRenderDamage = iblockaccess.getBlockMetadata(x, y, z);
			return colors[lastRenderDamage % colors.length][0];
		}
		return super.getBlockTexture(iblockaccess, x, y, z, i);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(currentPass == 2) {
			lastRenderDamage = j;
			return colors[lastRenderDamage % colors.length][0];
		}
		return super.getBlockTextureFromSideAndMetadata(i, j);
	}
	
	@Override
	protected int damageDropped(int i) {
		return i;
	}
	
	@Override
	public int getRenderColor(int i) {
		return currentPass == 2 ? 0xffffff : colors[i % colors.length][1];
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return currentPass == 2 ? 0xffffff : getRenderColor(iblockaccess.getBlockMetadata(i, j, k));
	}
	
	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemBlockColored(blockID - 256) {	
				@Override
				public int getColorFromDamage(int i) {
					return colors[i % colors.length][1];
				}
				
				@Override
				public int getIconFromDamage(int i) {
					setBlockBoundsForItemRender();
					return blockIndexInTexture;
				}
			};
		super.init();
	}
}
