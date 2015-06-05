package net.minecraft.src;

class ScheduledSound {
	String sound;
	float x;
	float y;
	float z;
	float volume;
	float pitch;
	int delayTicks;

	public ScheduledSound(String sound, float x, float y, float z, float volume, float pitch, int delayTicks) {
		this.sound = sound;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
		this.delayTicks = delayTicks;
	}
}
