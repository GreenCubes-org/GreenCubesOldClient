/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
