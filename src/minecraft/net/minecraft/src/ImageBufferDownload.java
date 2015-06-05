// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.*;

// Referenced classes of package net.minecraft.src:
//            ImageBuffer

public class ImageBufferDownload implements ImageBuffer {

	private int imageData[];
	private int imageWidth;
	private int imageHeight;

	public ImageBufferDownload() {
	}

	@Override
	public BufferedImage parseUserSkin(BufferedImage bufferedimage) {
		if(bufferedimage == null) {
			return null;
		}
		imageWidth = bufferedimage.getWidth();
		imageHeight = bufferedimage.getHeight();
		BufferedImage bufferedimage1 = new BufferedImage(imageWidth, imageHeight, 2);
		Graphics g = bufferedimage1.getGraphics();
		g.drawImage(bufferedimage, 0, 0, null);
		g.dispose();
		imageData = ((DataBufferInt) bufferedimage1.getRaster().getDataBuffer()).getData();
		func_884_b(0, 0, imageWidth / 2, imageHeight / 2);
		func_885_a(imageWidth / 2, 0, imageWidth, imageHeight);
		func_884_b(0, imageHeight / 2, imageWidth, imageHeight);
		boolean flag = false;
		for(int i = imageHeight; i < imageWidth; i++) {
			for(int k = 0; k < imageHeight / 2; k++) {
				int i1 = imageData[i + k * imageWidth];
				if((i1 >> 24 & 0xff) < 128) {
					flag = true;
				}
			}

		}

		if(!flag) {
			for(int j = imageHeight; j < imageWidth; j++) {
				for(int l = 0; l < imageHeight / 2; l++) {
					int j1 = imageData[j + l * imageWidth];
					boolean flag1;
					if((j1 >> 24 & 0xff) < 128) {
						flag1 = true;
					}
				}

			}

		}
		return bufferedimage1;
	}

	private void func_885_a(int i, int j, int k, int l) {
		if(func_886_c(i, j, k, l)) {
			return;
		}
		for(int i1 = i; i1 < k; i1++) {
			for(int j1 = j; j1 < l; j1++) {
				imageData[i1 + j1 * imageWidth] &= 0xffffff;
			}

		}

	}

	private void func_884_b(int i, int j, int k, int l) {
		for(int i1 = i; i1 < k; i1++) {
			for(int j1 = j; j1 < l; j1++) {
				imageData[i1 + j1 * imageWidth] |= 0xff000000;
			}

		}

	}

	private boolean func_886_c(int i, int j, int k, int l) {
		for(int i1 = i; i1 < k; i1++) {
			for(int j1 = j; j1 < l; j1++) {
				int k1 = imageData[i1 + j1 * imageWidth];
				if((k1 >> 24 & 0xff) < 128) {
					return true;
				}
			}

		}

		return false;
	}
}
