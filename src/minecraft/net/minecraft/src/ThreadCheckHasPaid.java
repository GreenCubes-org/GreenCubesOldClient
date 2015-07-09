// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Session

public class ThreadCheckHasPaid extends Thread {

	final Minecraft mc; /* synthetic field */

	public ThreadCheckHasPaid(Minecraft minecraft) {
		mc = minecraft;
		//        super();
	}

	@Override
	public void run() {
	}
}
