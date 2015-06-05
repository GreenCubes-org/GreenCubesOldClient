package net.minecraft.src;

public class BlockLantern extends Block {
	
	public int[] top;
	public int[] side;
	public String[] names;

	protected BlockLantern(int i, Material material, int[] top, int[] side, String[] names) {
		super(i, material);
		this.top = top;
		this.side = side;
		this.names = names;
		if(top.length != side.length || top.length != names.length)
			throw new AssertionError("Top, size and names must be same size!");
		setBlockName(names[0]);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j >= top.length)
			j = 0;
		return i == 0 || i == 1 ? top[j] : side[j];
	}
	
	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemBlock(blockID - 256) {
				@Override
				public String getItemNameIS(ItemStack itemstack) {
					int d = itemstack.itemDamage;
					if(d >= names.length)
						d = 0;
					return "tile." + names[d];
				}
				
				@Override
				public int getPlacedBlockMetadata(int i) {
					return i;
				}
			};
		super.init();
	}

}
