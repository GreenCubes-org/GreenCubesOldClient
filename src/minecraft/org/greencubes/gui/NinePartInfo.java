package org.greencubes.gui;

public class NinePartInfo {
	
	public int top_leftWidth, top_centerWidth, top_rightWidth, top_height;
	public int center_leftWidth, center_centerWidth, center_rightWidth, center_height;
	public int bottom_leftWidth, bottom_centerWidth, bottom_rightWidth, bottom_height;
	
	public boolean repeat = false;
	
	public NinePartInfo(int tlW, int tcW, int trW, int tH, int clW, int ccW, int crW, int cH, int blW, int bcW, int brW, int bH) {
		this.top_leftWidth = tlW;
		this.top_centerWidth = tcW;
		this.top_rightWidth = trW;
		this.top_height = tH;
		this.center_leftWidth = clW;
		this.center_centerWidth = ccW;
		this.center_rightWidth = crW;
		this.center_height = cH;
		this.bottom_rightWidth = brW;
		this.bottom_leftWidth = blW;
		this.bottom_centerWidth = bcW;
		this.bottom_height = bH;
	}
	
	public NinePartInfo repeat() {
		this.repeat = true;
		return this;
	}	
}
