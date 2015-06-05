package net.lahwran.wecui.obf;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Tessellator;

public class ObfHub {
	public static ObfHub inst;
	private Minecraft minecraft;
	private Tessellator tessellator = Tessellator.instance;

	public ObfHub(Minecraft minecraft) {
		this.minecraft = minecraft;
		inst = this;
	}

	public boolean isMultiplayerWorld() {
		return this.minecraft.isMultiplayerWorld();
	}

	public void sendChat(String chat) {
		this.minecraft.thePlayer.sendChatMessage(chat);
	}

	public void draw_begin(int type) {
		this.tessellator.startDrawing(type);
	}

	public double getPlayerX(float renderTick) {
		EntityPlayerSP plyr = this.minecraft.thePlayer;
		return plyr.lastTickPosX + (plyr.posX - plyr.lastTickPosX) * renderTick;
	}

	public double getPlayerY(float renderTick) {
		EntityPlayerSP plyr = this.minecraft.thePlayer;
		return plyr.lastTickPosY + (plyr.posY - plyr.lastTickPosY) * renderTick;
	}

	public double getPlayerZ(float renderTick) {
		EntityPlayerSP plyr = this.minecraft.thePlayer;
		return plyr.lastTickPosZ + (plyr.posZ - plyr.lastTickPosZ) * renderTick;
	}

	public void addVertex(double x, double y, double z) {
		this.tessellator.addVertex(x, y, z);
	}

	public void draw() {
		this.tessellator.draw();
	}

	public static File getAppDir(String app) {
		return Minecraft.getAppDir(app);
	}
}