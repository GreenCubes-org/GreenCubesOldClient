// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            EntityFX, ActiveRenderInfo, Entity, RenderEngine, 
//            Tessellator, MathHelper, Block, EntityDiggingFX, 
//            World

public class EffectRenderer {

	public int maxParticles = 0;

	protected World worldObj;
	private LinkedList<EntityFXGC>[] fxLayers = new LinkedList[4];
	private RenderEngine renderer;
	private Random rand = new Random();

	public int particlesRendered = 0;
	private List<EntityFXGC> addedEffects = new LinkedList<EntityFXGC>();

	public EffectRenderer(World world, RenderEngine renderengine) {
		worldObj = world;
		renderer = renderengine;
		for(int i = 0; i < 4; i++)
			fxLayers[i] = new LinkedList<EntityFXGC>();
	}

	public void addEffect(EntityFXGC entityfx) {
		addedEffects.add(entityfx);
	}

	public void updateEffects() {
		for(int i = 0; i < 4; i++) {
			for(Iterator<EntityFXGC> iterator = fxLayers[i].iterator(); iterator.hasNext();) {
				EntityFXGC entityfx = iterator.next();
				entityfx.onUpdate();
				if(entityfx.isDead)
					iterator.remove();
			}
		}
		for(Iterator<EntityFXGC> iterator = addedEffects.iterator(); iterator.hasNext();)
			addEffectReal(iterator.next());
		addedEffects.clear();
	}

	private void addEffectReal(EntityFXGC entityfx) {
		int i = entityfx.getFXLayer();
		if(fxLayers[i].size() >= 25000 * (Minecraft.theMinecraft.gameSettings.fancyGraphics ? 2 : 1) / Math.max(1, Minecraft.theMinecraft.gameSettings.particleSetting * 2))
			fxLayers[i].remove(0);
		fxLayers[i].add(entityfx);
		maxParticles = Math.max(maxParticles, fxLayers[i].size());
	}

	public void renderParticles(Entity entity, Frustrum camera, float f) {
		float f1 = ActiveRenderInfo.field_41070_d;
		float f2 = ActiveRenderInfo.field_41068_f;
		float f3 = ActiveRenderInfo.field_41069_g;
		float f4 = ActiveRenderInfo.field_41078_h;
		float f5 = ActiveRenderInfo.field_41071_e;
		EntityFXGC.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
		EntityFXGC.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
		EntityFXGC.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
		particlesRendered = 0;
		for(int i = 0; i < 3; i++) {
			if(fxLayers[i].size() == 0)
				continue;
			int j = 0;
			if(i == 0)
				j = renderer.getTexture("/particles.png");
			else if(i == 1)
				j = renderer.getTexture("/terrain.png");
			else if(i == 2)
				j = renderer.getTexture("/gui/items.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, j);
			Tessellator tessellator = Tessellator.instance;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			tessellator.startDrawingQuads();
			for(Iterator<EntityFXGC> iterator = fxLayers[i].iterator(); iterator.hasNext();) {
				EntityFXGC entityfx = iterator.next();
				if(!camera.isBoxInFrustum(entityfx.posX, entityfx.posY, entityfx.posZ, entityfx.posX, entityfx.posY, entityfx.posZ))
					continue;
				particlesRendered++;
				tessellator.setBrightness(entityfx.getEntityBrightnessForRender(f));
				entityfx.renderParticle(tessellator, f, f1, f5, f2, f3, f4);
			}
			tessellator.draw();
		}
	}

	public void func_1187_b(Entity entity, float framePart) {
		float f1 = MathHelper.cos((entity.rotationYaw * 3.141593F) / 180F);
		float f2 = MathHelper.sin((entity.rotationYaw * 3.141593F) / 180F);
		float f3 = -f2 * MathHelper.sin((entity.rotationPitch * 3.141593F) / 180F);
		float f4 = f1 * MathHelper.sin((entity.rotationPitch * 3.141593F) / 180F);
		float f5 = MathHelper.cos((entity.rotationPitch * 3.141593F) / 180F);
		if(fxLayers[3].size() == 0)
			return;
		Tessellator tessellator = Tessellator.instance;
		for(Iterator<EntityFXGC> iterator = fxLayers[3].iterator(); iterator.hasNext();) {
			EntityFXGC entityfx = iterator.next();
			tessellator.setBrightness(entityfx.getEntityBrightnessForRender(framePart));
			entityfx.renderParticle(tessellator, framePart, f1, f5, f2, f3, f4);
		}
	}

	public void clearEffects(World world) {
		worldObj = world;
		for(int i = 0; i < 4; i++)
			fxLayers[i].clear();
	}

	public void addBlockDestroyEffects(int i, int j, int k, int l, int i1) {
		if(l == 0)
			return;
		Block block = Block.blocksList[l];
		int j1 = 4;
		for(int k1 = 0; k1 < j1; k1++) {
			for(int l1 = 0; l1 < j1; l1++) {
				for(int i2 = 0; i2 < j1; i2++) {
					double d = i + (k1 + 0.5D) / j1;
					double d1 = j + (l1 + 0.5D) / j1;
					double d2 = k + (i2 + 0.5D) / j1;
					int j2 = rand.nextInt(6);
					addEffect((new EntityDiggingFXGC(worldObj, d, d1, d2, d - i - 0.5D, d1 - j - 0.5D, d2 - k - 0.5D, block, j2, i1)).func_4041_a(i, j, k));
				}
			}
		}
	}

	public void addBlockHitEffects(int i, int j, int k, int l) {
		int i1 = worldObj.getBlockId(i, j, k);
		if(i1 == 0)
			return;
		Block block = Block.blocksList[i1];
		float f = 0.1F;
		double d = i + rand.nextDouble() * (block.maxX - block.minX - (f * 2.0F)) + f + block.minX;
		double d1 = j + rand.nextDouble() * (block.maxY - block.minY - (f * 2.0F)) + f + block.minY;
		double d2 = k + rand.nextDouble() * (block.maxZ - block.minZ - (f * 2.0F)) + f + block.minZ;
		if(l == 0)
			d1 = (j + block.minY) - f;
		else if(l == 1)
			d1 = j + block.maxY + f;
		else if(l == 2)
			d2 = (k + block.minZ) - f;
		else if(l == 3)
			d2 = k + block.maxZ + f;
		else if(l == 4)
			d = (i + block.minX) - f;
		else if(l == 5)
			d = i + block.maxX + f;
		addEffect((new EntityDiggingFXGC(worldObj, d, d1, d2, 0.0D, 0.0D, 0.0D, block, l, worldObj.getBlockMetadata(i, j, k))).func_4041_a(i, j, k).multiplyVelocity(0.2F).setSize(0.6F));
	}

	public String getStatistics() {
		return new StringBuilder().append(particlesRendered).append("/").append(fxLayers[0].size() + fxLayers[1].size() + fxLayers[2].size()).append("/").append(maxParticles).toString();
	}
}
