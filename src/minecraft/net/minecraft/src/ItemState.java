package net.minecraft.src;

public class ItemState {

	public int blockX;
	public int blockY;
	public int blockZ;
	public int blockFace;
	public int state;

	public ItemState(int x, int y, int z, int face, int state) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.blockFace = face;
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blockFace;
		result = prime * result + blockX;
		result = prime * result + blockY;
		result = prime * result + blockZ;
		result = prime * result + state;
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
		ItemState other = (ItemState) obj;
		if(blockFace != other.blockFace)
			return false;
		if(blockX != other.blockX)
			return false;
		if(blockY != other.blockY)
			return false;
		if(blockZ != other.blockZ)
			return false;
		if(state != other.state)
			return false;
		return true;
	}
}
