// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.iterator.TIntObjectIterator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import com.jme3.math.ColorRGBA;

// Referenced classes of package net.minecraft.src:
//            Render, ModelBase, EntityLiving, OpenGlHelper, 
//            MathHelper, RenderManager, Tessellator, FontRenderer, 
//            Entity

public class RenderLiving extends Render {

	protected static final ColorRGBA healthDangerColor = new ColorRGBA(0.6f, 0.1f, 0.1f, 1f);
	protected static final ColorRGBA healthPeacefulColor = new ColorRGBA(0.1f, 0.6f, 0.1f, 1f);
	
	protected ModelBase mainModel;
	protected ModelBase renderPassModel;
	public float scale = 0.0F;

	public RenderLiving(ModelBase modelbase, float f) {
		mainModel = modelbase;
		shadowSize = f;
	}

	public void setRenderPassModel(ModelBase modelbase) {
		renderPassModel = modelbase;
	}

	public void doRenderLiving(EntityLiving entityliving, double x, double y, double z, float f, float tpf) {
		GL11.glPushMatrix();
		if(!Minecraft.theMinecraft.noCollidePlayers)
			GL11.glDisable(2884 /*GL_CULL_FACE*/);
		mainModel.onGround = renderSwingProgress(entityliving, tpf);
		if(renderPassModel != null) {
			renderPassModel.onGround = mainModel.onGround;
		}
		mainModel.isRiding = entityliving.isRiding();
		if(renderPassModel != null) {
			renderPassModel.isRiding = mainModel.isRiding;
		}
		mainModel.field_40301_k = entityliving.func_40127_l();
		if(renderPassModel != null) {
			renderPassModel.field_40301_k = mainModel.field_40301_k;
		}
		try {
			float renderYawOffset = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * tpf;
			float rotationYaw = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * tpf;
			float rotationPitch = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * tpf;
			renderLivingAt(entityliving, x, y, z);
			float stayingAnimation = handleRotationFloat(entityliving, tpf);
			rotateCorpse(entityliving, stayingAnimation, renderYawOffset, tpf);
			float f6 = 0.0625F;
			GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
			GL11.glScalef(-1F, -1F, 1.0F);
			// GreenCubes: i think, we should have chance to scale anything, not only GIANT ZOMBIES
			if(this.scale != 0.0F)
				GL11.glScalef(this.scale, this.scale, this.scale);
			preRenderCallback(entityliving, tpf);
			GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
			float f7 = entityliving.field_705_Q + (entityliving.field_704_R - entityliving.field_705_Q) * tpf;
			float f8 = entityliving.field_703_S - entityliving.field_704_R * (1.0F - tpf);
			if(entityliving.func_40127_l()) {
				f8 *= 3F;
			}
			if(f7 > 1.0F) {
				f7 = 1.0F;
			}
			GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
			mainModel.setLivingAnimations(entityliving, f8, f7, tpf);
			func_40270_a(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
			for(int i = 0; i < 4; i++) {
				int j = shouldRenderPass(entityliving, i, tpf);
				if(j <= 0) {
					continue;
				}
				renderPassModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
				if(j == 15) {
					float f10 = entityliving.ticksExisted + tpf;
					loadTexture("%blur%/misc/glint.png");
					GL11.glEnable(3042 /*GL_BLEND*/);
					float f12 = 0.5F;
					GL11.glColor4f(f12, f12, f12, 1.0F);
					GL11.glDepthFunc(514);
					GL11.glDepthMask(false);
					for(int i1 = 0; i1 < 2; i1++) {
						GL11.glDisable(2896 /*GL_LIGHTING*/);
						float f15 = 0.76F;
						GL11.glColor4f(0.5F * f15, 0.25F * f15, 0.8F * f15, 1.0F);
						GL11.glBlendFunc(768, 1);
						GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
						GL11.glLoadIdentity();
						float f17 = f10 * (0.001F + i1 * 0.003F) * 20F;
						float f18 = 0.3333333F;
						GL11.glScalef(f18, f18, f18);
						GL11.glRotatef(30F - i1 * 60F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, f17, 0.0F);
						GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
						renderPassModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
					}

					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
					GL11.glDepthMask(true);
					GL11.glLoadIdentity();
					GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
					GL11.glEnable(2896 /*GL_LIGHTING*/);
					GL11.glDisable(3042 /*GL_BLEND*/);
					GL11.glDepthFunc(515);
				} else if(entityliving instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityliving;
					ItemStack itemstack = player.inventory.armorItemInSlot(3 - i);
					if(itemstack != null && itemstack.isGlowing()) {
						float f10 = entityliving.ticksExisted + tpf;
						loadTexture("%blur%/misc/glint.png");
						GL11.glEnable(3042 /*GL_BLEND*/);
						float f12 = 0.5F;
						GL11.glColor4f(f12, f12, f12, 1.0F);
						GL11.glDepthFunc(514);
						GL11.glDepthMask(false);
						for(int i1 = 0; i1 < 2; i1++) {
							GL11.glDisable(2896 /*GL_LIGHTING*/);
							float f15 = 0.76F;
							if(itemstack.nbtData != null && itemstack.nbtData.hasKey("Glow")) {
								int color = itemstack.nbtData.getInteger("Glow");
								GL11.glColor4f(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, 1.0f);
							} else
								GL11.glColor4f(0.5F * f15, 0.25F * f15, 0.8F * f15, 1.0F);
							GL11.glBlendFunc(768, 1);
							GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
							GL11.glLoadIdentity();
							float f17 = f10 * (0.001F + i1 * 0.003F) * 20F;
							float f18 = 0.3333333F;
							GL11.glScalef(f18, f18, f18);
							GL11.glRotatef(30F - i1 * 60F, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(0.0F, f17, 0.0F);
							GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
							renderPassModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
						}

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
						GL11.glDepthMask(true);
						GL11.glLoadIdentity();
						GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
						GL11.glEnable(2896 /*GL_LIGHTING*/);
						GL11.glDisable(3042 /*GL_BLEND*/);
						GL11.glDepthFunc(515);
					}
				}
				GL11.glDisable(3042 /*GL_BLEND*/);
				GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
			}
			GL11.glDisable(2884 /*GL_CULL_FACE*/);
			renderEquippedItems(entityliving, tpf);
			float f9 = entityliving.getEntityBrightness(tpf);
			int k = getColorMultiplier(entityliving, f9, tpf);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
			GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
			if((k >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0) {
				GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
				GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
				GL11.glEnable(3042 /*GL_BLEND*/);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthFunc(514);
				if(entityliving.hurtTime > 0 || entityliving.deathTime > 0) {
					GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
					mainModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
					for(int l = 0; l < 4; l++) {
						if(inheritRenderPass(entityliving, l, tpf) >= 0) {
							GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
							renderPassModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
						}
					}

				}
				if((k >> 24 & 0xff) > 0) {
					float f11 = (k >> 16 & 0xff) / 255F;
					float f13 = (k >> 8 & 0xff) / 255F;
					float f14 = (k & 0xff) / 255F;
					float f16 = (k >> 24 & 0xff) / 255F;
					GL11.glColor4f(f11, f13, f14, f16);
					mainModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
					for(int j1 = 0; j1 < 4; j1++) {
						if(inheritRenderPass(entityliving, j1, tpf) >= 0) {
							GL11.glColor4f(f11, f13, f14, f16);
							renderPassModel.render(entityliving, f8, f7, stayingAnimation, rotationYaw - renderYawOffset, rotationPitch, f6);
						}
					}

				}
				GL11.glDepthFunc(515);
				GL11.glDisable(3042 /*GL_BLEND*/);
				GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
				GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
			}
			GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
		GL11.glEnable(2884 /*GL_CULL_FACE*/);
		GL11.glPopMatrix();
		passSpecialRender(entityliving, x, y, z);
	}

	protected void func_40270_a(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
		loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
		mainModel.render(entityliving, f, f1, f2, f3, f4, f5);
	}

	protected void renderLivingAt(EntityLiving entityliving, double d, double d1, double d2) {
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
	}

	protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2) {
		GL11.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
		if(entityliving.deathTime > 0) {
			float f3 = (((entityliving.deathTime + f2) - 1.0F) / 20F) * 1.6F;
			f3 = MathHelper.sqrt_float(f3);
			if(f3 > 1.0F) {
				f3 = 1.0F;
			}
			GL11.glRotatef(f3 * getDeathMaxRotation(entityliving), 1.0F, 0.0F, 1.0F);
		}
	}

	protected float renderSwingProgress(EntityLiving entityliving, float f) {
		return entityliving.getSwingProgress(f);
	}

	protected float handleRotationFloat(EntityLiving entityliving, float f) {
		return entityliving.ticksExisted + f;
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f) {
	}

	protected int inheritRenderPass(EntityLiving entityliving, int i, float f) {
		return shouldRenderPass(entityliving, i, f);
	}

	protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return -1;
	}

	protected float getDeathMaxRotation(EntityLiving entityliving) {
		return 90F;
	}

	protected int getColorMultiplier(EntityLiving entityliving, float f, float f1) {
		return 0;
	}

	protected void preRenderCallback(EntityLiving entityliving, float f) {
	}
	
	protected boolean hasOrganizationLabel(EntityLiving entity) {
		if(!(entity instanceof EntityPlayer))
			return false;
		EntityPlayer player = (EntityPlayer) entity;
		if(player.organizationUrl == null)
			return false;
		RenderEngine renderengine = renderManager.renderEngine;
		Minecraft.theMinecraft.renderEngine.blurTexture = true;
		boolean b = renderengine.getTextureForDownloadableImage(player.organizationUrl, null) >= 0;
		Minecraft.theMinecraft.renderEngine.blurTexture = false;
		return b;
	}

	protected void passSpecialRender(EntityLiving entity, double d, double d1, double d2) {
		Tessellator tessellator = Tessellator.instance;
		FontRenderer fr = getFontRendererFromRenderManager();
		if(Minecraft.isGuiEnabled()) {
			float f = 1.6F;
			float scale = 0.01666667F * f;
			if(entity.getDistanceToEntity(renderManager.livingPlayer) < (entity.isSneaking() ? 32F : 64F)) {
				float height = entity.height + 0.5f;
				if(entity.isPlayerSleeping())
					height -= 1.5f;
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d + 0.0F, (float) d1 + height, (float) d2);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-scale, -scale, scale);
				
				float labelWidth = 0;

				// Organization label and player name
				if(entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					String name = player.username;
					if(player instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP) player).coloredName != null)
						name = ((EntityOtherPlayerMP) player).coloredName;
					int nameWidth = fr.getStringWidth(name);
					boolean org = hasOrganizationLabel(entity);
					labelWidth = nameWidth + (org ? 8 : 0) + 2;
					
					GL11.glDepthMask(false);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					// Label bg
					tessellator.startDrawingQuads();
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator.addVertex(- labelWidth / 2, 0, 0.0D);
					tessellator.addVertex(- labelWidth / 2, 8, 0.0D);
					tessellator.addVertex(labelWidth / 2, 8, 0.0D);
					tessellator.addVertex(labelWidth / 2, 0, 0.0D);
					tessellator.draw();
					// Label content
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					if(org) { // Organization (no depth)
						Minecraft.theMinecraft.renderEngine.blurTexture = true;
						loadDownloadableImageTexture(player.organizationUrl, null);
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(- labelWidth / 2 + 0, 0, 0.0D, 0d, 0d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 0, 8, 0.0D, 0d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 8, 8, 0.0D, 1d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 8, 0, 0.0D, 1d, 0d);
						tessellator.draw();
					}
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(true);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					if(org) { // Organization
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(- labelWidth / 2 + 0, 0, 0.0D, 0d, 0d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 0, 8, 0.0D, 0d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 8, 8, 0.0D, 1d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 8, 0, 0.0D, 1d, 0d);
						tessellator.draw();
						Minecraft.theMinecraft.renderEngine.blurTexture = false;
					}
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(false);
					fr.drawString(name, (int) - labelWidth / 2 + 1 + (org ? 9 : 0), 0, 0x20ffffff);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(true);
					fr.drawString(name, (int) - labelWidth / 2 + 1 + (org ? 9 : 0), 0, -1);
					GL11.glTranslatef(0, 8, 0);
				}
				
				boolean isDanger = entity.isDanger();
				if(isDanger || entity.getEntityHealth() != entity.maxHealth) {
					// Health
					float percent = (float) entity.getEntityHealth() / (float) entity.maxHealth;
					String helath = entity.getEntityHealth() + " / " + entity.maxHealth;
					labelWidth = Math.max(labelWidth, fr.getStringWidth(helath) + 2);
					
					labelWidth *= 2;
					labelWidth -= 1;
					GL11.glScalef(0.5f, 0.5f, 0.5f);
					
					GL11.glDepthMask(true);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					// Label bg
					tessellator.startDrawingQuads();
					ColorRGBA hpColor = isDanger ? healthDangerColor : healthPeacefulColor;
					tessellator.setColorRGBA_F(hpColor.r, hpColor.g, hpColor.b, 0.7F);
					tessellator.addVertex(- labelWidth / 2, 0.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2, 9.5, 0.0D);
					float w = labelWidth * percent;
					tessellator.addVertex(- labelWidth / 2 + w, 9.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2 + w, 0.5, 0.0D);
					if(percent < 1.0f) {
						tessellator.setColorRGBA_F(0.3F, 0.3F, 0.3F, 0.7F);
						tessellator.addVertex(- labelWidth / 2 + w, 0.5, 0.0D);
						tessellator.addVertex(- labelWidth / 2 + w, 9.5, 0.0D);
						tessellator.addVertex(labelWidth / 2, 9.5, 0.0D);
						tessellator.addVertex(labelWidth / 2, 0.5, 0.0D);
					}
					// Border
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.7F);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 0.0, 0.0D);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 0.5, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 0.5, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 0.0, 0.0D);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 0.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 9.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2, 9.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2, 0.5, 0.0D);
					tessellator.addVertex(labelWidth / 2, 0.5, 0.0D);
					tessellator.addVertex(labelWidth / 2, 9.5, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 9.5, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 0.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 9.5, 0.0D);
					tessellator.addVertex(- labelWidth / 2 - 0.5, 10.0, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 10.0, 0.0D);
					tessellator.addVertex(labelWidth / 2 + 0.5, 9.5, 0.0D);
					tessellator.draw();
					// Label content
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glTranslatef(0, 1, -1);
					fr.drawString(helath, - fr.getStringWidth(helath) / 2, 0, -1);
					GL11.glTranslatef(0, -1, 1);
					GL11.glScalef(2f, 2f, 2f);
					GL11.glTranslatef(0, -8, 0);
					labelWidth += 1;
					labelWidth /= 2;
				}
				
				List<String> icons = new ArrayList<String>();
				if(entity.activeBuffs.size() > 0 && !(entity instanceof EntityPlayer)) {
					TIntObjectIterator<BuffActive> iterator = entity.activeBuffs.iterator();
					while(iterator.hasNext()) {
						iterator.advance();
						BuffActive ab = iterator.value();
						if(ab.buff.getTextureFramed() != null)
							icons.add(ab.buff.getTextureFramed());
					}
				}
				if(icons.size() > 0) {
					labelWidth = Math.max(labelWidth, icons.size() * 5);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glTranslatef(0, -4.5f, 0);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(true);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					for(int i = 0; i < icons.size(); ++i) {
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						tessellator.startDrawingQuads();
						tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
						tessellator.addVertex(- labelWidth / 2 + i * 5, 0, 0.0D);
						tessellator.addVertex(- labelWidth / 2 + i * 5, 4, 0.0D);
						tessellator.addVertex(- labelWidth / 2 + 4 + i * 5, 4, 0.0D);
						tessellator.addVertex(- labelWidth / 2 + 4 + i * 5, 0, 0.0D);
						tessellator.draw();
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						loadTexture(icons.get(i));
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(- labelWidth / 2 + i * 5, 0, -0.01D, 0d, 0d);
						tessellator.addVertexWithUV(- labelWidth / 2 + i * 5, 4, -0.01D, 0d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 4 + i * 5, 4, -0.01D, 1d, 1d);
						tessellator.addVertexWithUV(- labelWidth / 2 + 4 + i * 5, 0, -0.01D, 1d, 0d);
						tessellator.draw();
					}
				}
				
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}
		}
		
		//renderStatusLine(entity, d, d1, d2, 3.0d, 0.0d);
	}
	
	protected void renderLivingLabel(EntityLiving entityliving, String s, double d, double d1, double d2, int i) {
		renderLivingLabel(entityliving, s, d, d1, d2, i, 1.6f, 0.0f, 0.0f);
	}

	protected void renderLivingLabel(EntityLiving entityliving, String s, double d, double d1, double d2, int i, float scale, float xTranslation, double minWidth) {
		float f = entityliving.getDistanceToEntity(renderManager.livingPlayer);
		if(f > i)
			return;
		FontRenderer fontrenderer = getFontRendererFromRenderManager();
		double j = fontrenderer.getStringWidth(s) / 2 + 1;
		if(j < minWidth / 2.0d)
			j = minWidth / 2.0d;
		float f1 = scale;
		float f2 = 0.01666667F * f1;
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1 + 2.3F, (float) d2);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDepthMask(false);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if(xTranslation != 0.0f)
			GL11.glTranslatef(xTranslation, 0, 0);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex(-j, -1, 0.0D);
		tessellator.addVertex(-j, 8, 0.0D);
		tessellator.addVertex(j, 8, 0.0D);
		tessellator.addVertex(j, -1, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0x20ffffff);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, -1);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
	}
}
