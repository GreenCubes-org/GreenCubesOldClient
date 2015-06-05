package net.minecraft.src;

/**
 * Этот класс полностью переписан для
 * GreenCubes
 * @author Feyola
 *
 */
public class ChunkBlockMap {

	private static int blockMap[];

	public ChunkBlockMap() {
	}

	public static void func_26002_a(byte mainData[], NibbleArray addData) {
		for(int i = 0; i < mainData.length; i++) {
			int id = blockMap[(addData.getNibble(i) << 8) | (mainData[i] & 0xFF)];
			mainData[i] = (byte) (id & 0xFF);
			addData.setNibble(i, (id >> 8) & 0xF);
		}
	}

	public static int clearId(int id) {
		return blockMap[id];
	}

	static {
		blockMap = new int[Block.blocksList.length];
		try {
			for(int i = 0; i < blockMap.length; i++) {
				int id = i;
				if(i != 0 && Block.blocksList[i] == null) {
					id = 0;
				}
				blockMap[i] = id;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
