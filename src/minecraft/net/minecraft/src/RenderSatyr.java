package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class RenderSatyr extends RenderLiving {

	private ModelBiped modelBipedMain;

	public RenderSatyr() {
		super(new ModelBiped(0.0F), 0.5F);
		modelBipedMain = (ModelBiped) mainModel;
	}

	public void renderPlayer(EntityLiving entityplayer, double d, double d1, double d2, float f, float f1) {
		DistributedModels.updateForRender(entityplayer);
		ItemStack itemstack = entityplayer.getHeldItem();
		modelBipedMain.field_1278_i = itemstack == null ? 0 : 1;
		if(itemstack != null && entityplayer.func_35205_Y() > 0) {
			EnumAction enumaction = itemstack.getItemUseAction();
			if(enumaction == EnumAction.block) {
				modelBipedMain.field_1278_i = 3;
			} else if(enumaction == EnumAction.bow) {
				modelBipedMain.field_40333_u = true;
			}
		}
		modelBipedMain.isSneak = entityplayer.isSneaking();
		double d3 = d1 - entityplayer.yOffset;
		if(entityplayer.isSneaking() && !(entityplayer instanceof EntityPlayerSP)) {
			d3 -= 0.125D;
		}
		super.doRenderLiving(entityplayer, d, d3, d2, f, f1);
		modelBipedMain.field_40333_u = false;
		modelBipedMain.isSneak = false;
		modelBipedMain.field_1278_i = 0;
		DistributedModels.reset();
	}

	protected void renderPlayerScale(EntityLiving entityplayer, float f) {
		float f1 = 0.9375F;
		GL11.glScalef(f1, f1, f1);
	}

	@Override
	protected void passSpecialRender(EntityLiving entityliving, double d, double d1, double d2) {
		renderStatusLine(entityliving, d, d1, d2, 3.0d);
	}

	@Override
	protected void preRenderCallback(EntityLiving entityliving, float f) {
		renderPlayerScale(entityliving, f);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		renderPlayer(entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		renderPlayer((EntityLiving) entity, d, d1, d2, f, f1);
	}

}