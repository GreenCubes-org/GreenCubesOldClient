// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.client:
//            Minecraft

public class MinecraftApplet extends Applet {

	private Canvas mcCanvas;
	private Minecraft mc;
	private Thread mcThread;

	public MinecraftApplet() {
		mcThread = null;
	}

	@Override
	public void init() {
		mcCanvas = new CanvasMinecraftApplet(this);
		boolean flag = false;
		if(getParameter("fullscreen") != null) {
			flag = getParameter("fullscreen").equalsIgnoreCase("true");
		}
		mc = new MinecraftAppletImpl(this, this, mcCanvas, this, getWidth(), getHeight(), flag);
		mc.minecraftUri = getDocumentBase().getHost();
		if(getDocumentBase().getPort() > 0) {
			mc.minecraftUri += ":" + getDocumentBase().getPort();
		}
		if(getParameter("username") != null && getParameter("sessionid") != null) {
			mc.session = new Session(getParameter("username"), getParameter("sessionid"));
			System.out.println((new StringBuilder()).append("Setting user: ").append(mc.session.username).append(", ").append(mask(mc.session.sessionId)).toString());
			if(getParameter("mppass") != null) {
				mc.session.mpPassParameter = getParameter("mppass");
			}
		} else {
			mc.session = new Session("Player", "");
		}
		if(getParameter("server") != null && getParameter("port") != null) {
			mc.setServer(getParameter("server"), Integer.parseInt(getParameter("port")));
		}
		mc.hideQuitButton = true;
		if("true".equals(getParameter("stand-alone"))) {
			mc.hideQuitButton = false;
		}
		setLayout(new BorderLayout());
		add(mcCanvas, "Center");
		mcCanvas.setFocusable(true);
		validate();
		return;
	}
	
	private static String mask(String str) {
		return str.substring(0, str.length() / 2) + repeat("*", str.length() / 2);
	}

	private static String repeat(String str, int count) {
		StringBuilder sb = new StringBuilder(str.length() * count);
		for(int i = 0; i < count; ++i)
			sb.append(str);
		return sb.toString();
	}
	
	public void startMainThread() {
		if(mcThread != null) {
			return;
		} else {
			mcThread = new Thread(mc, "Minecraft main thread");
			mcThread.start();
			return;
		}
	}

	@Override
	public void start() {
		if(mc != null) {
			mc.isGamePaused = false;
		}
	}

	@Override
	public void stop() {
		if(mc != null) {
			mc.isGamePaused = true;
		}
	}

	@Override
	public void destroy() {
		shutdown();
	}

	public void shutdown() {
		if(mcThread == null) {
			return;
		}
		mc.shutdown();
		try {
			mcThread.join(10000L);
		} catch (InterruptedException interruptedexception) {
			try {
				mc.shutdownMinecraftApplet();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		mcThread = null;
	}

	public void clearApplet() {
		mcCanvas = null;
		mc = null;
		mcThread = null;
		try {
			removeAll();
			validate();
		} catch (Exception exception) {
		}
	}
}
