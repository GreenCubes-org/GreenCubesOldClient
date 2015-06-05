// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            IntHashMap, GLAllocation, TexturePackList, TexturePackBase, 
//            GameSettings, ThreadDownloadImageData, TextureFX, ImageBuffer

public class RenderEngine {

	public static boolean useMipmaps = false;
	private TObjectIntMap<String> textureMap = new TObjectIntHashMap<String>();
	private HashMap textureContentsMap;
	private TIntObjectHashMap<BufferedImage> textureNameToImageMap;
	private IntBuffer singleIntBuffer;
	private ByteBuffer imageData;
	private List<TextureFX> textureList;
	private Map<String, ThreadDownloadImageData> urlToImageDataMap;
	private GameSettings options;
	public boolean clampTexture;
	public boolean blurTexture;
	private TexturePackList texturePack;
	private BufferedImage missingTextureImage;
	private BufferedImage strangeTexture;

	// GC
	public static final int TERRAIN_HEIGHT = 1024;
	public static final float TERRAIN_HEIGHT_F = 1024.0F;
	public static final double TERRAIN_HEIGHT_D = 1024.0D;
	public static final int TERRAIN_MULT = TERRAIN_HEIGHT / 256;
	public static final int ITEMS_HEIGHT = 1024;
	public static final float ITEMS_HEIGHT_F = 1024.0F;
	public static final double ITEMS_HEIGHT_D = 1024.0D;
	public static final int ITEMS_MULT = ITEMS_HEIGHT / 256;
	public static final int TEX = 0xfffffff0;
	public boolean updateAllTextures = true;
	private Comparator<TextureFX> textureComparator;

	public RenderEngine(TexturePackList texturepacklist, GameSettings gamesettings) {
		textureContentsMap = new HashMap();
		textureNameToImageMap = new TIntObjectHashMap<BufferedImage>();
		singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
		imageData = GLAllocation.createDirectByteBuffer(0x1000);
		textureList = new ArrayList<TextureFX>();
		urlToImageDataMap = new HashMap();
		clampTexture = false;
		blurTexture = false;
		missingTextureImage = new BufferedImage(64, 64, 2);
		texturePack = texturepacklist;
		options = gamesettings;
		Graphics g = missingTextureImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 64, 64);
		g.setColor(Color.BLACK);
		g.drawString("missingtex", 1, 10);
		g.dispose();
		textureComparator = new TextureFXComparator();
	}

	// GC
	public static int getTextureSize() {
		return GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH) / 16;
	}

	private void ensureBuffer(int length) {
		if(imageData.capacity() < length) {
			System.out.println("Extending buffer to " + length + " bytes");
			imageData = GLAllocation.createDirectByteBuffer(length);
		}
	}

	public int[] getTextureContents(String s) {
		TexturePackBase texturepackbase = texturePack.selectedTexturePack;
		int ai[] = (int[]) textureContentsMap.get(s);
		if(ai != null) {
			return ai;
		}
		try {
			int ai1[] = null;
			if(s.startsWith("##")) {
				ai1 = getImageContentsAndAllocate(unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s.substring(2)))));
			} else if(s.startsWith("%clamp%")) {
				clampTexture = true;
				ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(s.substring(7))));
				clampTexture = false;
			} else if(s.startsWith("%blur%")) {
				blurTexture = true;
				clampTexture = true;
				ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(s.substring(6))));
				clampTexture = false;
				blurTexture = false;
			} else {
				InputStream inputstream = texturepackbase.getResourceAsStream(s);
				if(inputstream == null) {
					ai1 = getImageContentsAndAllocate(missingTextureImage);
				} else {
					ai1 = getImageContentsAndAllocate(readTextureImage(inputstream));
				}
			}
			textureContentsMap.put(s, ai1);
			return ai1;
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		int ai2[] = getImageContentsAndAllocate(missingTextureImage);
		textureContentsMap.put(s, ai2);
		return ai2;
	}

	private int[] getImageContentsAndAllocate(BufferedImage bufferedimage) {
		int i = bufferedimage.getWidth();
		int j = bufferedimage.getHeight();
		int ai[] = new int[i * j];
		bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
		return ai;
	}

	private int[] getImageContents(BufferedImage bufferedimage, int ai[]) {
		int i = bufferedimage.getWidth();
		int j = bufferedimage.getHeight();
		bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
		return ai;
	}

	public int getTexture(String s) {
		if(textureMap.containsKey(s))
			return textureMap.get(s);
		TexturePackBase texturepackbase = texturePack.selectedTexturePack;
		try {
			singleIntBuffer.clear();
			GLAllocation.generateTextureNames(singleIntBuffer);
			int i = singleIntBuffer.get(0);
			if(s.startsWith("##")) {
				setupTexture(unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s.substring(2)))), i);
			} else if(s.startsWith("%clamp%")) {
				clampTexture = true;
				setupTexture(readTextureImage(texturepackbase.getResourceAsStream(s.substring(7))), i);
				clampTexture = false;
			} else if(s.startsWith("%blur%")) {
				blurTexture = true;
				setupTexture(readTextureImage(texturepackbase.getResourceAsStream(s.substring(6))), i);
				blurTexture = false;
			} else if(s.startsWith("%blurclamp%")) {
				blurTexture = true;
				clampTexture = true;
				setupTexture(readTextureImage(texturepackbase.getResourceAsStream(s.substring(11))), i);
				blurTexture = false;
				clampTexture = false;
			} else {
				InputStream inputstream = texturepackbase.getResourceAsStream(s);
				if(inputstream == null) {
					setupTexture(missingTextureImage, i);
				} else {
					setupTexture(readTextureImage(inputstream), i, s.equals("/terrain.png") || s.equals("/gui/items.png")); // GreenCubes
				}
			}
			textureMap.put(s, i);
			return i;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		GLAllocation.generateTextureNames(singleIntBuffer);
		int j = singleIntBuffer.get(0);
		setupTexture(missingTextureImage, j);
		textureMap.put(s, j);
		return j;
	}

	private BufferedImage unwrapImageByColumns(BufferedImage bufferedimage) {
		int i = bufferedimage.getWidth() / 16;
		BufferedImage bufferedimage1 = new BufferedImage(16, bufferedimage.getHeight() * i, 2);
		Graphics g = bufferedimage1.getGraphics();
		for(int j = 0; j < i; j++) {
			g.drawImage(bufferedimage, -j * 16, j * bufferedimage.getHeight(), null);
		}

		g.dispose();
		return bufferedimage1;
	}

	public int allocateAndSetupTexture(BufferedImage bufferedimage) {
		singleIntBuffer.clear();
		GLAllocation.generateTextureNames(singleIntBuffer);
		int i = singleIntBuffer.get(0);
		setupTexture(bufferedimage, i);
		textureNameToImageMap.put(i, bufferedimage);
		return i;
	}

	// GC Start
	public void setupTexture(BufferedImage bufferedimage, int i) {
		setupTexture(bufferedimage, i, false);
	}

	public void setupTexture(BufferedImage bufferedimage, int i, boolean expand) {
		// GC end
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, i);
		if(useMipmaps) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9986 /*GL_NEAREST_MIPMAP_LINEAR*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9728 /*GL_NEAREST*/);
		} else {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9728 /*GL_NEAREST*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9728 /*GL_NEAREST*/);
		}
		if(blurTexture) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9729 /*GL_LINEAR*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9729 /*GL_LINEAR*/);
		}
		if(clampTexture) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10242 /*GL_TEXTURE_WRAP_S*/, 10496 /*GL_CLAMP*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10243 /*GL_TEXTURE_WRAP_T*/, 10496 /*GL_CLAMP*/);
		} else {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10242 /*GL_TEXTURE_WRAP_S*/, 10497 /*GL_REPEAT*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10243 /*GL_TEXTURE_WRAP_T*/, 10497 /*GL_REPEAT*/);
		}
		int j = bufferedimage.getWidth();
		int k = bufferedimage.getHeight();
		int ai[] = new int[j * k];
		byte abyte0[] = new byte[j * k * (expand ? TERRAIN_MULT * 4 : 4)]; // GC
		bufferedimage.getRGB(0, 0, j, k, ai, 0, j);
		for(int l = 0; l < ai.length; l++) {
			int j1 = ai[l] >> 24 & 0xff;
			int l1 = ai[l] >> 16 & 0xff;
			int j2 = ai[l] >> 8 & 0xff;
			int l2 = ai[l] & 0xff;
			if(options != null && options.anaglyph) {
				int j3 = (l1 * 30 + j2 * 59 + l2 * 11) / 100;
				int l3 = (l1 * 30 + j2 * 70) / 100;
				int j4 = (l1 * 30 + l2 * 70) / 100;
				l1 = j3;
				j2 = l3;
				l2 = j4;
			}
			abyte0[l * 4 + 0] = (byte) l1;
			abyte0[l * 4 + 1] = (byte) j2;
			abyte0[l * 4 + 2] = (byte) l2;
			abyte0[l * 4 + 3] = (byte) j1;
		}
		if(expand)
			k *= TERRAIN_MULT; // GC
		imageData.clear();
		ensureBuffer(abyte0.length);
		imageData.put(abyte0);
		imageData.position(0).limit(abyte0.length);
		GL11.glTexImage2D(3553 /*GL_TEXTURE_2D*/, 0, 6408 /*GL_RGBA*/, j, k, 0, 6408 /*GL_RGBA*/, 5121 /*GL_UNSIGNED_BYTE*/, imageData);
		if(useMipmaps) {
			for(int i1 = 1; i1 <= 4; i1++) {
				int k1 = j >> i1 - 1;
				int i2 = j >> i1;
				int k2 = k >> i1;
				for(int i3 = 0; i3 < i2; i3++) {
					for(int k3 = 0; k3 < k2; k3++) {
						int i4 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * k1) * 4);
						int k4 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * k1) * 4);
						int l4 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * k1) * 4);
						int i5 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * k1) * 4);
						int j5 = alphaBlend(alphaBlend(i4, k4), alphaBlend(l4, i5));
						imageData.putInt((i3 + k3 * i2) * 4, j5);
					}

				}

				GL11.glTexImage2D(3553 /*GL_TEXTURE_2D*/, i1, 6408 /*GL_RGBA*/, i2, k2, 0, 6408 /*GL_RGBA*/, 5121 /*GL_UNSIGNED_BYTE*/, imageData);
			}

		}
	}

	public void createTextureFromBytes(int ai[], int i, int j, int k) {
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, k);
		if(useMipmaps) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9986 /*GL_NEAREST_MIPMAP_LINEAR*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9728 /*GL_NEAREST*/);
		} else {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9728 /*GL_NEAREST*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9728 /*GL_NEAREST*/);
		}
		if(blurTexture) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9729 /*GL_LINEAR*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9729 /*GL_LINEAR*/);
		}
		if(clampTexture) {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10242 /*GL_TEXTURE_WRAP_S*/, 10496 /*GL_CLAMP*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10243 /*GL_TEXTURE_WRAP_T*/, 10496 /*GL_CLAMP*/);
		} else {
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10242 /*GL_TEXTURE_WRAP_S*/, 10497 /*GL_REPEAT*/);
			GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10243 /*GL_TEXTURE_WRAP_T*/, 10497 /*GL_REPEAT*/);
		}
		byte abyte0[] = new byte[i * j * 4];
		for(int l = 0; l < ai.length; l++) {
			int i1 = ai[l] >> 24 & 0xff;
			int j1 = ai[l] >> 16 & 0xff;
			int k1 = ai[l] >> 8 & 0xff;
			int l1 = ai[l] & 0xff;
			if(options != null && options.anaglyph) {
				int i2 = (j1 * 30 + k1 * 59 + l1 * 11) / 100;
				int j2 = (j1 * 30 + k1 * 70) / 100;
				int k2 = (j1 * 30 + l1 * 70) / 100;
				j1 = i2;
				k1 = j2;
				l1 = k2;
			}
			abyte0[l * 4 + 0] = (byte) j1;
			abyte0[l * 4 + 1] = (byte) k1;
			abyte0[l * 4 + 2] = (byte) l1;
			abyte0[l * 4 + 3] = (byte) i1;
		}

		imageData.clear();
		ensureBuffer(abyte0.length);
		imageData.put(abyte0);
		imageData.position(0).limit(abyte0.length);
		GL11.glTexSubImage2D(3553 /*GL_TEXTURE_2D*/, 0, 0, 0, i, j, 6408 /*GL_RGBA*/, 5121 /*GL_UNSIGNED_BYTE*/, imageData);
	}

	public void deleteTexture(int i) {
		textureNameToImageMap.remove(i);
		singleIntBuffer.clear();
		singleIntBuffer.put(i);
		singleIntBuffer.flip();
		GL11.glDeleteTextures(singleIntBuffer);
	}

	public int getTextureForDownloadableImage(String s, String s1) {
		ThreadDownloadImageData threaddownloadimagedata = urlToImageDataMap.get(s);
		if(threaddownloadimagedata != null && threaddownloadimagedata.image != null && !threaddownloadimagedata.textureSetupComplete) {
			if(threaddownloadimagedata.textureName < 0) {
				threaddownloadimagedata.textureName = allocateAndSetupTexture(threaddownloadimagedata.image);
			} else {
				setupTexture(threaddownloadimagedata.image, threaddownloadimagedata.textureName);
			}
			threaddownloadimagedata.textureSetupComplete = true;
		}
		if(threaddownloadimagedata == null || threaddownloadimagedata.textureName < 0) {
			if(s1 == null) {
				return -1;
			} else {
				return getTexture(s1);
			}
		} else {
			return threaddownloadimagedata.textureName;
		}
	}

	public ThreadDownloadImageData obtainImageData(String s, ImageBuffer imagebuffer) {
		ThreadDownloadImageData threaddownloadimagedata = urlToImageDataMap.get(s);
		if(threaddownloadimagedata == null) {
			urlToImageDataMap.put(s, new ThreadDownloadImageData(s, imagebuffer));
		} else {
			threaddownloadimagedata.referenceCount++;
		}
		return threaddownloadimagedata;
	}

	public void releaseImageData(String s) {
		ThreadDownloadImageData threaddownloadimagedata = urlToImageDataMap.get(s);
		if(threaddownloadimagedata != null) {
			threaddownloadimagedata.referenceCount--;
			if(threaddownloadimagedata.referenceCount == 0)
				threaddownloadimagedata.emptyTime = System.currentTimeMillis();
		}
	}

	public void registerTextureFX(TextureFX texturefx) {
		textureList.add(texturefx);
		texturefx.onTick();
	}

	public void updateDynamicTextures() {
		Iterator<Entry<String, ThreadDownloadImageData>> iterator = urlToImageDataMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, ThreadDownloadImageData> entry = iterator.next();
			ThreadDownloadImageData tdid = entry.getValue();
			if(tdid.referenceCount <= 0 && tdid.emptyTime + 300000 < System.currentTimeMillis()) {
				tdid.emptyTime = System.currentTimeMillis();
				if(tdid.textureName >= 0)
					deleteTexture(tdid.textureName);
				iterator.remove();
			}
		}
		int i = -1;
		int realPackDefs = -1;
		TextureFX[] textures = textureList.toArray(new TextureFX[0]);
		Arrays.sort(textures, textureComparator);
		for(int j = 0; j < textures.length; j++) {
			TextureFX texturefx = textures[j];
			if(texturefx instanceof ModTextureStatic && !updateAllTextures)
				continue;
			texturefx.anaglyphEnabled = options.anaglyph;
			texturefx.onTick();
			imageData.clear();
			ensureBuffer(texturefx.imageData.length);
			imageData.put(texturefx.imageData);
			imageData.position(0).limit(texturefx.imageData.length);
			imageData.rewind();
			if(texturefx.iconIndex != i) {
				texturefx.bindImage(this);
				i = texturefx.iconIndex;
				realPackDefs = getTextureSize();
			}
			int width = (int) Math.sqrt(texturefx.imageData.length / 4);
			for(int k = 0; k < texturefx.tileSize; k++)
				for(int l = 0; l < texturefx.tileSize; l++)
					GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, (texturefx.iconIndex % 16) * realPackDefs + k * realPackDefs, (texturefx.iconIndex / 16) * realPackDefs + l * realPackDefs, width, width, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
			//if(texturefx instanceof ModTextureStatic)
			//	System.out.println("Updated " + texturefx.toString());
		}
		if(updateAllTextures) {
			// TODO
		}
		updateAllTextures = false;
	}

	private int alphaBlend(int i, int j) {
		int k = (i & 0xff000000) >> 24 & 0xff;
		int l = (j & 0xff000000) >> 24 & 0xff;
		char c = '\377';
		if(k + l == 0) {
			k = 1;
			l = 1;
			c = '\0';
		}
		int i1 = (i >> 16 & 0xff) * k;
		int j1 = (i >> 8 & 0xff) * k;
		int k1 = (i & 0xff) * k;
		int l1 = (j >> 16 & 0xff) * l;
		int i2 = (j >> 8 & 0xff) * l;
		int j2 = (j & 0xff) * l;
		int k2 = (i1 + l1) / (k + l);
		int l2 = (j1 + i2) / (k + l);
		int i3 = (k1 + j2) / (k + l);
		return c << 24 | k2 << 16 | l2 << 8 | i3;
	}

	public void refreshTextures() {
		TexturePackBase texturepackbase = texturePack.selectedTexturePack;
		int i;
		BufferedImage bufferedimage;
		TIntObjectIterator<BufferedImage> iterator = textureNameToImageMap.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			setupTexture(iterator.value(), iterator.key());
		}

		for(Iterator iterator1 = urlToImageDataMap.values().iterator(); iterator1.hasNext();) {
			ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) iterator1.next();
			threaddownloadimagedata.textureSetupComplete = false;
		}

		TObjectIntIterator<String> iterator2 = textureMap.iterator();
		while(iterator2.hasNext()) {
			iterator2.advance();
			String s = iterator2.key();
			try {
				BufferedImage bufferedimage1;
				if(s.startsWith("##")) {
					bufferedimage1 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s.substring(2))));
				} else if(s.startsWith("%clamp%")) {
					clampTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(7)));
				} else if(s.startsWith("%blur%")) {
					blurTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(6)));
				} else if(s.startsWith("%blurclamp%")) {
					blurTexture = true;
					clampTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(11)));
				} else {
					bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s));
				}
				int j = textureMap.get(s);
				setupTexture(bufferedimage1, j, s.equals("/terrain.png") || s.equals("/gui/items.png"));
				blurTexture = false;
				clampTexture = false;
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}

		for(Iterator iterator3 = textureContentsMap.keySet().iterator(); iterator3.hasNext();) {
			String s1 = (String) iterator3.next();
			try {
				BufferedImage bufferedimage2;
				if(s1.startsWith("##")) {
					bufferedimage2 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s1.substring(2))));
				} else if(s1.startsWith("%clamp%")) {
					clampTexture = true;
					bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(7)));
				} else if(s1.startsWith("%blur%")) {
					blurTexture = true;
					bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(6)));
				} else {
					bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1));
				}
				getImageContents(bufferedimage2, (int[]) textureContentsMap.get(s1));
				blurTexture = false;
				clampTexture = false;
			} catch (IOException ioexception1) {
				ioexception1.printStackTrace();
			}
		}
		updateAllTextures = true;
	}

	private BufferedImage readTextureImage(InputStream inputstream) throws IOException {
		BufferedImage bufferedimage = ImageIO.read(inputstream);
		inputstream.close();
		return bufferedimage;
	}

	public void bindTexture(int i) {
		if(i < 0) {
			return;
		} else {
			GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, i);
			return;
		}
	}

}
