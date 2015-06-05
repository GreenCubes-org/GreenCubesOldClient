// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            ISaveHandler, WorldInfo, WorldProvider, IChunkLoader

public class SaveHandlerMP implements ISaveHandler {

	public SaveHandlerMP() {
	}

	@Override
	public WorldInfo loadWorldInfo() {
		return null;
	}

	@Override
	public void checkSessionLock() {
	}

	@Override
	public IChunkLoader getChunkLoader(WorldProvider worldprovider) {
		return null;
	}

	@Override
	public void saveWorldInfoAndPlayer(WorldInfo worldinfo, List list) {
	}

	@Override
	public void saveWorldInfo(WorldInfo worldinfo) {
	}

	@Override
	public File getMapFile(String s) {
		return null;
	}

	@Override
	public String func_40530_d() {
		return "none";
	}
}
