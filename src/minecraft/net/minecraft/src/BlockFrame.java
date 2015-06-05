package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class BlockFrame extends BlockContainer {
	
	protected static final int[] textures = {frame_face_0, frame_face_1, frame_face_2, frame_face_3, frame_face_4, frame_face_5, frame_face_6, frame_face_7, frame_face_8, frame_face_9,
		frame_face_10, frame_face_11, frame_face_12, frame_face_13, frame_face_14, frame_face_15, frame_face_16, frame_face_17, frame_face_18, frame_face_19, frame_face_20, frame_face_21,
		frame_face_22, frame_face_23, frame_face_24, frame_face_25, frame_face_26, frame_face_27, frame_face_28, frame_face_29, frame_face_30, frame_face_31};
	
	protected float depth = 0.5f / 16f;
	protected float width = 15f / 16f;
	protected float widthMin = (1 - width) * 0.5f;
	protected float widthMax = (1 + width) * 0.5f;
	protected int textureShift;
	protected int overlayTexture;
	public boolean renderingOverlay = false;
	
	public BlockFrame(int i, Material material, int textureShift, int overlayTexture) {
		super(i, material);
		this.textureShift = textureShift;
		this.overlayTexture = overlayTexture;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return renderingOverlay ? overlayTexture : textures[((j >> 3) + textureShift) % textures.length];
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}
	
	public int getOverlayTexture() {
		return overlayTexture;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(renderingOverlay) {
			BlockFace niceFace = BlockFace.getFaceById(l);
			BlockFace face = BlockOnWall.getAttachedSide(iblockaccess.getBlockMetadata(i - niceFace.getModX(), j - niceFace.getModY(), k - niceFace.getModZ()));
			if(face == niceFace)
				return true;
			return false;
		}
		return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		BlockFace face = BlockOnWall.getAttachedSide(iblockaccess.getBlockMetadata(i, j, k));
		switch(face) {
		case NORTH:
			setBlockBounds(renderingOverlay ? 1 - depth - 1f/64f : 1 - depth, widthMin, widthMin, renderingOverlay ? 1 - depth - 1f/64f : 1, widthMax, widthMax);
			break;
		case SOUTH:
			setBlockBounds(renderingOverlay ? depth + 1f / 64f : 0, widthMin, widthMin, renderingOverlay ? depth + 1f / 64f : depth, widthMax, widthMax);
			break;
		case EAST:
			setBlockBounds(widthMin, widthMin, renderingOverlay ? 1 - depth - 1f / 64f : 1 - depth, widthMax, widthMax, renderingOverlay ? 1 - depth - 1f / 64f : 1);
			break;
		case WEST:
		default:
			setBlockBounds(widthMin, widthMin, renderingOverlay ? depth + 1f / 64f : 0, widthMax, widthMax, renderingOverlay ? depth + 1f / 64f : depth);
			break;
		}
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityFrame();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 43;
	}
	
	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return true;
	}
}
