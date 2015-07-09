// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            ProfilerResult

public class Profiler {

	public static boolean profilingEnabled = false;
	private static List<String> sectionList = new ArrayList<String>();
	private static List<Long> timestampList = new ArrayList<Long>();
	private static String profilingSection = "";
	private static Map<String, Long> profilingMap = new HashMap<String, Long>();

	public Profiler() {
	}

	public static void clearProfiling() {
		profilingMap.clear();
	}

	public static void startSection(String s) {
		if(!profilingEnabled) {
			return;
		}
		if(profilingSection.length() > 0) {
			profilingSection = (new StringBuilder()).append(profilingSection).append(".").toString();
		}
		profilingSection = (new StringBuilder()).append(profilingSection).append(s).toString();
		sectionList.add(profilingSection);
		timestampList.add(Long.valueOf(System.nanoTime()));
	}

	public static void endSection() {
		if(!profilingEnabled) {
			return;
		}
		long l = System.nanoTime();
		long l1 = ((Long) timestampList.remove(timestampList.size() - 1)).longValue();
		sectionList.remove(sectionList.size() - 1);
		long l2 = l - l1;
		if(profilingMap.containsKey(profilingSection)) {
			profilingMap.put(profilingSection, Long.valueOf(((Long) profilingMap.get(profilingSection)).longValue() + l2));
		} else {
			profilingMap.put(profilingSection, Long.valueOf(l2));
		}
		profilingSection = sectionList.size() <= 0 ? "" : (String) sectionList.get(sectionList.size() - 1);
	}

	@SuppressWarnings("unchecked")
	public static List<ProfilerResult> getProfilingData(String s) {
		if(!profilingEnabled) {
			return null;
		}
		String s1 = s;
		long l = profilingMap.containsKey("root") ? ((Long) profilingMap.get("root")).longValue() : 0L;
		long l1 = profilingMap.containsKey(s) ? ((Long) profilingMap.get(s)).longValue() : -1L;
		ArrayList<ProfilerResult> arraylist = new ArrayList<ProfilerResult>();
		if(s.length() > 0) {
			s = (new StringBuilder()).append(s).append(".").toString();
		}
		long l2 = 0L;
		Iterator<String> iterator = profilingMap.keySet().iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			String s2 = iterator.next();
			if(s2.length() > s.length() && s2.startsWith(s) && s2.indexOf(".", s.length() + 1) < 0) {
				l2 += profilingMap.get(s2).longValue();
			}
		} while(true);
		float f = l2;
		if(l2 < l1) {
			l2 = l1;
		}
		if(l < l2) {
			l = l2;
		}
		Iterator<String> iterator1 = profilingMap.keySet().iterator();
		do {
			if(!iterator1.hasNext()) {
				break;
			}
			String s3 = iterator1.next();
			if(s3.length() > s.length() && s3.startsWith(s) && s3.indexOf(".", s.length() + 1) < 0) {
				long l3 = profilingMap.get(s3).longValue();
				double d = (l3 * 100D) / l2;
				double d1 = (l3 * 100D) / l;
				String s5 = s3.substring(s.length());
				arraylist.add(new ProfilerResult(s5, d, d1));
			}
		} while(true);
		String s4;
		for(Iterator<String> iterator2 = profilingMap.keySet().iterator(); iterator2.hasNext(); profilingMap.put(s4, Long.valueOf((profilingMap.get(s4).longValue() * 999L) / 1000L))) {
			s4 = iterator2.next();
		}

		if(l2 > f) {
			arraylist.add(new ProfilerResult("unspecified", ((l2 - f) * 100D) / l2, ((l2 - f) * 100D) / l));
		}
		Collections.sort(arraylist);
		arraylist.add(0, new ProfilerResult(s1, 100D, (l2 * 100D) / l));
		return arraylist;
	}

	public static void endStartSection(String s) {
		endSection();
		startSection(s);
	}

}
