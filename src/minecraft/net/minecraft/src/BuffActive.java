package net.minecraft.src;

public class BuffActive {

	private long lastTick = System.currentTimeMillis();

	public final int id;
	public final Buff buff;
	public final EntityLiving entity;
	public BuffEffect[] effects;
	public long timeLeft = -1;

	protected BuffActive(int id, Buff buff, EntityLiving entity, BuffEffect[] effects, long timeLeft) {
		this.buff = buff;
		this.entity = entity;
		this.id = id;
		this.effects = effects;
		this.timeLeft = timeLeft;
	}

	public void update() {
		long time = System.currentTimeMillis() - lastTick;
		lastTick = System.currentTimeMillis();
		if(timeLeft != -1) {
			timeLeft -= time;
			if(timeLeft <= 0) {
				timeLeft = 0;
				return;
			}
		}
	}
}
