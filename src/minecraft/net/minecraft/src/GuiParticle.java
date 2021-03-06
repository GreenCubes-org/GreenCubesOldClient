// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Gui, Particle, RenderEngine

public class GuiParticle extends Gui {

	private List particles;
	private Minecraft mc;

	public GuiParticle(Minecraft minecraft) {
		particles = new ArrayList();
		mc = minecraft;
	}

	public void update() {
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = (Particle) particles.get(i);
			particle.preUpdate();
			particle.update(this);
			if(particle.isDead) {
				particles.remove(i--);
			}
		}

	}

	public void draw(float f) {
		mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/particles.png"));
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = (Particle) particles.get(i);
			int j = (int) ((particle.prevPosX + (particle.posX - particle.prevPosX) * f) - 4D);
			int k = (int) ((particle.prevPosY + (particle.posY - particle.prevPosY) * f) - 4D);
			float f1 = (float) (particle.prevTintAlpha + (particle.tintAlpha - particle.prevTintAlpha) * f);
			float f2 = (float) (particle.prevTintRed + (particle.tintRed - particle.prevTintRed) * f);
			float f3 = (float) (particle.prevTintGreen + (particle.tintGreen - particle.prevTintGreen) * f);
			float f4 = (float) (particle.prevTintBlue + (particle.tintBlue - particle.prevTintBlue) * f);
			GL11.glColor4f(f2, f3, f4, f1);
			drawTexturedModalRect(j, k, 40, 0, 8, 8);
		}

	}
}
