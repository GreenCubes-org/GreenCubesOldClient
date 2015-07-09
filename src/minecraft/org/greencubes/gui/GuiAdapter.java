package org.greencubes.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiAdapter {
	
	protected static final FancyGUI gui = FancyGUI.getInstance(); // Short-cut
	
	protected int screenWidth;
	protected int screenHeight;
	protected int guiScale;
	protected int mouseX;
	protected int mouseY;
	
	protected List<Tooltip> tooltips = new ArrayList<Tooltip>();
	
	public GuiAdapter() {
	}
	
	public void startFrame(int screenWidth, int screenHeight, int mouseX, int mouseY, float framePart, int guiScale) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.guiScale = guiScale;
		this.tooltips.clear();
	}
	
	public void addTooltip(Tooltip tt) {
		this.tooltips.add(tt);
	}
	
	public void endFrame() {
		for(int i = 0; i < tooltips.size(); ++i)
			tooltips.get(i).render(Minecraft.theMinecraft.fontRenderer, mouseX, mouseY, this.screenWidth, this.screenHeight);
	}
	
}
