// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            SPCPlugin, PlayerHelper, Block, SPCObjectHit, 
//            EntityPlayerSP, MovingObjectPosition, World

public class spc_paint extends SPCPlugin {

	public int paintID;
	public int paintDamage;
	public boolean paint;
	private static int cooldown;
	public PlayerHelper ph;

	public spc_paint() {
		paintID = -1;
		paintDamage = 0;
		paint = false;
		cooldown = 0;
	}

	@Override
	public void setPlayerHelper(PlayerHelper playerhelper) {
		ph = playerhelper;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getName() {
		return "Paint v1.1";
	}

	@Override
	public boolean handleCommand(String as[]) {
		String as1[] = as;
		if(as1[0].equalsIgnoreCase("trunksbomb")) {
			ph.sendMessage("trunksbomb is the greatest.");
			return true;
		}
		if(as1[0].equalsIgnoreCase("paint")) {
			System.out.println("Trying to paint..");
			if(paint && as1.length == 1) {
				paint = false;
				paintID = -1;
				paintDamage = 0;
				ph.sendMessage("No longer painting.");
				return true;
			}
			if(as1.length > 1) {
				paintID = ph.getBlockID(as1[1]);
				System.out.println((new StringBuilder()).append("Painting ").append(paintID).toString());
				if(paintID >= 0) {
					paint = true;
					if(as1.length > 2) {
						try {
							paintDamage = Integer.parseInt(as1[2]);
							if(paintDamage < 0 || paintDamage > 15) {
								ph.sendMessage("Damage value must be 0-15");
								paintDamage = 0;
							}
						} catch (Exception exception) {
							ph.sendError("Not a valid damage value.");
						}
					} else {
						paintDamage = 0;
					}
					ph.sendMessage((new StringBuilder()).append("Now painting ").append(paintID != 0 ? Block.blocksList[paintID].getBlockName().substring(Block.blocksList[paintID].getBlockName().indexOf('.') + 1) : "air").append(paintDamage != 0 ? (new StringBuilder()).append(" with damage ").append(paintDamage).append(".").toString() : ".").append(" Left-click to replace clicked block, right click to").append(" place new block.").toString());
				} else {
					ph.sendError("Invalid block name or ID");
				}
				return true;
			} else {
				ph.sendError("Not enough parameters for paint command.");
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public void handleLeftClick(SPCObjectHit spcobjecthit) {
		if(paint) {
			paintBlocks(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz);
		}
	}

	@Override
	public void handleRightClick(SPCObjectHit spcobjecthit) {
		if(paint) {
			paintBlocksRightClick(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz);
		}
	}

	@Override
	public void handleLeftButtonDown(SPCObjectHit spcobjecthit) {
		if(paint) {
			paintBlocks(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz);
		}
	}

	@Override
	public void handleRightButtonDown(SPCObjectHit spcobjecthit) {
		if(paint) {
			paintBlocksRightClick(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz);
		}
	}

	@Override
	public void atUpdate() {
		if(cooldown > 0) {
			cooldown--;
		}
	}

	public void paintBlocksRightClick(int i, int j, int k) {
		if(cooldown > 0) {
			return;
		}
		cooldown = 3;
		MovingObjectPosition movingobjectposition = ph.ep.rayTrace(1024D, 1.0F);
		int l = movingobjectposition.sideHit;
		if(l == 0) {
			j--;
		}
		if(l == 1) {
			j++;
		}
		if(l == 2) {
			k--;
		}
		if(l == 3) {
			k++;
		}
		if(l == 4) {
			i--;
		}
		if(l == 5) {
			i++;
		}
		paintBlocks(i, j, k);
	}

	public void paintBlocks(int i, int j, int k) {
		if(ph.ep.worldObj.getBlockId(i, j, k) != Block.bedrock.blockID) {
			ph.ep.worldObj.setBlockWithNotify(i, j, k, paintID);
		}
		ph.ep.worldObj.setBlockAndMetadataWithNotify(i, j, k, paintID, paintDamage);
	}
}
