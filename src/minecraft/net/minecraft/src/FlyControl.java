package net.minecraft.src;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class FlyControl {

	public GameSettings settings = Minecraft.theMinecraft.gameSettings;
	public EntityPlayer player;
	private boolean isFlying = false;
	private boolean isSpeed = false;
	private float walked;

	public FlyControl(EntityPlayer player) {
		this.player = player;
	}

	public void update1() {
		if(!isFlying())
			return;
		walked = player.distanceWalkedModified;
		if(Minecraft.theMinecraft.currentScreen == null) {
			if(Keyboard.isKeyDown(settings.keyBindSneak.keyCode))
				key(settings.keyBindSneak.keyCode);
			if(Keyboard.isKeyDown(settings.keyBindJump.keyCode))
				key(settings.keyBindJump.keyCode);
		}
	}

	public void update2() {
		if(!isFlying())
			return;
		player.distanceWalkedModified = walked;
		key(-1);
	}

	private void key(int i) {
		player.onGround = true;
		if(i == settings.keyBindSneak.keyCode) {
			player.jump();
			player.motionY = -player.motionY;
			return;
		}
		if(i == settings.keyBindJump.keyCode) {
			player.jump();
			return;
		}
		player.motionY = 0.0D;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public boolean isSpeed() {
		return canFly() && settings.keySpeed.pressed;
	}

	public void keyFly() {
		if(!canFly()) {
			isFlying = false;
			return;
		}
		player.fallDistance = 0.0F;
		isFlying = !isFlying;
	}

	public float getSpeedMultipler() {
		return isSpeed() ? settings.speedSpeed : isFlying() ? settings.flySpeed : 1.0F;
	}

	private boolean canFly() {
		return Minecraft.theMinecraft.getSendQueue() == null || Minecraft.theMinecraft.getSendQueue().canFly;
	}

}
