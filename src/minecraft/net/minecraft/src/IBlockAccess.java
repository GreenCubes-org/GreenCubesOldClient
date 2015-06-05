package net.minecraft.src;

/**
 * Interface to something that holds blocks
 * @author Notch
 *
 */
public interface IBlockAccess {

	public abstract int getBlockId(int x, int y, int z);

	public abstract TileEntity getBlockTileEntity(int x, int y, int z);

	public abstract int getLightBrightnessForSkyBlocks(int x, int y, int z, int l);

	public abstract float getBrightness(int x, int y, int z, int l);

	public abstract float getLightBrightness(int x, int y, int z);

	public abstract int getBlockMetadata(int x, int y, int z);

	public abstract Material getBlockMaterial(int x, int y, int z);

	public abstract boolean isBlockOpaqueCube(int x, int y, int z);

	public abstract boolean isBlockNormalCube(int x, int y, int z);

	public abstract boolean isAirBlock(int x, int y, int z);

	public abstract WorldChunkManager getWorldChunkManager();

	public abstract int func_35452_b();

	public BiomeGenBase getBiomeAt(int x, int y, int z);
}
