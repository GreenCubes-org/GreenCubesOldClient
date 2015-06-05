package net.lahwran.wecui;

public abstract class CuiRegion {
	
	protected int regionSize;

	public abstract void render();

	public abstract void setPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
}