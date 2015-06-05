package net.minecraft.src;

public class TMIBlockSpawner extends BlockMobSpawner {
	protected TMIBlockSpawner(int i, int j) {
		super(i, j);
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	@Override
	public TileEntity getBlockEntity() {
		TileEntityMobSpawner tileentitymobspawner = new TileEntityMobSpawner();
		tileentitymobspawner.setMobID((String) TMIConfig.getInstance().getSettings().get("spawner"));
		return tileentitymobspawner;
	}
}
