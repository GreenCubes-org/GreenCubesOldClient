package org.greencubes.compatibility;

import gnu.trove.map.hash.THashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.greencubes.nbt.NBTTagCompound;
import org.greencubes.nbt.NBTTagList;
import org.greencubes.nbt.NBTWorker;
import org.greencubes.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;

public final class BlockDurabilityFactory {
	
	private static byte[] durabilityData;
	public static byte[] durabilityDataHash;
	
	public static void load() {
		File cacheFile = new File(Minecraft.getAppDir("greencubes"), "cache/durability.dat");
		if(cacheFile.exists()) {
			try {
				FileInputStream fs = new FileInputStream(cacheFile);
				NBTTagCompound tag = NBTWorker.read(new DataInputStream(new GZIPInputStream(fs)));
				loadFromTag(tag);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void afterLoad() {
		NBTTagCompound durabilityTag = new NBTTagCompound();
		NBTTagList durabilities = new NBTTagList();
		for(int i = 0; i < Block.blocksList.length; ++i) {
			Block block = Block.blocksList[i];
			if(block != null) {
				NBTTagCompound tag = block.getDurability().toNBTTag(null);
				tag.setShort("block", (short) block.blockID);
				durabilities.add(tag);
			}
		}
		durabilityTag.setNBTTag("durs", durabilities);
		durabilityData = NBTWorker.toGZIPArray(durabilityTag);
		durabilityDataHash = Util.sha1(durabilityData);
		System.out.println("Durability data size: " + durabilityData.length + ", hash: " + Util.toHexString(durabilityDataHash));
		
	}
	
	public static void loadFromTag(NBTTagCompound tag) {
		NBTTagList durabilities = tag.getTagList("durs");
		for(int i = 0; i < durabilities.size(); ++i) {
			NBTTagCompound blockTag = (NBTTagCompound) durabilities.get(i);
			Block block = Block.blocksList[blockTag.getInt("block")];
			if(block != null) {
				BlockDurability dur = BlockDurability.fromNBTTag(blockTag);
				block.setDurability(dur);
			}
		}
		try {
			saveDurability(tag);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void saveDurability(NBTTagCompound tag) throws IOException {
		File f = new File(Minecraft.getAppDir("greencubes"), "cache/durability.dat");
		if(!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		FileOutputStream fs = new FileOutputStream(f);
		NBTWorker.write(tag, new DataOutputStream(new GZIPOutputStream(fs)));
		fs.close();
	}
}
