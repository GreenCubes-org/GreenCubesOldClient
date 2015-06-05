// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            NetworkReaderThread, NetworkWriterThread, Packet, NetHandler, 
//            NetworkMasterThread, ThreadCloseConnection

public class NetworkManager implements INetworkManager {

	public static final Object threadSyncObject = new Object();
	public static int numReadThreads;
	public static int numWriteThreads;
	private Object sendQueueLock;
	public Socket networkSocket;
	private final SocketAddress remoteSocketAddress;
	private DataInputStream socketInputStream;
	private DataOutputStream socketOutputStream;
	private boolean isRunning;
	private List<Packet> readPackets;
	private List dataPackets;
	private List chunkDataPackets;
	private NetClientHandler netHandler;
	private boolean isServerTerminating;
	private Thread writeThread;
	private Thread readThread;
	private boolean isTerminating;
	private String terminationReason;
	private Object field_20101_t[];
	private int timeSinceLastRead;
	private int sendQueueByteLength;
	public static long field_28145_d[] = new long[256];
	public static long field_28144_e[] = new long[256];
	public int chunkDataSendCounter;
	private int field_20100_w;
	private int lastPacketRecieved = 0;
	private int lastPacketSend = 0;
	private String addErrorInfo;

	private ReplayWriter replay;

	private int packetNumber = 0;

	public NetworkManager(Socket socket, String s, NetClientHandler nethandler) throws IOException {
		sendQueueLock = new Object();
		isRunning = true;
		readPackets = Collections.synchronizedList(new ArrayList());
		dataPackets = Collections.synchronizedList(new ArrayList());
		chunkDataPackets = Collections.synchronizedList(new ArrayList());
		isServerTerminating = false;
		isTerminating = false;
		terminationReason = "";
		timeSinceLastRead = 0;
		sendQueueByteLength = 0;
		chunkDataSendCounter = 0;
		field_20100_w = 50;
		networkSocket = socket;
		remoteSocketAddress = socket.getRemoteSocketAddress();
		netHandler = nethandler;
		try {
			socket.setSoTimeout(30000);
			socket.setTrafficClass(24);
		} catch (SocketException socketexception) {
			System.err.println(socketexception.getMessage());
		}
		socketInputStream = new DataInputStream(socket.getInputStream());
		socketOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
		readThread = new NetworkReaderThread(this, (new StringBuilder()).append(s).append(" read thread").toString());
		writeThread = new NetworkWriterThread(this, (new StringBuilder()).append(s).append(" write thread").toString());
		readThread.start();
		writeThread.start();
	}

	@Override
	public void addToSendQueue(Packet packet) {
		if(isServerTerminating) {
			return;
		}
		if(replay != null)
			replay.processPacketClient(packet);
		synchronized(sendQueueLock) {
			sendQueueByteLength += packet.getPacketSize() + 1;
			if(packet.isChunkDataPacket) {
				chunkDataPackets.add(packet);
			} else {
				dataPackets.add(packet);
			}
		}
	}

	private boolean sendPacket() {
		boolean flag = false;
		try {
			if(!dataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet) dataPackets.get(0)).creationTimeMillis >= chunkDataSendCounter)) {
				Packet packet;
				synchronized(sendQueueLock) {
					packet = (Packet) dataPackets.remove(0);
					sendQueueByteLength -= packet.getPacketSize() + 1;
				}
				// GreenCubes start
				Packet.writePacket(packet, socketOutputStream, packetNumber++);
				int id = packet.getPacketId();
				if(id != 0)
					lastPacketSend = id;
				// GreenCubes end
				field_28144_e[packet.getPacketId()] += packet.getPacketSize() + 1;
				flag = true;
			}
			if(field_20100_w-- <= 0 && !chunkDataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet) chunkDataPackets.get(0)).creationTimeMillis >= chunkDataSendCounter)) {
				Packet packet1;
				synchronized(sendQueueLock) {
					packet1 = (Packet) chunkDataPackets.remove(0);
					sendQueueByteLength -= packet1.getPacketSize() + 1;
				}
				// GreenCubes start
				Packet.writePacket(packet1, socketOutputStream, packetNumber++);
				// GreenCubes end
				field_28144_e[packet1.getPacketId()] += packet1.getPacketSize() + 1;
				field_20100_w = 0;
				flag = true;
			}
		} catch (Exception exception) {
			if(!isTerminating) {
				onNetworkError(exception);
			}
			return false;
		}
		return flag;
	}

	@Override
	public void wakeThreads() {
		readThread.interrupt();
		writeThread.interrupt();
	}

	private boolean readPacket() {
		boolean flag = false;
		try {
			// GreenCubes start
			Packet packet = Packet.readPacket(socketInputStream, netHandler.isServerHandler());
			// GreenCubes end
			if(packet != null) {
				int id = packet.getPacketId();
				if(id != 0)
					lastPacketRecieved = id;
				field_28145_d[packet.getPacketId()] += packet.getPacketSize() + 1;
				readPackets.add(packet);
				flag = true;
			} else {
				networkShutdown("disconnect.endOfStream", new Object[0]);
			}
		} catch (Exception exception) {
			if(!isTerminating) {
				onNetworkError(exception);
			}
			return false;
		}
		return flag;
	}

	private void onNetworkError(Exception exception) {
		System.err.println("Last rcvd: " + lastPacketRecieved + ", last sent: " + lastPacketSend);
		exception.printStackTrace();
		networkShutdown("disconnect.genericReason", new Object[]{(new StringBuilder()).append("Internal exception: ").append(exception.toString()).toString()});
	}

	@Override
	public void networkShutdown(String s, Object aobj[]) {
		if(!isRunning) {
			return;
		}
		isTerminating = true;
		terminationReason = s;
		field_20101_t = aobj;
		(new NetworkMasterThread(this)).start();
		isRunning = false;
		try {
			socketInputStream.close();
			socketInputStream = null;
		} catch (Throwable throwable) {
		}
		try {
			socketOutputStream.close();
			socketOutputStream = null;
		} catch (Throwable throwable1) {
		}
		try {
			networkSocket.close();
			networkSocket = null;
		} catch (Throwable throwable2) {
		}
		if(replay != null)
			try {
				replay.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void processReadPackets() {
		if(replay != null)
			replay.startNewCycle();
		if(sendQueueByteLength > 0x100000) {
			networkShutdown("disconnect.overflow", new Object[0]);
		}
		if(readPackets.isEmpty()) {
			if(timeSinceLastRead++ == 1200) {
				addErrorInfo = "Last packets: " + lastPacketRecieved + "/" + lastPacketSend + ", status: " + ((NetClientHandler) netHandler).connectionStatus;
				networkShutdown("disconnect.timeout", new Object[0]);
			}
		} else {
			timeSinceLastRead = 0;
		}
		Packet packet;
		int i = 1000;
		while(!readPackets.isEmpty() && i-- >= 0) {
			packet = readPackets.remove(0);
			packet.processPacket(netHandler);
			if(replay != null)
				packet.processPacket(replay);
		}

		wakeThreads();
		if(isTerminating && readPackets.isEmpty()) {
			netHandler.handleErrorMessage(terminationReason, field_20101_t, addErrorInfo);
		}
	}

	@Override
	public void shutdown() {
		wakeThreads();
		isServerTerminating = true;
		readThread.interrupt();
		(new ThreadCloseConnection(this)).start();
	}

	static boolean isRunning(NetworkManager networkmanager) {
		return networkmanager.isRunning;
	}

	static boolean isServerTerminating(NetworkManager networkmanager) {
		return networkmanager.isServerTerminating;
	}

	static boolean readNetworkPacket(NetworkManager networkmanager) {
		return networkmanager.readPacket();
	}

	static boolean sendNetworkPacket(NetworkManager networkmanager) {
		return networkmanager.sendPacket();
	}

	static DataOutputStream getOutputStream(NetworkManager networkmanager) {
		return networkmanager.socketOutputStream;
	}

	static boolean getIsTerminating(NetworkManager networkmanager) {
		return networkmanager.isTerminating;
	}

	static void func_30005_a(NetworkManager networkmanager, Exception exception) {
		networkmanager.onNetworkError(exception);
	}

	static Thread getReadThread(NetworkManager networkmanager) {
		return networkmanager.readThread;
	}

	static Thread getWriteThread(NetworkManager networkmanager) {
		return networkmanager.writeThread;
	}

}
