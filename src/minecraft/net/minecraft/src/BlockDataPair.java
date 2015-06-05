package net.minecraft.src;

public class BlockDataPair {

	private final int blockId;
	private final int blockData;

	public BlockDataPair(int id) {
		this(id, -1);
	}

	public BlockDataPair(int id, int data) {
		this.blockId = id;
		this.blockData = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blockData;
		result = prime * result + blockId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		BlockDataPair other = (BlockDataPair) obj;
		if(blockData != other.blockData)
			return false;
		if(blockId != other.blockId)
			return false;
		return true;
	}

	public boolean matches(int id, int data) {
		return blockId == id && (blockData < 0 || blockData == data);
	}

}
