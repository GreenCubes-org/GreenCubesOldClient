package net.lahwran.wecui.rendering;

public class HighlightPosition {
	public int x;
	public int y;
	public int z;
	public boolean active = false;
	public final LineInfo drawnormal;
	public final LineInfo drawhidden;

	public HighlightPosition(LineInfo color, float alphanormal, float alphahidden) {
		this.drawhidden = new LineInfo(color);
		this.drawhidden.alpha = alphahidden;
		this.drawhidden.depthfunc = 518;
		this.drawnormal = new LineInfo(color);
		this.drawnormal.alpha = alphanormal;
		this.drawnormal.depthfunc = 513;
	}

	public void render() {
		if(!this.active)
			return;
		double off = 0.02999999932944775D;
		RenderShapes.box(this.drawhidden, this.x - off, this.y - off, this.z - off, this.x + 1 + off, this.y + 1 + off, this.z + 1 + off);
		RenderShapes.box(this.drawnormal, this.x - off, this.y - off, this.z - off, this.x + 1 + off, this.y + 1 + off, this.z + 1 + off);
	}
}