// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StringTranslate, GuiSmallButton, TexturePackList, 
//            GuiTexturePackSlot, GuiButton, RenderEngine, FontRenderer

public class GuiTexturePacks extends GuiScreen {

	protected GuiScreen guiScreen;
	private int field_6454_o;
	private String fileLocation;
	private GuiTexturePackSlot guiTexturePackSlot;

	public GuiTexturePacks(GuiScreen guiscreen) {
		field_6454_o = -1;
		fileLocation = "";
		guiScreen = guiscreen;
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(new GuiSmallButton(5, width / 2 - 154, height - 48, stringtranslate.translateKey("texturePack.openFolder")));
		controlList.add(new GuiSmallButton(6, width / 2 + 4, height - 48, stringtranslate.translateKey("gui.done")));
		mc.texturePackList.updateAvaliableTexturePacks();
		fileLocation = (new File(Minecraft.getMinecraftDir(), "texturepacks")).getAbsolutePath();
		guiTexturePackSlot = new GuiTexturePackSlot(this);
		guiTexturePackSlot.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		if(guibutton.id == 5) {
			Sys.openURL((new StringBuilder()).append("file://").append(fileLocation).toString());
		} else if(guibutton.id == 6) {
			mc.renderEngine.refreshTextures();
			mc.displayGuiScreen(guiScreen);
		} else {
			guiTexturePackSlot.actionPerformed(guibutton);
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
	}

	@Override
	protected void mouseMovedOrUp(int i, int j, int k) {
		super.mouseMovedOrUp(i, j, k);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		guiTexturePackSlot.drawScreen(i, j, f);
		if(field_6454_o <= 0) {
			mc.texturePackList.updateAvaliableTexturePacks();
			field_6454_o += 20;
		}
		StringTranslate stringtranslate = StringTranslate.getInstance();
		// GreenCubes start
		fontRenderer.drawString("\2472Green", 8, height - 16, (0x00) << 16 | (0x77) << 8 | (0x04));
		fontRenderer.drawString("Cubes", 31, height - 16, (0xee) << 16 | (0xee) << 8 | (0xee));
		// GreenCubes end
		drawCenteredString(fontRenderer, stringtranslate.translateKey("texturePack.title"), width / 2, 16, 0xffffff);
		drawCenteredString(fontRenderer, stringtranslate.translateKey("texturePack.folderInfo"), width / 2 - 77, height - 26, 0x808080);
		super.drawScreen(i, j, f);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		field_6454_o--;
	}

	static Minecraft func_22124_a(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22126_b(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22119_c(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22122_d(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22117_e(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_35307_f(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_35308_g(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22118_f(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22116_g(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22121_h(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static Minecraft func_22123_i(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.mc;
	}

	static FontRenderer func_22127_j(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.fontRenderer;
	}

	static FontRenderer func_22120_k(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.fontRenderer;
	}

	static FontRenderer func_22125_l(GuiTexturePacks guitexturepacks) {
		return guitexturepacks.fontRenderer;
	}
}
