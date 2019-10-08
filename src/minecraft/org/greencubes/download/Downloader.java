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
package org.greencubes.download;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.greencubes.util.OperatingSystem;
import org.greencubes.util.Util;
import org.greencubes.util.io.GByteArrayOutputStream;

public class Downloader {
	
	public static boolean printUrls = false;
	
	public List<Exception> errors = new ArrayList<Exception>();
	
	private List<String> serversList = new ArrayList<String>();
	private int currentServer = 0;
	private int serverListRepeated = 0;
	
	public volatile int bytesToDownload = 0;
	public volatile int bytesDownloaded = 0;
	public volatile IOException lastError = null;
	public volatile boolean waitingForRepeat = false;
	public boolean ignoreHTTPErrors = false;
	private final CloseableHttpClient httpClient;
	
	private List<String> log = new ArrayList<String>();
	
	public Downloader(String server) {
		this();
		addServer(server);
	}
	
	public Downloader() {
		httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(500).setConnectTimeout(500).build()).build();
	}
	
	public void close() {
		try {
			httpClient.close();
		} catch(IOException e) {
			// How can it be?
			e.printStackTrace();
		}
	}
	
	public void clearServers() {
		serversList.clear();
	}
	
	public String[] getServers() {
		return serversList.toArray(new String[0]);
	}
	
	public String[] getLog() {
		return log.toArray(new String[0]);
	}
	
	public void addServer(String serverUrl) {
		if(serversList.contains(serverUrl))
			return;
		serversList.add(serverUrl);
		serverListRepeated = 0;
		currentServer = 0;
	}
	
	private void nextServer() {
		if(currentServer == serversList.size() - 1) {
			serverListRepeated++;
			currentServer = 0;
		} else
			currentServer++;
	}
	
	public boolean isCrashed() {
		return serverListRepeated > 1;
	}
	
	public int getFileSize(String fileName) throws IOException {
		if(serversList.size() == 0)
			throw new IOException("No download servers specified!");
		if(printUrls)
			System.out.println("File size " + fileName);
		serverListRepeated = 0;
		currentServer = 0;
		bytesToDownload = 0;
		bytesDownloaded = 0;
		lastError = null;
		while(!isCrashed()) {
			URL u = new URL(serversList.get(currentServer) + fileName);
			waitingForRepeat = false;
			HttpHead headRequest;
			try {
				headRequest = new HttpHead(u.toURI());
			} catch(URISyntaxException e2) {
				throw new IOException("URL Syntax exception", e2);
			}
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(headRequest);
				int code = response.getStatusLine().getStatusCode();
				if(code >= 400 && !ignoreHTTPErrors) {
					if(printUrls)
						System.out.println("Http no 200 answer: " + code + " " + response.getStatusLine().getReasonPhrase());
					EntityUtils.consume(response.getEntity());
					throw new HTTPResponseError(code + " " + response.getStatusLine().getReasonPhrase() + " for " + u.getPath());
				}
				EntityUtils.consume(response.getEntity());
				return Integer.parseInt(response.getFirstHeader("Content-Length").getValue());
			} catch(IOException e) {
				if(lastError == null)
					lastError = e;
				errors.add(e);
				nextServer();
				waitingForRepeat = true;
				try {
					Thread.sleep(2000L);
				} catch(InterruptedException e1) {
				}
				continue;
			} finally {
				Util.close(response);
			}
		}
		if(lastError != null)
			throw lastError;
		return -1;
	}
	
	public void downloadFile(File output, String fileName) throws IOException {
		if(serversList.size() == 0)
			throw new IOException("No download servers specified!");
		if(!output.exists()) {
			if(!output.getParentFile().exists() && !output.getParentFile().mkdirs())
				throw new IOException("Unable to create dir for " + output.getAbsolutePath());
			if(!output.createNewFile())
				throw new IOException("Unable to create new file for " + output.getAbsolutePath());
		}
		if(printUrls)
			System.out.println("Downloading " + fileName);
		serverListRepeated = 0;
		currentServer = 0;
		bytesToDownload = 0;
		lastError = null;
		while(!isCrashed()) {
			URL u = new URL(serversList.get(currentServer) + fileName);
			bytesDownloaded = 0;
			waitingForRepeat = false;
			HttpGet getRequest;
			try {
				getRequest = new HttpGet(u.toURI());
			} catch(URISyntaxException e2) {
				throw new IOException("URL Syntax exception", e2);
			}
			InputStream is = null;
			FileOutputStream os = null;
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(getRequest);
				HttpEntity entity = response.getEntity();
				int code = response.getStatusLine().getStatusCode();
				if(code >= 400 && !ignoreHTTPErrors) {
					if(printUrls)
						System.out.println("Http no 200 answer: " + code + " " + response.getStatusLine().getReasonPhrase());
					EntityUtils.consume(response.getEntity());
					throw new HTTPResponseError(code + " " + response.getStatusLine().getReasonPhrase() + " for " + u.getPath());
				}
				is = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(is);
				bytesToDownload = (int) entity.getContentLength();
				os = new FileOutputStream(output);
				byte[] buffer = new byte[1024];
				int bufferSize;
				while((bufferSize = bis.read(buffer, 0, buffer.length)) != -1) {
					os.write(buffer, 0, bufferSize);
					bytesDownloaded += bufferSize;
				}
				EntityUtils.consume(entity);
				return;
			} catch(IOException e) {
				if(printUrls) {
					System.out.println("Exception when using server " + serversList.get(currentServer) + ":");
					e.printStackTrace();
				}
				if(lastError == null)
					lastError = e;
				errors.add(e);
				nextServer();
				waitingForRepeat = true;
				try {
					Thread.sleep(2000L);
				} catch(InterruptedException e1) {
				}
				continue;
			} finally {
				Util.close(is, os, response);
			}
		}
		if(lastError != null)
			throw lastError;
	}
	
	public InputStream getInputStream(String request) throws IOException {
		if(serversList.size() == 0)
			throw new IOException("No download servers specified!");
		if(printUrls)
			System.out.println("Reading " + request);
		serverListRepeated = 0;
		currentServer = 0;
		bytesToDownload = 0;
		bytesDownloaded = 0;
		lastError = null;
		while(!isCrashed()) {
			URL u = new URL(serversList.get(currentServer) + request);
			waitingForRepeat = false;
			HttpGet getRequest;
			try {
				getRequest = new HttpGet(u.toURI());
			} catch(URISyntaxException e2) {
				throw new IOException("URL Syntax exception", e2);
			}
			InputStream is = null;
			CloseableHttpResponse response = null;
			GByteArrayOutputStream baos = null;
			try {
				response = httpClient.execute(getRequest);
				HttpEntity entity = response.getEntity();
				int code = response.getStatusLine().getStatusCode();
				if(code >= 400 && !ignoreHTTPErrors) {
					if(printUrls)
						System.out.println("Http no 200 answer: " + code + " " + response.getStatusLine().getReasonPhrase());
					EntityUtils.consume(response.getEntity());
					throw new HTTPResponseError(code + " " + response.getStatusLine().getReasonPhrase() + " for " + u.getPath());
				}
				is = entity.getContent();
				baos = new GByteArrayOutputStream((int) entity.getContentLength());
				byte[] buff = new byte[1024];
				int read;
				while((read = is.read(buff)) != -1) {
					if(read > 0)
						baos.write(buff, 0, read);
				}
				return new ByteArrayInputStream(baos.getArray(), 0, baos.size());
			} catch(IOException e) {
				if(lastError == null)
					lastError = e;
				errors.add(e);
				nextServer();
				waitingForRepeat = true;
				try {
					Thread.sleep(2000L);
				} catch(InterruptedException e1) {
				}
				continue;
			} finally {
				Util.close(response, is, baos);
			}
		}
		if(lastError != null)
			throw lastError;
		return null;
	}
	
	public String readURL(String request) throws IOException {
		return readURL(request, null);
	}
	
	public String readURL(String request, Map<String, String> post) throws IOException {
		if(serversList.size() == 0)
			throw new IOException("No download servers specified!");
		if(printUrls)
			System.out.println("Reading " + request);
		serverListRepeated = 0;
		currentServer = 0;
		bytesToDownload = 0;
		bytesDownloaded = 0;
		lastError = null;
		while(!isCrashed()) {
			URL u = new URL(serversList.get(currentServer) + request);
			waitingForRepeat = false;
			HttpUriRequest getRequest;
			try {
				if(post != null) {
					getRequest = new HttpPost(u.toURI());
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(post.size());
					Iterator<Entry<String, String>> iter = post.entrySet().iterator();
					while(iter.hasNext()) {
						Entry<String, String> e = iter.next();
						nameValuePairs.add(new BasicNameValuePair(e.getKey(), e.getValue()));
					}
					((HttpPost) getRequest).setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
				} else {
					getRequest = new HttpGet(u.toURI());
				}
			} catch(URISyntaxException e2) {
				throw new IOException(e2);
			}
			InputStream is = null;
			FileOutputStream os = null;
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(getRequest);
				HttpEntity entity = response.getEntity();
				int code = response.getStatusLine().getStatusCode();
				if(code >= 400 && !ignoreHTTPErrors) {
					if(printUrls)
						System.out.println("Http no 200 answer: " + code + " " + response.getStatusLine().getReasonPhrase());
					EntityUtils.consume(response.getEntity());
					throw new HTTPResponseError(code + " " + response.getStatusLine().getReasonPhrase() + " for " + u.getPath());
				}
				ContentType ct = ContentType.get(entity);
				is = entity.getContent();
				bytesToDownload = (int) entity.getContentLength();
				BufferedReader reader = new BufferedReader(ct != null && ct.getCharset() != null ? new InputStreamReader(is, ct.getCharset()) : new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String s;
				while((s = reader.readLine()) != null) {
					if(sb.length() != 0)
						sb.append('\n');
					sb.append(s);
				}
				return sb.toString();
			} catch(IOException e) {
				if(lastError == null)
					lastError = e;
				errors.add(e);
				nextServer();
				waitingForRepeat = true;
				try {
					Thread.sleep(2000L);
				} catch(InterruptedException e1) {
				}
				continue;
			} finally {
				Util.close(is, os, response);
			}
		}
		if(lastError != null)
			throw lastError;
		return null;
	}
	
	public static void setupBetterKeystore() {
		if(OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
			try {
				KeyStore ks = KeyStore.getInstance("Windows-ROOT");
				ks.load(null, null);
				System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
				//System.out.println("System keysore is set up");
			} catch(Exception e) {
				// Ignore: windows keystore is bad, left default
			}
		}
	}
}
