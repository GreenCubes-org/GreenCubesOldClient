// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GLAllocation, RenderEngine, Tessellator, GameSettings, 
//            ChatAllowedCharacters

public class FontRenderer {

	public float charWidth[]; // GreenCubes: private -> public
	public int fontTextureName;
	public int FONT_HEIGHT;
	private int fontDisplayLists;
	private IntBuffer buffer;
	public Random field_41064_c;

	public FontRenderer(GameSettings gamesettings, String s, RenderEngine renderengine, boolean b) {
		charWidth = new float[256];
		fontTextureName = 0;
		FONT_HEIGHT = 8;
		buffer = GLAllocation.createDirectIntBuffer(1024);
		field_41064_c = new Random();
		BufferedImage bufferedimage;
		try {
			bufferedimage = ImageIO.read((net.minecraft.src.RenderEngine.class).getResourceAsStream(s));
		} catch (IOException ioexception) {
			throw new RuntimeException(ioexception);
		}
		int imgWidth = bufferedimage.getWidth();
		int imgHeight = bufferedimage.getHeight();

		int charW = imgWidth / 16;
		int charH = imgHeight / 16;

		int kx = imgWidth / 128;

		int[] ai = new int[imgWidth * imgHeight];
		bufferedimage.getRGB(0, 0, imgWidth, imgHeight, ai, 0, imgWidth);
		for(int k = 0; k < 256; k++) {
			int cx = k % 16;
			int cy = k / 16;
			int px = 0;
			for(px = charW - 1; px >= 0; px--) {
				int x = cx * charW + px;
				boolean flag = true;
				for(int py = 0; (py < charH) && (flag); py++) {
					int ypos = (cy * charH + py) * imgWidth;
					int color = ai[(x + ypos)] & 0xFF;
					if(color <= 0)
						continue;
					flag = false;
				}
				if(!flag)
					break;
			}

			if(k == 32)
				px = 2 * kx;
			this.charWidth[k] = ((px + 2) / kx) + 0.5f;
		}

		fontTextureName = renderengine.allocateAndSetupTexture(bufferedimage);
		fontDisplayLists = GLAllocation.generateDisplayLists(1024 + 32 + 2);
		Tessellator tessellator = Tessellator.instance;
		for(int i1 = 0; i1 < 1024; i1++) {
			GL11.glNewList(fontDisplayLists + i1, GL11.GL_COMPILE);
			float f = (i1 % 16) * 8;
			float f1 = (i1 / 16) * 8;
			float f2 = i1 > 256 ? 1F : 0.0F;
			int n = i1;
			while(n >= 256) {
				n -= 256;
			}
			float f3 = charWidth[n] - 0.01F;
			if(i1 < 512) {
				if(n != 32) {
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(f2, 0.0F, 0.0F, f / 128F, f1 / 128F);
					tessellator.addVertexWithUV(-f2, 7.99F, 0.0F, f / 128F, (f1 + 7.99F) / 128F);
					tessellator.addVertexWithUV(f3 - f2, 7.99F, 0.0F, (f + f3) / 128F, (f1 + 7.99F) / 128F);
					tessellator.addVertexWithUV(f3 + f2, 0.0F, 0.0F, (f + f3) / 128F, f1 / 128F);
					tessellator.draw();
				}
				GL11.glTranslatef(charWidth[n], 0.0F, 0.0F);
			} else if(i1 < 512 + 256) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tessellator.startDrawingQuads();
				tessellator.addVertex(-0.5F, 4.5f, 0.0D);
				tessellator.addVertex(-0.5f, 5.0f, 0.0D);
				tessellator.addVertex(charWidth[n] - 0.5f, 5.0f, 0.0D);
				tessellator.addVertex(charWidth[n] - 0.5f, 4.5f, 0.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			} else if(i1 < 1024) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tessellator.startDrawingQuads();
				tessellator.addVertex(-0.5F, 8.0f, 0.0D);
				tessellator.addVertex(-0.5F, 8.5f, 0.0D);
				tessellator.addVertex(charWidth[n] - 0.5F, 8.5f, 0.0D);
				tessellator.addVertex(charWidth[n] - 0.5F, 8.0f, 0.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
			GL11.glEndList();
		}

		for(int j1 = 0; j1 < 32; j1++) {
			int i2 = (j1 >> 3 & 1) * 85;
			int l2 = (j1 >> 2 & 1) * 170 + i2;
			int j3 = (j1 >> 1 & 1) * 170 + i2;
			int k3 = (j1 >> 0 & 1) * 170 + i2;
			if(j1 == 6)
				l2 += 85;
			boolean flag1 = j1 >= 16;
			if(gamesettings.anaglyph) {
				int j4 = (l2 * 30 + j3 * 59 + k3 * 11) / 100;
				int l4 = (l2 * 30 + j3 * 70) / 100;
				int i5 = (l2 * 30 + k3 * 70) / 100;
				l2 = j4;
				j3 = l4;
				k3 = i5;
			}
			if(flag1) {
				l2 /= 8;
				j3 /= 8;
				k3 /= 8;
			}
			GL11.glNewList(fontDisplayLists + 1024 + j1, GL11.GL_COMPILE);
			GL11.glColor3f(l2 / 255F, j3 / 255F, k3 / 255F);
			GL11.glEndList();
		}
	}

	public void drawStringWithShadow(String s, int i, int j, int k) {
		renderString(s, i + 1, j + 1, k, true);
		drawString(s, i, j, k);
	}

	public void drawStringWithShadow(String s, int i, int j, int k, float space, int spaces) {
		renderString(s, i + 1, j + 1, k, true, space, spaces);
		renderString(s, i, j, k, false, space, spaces);
	}

	public void drawString(String s, int i, int j, int k) {
		renderString(s, i, j, k, false);
	}

	public void renderString(CharSequence s, int i, int j, int k, boolean flag) {
		renderString(s, i, j, k, flag, 0.0f, 0);
	}

	public void renderString(CharSequence s, int i, int j, int k, boolean flag, float space, int spaces) {
		if(s == null)
			return;
		if(flag)
			k = (k & 0xf8f8f8) >> 3 | k & 0xff000000;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureName);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		float f = (k >> 16 & 0xff) / 255F;
		float f1 = (k >> 8 & 0xff) / 255F;
		float f2 = (k & 0xff) / 255F;
		float f3 = (k >> 24 & 0xff) / 255F;
		if(f3 == 0.0F)
			f3 = 1.0F;
		int spaceSymbol = fontDisplayLists + 32;
		int spaceUnderline = fontDisplayLists + 32 + 512 + 256;
		if(space != 0.0f) {
			spaceSymbol = fontDisplayLists + 1024 + 32 + 1;
			GL11.glNewList(spaceSymbol, GL11.GL_COMPILE);
			//GL11.glCallList(fontDisplayLists + 32);
			GL11.glTranslatef(space + charWidth[32], 0.0f, 0.0f);
			GL11.glEndList();
			spaceUnderline = fontDisplayLists + 1024 + 32 + 2;
			GL11.glNewList(spaceUnderline, GL11.GL_COMPILE);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertex(-0.5F, 8.0f, 0.0D);
			tessellator.addVertex(-0.5F, 8.5f, 0.0D);
			tessellator.addVertex(charWidth[32] + space - 0.5F, 8.5f, 0.0D);
			tessellator.addVertex(charWidth[32] + space - 0.5F, 8.0f, 0.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEndList();
		}
		GL11.glColor4f(f, f1, f2, f3);
		buffer.clear();
		GL11.glPushMatrix();
		if(i != 0 || j != 0)
			GL11.glTranslatef(i, j, 0.0F);
		boolean italic = false;
		boolean underline = false;
		boolean stroke = false;
		for(int i1 = 0; i1 < s.length(); i1++) {
			for(; s.length() > i1 + 1 && s.charAt(i1) == '\247'; i1 += 2) {
				char c = s.charAt(i1 + 1);
				c = Character.toLowerCase(c);
				if(c == 'r') {
					if(s.length() >= i1 + 9) {
						try {
							CharSequence s1 = s.subSequence(i1 + 2, i1 + 4);
							CharSequence s2 = s.subSequence(i1 + 4, i1 + 6);
							CharSequence s3 = s.subSequence(i1 + 6, i1 + 8);
							CharSequence s4 = s.subSequence(i1 + 8, i1 + 10);
							int color = Integer.parseInt(s1.toString(), 16) << 24 | Integer.parseInt(s2.toString(), 16) << 16 | Integer.parseInt(s3.toString(), 16) << 8 | Integer.parseInt(s4.toString(), 16);
							if(flag)
								color = (color & 0xf8f8f8) >> 3 | color & 0xff000000;
							float ff = (color >> 16 & 0xff) / 255F;
							float ff1 = (color >> 8 & 0xff) / 255F;
							float ff2 = (color & 0xff) / 255F;
							float ff3 = (color >> 24 & 0xff) / 255F;
							buffer.flip();
							GL11.glCallLists(buffer);
							buffer.clear();
							GL11.glColor4f(ff, ff1, ff2, ff3);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						i1 += 8;
						continue;
					}
				}
				if(c == 'i') {
					italic = true;
					continue;
				}
				if(c == 'p') {
					italic = false;
					stroke = false;
					underline = false;
					continue;
				}
				if(c == 'u') {
					underline = true;
					continue;
				}
				if(c == 's') {
					stroke = true;
					continue;
				}
				int k1 = "0123456789abcdef".indexOf(c);
				if(k1 < 0 || k1 > 15)
					k1 = 15;
				buffer.put(fontDisplayLists + 1024 + k1 + (flag ? 16 : 0));
				if(buffer.remaining() == 0) {
					buffer.flip();
					GL11.glCallLists(buffer);
					buffer.clear();
				}
			}

			if(i1 < s.length()) {
				int j1 = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(i1));
				if(j1 >= 0) {
					int count = (stroke ? 1 : 0) + (underline ? 1 : 0);
					if(buffer.remaining() < count) {
						buffer.flip();
						GL11.glCallLists(buffer);
						buffer.clear();
					}
					if(stroke)
						buffer.put(fontDisplayLists + j1 + 32 + 512);
					if(j1 == 0) {
						if(underline)
							buffer.put(spaceUnderline);
						if(spaces-- > 0)
							buffer.put(spaceSymbol);
						else
							buffer.put(fontDisplayLists + 32);
					} else {
						if(underline)
							buffer.put(fontDisplayLists + j1 + 32 + 512 + 256);
						buffer.put(fontDisplayLists + j1 + 32 + (italic ? 256 : 0));
					}
				}
			}
			if(buffer.remaining() == 0) {
				buffer.flip();
				GL11.glCallLists(buffer);
				buffer.clear();
			}
		}

		buffer.flip();
		GL11.glCallLists(buffer);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawStringBuilder(StringBuilder s, int i, int j, int k) {
		renderStringBuilder(s, i, j, k, false);
	}

	public void drawStringBuilderWithShadow(StringBuilder s, int i, int j, int k) {
		renderStringBuilder(s, i + 1, j + 1, k + 1, true);
		renderStringBuilder(s, i, j, k, false);
	}

	public void renderStringBuilder(StringBuilder s, int i, int j, int k, boolean flag) {
		renderString(s, i, j, k, flag);
	}

	public int getStringWidth(CharSequence s) {
		if(s == null)
			return 0;
		float i = 0;
		for(int j = 0; j < s.length(); j++) {
			if(s.charAt(j) == '\247') {
				j++;
				if(s.length() > j && s.charAt(j) == 'r')
					j += 8;
				continue;
			}
			int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
			if(k >= 0)
				i += charWidth[k + 32];
		}
		return (int) Math.ceil(i);
	}

	public String trimStringToWidth(CharSequence s, int width) {
		StringBuilder sb = new StringBuilder(s.length());
		float i = 0;
		for(int j = 0; j < s.length(); j++) {
			if(s.charAt(j) == '\247') {
				sb.append(s.charAt(j));
				j++;
				sb.append(s.charAt(j));
				if(s.length() > j && s.charAt(j) == 'r')
					j += 8;
				for(int n = j - 8; n < j && n > s.length(); ++n)
					sb.append(s.charAt(n));
				continue;
			}
			int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
			if(k >= 0)
				if(i + charWidth[k + 32] > width) {
					return sb.toString();
				} else {
					sb.append(s.charAt(j));
					i += charWidth[k + 32];
				}
		}
		return sb.toString();
	}

	public int getStringPartWidth(CharSequence s, int lastPosition) {
		if(s == null)
			return 0;
		float i = 0;
		for(int j = 0; j < s.length() && j < lastPosition; j++) {
			if(s.charAt(j) == '\247') {
				j++;
				if(s.length() > j && s.charAt(j) == 'r')
					j += 8;
				continue;
			}
			int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
			if(k >= 0)
				i += charWidth[k + 32];
		}

		return (int) Math.ceil(i);
	}

	public int getClosestSymbolByPadding(StringBuilder s, int padding) {
		int symbol = 0;
		float currentWidth = 0;
		for(int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if(c == '\247') {
				i++;
				if(s.length() > i && s.charAt(i) == 'r') {
					i += 8;
					symbol += 8;
				}
				symbol += 2;
				continue;
			}
			int id = ChatAllowedCharacters.allowedCharacters.indexOf(c);
			if(id >= 0) {
				float width = charWidth[id + 32];
				if(currentWidth + width > padding)
					break;
				symbol++;
				currentWidth += width;
			}
		}
		return symbol;
	}

	public int getStringWidth(String s) {
		if(s == null)
			return 0;
		float i = 0;
		for(int j = 0; j < s.length(); j++) {
			if(s.charAt(j) == '\247') {
				j++;
				if(s.length() > j && s.charAt(j) == 'r')
					j += 8;
				continue;
			}
			int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
			if(k >= 0)
				i += charWidth[k + 32];
		}

		return (int) Math.ceil(i);
	}

	public float getFloatStringWidth(String s) {
		if(s == null)
			return 0;
		float i = 0;
		for(int j = 0; j < s.length(); j++) {
			if(s.charAt(j) == '\247') {
				j++;
				if(s.length() > j && s.charAt(j) == 'r')
					j += 8;
				continue;
			}
			int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
			if(k >= 0)
				i += charWidth[k + 32];
		}

		return i;
	}

	public void drawSplitString(String s, int i, int j, int k, int l) {
		func_40609_a(s, i, j, k, l, false);
	}

	public void func_40609_a(String s, int i, int j, int k, int l, boolean flag) {
		String as[] = s.split("\n");
		if(as.length > 1) {
			for(int i1 = 0; i1 < as.length; i1++) {
				drawSplitString(as[i1], i, j, k, l);
				j += splitStringWidth(as[i1], k);
			}
			return;
		}
		String as1[] = s.split(" ");
		int j1 = 0;
		String s1 = "";
		do {
			if(j1 >= as1.length)
				break;
			String s2;
			for(s2 = (new StringBuilder()).append(s1).append(as1[j1++]).append(" ").toString(); j1 < as1.length && getStringWidth((new StringBuilder()).append(s2).append(as1[j1]).toString()) < k; s2 = (new StringBuilder()).append(s2).append(as1[j1++]).append(" ").toString()) {
			}
			int k1;
			for(; getStringWidth(s2) > k; s2 = (new StringBuilder()).append(s1).append(s2.substring(k1)).toString()) {
				for(k1 = 0; getStringWidth(s2.substring(0, k1 + 1)) <= k; k1++) {
				}
				if(s2.substring(0, k1).trim().length() <= 0)
					continue;
				String s3 = s2.substring(0, k1);
				if(s3.lastIndexOf("\247") >= 0)
					s1 = (new StringBuilder()).append("\247").append(s3.charAt(s3.lastIndexOf("\247") + 1)).toString();
				renderString(s3, i, j, l, flag);
				j += FONT_HEIGHT;
			}

			if(getStringWidth(s2.trim()) > 0) {
				if(s2.lastIndexOf("\247") >= 0)
					s1 = (new StringBuilder()).append("\247").append(s2.charAt(s2.lastIndexOf("\247") + 1)).toString();
				renderString(s2, i, j, l, flag);
				j += FONT_HEIGHT;
			}
		} while(true);
	}

	public int splitStringWidth(String s, int i) {
		String as[] = s.split("\n");
		if(as.length > 1) {
			int j = 0;
			for(int k = 0; k < as.length; k++)
				j += splitStringWidth(as[k], i);
			return j;
		}
		String as1[] = s.split(" ");
		int l = 0;
		int i1 = 0;
		do {
			if(l >= as1.length)
				break;
			String s1;
			for(s1 = (new StringBuilder()).append(as1[l++]).append(" ").toString(); l < as1.length && getStringWidth((new StringBuilder()).append(s1).append(as1[l]).toString()) < i; s1 = (new StringBuilder()).append(s1).append(as1[l++]).append(" ").toString()) {
			}
			int j1;
			for(; getStringWidth(s1) > i; s1 = s1.substring(j1)) {
				for(j1 = 0; getStringWidth(s1.substring(0, j1 + 1)) <= i; j1++) {
				}
				if(s1.substring(0, j1).trim().length() > 0)
					i1 += FONT_HEIGHT;
			}
			if(s1.trim().length() > 0)
				i1 += FONT_HEIGHT;
		} while(true);
		if(i1 < FONT_HEIGHT)
			i1 += FONT_HEIGHT;
		return i1;
	}
}
