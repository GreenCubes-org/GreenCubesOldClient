// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ModelBase, ModelRenderer, MathHelper, Entity

public class ModelSnowmanHead extends ModelBase {

	public ModelRenderer bipedHead;
	public ModelRenderer bipedHeadwear;
	public int field_1279_h;
	public int field_1278_i;
	public boolean isSneak;
	public boolean field_40333_u;
	public float scaleX;
	public float scaleY;

	public ModelSnowmanHead() {
		this(0.0F);
	}

	public ModelSnowmanHead(float f) {
		this(f, 0.0F);
	}

	public ModelSnowmanHead(float f, float f1) {
		scaleX = f;
		scaleY = f1;
		field_1279_h = 0;
		field_1278_i = 0;
		isSneak = false;
		field_40333_u = false;
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4F, -5F, -4F, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		bipedHeadwear = new ModelRenderer(this, 32, 0);
		bipedHeadwear.addBox(-4F, -5F, -4F, 8, 8, 8, f + 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		bipedHead.render(f5);
		bipedHeadwear.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		bipedHead.rotateAngleY = f3 / 57.29578F;
		bipedHead.rotateAngleX = f4 / 57.29578F;
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;

		if(isSneak) {
			bipedHead.rotationPointY = 1.0F;
		} else {
			bipedHead.rotationPointY = 0.0F;
		}
	}
}
