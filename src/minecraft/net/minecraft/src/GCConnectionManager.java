package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.util.EntityUtils;

public class GCConnectionManager extends Thread {

	private static GCConnectionManager instance = null;

	private final HttpClient clientInstance;
	private final ClientConnectionManager mngr;
	private ArrayList<ThreadDownloadImage> queue = new ArrayList<ThreadDownloadImage>(250);

	private GCConnectionManager(HttpClient c) {
		super("GC Connection Manager");
		clientInstance = c;
		mngr = c.getConnectionManager();
		this.setDaemon(true);
		this.start();
	}

	private HttpClient getClientInstance() {
		return clientInstance;
	}

	private void addReal(ThreadDownloadImage td) {
		try {
			synchronized(queue) {
				if(!queue.contains(td))
					queue.add(td);
			}
		} catch (IllegalStateException e) {

		}
	}

	private static GCConnectionManager getInstance() {
		if(instance != null)
			return instance;
		CacheConfig cache = new CacheConfig();
		cache.setMaxCacheEntries(400);
		cache.setMaxObjectSize(1024 * 512);

		HttpClient c = new CachingHttpClient(new DefaultHttpClient(), cache);
		instance = new GCConnectionManager(c);
		return instance;
	}

	public static void add(ThreadDownloadImage td) {
		getInstance().addReal(td);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(queue.size() > 0) {
				ThreadDownloadImage td;
				synchronized(queue) {
					td = queue.remove(0);
				}
				if(td != null) {
					HttpGet httpget = new HttpGet(td.location);
					InputStream is = null;
					try {
						HttpResponse r = clientInstance.execute(httpget);
						is = new ByteArrayInputStream(EntityUtils.toByteArray(r.getEntity()));
						if(td.buffer == null) {
							td.imageData.image = ImageIO.read(is);
						} else {
							td.imageData.image = td.buffer.parseUserSkin(ImageIO.read(is));
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is.close();
						} catch (Exception e) {
						}
						;
					}
				}
			}
			clientInstance.getConnectionManager().closeExpiredConnections();
			clientInstance.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
		}
	}
}
