package net.minecraft.src;

public interface INetworkManager {

	public void processReadPackets();

	public void wakeThreads();

	public void addToSendQueue(Packet packet);

	public void networkShutdown(String string, Object[] objects);

	public void shutdown();

}
