package net.lahwran.wecui;

import net.lahwran.wecui.rendering.HighlightPosition;
import net.lahwran.wecui.rendering.LineInfo;
import net.lahwran.wecui.rendering.RenderShapes;

public class CuboidRegion extends CuiRegion {
	private final HighlightPosition[] pts;
	public static LineInfo firstpoint = new LineInfo(3.0F, 0.2F, 0.8F, 0.2F);
	public static LineInfo secondpoint = new LineInfo(3.0F, 0.2F, 0.2F, 0.8F);
	public static LineInfo gridnormal = new LineInfo(3.0F, 0.8F, 0.2F, 0.2F, 0.2F, 513);
	public static LineInfo gridhidden = new LineInfo(2.0F, 0.4F, 0.1F, 0.1F, 0.1F, 518);
	public static LineInfo boxnormal = new LineInfo(3.0F, 0.8F, 0.2F, 0.2F, 1.0F, 513);
	public static LineInfo boxhidden = new LineInfo(2.0F, 0.4F, 0.1F, 0.1F, 0.2F, 518);
	public double x1;
	public double y1;
	public double z1;
	public double x2;
	public double y2;
	public double z2;

	public CuboidRegion() {
		this.pts = new HighlightPosition[2];
		this.pts[0] = new HighlightPosition(firstpoint, 0.8F, 0.2F);
		this.pts[1] = new HighlightPosition(secondpoint, 0.8F, 0.2F);
	}

	@Override
	public void render() {
		this.pts[0].render();
		this.pts[1].render();
		if((this.pts[0].active) && (this.pts[1].active)) {
			RenderShapes.gridSurface(gridnormal, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
			RenderShapes.gridSurface(gridhidden, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
			RenderShapes.box(boxhidden, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
			RenderShapes.box(boxnormal, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
		}
	}

	private void calcBounds() {
		double off = 0.02D;
		double off1 = 1.0D + off;
		this.x1 = 1.7976931348623157E+308D;
		this.y1 = 1.7976931348623157E+308D;
		this.z1 = 1.7976931348623157E+308D;
		this.x2 = -1.797693134862316E+307D;
		this.y2 = -1.797693134862316E+307D;
		this.z2 = -1.797693134862316E+307D;

		for(int index = 0; index < this.pts.length; index++) {
			HighlightPosition pos = this.pts[index];
			if(!pos.active)
				continue;
			if(pos.x + off1 > this.x2) {
				this.x2 = (pos.x + off1);
			}
			if(pos.x - off < this.x1) {
				this.x1 = (pos.x - off);
			}
			if(pos.y + off1 > this.y2) {
				this.y2 = (pos.y + off1);
			}
			if(pos.y - off < this.y1) {
				this.y1 = (pos.y - off);
			}
			if(pos.z + off1 > this.z2) {
				this.z2 = (pos.z + off1);
			}
			if(pos.z - off < this.z1)
				this.z1 = (pos.z - off);
		}
	}

	@Override
	public void setPoint(int id, int x, int y, int z, int regionSize) {
		if(id < this.pts.length) {
			this.pts[id].x = x;
			this.pts[id].y = y;
			this.pts[id].z = z;
			this.pts[id].active = true;
			calcBounds();
		}
	}
}