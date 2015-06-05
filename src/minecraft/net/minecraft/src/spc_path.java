// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            SPCPlugin, PlayerHelper, EntityPlayerSP, MathHelper, 
//            World

public class spc_path extends SPCPlugin {

	public int block;
	public int size;
	private int prevx;
	private int prevy;
	private int prevz;

	public spc_path() {
		block = -1;
		size = 3;
		prevx = -1;
		prevy = -1;
		prevz = -1;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getName() {
		return "Path";
	}

	@Override
	public boolean handleCommand(String as[]) {
		if(as == null || !as[0].equalsIgnoreCase("path")) {
			return false;
		}
		if(as.length > 1) {
			int i = -1;
			int j = -1;
			try {
				i = Integer.parseInt(as[1]);
			} catch (NumberFormatException numberformatexception) {
				i = -1;
			}
			if(as.length > 2) {
				try {
					j = Integer.parseInt(as[2]);
				} catch (NumberFormatException numberformatexception1) {
					j = -1;
				}
			}
			if(j > 0) {
				size = j;
			}
			if(i > -1) {
				block = i;
				ph.sendMessage("Path mode enabled.");
			}
		} else if(block > -1) {
			block = -1;
			ph.sendMessage("Path mode disabled.");
		}
		return true;
	}

	@Override
	public void atUpdate() {
		if(block < 0) {
			return;
		}
		int i = MathHelper.floor_double(ph.ep.posX);
		int j = MathHelper.floor_double(ph.ep.posY) - 1;
		int k = MathHelper.floor_double(ph.ep.posZ);
		if(i == prevx && j == prevy && k == prevz) {
			return;
		}
		int l = size * -1 + 1;
		for(int i1 = l; i1 < size; i1++) {
			for(int j1 = -1; j1 < size; j1++) {
				for(int k1 = l; k1 < size; k1++) {
					if(j1 == -1) {
						setBlock(i + i1, j + j1, k + k1, block);
					} else {
						setBlock(i + i1, j + j1, k + k1, 0);
					}
				}

			}

		}

		prevx = i;
		prevy = j;
		prevz = k;
	}

	private void setBlock(int i, int j, int k, int l) {
		ph.mc.theWorld.setBlockWithNotify(i, j, k, l);
	}
}
