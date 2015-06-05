// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

public class SPCPoint {

	public int ix;
	public int iy;
	public int iz;
	public double dx;
	public double dy;
	public double dz;

	public SPCPoint(int i, int j, int k) {
		set(i, j, k);
	}

	public SPCPoint(double d, double d1, double d2) {
		set(d, d1, d2);
	}

	public void setX(int i) {
		ix = i;
		dx = i;
	}

	public void setX(double d) {
		ix = getInt(d);
		dx = d;
	}

	public void setY(int i) {
		iy = i;
		dy = i;
	}

	public void setY(double d) {
		iy = getInt(d);
		dy = d;
	}

	public void setZ(int i) {
		iz = i;
		dz = i;
	}

	public void setZ(double d) {
		iz = getInt(d);
		dz = d;
	}

	public void set(int i, int j, int k) {
		ix = i;
		iy = j;
		iz = k;
		dx = i;
		dy = j;
		dz = k;
	}

	public void set(double d, double d1, double d2) {
		ix = getInt(d);
		iy = getInt(d1);
		iz = getInt(d2);
		dx = d;
		dy = d1;
		dz = d2;
	}

	public static int getInt(double d) {
		int i = (int) d;
		return d < i ? i - 1 : i;
	}
}
