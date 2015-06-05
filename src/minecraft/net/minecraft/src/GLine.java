package net.minecraft.src;

public class GLine {

	public final String text;
	public boolean canWide = false;
	public int age = 0;

	public boolean isUsed = false;
	public int list = -1;

	public GLine(String text) {
		this.text = text;
	}
}
