package net.minecraft.src;

public class BuffEffect {

	public final BuffEffectType type;
	public final float multipler;

	public BuffEffect(BuffEffectType p, float multipler) {
		this.type = p;
		this.multipler = multipler;
	}

	@Override
	public String toString() {
		return "BuffEffect{" + type.toString() + "," + multipler + "}";
	}
}
