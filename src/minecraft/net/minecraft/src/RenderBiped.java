// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, EntityLiving, ModelBiped, ModelRenderer, 
//            ItemStack, Block, RenderBlocks, Item, 
//            RenderManager, ItemRenderer, ItemPotion

public class RenderBiped extends RenderLiving {

	protected ModelBiped modelBipedMain;
	protected float field_40296_d;

	public RenderBiped(ModelBiped modelbiped, float f) {
		this(modelbiped, f, 1.0F);
		modelBipedMain = modelbiped;
	}

	public RenderBiped(ModelBiped modelbiped, float f, float f1) {
		super(modelbiped, f);
		modelBipedMain = modelbiped;
		field_40296_d = f1;
	}
	
	@Override
	public void doRenderLiving(EntityLiving entityplayer, double d, double d1, double d2, float f, float f1) {
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

	private List<ItemStack> cache = new ArrayList<ItemStack>();

	@Override
	protected void renderEquippedItems(EntityLiving entityliving, float f) {
		super.renderEquippedItems(entityliving, f);
		ItemStack itemstack = entityliving.getHeldItem();
		if(itemstack != null) {
			if(itemstack.getItem().useMultirender(itemstack, ItemRenderType.HANDS)) {
				cache = itemstack.getItem().getMultirender(itemstack, ItemRenderType.HANDS);
			} else {
				cache.clear();
				cache.add(itemstack);
			}
			for(int i = 0; i < cache.size(); ++i) {
				itemstack = cache.get(i);
				GL11.glPushMatrix();
				modelBipedMain.bipedRightArm.postRender(0.0625F);
				GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
				if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
					float f1 = 0.5F;
					GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
					f1 *= 0.75F;
					GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f1, -f1, f1);
				} else if(itemstack.getItem() instanceof ItemBow) {
					float f2 = 0.625F;
					GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
					GL11.glRotatef(-20F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f2, -f2, f2);
					GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				} else if(Item.itemsList[itemstack.itemID].isFull3D()) {
					float f3 = 0.625F;
					GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
					GL11.glScalef(f3, -f3, f3);
					GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				} else {
					float f4 = 0.375F;
					GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
					GL11.glScalef(f4, f4, f4);
					GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
				}
				int i2 = itemstack.getItem().getColorFromIS(itemstack);
				float f3 = (i2 >> 16 & 0xff) / 255F;
				float f6 = (i2 >> 8 & 0xff) / 255F;
				float f8 = (i2 & 0xff) / 255F;
				GL11.glColor4f(f3, f6, f8, 1.0F);
				renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
				if(itemstack.itemID == Item.potion.shiftedIndex) {
					renderManager.itemRenderer.renderItem(entityliving, itemstack, 1);
				}
				GL11.glPopMatrix();
			}
		}
	}
}
