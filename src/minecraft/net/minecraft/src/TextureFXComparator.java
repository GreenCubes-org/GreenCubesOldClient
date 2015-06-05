package net.minecraft.src;

import java.util.Comparator;

public class TextureFXComparator implements Comparator<TextureFX> {

	@Override
	public int compare(TextureFX o1, TextureFX o2) {
		return o1.textureId < o2.textureId ? -1 : o1.textureId == o2.textureId ? 0 : 1;
	}
}
