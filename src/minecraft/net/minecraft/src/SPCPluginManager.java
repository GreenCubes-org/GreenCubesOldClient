// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// Referenced classes of package net.minecraft.src:
//            PlayerHelper, SPCPlugin, SPCCommand

public class SPCPluginManager {

	public Vector plugins;
	public Vector disabledplugins;
	public HashMap commands;
	public PlayerHelper ph;
	public boolean enabled;
	public static SPCPluginManager MANAGER;

	public SPCPluginManager(PlayerHelper playerhelper) {
		ph = playerhelper;
		plugins = new Vector();
		disabledplugins = new Vector();
		commands = new HashMap();
		enabled = true;
		MANAGER = this;
	}

	public static SPCPluginManager getPluginManager() {
		return MANAGER;
	}

	public void setEnabled(boolean flag) {
		enabled = flag;
	}

	public boolean loadPlugins() {
		String s = "";
		try {
			String s1 = (net.minecraft.src.PlayerHelper.class).getResource("PlayerHelper.class").toString();
			if(s1.toLowerCase().startsWith("jar")) {
				String s2 = s1.replaceAll("jar:", "").split("!")[0];
				JarFile jarfile = new JarFile(new File((new URL(s2)).toURI()));
				Enumeration enumeration = jarfile.entries();
				do {
					if(!enumeration.hasMoreElements()) {
						break;
					}
					JarEntry jarentry = (JarEntry) enumeration.nextElement();
					try {
						if(jarentry.getName().toLowerCase().contains("spc_")) {
							Package package1 = (net.minecraft.src.PlayerHelper.class).getPackage();
							SPCPlugin spcplugin = loadPlugin(jarentry.getName(), package1 != null ? package1.getName() : null);
							if(spcplugin != null) {
								plugins.add(spcplugin);
								Map map = loadCommands(spcplugin);
								if(map != null) {
									commands.putAll(map);
								}
							} else {
								s = (new StringBuilder()).append(s).append(jarentry.getName()).append(" ").toString();
							}
						}
					} catch (Throwable throwable) {
						throwable.printStackTrace();
					}
				} while(true);
			} else {
				String s3 = (new StringBuilder()).append((net.minecraft.src.PlayerHelper.class).getName().replace('.', '/')).append(".class").toString();
				URLClassLoader urlclassloader = (URLClassLoader) (net.minecraft.src.PlayerHelper.class).getClassLoader();
				URL url = urlclassloader.getResource(s3);
				File afile[] = (new File(url.getFile())).getParentFile().listFiles();
				if(afile == null) {
					afile = (new File(url.toURI())).getParentFile().listFiles();
				}
				File afile1[] = afile;
				int i = afile1.length;
				for(int j = 0; j < i; j++) {
					File file = afile1[j];
					if(file.isFile() && file.getName().toLowerCase().startsWith("spc_")) {
						try {
							Package package2 = (net.minecraft.src.PlayerHelper.class).getPackage();
							SPCPlugin spcplugin1 = loadPlugin(file.getName(), package2 != null ? package2.getName() : null);
							if(spcplugin1 != null) {
								plugins.add(spcplugin1);
								Map map1 = loadCommands(spcplugin1);
								if(map1 != null) {
									commands.putAll(map1);
								}
							} else {
								s = (new StringBuilder()).append(s).append(file.getName()).append(" ").toString();
							}
						} catch (Exception exception1) {
						}
					}
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		if(!s.equalsIgnoreCase("")) {
			//ph.sendError((new StringBuilder()).append("Plugin(s) failed to load: ").append(s).toString());
			//ph.sendError("Please refer to installation instructions.");
		}
		return true;
	}

	public static SPCPlugin loadPlugin(String s, String s1) throws Exception {
		if(!s.endsWith(".class")) {
			throw new Exception("Not a plugin.");
		}
		s = s.split("\\.")[0];
		s = s1 != null ? (new StringBuilder()).append(s1).append(".").append(s).toString() : s;
		s = s.replaceAll("/", ".");
		System.out.println((new StringBuilder()).append("Attempting to load: ").append(s).toString());
		URLClassLoader urlclassloader = (URLClassLoader) (net.minecraft.src.PlayerHelper.class).getClassLoader();
		Class class1;
		try {
			class1 = urlclassloader.loadClass(s);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		if(class1 != null && (net.minecraft.src.SPCPlugin.class).isAssignableFrom(class1)) {
			try {
				SPCPlugin spcplugin = (SPCPlugin) class1.newInstance();
				if(spcplugin != null) {
					return spcplugin;
				}
			} catch (Exception exception1) {
				exception1.printStackTrace();
				return null;
			}
		} else {
			throw new Exception("Not a plugin.");
		}
		return null;
	}

	public static Map loadCommands(SPCPlugin spcplugin) {
		Method amethod[] = null;
		try {
			amethod = spcplugin.getClass().getDeclaredMethods();
		} catch (Exception exception) {
			PlayerHelper.printStackTrace(exception);
			return null;
		}
		HashMap hashmap = new HashMap();
		Method amethod1[] = amethod;
		int i = amethod1.length;
		for(int j = 0; j < i; j++) {
			Method method = amethod1[j];
			try {
				method.setAccessible(true);
				Class aclass[] = method.getParameterTypes();
				if(aclass.length == 0 || !aclass[0].isAssignableFrom(java.lang.String[].class)) {
					continue;
				}
				SPCCommand spccommand = null;
				if((spccommand = method.getAnnotation(net.minecraft.src.SPCCommand.class)) != null) {
					hashmap.put(spccommand.cmd(), (new Object[]{spcplugin, method, spccommand}));
				}
			} catch (Exception exception1) {
				PlayerHelper.printStackTrace(exception1);
			}
		}

		return hashmap;
	}

	public boolean callPluginMethods(Method method, Object aobj[]) {
		if(method == null || aobj == null || !enabled) {
			return false;
		}
		boolean flag = false;
		Iterator iterator = plugins.iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			SPCPlugin spcplugin = (SPCPlugin) iterator.next();
			try {
				Object obj = method.invoke(spcplugin, aobj);
				if((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
					flag = true;
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		} while(true);
		return flag;
	}

	public boolean handleCommand(String as[]) {
		return handleCommand(as, commands);
	}

	public boolean handleCommand(String as[], HashMap hashmap) {
		if(as == null || hashmap == null || as[0].equalsIgnoreCase("")) {
			return false;
		}
		Object aobj[] = (Object[]) hashmap.get(as[0]);
		if(aobj == null || aobj.length != 3) {
			return false;
		}
		try {
			Method method = (Method) aobj[1];
			SPCPlugin spcplugin = (SPCPlugin) aobj[0];
			method.invoke(spcplugin, new Object[]{as});
		} catch (Exception exception) {
			PlayerHelper.printStackTrace(exception);
		}
		return true;
	}

	public String[] getCommands() {
		String as[] = new String[commands.size()];
		Iterator iterator = commands.keySet().iterator();
		for(int i = 0; i < as.length; i++) {
			as[i] = (String) iterator.next();
		}

		Vector vector = new Vector();
		int j = 0;
		Iterator iterator1 = plugins.iterator();
		do {
			if(!iterator1.hasNext()) {
				break;
			}
			SPCPlugin spcplugin = (SPCPlugin) iterator1.next();
			List list = spcplugin.getCommands();
			if(list != null) {
				j += list.size();
				vector.add(list);
			}
		} while(true);
		String as1[] = new String[j + as.length];
		int k = 0;
		String as2[] = as;
		int l = as2.length;
		for(int i1 = 0; i1 < l; i1++) {
			String s = as2[i1];
			as1[k++] = s;
		}

		for(Iterator iterator2 = vector.iterator(); iterator2.hasNext();) {
			Iterator iterator3 = ((List) iterator2.next()).iterator();
			while(iterator3.hasNext()) {
				as1[k++] = (String) iterator3.next();
			}
		}

		return as1;
	}

	public String[] getHelp(String s) {
		if(s == null || s.equalsIgnoreCase("")) {
			return null;
		}
		Object aobj[] = (Object[]) commands.get(s);
		if(aobj == null) {
			for(Iterator iterator = plugins.iterator(); iterator.hasNext();) {
				SPCPlugin spcplugin = (SPCPlugin) iterator.next();
				String as[] = null;
				if((as = spcplugin.getHelp(s)) != null) {
					return as;
				}
			}

			return null;
		} else {
			try {
				SPCCommand spccommand = (SPCCommand) aobj[2];
				return (new String[]{spccommand.help(), spccommand.args(), spccommand.example()});
			} catch (Exception exception) {
				PlayerHelper.printStackTrace(exception);
			}
			return null;
		}
	}

	public List getPlugins() {
		return plugins;
	}

	public List getDisabledPlugins() {
		return disabledplugins;
	}

	public boolean disablePlugin(SPCPlugin spcplugin) {
		if(plugins.remove(spcplugin)) {
			disabledplugins.add(spcplugin);
			return true;
		} else {
			return false;
		}
	}

	public boolean enablePlugin(SPCPlugin spcplugin) {
		if(disabledplugins.remove(spcplugin)) {
			plugins.add(spcplugin);
			return true;
		} else {
			return false;
		}
	}

	public SPCPlugin[] getPlugin(String s) {
		Vector vector = new Vector();
		Iterator iterator = plugins.iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			SPCPlugin spcplugin = (SPCPlugin) iterator.next();
			if(spcplugin.getName() != null && spcplugin.getName().equalsIgnoreCase(s)) {
				vector.add(spcplugin);
			}
		} while(true);
		return (SPCPlugin[]) vector.toArray(new SPCPlugin[vector.size()]);
	}

	static {
		try {
			File file = new File(PlayerHelper.MODDIR, "jars");
			if(!file.exists()) {
				file.mkdir();
			} else {
				File afile[] = file.listFiles();
				Vector vector = new Vector();
				vector.add("minecraft.jar");
				vector.add("lwjgl.jar");
				vector.add("lwjgl_util.jar");
				vector.add("jinput.jar");
				File afile1[] = afile;
				int i = afile1.length;
				for(int j = 0; j < i; j++) {
					File file1 = afile1[j];
					String s = file1.getName();
					if(!vector.contains(s) && s.endsWith(".jar")) {
						PlayerHelper.addToClasspath(file1);
					}
				}

			}
		} catch (Exception exception) {
			PlayerHelper.printStackTrace(exception);
		}
	}
}
