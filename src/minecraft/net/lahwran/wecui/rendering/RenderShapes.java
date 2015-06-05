package net.lahwran.wecui.rendering;

import net.lahwran.wecui.obf.ObfHub;

public class RenderShapes {
	public static void box(LineInfo info, double x1, double y1, double z1, double x2, double y2, double z2) {
		info.prepareRender();

		ObfHub o = ObfHub.inst;

		o.draw_begin(2);
		info.prepareColor();
		o.addVertex(x1, y1, z1);
		o.addVertex(x2, y1, z1);
		o.addVertex(x2, y1, z2);
		o.addVertex(x1, y1, z2);
		o.draw();

		o.draw_begin(2);
		info.prepareColor();
		o.addVertex(x1, y2, z1);
		o.addVertex(x2, y2, z1);
		o.addVertex(x2, y2, z2);
		o.addVertex(x1, y2, z2);
		o.draw();

		o.draw_begin(1);
		info.prepareColor();

		o.addVertex(x1, y1, z1);
		o.addVertex(x1, y2, z1);

		o.addVertex(x2, y1, z1);
		o.addVertex(x2, y2, z1);

		o.addVertex(x2, y1, z2);
		o.addVertex(x2, y2, z2);

		o.addVertex(x1, y1, z2);
		o.addVertex(x1, y2, z2);

		o.draw();
	}

	public static void gridSurface(LineInfo info, double x1, double y1, double z1, double x2, double y2, double z2) {
		info.prepareRender();
		ObfHub o = ObfHub.inst;
		o.draw_begin(1);
		info.prepareColor();

		double offsetSize = 1.0D;

		double z = z2;
		double y = y1;
		int msize = 150;
		if(y2 - y / offsetSize < msize) {
			for(double yoff = 0.0D; yoff + y <= y2; yoff += offsetSize) {
				o.addVertex(x1, y + yoff, z);
				o.addVertex(x2, y + yoff, z);
			}
		}

		z = z1;
		if(y2 - y / offsetSize < msize) {
			for(double yoff = 0.0D; yoff + y <= y2; yoff += offsetSize) {
				o.addVertex(x1, y + yoff, z);
				o.addVertex(x2, y + yoff, z);
			}
		}

		double x = x1;
		if(y2 - y / offsetSize < msize) {
			for(double yoff = 0.0D; yoff + y <= y2; yoff += offsetSize) {
				o.addVertex(x, y + yoff, z1);
				o.addVertex(x, y + yoff, z2);
			}
		}

		x = x2;
		if(y2 - y / offsetSize < msize) {
			for(double yoff = 0.0D; yoff + y <= y2; yoff += offsetSize) {
				o.addVertex(x, y + yoff, z1);
				o.addVertex(x, y + yoff, z2);
			}
		}

		x = x1;
		z = z1;
		if(x2 - x / offsetSize < msize) {
			for(double xoff = 0.0D; xoff + x <= x2; xoff += offsetSize) {
				o.addVertex(x + xoff, y1, z);
				o.addVertex(x + xoff, y2, z);
			}
		}
		z = z2;
		if(x2 - x / offsetSize < msize) {
			for(double xoff = 0.0D; xoff + x <= x2; xoff += offsetSize) {
				o.addVertex(x + xoff, y1, z);
				o.addVertex(x + xoff, y2, z);
			}
		}
		y = y2;
		if(x2 - x / offsetSize < msize) {
			for(double xoff = 0.0D; xoff + x <= x2; xoff += offsetSize) {
				o.addVertex(x + xoff, y, z1);
				o.addVertex(x + xoff, y, z2);
			}
		}
		y = y1;
		if(x2 - x / offsetSize < msize) {
			for(double xoff = 0.0D; xoff + x <= x2; xoff += offsetSize) {
				o.addVertex(x + xoff, y, z1);
				o.addVertex(x + xoff, y, z2);
			}
		}

		z = z1;
		y = y1;
		if(z2 - z / offsetSize < msize) {
			for(double zoff = 0.0D; zoff + z <= z2; zoff += offsetSize) {
				o.addVertex(x1, y, z + zoff);
				o.addVertex(x2, y, z + zoff);
			}
		}
		y = y2;
		if(z2 - z / offsetSize < msize) {
			for(double zoff = 0.0D; zoff + z <= z2; zoff += offsetSize) {
				o.addVertex(x1, y, z + zoff);
				o.addVertex(x2, y, z + zoff);
			}
		}
		x = x2;
		if(z2 - z / offsetSize < msize) {
			for(double zoff = 0.0D; zoff + z <= z2; zoff += offsetSize) {
				o.addVertex(x, y1, z + zoff);
				o.addVertex(x, y2, z + zoff);
			}
		}
		x = x1;
		if(z2 - z / offsetSize < msize) {
			for(double zoff = 0.0D; zoff + z <= z2; zoff += offsetSize) {
				o.addVertex(x, y1, z + zoff);
				o.addVertex(x, y2, z + zoff);
			}
		}
		o.draw();
	}
}