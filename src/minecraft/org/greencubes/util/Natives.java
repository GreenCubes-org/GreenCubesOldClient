package org.greencubes.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Natives {
	
	public static final boolean IS_64_BIT_JAVA;
	
	private static final Logger logger = Logger.getLogger(Natives.class.getName());
	private static final byte[] buf = new byte[1024];
	private static File extractionDirOverride = null;
	private static File extractionDir = null;
	
	public static void setExtractionDir(String name) {
		extractionDirOverride = new File(name).getAbsoluteFile();
	}
	
	public static File getExtractionDir() {
		if(extractionDirOverride != null) {
			return extractionDirOverride;
		}
		if(extractionDir == null) {
			File workingFolder = new File("").getAbsoluteFile();
			if(!workingFolder.canWrite()) {
				setStorageExtractionDir();
			} else {
				try {
					File file = new File(workingFolder.getAbsolutePath() + File.separator + ".jmetestwrite");
					file.createNewFile();
					file.delete();
					extractionDir = workingFolder;
				} catch(Exception e) {
					setStorageExtractionDir();
				}
			}
		}
		return extractionDir;
	}
	
	private static void setStorageExtractionDir() {
		logger.log(Level.WARNING, "Working directory is not writable. Using home directory instead.");
		extractionDir = new File("", "natives_" + Integer.toHexString(computeNativesHash()));
		if(!extractionDir.exists()) {
			extractionDir.mkdir();
		}
	}
	
	private static int computeNativesHash() {
		URLConnection conn = null;
		try {
			String classpath = System.getProperty("java.class.path");
			URL url = Thread.currentThread().getContextClassLoader().getResource("org/greencubes/util/Natives.class");
			
			StringBuilder sb = new StringBuilder(url.toString());
			if(sb.indexOf("jar:") == 0) {
				sb.delete(0, 4);
				sb.delete(sb.indexOf("!"), sb.length());
				sb.delete(sb.lastIndexOf("/") + 1, sb.length());
			}
			try {
				url = new URL(sb.toString());
			} catch(MalformedURLException ex) {
				throw new UnsupportedOperationException(ex);
			}
			
			conn = url.openConnection();
			int hash = classpath.hashCode() ^ (int) conn.getLastModified();
			return hash;
		} catch(IOException ex) {
			throw new UnsupportedOperationException(ex);
		} finally {
			if(conn != null) {
				try {
					conn.getInputStream().close();
					conn.getOutputStream().close();
				} catch(IOException ex) {}
			}
		}
	}
	
	public static void extractNativeLib(String sysName, String name) throws IOException {
		extractNativeLib(sysName, name, false, true);
	}
	
	public static void extractNativeLib(String sysName, String name, boolean load) throws IOException {
		extractNativeLib(sysName, name, load, true);
	}
	
	public static void extractNativeLib(String sysName, String name, boolean load, boolean warning) throws IOException {
		String fullname;
		String path;
		//XXX: hack to allow specifying the extension via supplying an extension in the name (e.g. "blah.dylib")
		//     this is needed on osx where the openal.dylib always needs to be extracted as dylib
		//     and never as jnilib, even if that is the platform JNI lib suffix (OpenAL is no JNI library)
		if(!name.contains(".")) {
			// automatic name mapping
			fullname = System.mapLibraryName(name);
			path = "native/" + sysName + "/" + fullname;
			//XXX: Hack to extract jnilib to dylib on OSX Java 1.7+
			//     This assumes all jni libs for osx are stored as "jnilib" in the jar file.
			//     It will be extracted with the name required for the platform.
			//     At a later stage this should probably inverted so that dylib is the default name.
			if(sysName.equals("macosx")) {
				path = path.replaceAll("dylib", "jnilib");
			}
		} else {
			fullname = name;
			path = "native/" + sysName + "/" + fullname;
		}
		
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		
		if(url == null) {
			if(!warning) {
				logger.log(Level.WARNING, "Cannot locate native library: {0}/{1}", new String[]{sysName, fullname});
			}
			return;
		}
		
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		File targetFile = new File(getExtractionDir(), fullname);
		OutputStream out = null;
		try {
			if(targetFile.exists()) {
				// OK, compare last modified date of this file to 
				// file in jar
				long targetLastModified = targetFile.lastModified();
				long sourceLastModified = conn.getLastModified();
				
				// Allow ~1 second range for OSes that only support low precision
				if(targetLastModified + 1000 > sourceLastModified) {
					logger.log(Level.FINE, "Not copying library {0}. Latest already extracted.", fullname);
					return;
				}
			}
			
			out = new FileOutputStream(targetFile);
			int len;
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			in = null;
			out.close();
			out = null;
			
			// NOTE: On OSes that support "Date Created" property, 
			// this will cause the last modified date to be lower than
			// date created which makes no sense
			targetFile.setLastModified(conn.getLastModified());
		} catch(FileNotFoundException ex) {
			if(ex.getMessage().contains("used by another process")) {
				return;
			}
			
			throw ex;
		} finally {
			if(load) {
				System.load(targetFile.getAbsolutePath());
			}
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
		}
		logger.log(Level.FINE, "Copied {0} to {1}", new Object[]{fullname, targetFile});
	}
	
	public static void extractNativeLibs() throws IOException {
		String libraryPath = getExtractionDir().toString();
		logger.log(Level.INFO, "Extraction Directory: {0}", libraryPath);
		
		// LWJGL supports this feature where
		// it can load libraries from this path.
		System.setProperty("org.lwjgl.librarypath", libraryPath);
		// AND Luckily enough JInput supports the same feature.
		System.setProperty("net.java.games.input.librarypath", libraryPath);
		
		switch(OperatingSystem.getCurrentPlatform()) {
		case WINDOWS:
			if(IS_64_BIT_JAVA) {
				extractNativeLib("windows", "lwjgl64");
				extractNativeLib("windows", "OpenAL64");
				extractNativeLib("windows", "jinput-dx8_64");
				extractNativeLib("windows", "jinput-raw_64");
				extractNativeLib("windows", "jacob-1.17-x64");
			} else {
				extractNativeLib("windows", "lwjgl");
				extractNativeLib("windows", "OpenAL32");
				extractNativeLib("windows", "jinput-dx8");
				extractNativeLib("windows", "jinput-raw");
				extractNativeLib("windows", "jacob-1.17-x86");
			}
			break;
		case LINUX:
			if(IS_64_BIT_JAVA) {
				extractNativeLib("linux", "lwjgl64");
				extractNativeLib("linux", "jinput-linux64");
				extractNativeLib("linux", "openal64");
				//extractNativeLib("linux", "jacob-1.17-x64");
			} else {
				extractNativeLib("linux", "lwjgl");
				extractNativeLib("linux", "jinput-linux");
				extractNativeLib("linux", "openal");
				//extractNativeLib("linux", "jacob-1.17-x86");
			}
			break;
		case OSX:
			extractNativeLib("macosx", "lwjgl");
			extractNativeLib("macosx", "openal.dylib");
			extractNativeLib("macosx", "jinput-osx");
			extractNativeLib("macosx", "jacob-1.17");
			break;
		default:
			throw new Error("Platform is not supported");
		}
	}
	
	static {
		String[] opts = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
		boolean is64bit = false;
		for(String opt : opts) {
			String val = System.getProperty(opt);
			if(val != null && val.contains("64")) {
				is64bit = true;
				break;
			}
		}
		IS_64_BIT_JAVA = is64bit;
	}
}
