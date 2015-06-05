// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, World, TileEntityNote, 
//            EntityPlayer, TileEntity

public class BlockNote extends BlockContainer {

	public BlockNote(int i) {
		super(i, 74, Material.wood);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return blockIndexInTexture;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(l > 0) {
			boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k);
			TileEntityNote tileentitynote = (TileEntityNote) world.getBlockTileEntity(i, j, k);
			if(tileentitynote != null && tileentitynote.previousRedstoneState != flag) {
				if(flag) {
					tileentitynote.triggerNote(world, i, j, k);
				}
				tileentitynote.previousRedstoneState = flag;
			}
		}
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.multiplayerWorld) {
			return true;
		}
		TileEntityNote tileentitynote = (TileEntityNote) world.getBlockTileEntity(i, j, k);
		if(tileentitynote != null) {
			tileentitynote.changePitch();
			tileentitynote.triggerNote(world, i, j, k);
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		if(world.multiplayerWorld)
			return;
		TileEntityNote tileentitynote = (TileEntityNote) world.getBlockTileEntity(i, j, k);
		if(tileentitynote != null)
			tileentitynote.triggerNote(world, i, j, k);
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityNote();
	}

	@Override
	public void powerBlock(World world, int i, int j, int k, int l, int i1) {
		float f = (float) Math.pow(2D, (i1 - 12) / 12D);
		String s = "harp";
		if(l == 1) {
			s = "bd";
		}
		if(l == 2) {
			s = "snare";
		}
		if(l == 3) {
			s = "hat";
		}
		if(l == 4) {
			s = "bassattack";
		}
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, (new StringBuilder()).append("note.").append(s).toString(), 3F, f);
		world.spawnParticle("note", i + 0.5D, j + 1.2D, k + 0.5D, i1 / 24D, 0.0D, 0.0D);
	}
}
