// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, ModelSnowMan, ItemStack, Block, 
//            Item, ModelRenderer, RenderBlocks, RenderManager, 
//            ItemRenderer, EntitySnowman, EntityLiving

public class RenderSnowMan extends RenderLiving {

	private ModelSnowMan snowmanModel;
	private ModelBase modelArmor;

	public RenderSnowMan() {
		super(new ModelSnowMan(), 0.5F);
		snowmanModel = (ModelSnowMan) super.mainModel;
		setRenderPassModel(snowmanModel);
		modelArmor = new ModelSnowmanHead(0.0F);
	}

	@Override
	protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
		if(i == 0) {
			ItemStack itemstack = entityliving.getEquipment(4);
			if(itemstack != null) {
				Item item = itemstack.getItem();
				if(item instanceof ItemWearable) {
					ItemWearable itemarmor = (ItemWearable) item;
					itemarmor.bindTexture(Minecraft.theMinecraft.renderEngine, itemstack);
					setRenderPassModel(modelArmor);
					return !itemstack.hasEnchantment() ? 1 : 15;
				} else if(itemstack.itemID < Block.blocksLength && Block.blocksList[itemstack.itemID] != null) {
					GL11.glPushMatrix();
					this.snowmanModel.field_40305_c.postRender(0.0625F);
					if(RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
						float var4 = 0.625F;
						GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
						GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
						GL11.glScalef(var4, -var4, var4);
					}
					this.renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
					GL11.glPopMatrix();
				}
			}
		}
		return -1;
	}
}
