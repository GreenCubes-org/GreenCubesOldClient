// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            TileEntitySign, TileEntitySignRenderer, TileEntityMobSpawner, TileEntityMobSpawnerRenderer, 
//            TileEntityPiston, TileEntityRendererPiston, TileEntityChest, TileEntityChestRenderer, 
//            TileEntityEnchantmentTable, RenderEnchantmentTable, TileEntityEndPortal, RenderEndPortal, 
//            TileEntitySpecialRenderer, TileEntity, EntityLiving, World, 
//            OpenGlHelper, FontRenderer, RenderEngine

public class TileEntityRenderer {

	private Map specialRendererMap;
	public static TileEntityRenderer instance = new TileEntityRenderer();
	private FontRenderer fontRenderer;
	public static double staticPlayerX;
	public static double staticPlayerY;
	public static double staticPlayerZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityLiving entityLivingPlayer;
	public float playerYaw;
	public float playerPitch;
	public double playerX;
	public double playerY;
	public double playerZ;

	private int boundingBoxList;
	private List<TileEntity> secondPass;
	private ARBOcclusionChecker arb = ARBOcclusionChecker.instance;

	private TileEntityRenderer() {
		specialRendererMap = new HashMap();
		specialRendererMap.put(net.minecraft.src.TileEntitySign.class, new TileEntitySignRenderer());
		specialRendererMap.put(net.minecraft.src.TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		specialRendererMap.put(net.minecraft.src.TileEntityPiston.class, new TileEntityRendererPiston());
		specialRendererMap.put(net.minecraft.src.TileEntityChest.class, new TileEntityChestRenderer());
		specialRendererMap.put(TileEntityFrame.class, new TileEntityFrameRenderer());
		specialRendererMap.put(net.minecraft.src.TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
		specialRendererMap.put(net.minecraft.src.TileEntityEndPortal.class, new RenderEndPortal());
		specialRendererMap.put(TileEntityNormalChest.class, new TileEntityNormalChestRenderer());
		TileEntitySpecialRenderer tileentityspecialrenderer;
		for(Iterator iterator = specialRendererMap.values().iterator(); iterator.hasNext(); tileentityspecialrenderer.setTileEntityRenderer(this)) {
			tileentityspecialrenderer = (TileEntitySpecialRenderer) iterator.next();
		}
		secondPass = new ArrayList<TileEntity>();
	}

	public void init() {
		boundingBoxList = -1;
		/*GL11.glGenLists(1);
		GL11.glNewList(boundingBoxList, GL11.GL_COMPILE);
		RenderItem.renderAABB(AxisAlignedBB.getBoundingBoxFromPool(0, 0, 0, 2, 1.5, 2));
		GL11.glEndList();*/
	}

	public TileEntitySpecialRenderer getSpecialRendererForClass(Class class1) {
		TileEntitySpecialRenderer tileentityspecialrenderer = (TileEntitySpecialRenderer) specialRendererMap.get(class1);
		if(tileentityspecialrenderer == null && class1 != (net.minecraft.src.TileEntity.class)) {
			tileentityspecialrenderer = getSpecialRendererForClass(class1.getSuperclass());
			specialRendererMap.put(class1, tileentityspecialrenderer);
		}
		return tileentityspecialrenderer;
	}

	public boolean hasSpecialRenderer(TileEntity tileentity) {
		return getSpecialRendererForEntity(tileentity) != null;
	}

	public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity tileentity) {
		if(tileentity == null) {
			return null;
		} else {
			return getSpecialRendererForClass(tileentity.getClass());
		}
	}

	public void cacheActiveRenderInfo(World world, RenderEngine renderengine, FontRenderer fontrenderer, EntityLiving entityliving, float f) {
		if(worldObj != world) {
			func_31072_a(world);
		}
		renderEngine = renderengine;
		entityLivingPlayer = entityliving;
		fontRenderer = fontrenderer;
		playerYaw = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f;
		playerPitch = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f;
		playerX = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * f;
		playerY = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * f;
		playerZ = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * f;
	}

	public void func_40742_a() {
	}

	public void renderTileEntity(TileEntity tileentity, float f) {
		double distance = tileentity.getDistanceFrom(playerX, playerY, playerZ);
		if(distance < 4096D) {
			Minecraft.theMinecraft.renderGlobal.tilesRendered++;
			if(arb.isEnabled()) {
				boolean result = arb.queueForARB(tileentity, 0, 0, 0, AxisAlignedBB.getBoundingBox(tileentity.xCoord - staticPlayerX, tileentity.yCoord - staticPlayerY, tileentity.zCoord - staticPlayerZ, tileentity.xCoord - staticPlayerX + 2.0D, tileentity.yCoord - staticPlayerY + 1.2D, tileentity.zCoord - staticPlayerZ + 2.0D), boundingBoxList);
				if(!result && distance > 16D)
					return;
			}
			int i = worldObj.getLightBrightnessForSkyBlocks(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 0);
			int j = i % 0x10000;
			int k = i / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapEnabled, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			renderTileEntityAt(tileentity, tileentity.xCoord - staticPlayerX, tileentity.yCoord - staticPlayerY, tileentity.zCoord - staticPlayerZ, f);
		}
	}

	public void secondPass(float f) {
		for(int i = 0; i < secondPass.size(); ++i) {
			TileEntity tileentity = secondPass.get(i);
			secondPassTileAt(tileentity, tileentity.xCoord - staticPlayerX, tileentity.yCoord - staticPlayerY, tileentity.zCoord - staticPlayerZ, f);
		}
		secondPass.clear();
	}

	public void secondPassTileAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		TileEntitySpecialRenderer tileentityspecialrenderer = getSpecialRendererForEntity(tileentity);
		if(tileentityspecialrenderer != null)
			tileentityspecialrenderer.secondPass(tileentity, d, d1, d2, f);
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		TileEntitySpecialRenderer tileentityspecialrenderer = getSpecialRendererForEntity(tileentity);
		if(tileentityspecialrenderer != null) {
			tileentityspecialrenderer.renderTileEntityAt(tileentity, d, d1, d2, f);
			secondPass.add(tileentity);
		}
	}

	public void func_31072_a(World world) {
		worldObj = world;
		Iterator iterator = specialRendererMap.values().iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			TileEntitySpecialRenderer tileentityspecialrenderer = (TileEntitySpecialRenderer) iterator.next();
			if(tileentityspecialrenderer != null) {
				tileentityspecialrenderer.func_31069_a(world);
			}
		} while(true);
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

}
