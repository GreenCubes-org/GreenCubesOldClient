/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.greencubes.compatibility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
