package org.greencubes.gui;

import java.awt.image.BufferedImage;

public class ImageUtil {
	
	public static BufferedImage flipVertical(BufferedImage source) {
		BufferedImage out = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < source.getWidth(); ++x) {
			for(int y = 0; y < source.getHeight(); ++y) {
				out.setRGB(x, y, source.getRGB(x, source.getHeight() - y - 1));
			}
		}
		return out;
	}
	
}
