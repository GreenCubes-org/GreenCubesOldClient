// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ThreadDownloadImageData, ImageBuffer

class ThreadDownloadImage {

	final String location; /* synthetic field */
	final ImageBuffer buffer; /* synthetic field */
	final ThreadDownloadImageData imageData; /* synthetic field */

	ThreadDownloadImage(ThreadDownloadImageData threaddownloadimagedata, String s, ImageBuffer imagebuffer) {
		imageData = threaddownloadimagedata;
		location = s;
		buffer = imagebuffer;
		GCConnectionManager.add(this);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof ThreadDownloadImage)
			return ((ThreadDownloadImage) o).location.equals(location);
		return false;
	}
}
