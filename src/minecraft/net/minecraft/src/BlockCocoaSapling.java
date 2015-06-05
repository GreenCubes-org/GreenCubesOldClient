package net.minecraft.src;

/**
 * GreenCubes
 * @author Feyola
 *
 */

public class BlockCocoaSapling extends BlockSapling {

	protected BlockCocoaSapling(int int1, int int2) {
		super(int1, int2);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
}