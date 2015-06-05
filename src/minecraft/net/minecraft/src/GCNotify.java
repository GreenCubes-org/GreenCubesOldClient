package net.minecraft.src;

public class GCNotify {

	public String text;
	public ItemStack item;
	public float animation = 0.0F;
	public String[] formattedText;

	public GCNotify() {

	}

	public GCNotify(Packet207Notify packet) {
		this.text = packet.text;
		this.item = packet.item;
	}
}
