package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet036EntityHealth extends Packet {
	
	public int entityId;
	public int currentHealth;
	public int maxHealth;

	public Packet036EntityHealth() {
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		entityId = input.readInt();
		maxHealth = input.readInt();
		currentHealth = input.readInt();
		
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeInt(maxHealth);
		output.writeInt(currentHealth);
	}

	@Override
	public void processPacket(NetHandler handler) {
		handler.handleEntityHealth(this);
	}

	@Override
	public int getPacketSize() {
		return 12;
	}

}
