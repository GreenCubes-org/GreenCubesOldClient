// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;

// Referenced classes of package net.minecraft.src:
//            SPCPoint

public interface SPCWorldInterface {

	public abstract void setBlock(SPCPoint spcpoint, int i);

	public abstract void setBlockWithNotify(SPCPoint spcpoint, int i);

	public abstract void setMetadata(SPCPoint spcpoint, int i);

	public abstract void setMetadataWithNotify(SPCPoint spcpoint, int i);

	public abstract int getBlock(SPCPoint spcpoint, int i);

	public abstract int getMetadata(SPCPoint spcpoint, int i);

	public abstract SPCPoint getSpawn();

	public abstract void setSpawn(SPCPoint spcpoint);

	public abstract File getWorldDir();

	public abstract long getTime();

	public abstract void setTime(long l);
}
