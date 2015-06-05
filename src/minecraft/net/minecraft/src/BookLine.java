package net.minecraft.src;

import net.minecraft.client.Minecraft;

/**
 * GreenCubes
 * Books mod
 * @author Rena
 *
 */
public class BookLine {

	public boolean isNewLine;
	public StringBuilder text;

	public BookLine() {
		text = new StringBuilder(50);
	}

	public BookLine(Minecraft mc, boolean newLine, String line) {
		isNewLine = newLine;
		text = new StringBuilder(line);
	}

	public BookLine(Minecraft mc, boolean newLine, StringBuilder line) {
		isNewLine = newLine;
		text = line;
	}
}
