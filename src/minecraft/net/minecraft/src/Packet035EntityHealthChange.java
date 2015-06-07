package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.greencubes.entity.HealthChange;

public class Packet035EntityHealthChange extends Packet {
	
	public int entityId;
	public int changeValue;
	public HealthChange changeType;

	public Packet035EntityHealthChange() {
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		entityId = input.readInt();
		changeValue = input.readUnsignedShort();
		changeType = HealthChange.getById(input.readUnsignedByte());
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeShort(changeValue);
		output.writeByte(changeType.id);
	}

	@Override
	public void processPacket(NetHandler handler) {
		handler.handleEntityHealthChange(this);
	}

	@Override
	public int getPacketSize() {
		return 7;
	}

}
