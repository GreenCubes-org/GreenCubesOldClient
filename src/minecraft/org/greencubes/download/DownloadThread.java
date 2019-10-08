package org.greencubes.download;

import java.io.File;
import java.io.IOException;

public class DownloadThread extends Thread {

	public IOException lastError;

	private Downloader downloader;
	private File f;
	private String url;
	public volatile boolean downloaded = false;

	public DownloadThread(File f, String url, Downloader downloader) {
		super("Download " + url);
		this.f = f;
		this.url = url;
		this.downloader = downloader;
	}

	@Override
	public void run() {
		try {
			downloader.downloadFile(f, url);
		} catch(IOException e) {
			lastError = e;
		}
		downloaded = true;
	}
}
