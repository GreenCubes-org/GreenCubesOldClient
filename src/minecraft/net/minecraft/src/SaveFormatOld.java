// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            ISaveFormat, SaveFormatComparator, WorldInfo, CompressedStreamTools, 
//            NBTTagCompound, SaveHandler, ISaveHandler, IProgressUpdate

public class SaveFormatOld implements ISaveFormat {

	protected final File savesDirectory;

	public SaveFormatOld(File file) {
		if(!file.exists()) {
			file.mkdirs();
		}
		savesDirectory = file;
	}

	@Override
	public String func_22178_a() {
		return "Old Format";
	}

	@Override
	public List getSaveList() {
		ArrayList arraylist = new ArrayList();
		for(int i = 0; i < 5; i++) {
			String s = (new StringBuilder()).append("World").append(i + 1).toString();
			WorldInfo worldinfo = getWorldInfo(s);
			if(worldinfo != null) {
				arraylist.add(new SaveFormatComparator(s, "", worldinfo.getLastTimePlayed(), worldinfo.getSizeOnDisk(), worldinfo.getGameType(), false, worldinfo.isHardcoreModeEnabled()));
			}
		}

		return arraylist;
	}

	@Override
	public void flushCache() {
	}

	@Override
	public WorldInfo getWorldInfo(String s) {
		File file = new File(savesDirectory, s);
		if(!file.exists()) {
			return null;
		}
		File file1 = new File(file, "level.dat");
		if(file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(file1));
				NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound2);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		file1 = new File(file, "level.dat_old");
		if(file1.exists()) {
			try {
				NBTTagCompound nbttagcompound1 = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(file1));
				NBTTagCompound nbttagcompound3 = nbttagcompound1.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound3);
			} catch (Exception exception1) {
				exception1.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void renameWorld(String s, String s1) {
		File file = new File(savesDirectory, s);
		if(!file.exists()) {
			return;
		}
		File file1 = new File(file, "level.dat");
		if(file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(file1));
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
				nbttagcompound1.setString("LevelName", s1);
				CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound, new FileOutputStream(file1));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void deleteWorldDirectory(String s) {
		File file = new File(savesDirectory, s);
		if(!file.exists()) {
			return;
		} else {
			deleteFiles(file.listFiles());
			file.delete();
			return;
		}
	}

	protected static void deleteFiles(File afile[]) {
		for(int i = 0; i < afile.length; i++) {
			if(afile[i].isDirectory()) {
				System.out.println((new StringBuilder()).append("Deleting ").append(afile[i]).toString());
				deleteFiles(afile[i].listFiles());
			}
			afile[i].delete();
		}

	}

	@Override
	public ISaveHandler getSaveLoader(String s, boolean flag) {
		return new SaveHandler(savesDirectory, s, flag);
	}

	@Override
	public boolean isOldMapFormat(String s) {
		return false;
	}

	@Override
	public boolean convertMapFormat(String s, IProgressUpdate iprogressupdate) {
		return false;
	}
}
