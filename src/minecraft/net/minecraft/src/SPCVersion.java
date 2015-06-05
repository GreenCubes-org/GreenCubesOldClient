// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Date;

// Referenced classes of package net.minecraft.src:
//            SPCVersionInterface

public class SPCVersion implements SPCVersionInterface {

	private String name;
	private String version;
	private Date lastupdate;

	public SPCVersion(String s, String s1, Date date) {
		name = s;
		version = s1;
		lastupdate = date;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public Date getLastUpdate() {
		return lastupdate;
	}
}
