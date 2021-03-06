package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class ARBOcclusionChecker {

	private static final int arbQueryMaxSize = 2048;
	private static final int ticksToClear = 10;
	private static final long checkTimeLimit = 5;
	private static final long queueTimeLimit = 5;

	public static final ARBOcclusionChecker instance = new ARBOcclusionChecker();

	private Minecraft mc;
	private boolean enabled = false;
	private boolean canEnable = false;
	private IntBuffer glOcclusionQueryBase;
	private IntBuffer occlusionResult;
	private Map<Object, ARBResult> arbResult = new HashMap<Object, ARBResult>();
	private Queue<Integer> arbIdsQueue;
	private boolean isInQueue = false;
	private Queue<ARBResult> toQueue;
	private Queue<ARBResult> toCheck;
	private List<ARBResult> resultsList;
	private int checkLimit = 200;
	private int queueLimit = 200;

	public int objects = 0;
	public int hiddenObjects = 0;

	private ARBOcclusionChecker() {

	}

	public void init() {
		mc = Minecraft.theMinecraft;
		canEnable = GLContext.getCapabilities().GL_ARB_occlusion_query;
		enabled = canEnable && mc.gameSettings.occ > 0;
		if(canEnable) {
			occlusionResult = GLAllocation.createDirectIntBuffer(64);
			glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(arbQueryMaxSize);
			glOcclusionQueryBase.clear();
			glOcclusionQueryBase.position(0);
			glOcclusionQueryBase.limit(arbQueryMaxSize);
			ARBOcclusionQuery.glGenQueriesARB(glOcclusionQueryBase);
			System.out.println("ARB Occlusion Enabled");
			arbIdsQueue = new ArrayBlockingQueue<Integer>(arbQueryMaxSize);
			for(int i = 0; i < arbQueryMaxSize; ++i)
				arbIdsQueue.add(Integer.valueOf(i));
			toQueue = new LinkedBlockingQueue<ARBResult>();
			toCheck = new LinkedBlockingQueue<ARBResult>();
			resultsList = new ArrayList<ARBResult>();
		}
	}

	private int getNextBufferId() {
		Integer ret = arbIdsQueue.poll();
		if(ret == null)
			return -1;
		return ret;
	}

	public void tick() {
		boolean wereEnabled = enabled;
		enabled = canEnable && mc.gameSettings.occ > 0;
		if(!enabled) {
			if(wereEnabled) {
				for(int i = 0; i < resultsList.size(); ++i) {
					ARBResult r = resultsList.get(i);
					clearResult(r);
				}
				resultsList.clear();
				toQueue.clear();
				toCheck.clear();
				arbResult.clear();
				objects = 0;
				hiddenObjects = 0;
			}
			return;
		}
		float f = mc.gameSettings.occ;
		checkLimit = queueLimit = (int) (f <= 0.5f ? f * 400 : f * 1000);
		objects = arbResult.size();
		hiddenObjects = 0;
		if(objects > 0) {
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(3553 /* GL_TEXTURE_2D */);
			GL11.glDisable(2896 /* GL_LIGHTING */);
			GL11.glDisable(3008 /* GL_ALPHA_TEST */);
			GL11.glDisable(2912 /* GL_FOG */);
			GL11.glColorMask(false, false, false, false);
			GL11.glDepthMask(false);
			for(int i = 0; i < resultsList.size(); ++i) {
				ARBResult r = resultsList.get(i);
				r.ticksFromLastCheck++;
				if(r.ticksFromLastCheck >= ticksToClear) {
					clearResult(r);
					continue;
				}
				if(!r.result)
					hiddenObjects++;
			}
			long start = System.currentTimeMillis();
			for(int i = 0; i < toCheck.size() && i < checkLimit; ++i) {
				ARBResult r = toCheck.poll();
				if(!r.hasResult) {
					occlusionResult.clear();
					ARBOcclusionQuery.glGetQueryObjectuARB(r.id, ARBOcclusionQuery.GL_QUERY_RESULT_AVAILABLE_ARB, occlusionResult);
					if(occlusionResult.get(0) != 0) {
						r.hasResult = true;
						occlusionResult.clear();
						ARBOcclusionQuery.glGetQueryObjectuARB(r.id, ARBOcclusionQuery.GL_QUERY_RESULT_ARB, occlusionResult);
						r.result = occlusionResult.get(0) != 0;
					} else
						toCheck.add(r);
				}
				if(start + checkTimeLimit < System.currentTimeMillis())
					break;
			}
			start = System.currentTimeMillis();
			GL11.glPushMatrix();
			for(int i = 0; i < toQueue.size() && i < queueLimit; ++i) {
				ARBResult r = toQueue.poll();
				if(!r.isQueuing) {
					r.isQueuing = true;
					ARBOcclusionQuery.glBeginQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB, r.id);
					//GL11.glPushMatrix();
					//GL11.glTranslatef(r.x, r.y, r.z);
					if(r.bb != null) {
						//if(r.list != -1)
						//	GL11.glDeleteLists(r.list, 1);
						//r.list = GL11.glGenLists(1);
						//GL11.glNewList(r.list, GL11.GL_COMPILE_AND_EXECUTE);
						Render.renderAABB(r.bb);
						//GL11.glEndList();
						//r.bb = null;
					} else if(r.list != -1) {
						GL11.glCallList(r.list);
					}
					//GL11.glPopMatrix();
					ARBOcclusionQuery.glEndQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB);
					toCheck.add(r);
				}
				if(start + queueTimeLimit < System.currentTimeMillis())
					break;
			}
			GL11.glPopMatrix();
			if(mc.gameSettings.anaglyph) {
				if(EntityRenderer.anaglyphField == 0) {
					GL11.glColorMask(false, true, true, true);
				} else {
					GL11.glColorMask(true, false, false, true);
				}
			} else {
				GL11.glColorMask(true, true, true, true);
			}
			GL11.glDepthMask(true);
			GL11.glEnable(3553 /* GL_TEXTURE_2D */);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			GL11.glEnable(2912 /* GL_FOG */);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean canEnable() {
		return canEnable;
	}

	public boolean checkEnabled() {
		return canEnable && mc.gameSettings.occ > 0;
	}

	private void clearResult(ARBResult result) {
		toQueue.remove(result);
		toCheck.remove(result);
		resultsList.remove(result);
		arbResult.remove(result.o);
		arbIdsQueue.add(result.index);
		if(result.list != -1)
			GL11.glDeleteLists(result.list, 1);
		ARBOcclusionQuery.glDeleteQueriesARB(result.id);
	}

	public boolean queueForARB(Object object, float x, float y, float z, AxisAlignedBB bb) {
		return queueForARB(object, x, y, z, bb, -1);
	}

	public boolean hasCheck(Object o) {
		ARBResult r = arbResult.get(o);
		return r != null && !r.hasResult;
	}

	public boolean queueForARB(Object object, float x, float y, float z, AxisAlignedBB bb, int list) {
		if(!enabled)
			return true;
		ARBResult r = arbResult.get(object);
		if(r == null) {
			if(bb == null && list == -1)
				throw new IllegalStateException("No model for new arb check!");
			int nextBuffer = getNextBufferId();
			if(nextBuffer == -1)
				return true;
			r = new ARBResult();
			r.index = nextBuffer;
			r.id = glOcclusionQueryBase.get(nextBuffer);
			r.o = object;
			r.bb = bb;
			//r.list = list;
			//r.x = x;
			//r.y = y;
			//r.z = z;
			toQueue.add(r);
			resultsList.add(r);
			arbResult.put(object, r);
			r.result = true;
		} else if(r.hasResult) {
			r.hasOldResult = true;
			r.hasResult = false;
			r.isQueuing = false;
			toQueue.add(r);
			if(bb != null)
				r.bb = bb;
			//if(list != -1)
			//	r.list = list;
		}
		//r.x = x;
		//r.y = y;
		//r.z = z;
		r.ticksFromLastCheck = 0;
		return r.result;
	}

	private class ARBResult {
		public float x;
		public float y;
		public float z;
		public boolean isQueuing = false;
		public boolean hasResult = false;
		public boolean hasOldResult = false;
		public boolean result = false;
		public int ticksFromLastCheck = 0;
		public int id;
		public Object o;
		public AxisAlignedBB bb;
		public int list = -1;
		public int index;
	}
}
