package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class EntityDamageFXGC extends EntityFXGC {
	
	public final String string;
	public final Entity source;
	
	public EntityDamageFXGC(Entity source, double x, double y, double z, String string) {
		super(source.worldObj, x, y, z, 0, 0.1d, 0);
		this.source = source;
		this.string = string;
		this.particleMaxAge = 40;
		this.motionX *= 0.1d;
		this.motionZ *= 0.1d;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float framePart, float f1, float f2, float f3, float f4, float f5) {
		float f10 = 0.1F * particleScale;
		float x = (float) ((prevPosX + (posX - prevPosX) * framePart) - interpPosX);
		float y = (float) ((prevPosY + (posY - prevPosY) * framePart) - interpPosY);
		float z = (float) ((prevPosZ + (posZ - prevPosZ) * framePart) - interpPosZ);
		float f = 3F;
		float scale = 0.01666667F * f;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, z);
		RenderManager renderManager = RenderManager.instance;
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glScalef(-scale, -scale, scale);
		int width = renderManager.getFontRenderer().getStringWidth(string);
		renderManager.getFontRenderer().drawString(string, -width / 2, 0, -1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public int getFXLayer() {
		return 3;
	}
}
