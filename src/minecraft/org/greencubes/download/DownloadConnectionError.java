package org.greencubes.download;

import java.io.IOException;

public class DownloadConnectionError extends IOException {

	private static final long serialVersionUID = -1197764857185189296L;

	public DownloadConnectionError(String s, IOException e) {
		super(s, e);
	}
}
