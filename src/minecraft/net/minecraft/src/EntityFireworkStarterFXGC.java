package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityFireworkStarterFXGC extends EntityFXGC {

	private int field_92042_ax = 0;
	private NBTTagList field_92039_az;
	boolean field_92041_a;

	public EntityFireworkStarterFXGC(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, NBTTagCompound par15NBTTagCompound) {
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		this.motionX = par8;
		this.motionY = par10;
		this.motionZ = par12;
		this.particleMaxAge = 8;

		if(par15NBTTagCompound != null) {
			this.field_92039_az = par15NBTTagCompound.getTagList("Explosions");
			if(this.field_92039_az.size() == 0) {
				this.field_92039_az = null;
			} else {
				this.particleMaxAge = this.field_92039_az.size() * 2 - 1;

				for(int var16 = 0; var16 < this.field_92039_az.size(); ++var16) {
					NBTTagCompound var17 = (NBTTagCompound) this.field_92039_az.get(var16);

					if(var17.getBoolean("Flicker")) {
						this.field_92041_a = true;
						this.particleMaxAge += 15;
						break;
					}
				}
			}
		}
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		boolean var1;

		if(this.field_92042_ax == 0 && this.field_92039_az != null) {
			var1 = this.isFarFromPlayer();
			boolean var2 = false;

			if(this.field_92039_az.size() >= 3) {
				var2 = true;
			} else {
				for(int var3 = 0; var3 < this.field_92039_az.size(); ++var3) {
					NBTTagCompound var4 = (NBTTagCompound) this.field_92039_az.get(var3);

					if(var4.getByte("Type") == 1) {
						var2 = true;
						break;
					}
				}
			}

			String var15 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
			this.worldObj.playSound(this.posX, this.posY, this.posZ, var15, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
		}

		if(this.field_92042_ax % 2 == 0 && this.field_92039_az != null && this.field_92042_ax / 2 < this.field_92039_az.size()) {
			int var13 = this.field_92042_ax / 2;
			NBTTagCompound var14 = (NBTTagCompound) this.field_92039_az.get(var13);
			byte var17 = var14.getByte("Type");
			boolean trail = var14.getBoolean("Trail");
			boolean flicker = var14.getBoolean("Flicker");
			int[] colors = var14.getIntArray("Colors");
			int[] fadeColors = var14.getIntArray("FadeColors");

			if(var17 == 1) {
				this.spawnSphere(0.5D, 4, colors, fadeColors, trail, flicker);
			} else if(var17 == 2) {
				this.spawnShape(0.5D, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, colors, fadeColors, trail, flicker, false);
			} else if(var17 == 3) {
				this.spawnShape(0.5D, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, colors, fadeColors, trail, flicker, true);
			} else if(var17 == 4) {
				this.spawnBlow(colors, fadeColors, trail, flicker);
			} else if(var17 == 5) {
				this.spawnUnsimmetry(0.5D, new double[][]{{0.0D, 0.0D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.6D, 0.0D}, {0.8D, 0.0D}, {0.8D, -0.2D}, {0.8D, -0.4D}, {0.8D, -0.6D}, {0.8D, -0.8D}, {0.6D, -0.8D}, {0.4D, -0.8D}, {0.2D, -0.8D}, {0.0, -0.8}, {-0.2, -0.8}, {-0.4, -0.8}, {-0.6, -0.8}, {-0.8, -0.8}, {-0.8, -0.6}, {-0.8, -0.4}, {-0.8, -0.2}, {-0.8, 0.0}, {-0.8, 0.2}, {-0.8, 0.4}, {-0.8, 0.6}, {-0.8, 0.8}, {-0.6, 0.8}, {-0.4, 0.8}, {-0.2, 0.8}, {0.0, 0.8}, {0.2, 0.8}, {0.4, 0.8}, {0.6, 0.8}, {0.8, 0.8}}, colors, fadeColors, trail, flicker, true);
			} else {
				this.spawnSphere(0.25D, 2, colors, fadeColors, trail, flicker);
			}

			int var8 = colors[0];
			float var9 = ((var8 & 16711680) >> 16) / 255.0F;
			float var10 = ((var8 & 65280) >> 8) / 255.0F;
			float var11 = ((var8 & 255) >> 0) / 255.0F;
			EntityFireworkOverlayFXGC var12 = new EntityFireworkOverlayFXGC(this.worldObj, this.posX, this.posY, this.posZ);
			var12.setColor(var9, var10, var11);
			Minecraft.theMinecraft.effectRenderer.addEffect(var12);
		}

		++this.field_92042_ax;

		if(this.field_92042_ax > this.particleMaxAge) {
			if(this.field_92041_a) {
				var1 = this.isFarFromPlayer();
				String var16 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
				this.worldObj.playSound(this.posX, this.posY, this.posZ, var16, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
			}

			this.setEntityDead();
		}
	}

	private boolean isFarFromPlayer() {
		return Minecraft.theMinecraft.renderViewEntity == null || Minecraft.theMinecraft.renderViewEntity.getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
	}

	private void spawnSpark(double x, double y, double z, double motX, double motY, double motZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker) {
		EntityFireworkSparkFXGC var17 = new EntityFireworkSparkFXGC(this.worldObj, x, y, z, motX, motY, motZ);
		var17.setTrail(trail);
		var17.setFlicker(flicker);
		int var18 = this.rand.nextInt(colors.length);
		var17.setColor(colors[var18]);

		if(fadeColors != null && fadeColors.length > 0) {
			var17.setFadeColor(fadeColors[this.rand.nextInt(fadeColors.length)]);
		}

		Minecraft.theMinecraft.effectRenderer.addEffect(var17);
	}

	private void spawnSphere(double par1, int radius, int[] colors, int[] fadeColors, boolean par6, boolean par7) {
		double var8 = this.posX;
		double var10 = this.posY;
		double var12 = this.posZ;

		for(int var14 = -radius; var14 <= radius; ++var14) {
			for(int var15 = -radius; var15 <= radius; ++var15) {
				for(int var16 = -radius; var16 <= radius; ++var16) {
					double var17 = var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
					double var19 = var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
					double var21 = var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
					double var23 = MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / par1 + this.rand.nextGaussian() * 0.05D;
					this.spawnSpark(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, colors, fadeColors, par6, par7);

					if(var14 != -radius && var14 != radius && var15 != -radius && var15 != radius) {
						var16 += radius * 2 - 1;
					}
				}
			}
		}
	}

	private void spawnUnsimmetry(double par1, double[][] shape, int[] colors, int[] fadeColors, boolean par6, boolean par7, boolean par8) {
		double var9 = shape[0][0];
		double var11 = shape[0][1];
		//this.func_92034_a(this.posX, this.posY, this.posZ, var9 * par1, var11 * par1, 0.0D, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
		float var13 = this.rand.nextFloat() * (float) Math.PI;
		double var14 = par8 ? 0.034D : 0.34D;

		for(int var16 = 0; var16 < 2; ++var16) {
			double var17 = var13 + (var16 * (float) Math.PI) * var14;
			double var19 = var9;
			double var21 = var11;

			for(int var23 = 1; var23 < shape.length; ++var23) {
				double var24 = shape[var23][0];
				double var26 = shape[var23][1];

				for(double var28 = 0.25D; var28 <= 1D; var28 += 0.25D) {
					double var30 = (var19 + (var24 - var19) * var28) * par1;
					double var32 = (var21 + (var26 - var21) * var28) * par1;
					double var34 = var30 * Math.sin(var17);
					var30 *= Math.cos(var17);
					this.spawnSpark(this.posX, this.posY, this.posZ, var30, var32, var34, colors, fadeColors, par6, par7);
				}

				var19 = var24;
				var21 = var26;
			}
		}
	}

	private void spawnShape(double par1, double[][] shape, int[] colors, int[] fadeColors, boolean par6, boolean par7, boolean par8) {
		double var9 = shape[0][0];
		double var11 = shape[0][1];
		this.spawnSpark(this.posX, this.posY, this.posZ, var9 * par1, var11 * par1, 0.0D, colors, fadeColors, par6, par7);
		float var13 = this.rand.nextFloat() * (float) Math.PI;
		double var14 = par8 ? 0.034D : 0.34D;

		for(int var16 = 0; var16 < 3; ++var16) {
			double var17 = var13 + (var16 * (float) Math.PI) * var14;
			double var19 = var9;
			double var21 = var11;

			for(int var23 = 1; var23 < shape.length; ++var23) {
				double var24 = shape[var23][0];
				double var26 = shape[var23][1];

				for(double var28 = 0.25D; var28 <= 1.0D; var28 += 0.25D) {
					double var30 = (var19 + (var24 - var19) * var28) * par1;
					double var32 = (var21 + (var26 - var21) * var28) * par1;
					double var34 = var30 * Math.sin(var17);
					var30 *= Math.cos(var17);

					for(double var36 = -1.0D; var36 <= 1.0D; var36 += 2.0D) {
						this.spawnSpark(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, colors, fadeColors, par6, par7);
					}
				}

				var19 = var24;
				var21 = var26;
			}
		}
	}

	private void spawnBlow(int[] colors, int[] fadeColors, boolean par3, boolean par4) {
		double var5 = this.rand.nextGaussian() * 0.05D;
		double var7 = this.rand.nextGaussian() * 0.05D;

		for(int var9 = 0; var9 < 70; ++var9) {
			double var10 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + var5;
			double var12 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + var7;
			double var14 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
			this.spawnSpark(this.posX, this.posY, this.posZ, var10, var14, var12, colors, fadeColors, par3, par4);
		}
	}

	@Override
	public int getFXLayer() {
		return 0;
	}
}
