// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.lang.annotation.Annotation;

public interface SPCCommand extends Annotation {

	public abstract String cmd();

	public abstract String help();

	public abstract String args();

	public abstract String example();

	public abstract boolean multiplayer();

	public abstract String[] alias();
}
