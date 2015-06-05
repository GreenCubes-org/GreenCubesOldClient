// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

public class ClippingHelper {

	public float frustum[][];
	public float projectionMatrix[];
	public float modelviewMatrix[];
	public float clippingMatrix[];

	public ClippingHelper() {
		frustum = new float[16][16];
		projectionMatrix = new float[16];
		modelviewMatrix = new float[16];
		clippingMatrix = new float[16];
	}

	public boolean isBoxInFrustum(double d, double d1, double d2, double d3, double d4, double d5) {
		for(int i = 0; i < 6; i++) {
			if(frustum[i][0] * d + frustum[i][1] * d1 + frustum[i][2] * d2 + frustum[i][3] <= 0.0D && frustum[i][0] * d3 + frustum[i][1] * d1 + frustum[i][2] * d2 + frustum[i][3] <= 0.0D && frustum[i][0] * d + frustum[i][1] * d4 + frustum[i][2] * d2 + frustum[i][3] <= 0.0D && frustum[i][0] * d3 + frustum[i][1] * d4 + frustum[i][2] * d2 + frustum[i][3] <= 0.0D && frustum[i][0] * d + frustum[i][1] * d1 + frustum[i][2] * d5 + frustum[i][3] <= 0.0D && frustum[i][0] * d3 + frustum[i][1] * d1 + frustum[i][2] * d5 + frustum[i][3] <= 0.0D && frustum[i][0] * d + frustum[i][1] * d4 + frustum[i][2] * d5 + frustum[i][3] <= 0.0D && frustum[i][0] * d3 + frustum[i][1] * d4 + frustum[i][2] * d5 + frustum[i][3] <= 0.0D) {
				return false;
			}
		}

		return true;
	}
}
