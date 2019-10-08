/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
