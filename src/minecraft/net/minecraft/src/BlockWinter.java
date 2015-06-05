package net.minecraft.src;

public class BlockWinter extends Block implements IBlockMadeOf {

	private final Block original;

	public BlockWinter(int i, int j, Block original) {
		super(i, j, original.blockMaterial);
		setHardness(original.blockHardness);
		setResistance(original.blockResistance);
		setStepSound(original.stepSound);
		this.blockGlassType = original.blockGlassType;
		this.original = original;
	}

	@Override
	public Block getBlockMadeOf() {
		return original;
	}

}
