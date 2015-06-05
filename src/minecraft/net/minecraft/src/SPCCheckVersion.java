// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

// Referenced classes of package net.minecraft.src:
//            SPCVersionInterface, SPCVersion

public class SPCCheckVersion extends Thread {

	public static final String MANIFEST = "http://bit.ly/spccheckupdate";
	private Method method;
	private List projects;
	private String mcversion;
	private Object callbackinstance;

	public SPCCheckVersion(SPCVersionInterface aspcversioninterface[], String s, Method method1, Object obj) {
		method = method1;
		projects = Arrays.asList(aspcversioninterface);
		mcversion = s;
		callbackinstance = obj;
	}

	@Override
	public void run() {
		File file = downloadFile("http://bit.ly/spccheckupdate");
		if(file == null || !file.exists()) {
			return;
		}
		Vector vector = parseManifest(file);
		Vector vector1 = new Vector();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Iterator iterator = vector.iterator(); iterator.hasNext();) {
			HashMap hashmap = (HashMap) iterator.next();
			boolean flag = true;
			String s = (String) hashmap.get("version");
			if(s == null) {
				flag = false;
			}
			boolean flag1 = true;
			String s1 = (String) hashmap.get("datetime");
			if(s1 == null) {
				flag1 = false;
			}
			Iterator iterator1 = projects.iterator();
			while(iterator1.hasNext()) {
				SPCVersionInterface spcversioninterface = (SPCVersionInterface) iterator1.next();
				boolean flag2 = false;
				if(flag && spcversioninterface.getVersion() != null && s.compareTo(spcversioninterface.getVersion()) > 0) {
					flag2 = true;
				}
				if(!flag2 && flag1 && spcversioninterface.getLastUpdate() != null) {
					long l = 0L;
					try {
						Date date = simpledateformat.parse(s1);
						l = date.getTime();
					} catch (ParseException parseexception) {
					}
					if(l > spcversioninterface.getLastUpdate().getTime()) {
						flag2 = true;
					}
				}
				if(flag2) {
					if(mcversion == null || mcversion.equalsIgnoreCase("")) {
						vector1.add(hashmap);
					} else {
						Iterator iterator2 = ((List) hashmap.get("minecraft")).iterator();
						while(iterator2.hasNext()) {
							String s2 = (String) iterator2.next();
							if(s2.equalsIgnoreCase(mcversion)) {
								vector1.add(hashmap);
							}
						}
					}
				}
			}
		}

		try {
			file.delete();
		} catch (Exception exception) {
		}
		if(method != null) {
			try {
				method.invoke(callbackinstance, new Object[]{vector1});
			} catch (Exception exception1) {
				exception1.printStackTrace();
			}
		}
	}

	public Vector parseManifest(File file) {
		DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
		Vector vector = null;
		try {
			DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
			Document document = documentbuilder.parse(file);
			Element element = document.getDocumentElement();
			if(element.getAttribute("version").compareTo("1.0") > 0) {
				System.out.println("Warning: checking for the update may fail. New version of manifest file detected");
			}
			NodeList nodelist = element.getElementsByTagName("project");
			vector = new Vector();
			if(nodelist != null && nodelist.getLength() > 0) {
				for(int i = 0; i < nodelist.getLength(); i++) {
					Element element1 = (Element) nodelist.item(i);
					Iterator iterator = projects.iterator();
					do {
						if(!iterator.hasNext()) {
							break;
						}
						SPCVersionInterface spcversioninterface = (SPCVersionInterface) iterator.next();
						if(spcversioninterface.getName() != null && element1.getAttribute("name").equalsIgnoreCase(spcversioninterface.getName())) {
							HashMap hashmap = getProject(element1);
							hashmap.put("name", spcversioninterface.getName());
							vector.add(hashmap);
						}
					} while(true);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return vector;
	}

	private HashMap getProject(Element element) {
		HashMap hashmap = new HashMap();
		hashmap.put("version", getValue(element, "version"));
		hashmap.put("datetime", getValue(element, "datetime"));
		hashmap.put("website", getValue(element, "website"));
		hashmap.put("download", getValue(element, "download"));
		hashmap.put("message", getValue(element, "message"));
		NodeList nodelist = element.getElementsByTagName("minecraft");
		Vector vector = new Vector();
		for(int i = 0; i < nodelist.getLength(); i++) {
			vector.add(getValue((Element) nodelist.item(i), "supportedversion"));
		}

		hashmap.put("minecraft", vector);
		return hashmap;
	}

	private String getValue(Element element, String s) {
		String s1 = null;
		NodeList nodelist = element.getElementsByTagName(s);
		if(nodelist != null && nodelist.getLength() > 0) {
			s1 = ((Element) nodelist.item(0)).getTextContent();
		}
		return s1;
	}

	public File downloadFile(String s) {
		File file = null;
		InputStream inputstream = null;
		FileOutputStream fileoutputstream = null;
		try {
			URL url = new URL(s);
			url.openConnection();
			inputstream = url.openStream();
			file = File.createTempFile("spcupdate", (new StringBuilder()).append(System.currentTimeMillis()).append("").toString());
			fileoutputstream = new FileOutputStream(file);
			byte abyte0[] = new byte[1024];
			for(int i = 0; (i = inputstream.read(abyte0)) > 0;) {
				fileoutputstream.write(abyte0, 0, i);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			try {
				file.delete();
			} catch (Exception exception1) {
			}
			file = null;
		} finally {
			try {
				fileoutputstream.close();
			} catch (Exception exception3) {
			}
			try {
				inputstream.close();
			} catch (Exception exception4) {
			}
		}
		return file;
	}

	public static void main(String args[]) {
		(new SPCCheckVersion(new SPCVersion[]{new SPCVersion("Single Player Commands", "2.11", new Date())}, "1.7.3", null, null)).start();
	}
}
