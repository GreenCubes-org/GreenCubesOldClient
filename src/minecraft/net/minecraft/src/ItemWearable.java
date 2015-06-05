// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Item, EnumArmorMaterial

public abstract class ItemWearable extends Item {

	private static final Set<String> obtainedUrls = new HashSet<String>();
	public static final String armorFilenamePrefix[] = {"cloth", "chain", "iron", "diamond", "gold", "red",
		"blue", "green", "orange", "velvet", "cyan", "ny", "dark", "light", "earflapshat", "bowhat",
		"keffiyehhat", "crownhat", "creeper_mask", "enderman_mask", "skeleton_mask", "spider_mask",
		"steve_mask", "zombie_mask", "speed_boots", "japanese_bandage", "watermelon", "laureate_wreath",
		"red_dotted_bow", "black_bow", "samurai", "blue_xmas_hat", "felt_boots", "bowler_hat", "gent_set",
		"guyfawkes_mask", "monocle", "mustache", "tie", "dealwithitglasses", "dealwithitglassesgold",
		"ny2015beard", "bluenyhat2014beard", "rednyhat2013beard"
	};

	/**
	 * Hand, feet, pants, body, head
	 */
	protected static final double wearablesHeight[] = {0.7D, 0.1D, 0.25D, 0.6D, 0.85D};

	protected final Random rand = new Random();
	public final int renderIndex;
	public final int slot;

	public ItemWearable(int i, int texture, int slot) {
		super(i);
		this.slot = slot;
		this.renderIndex = texture;
		this.maxStackSize = 1;
	}

	public void bindTexture(RenderEngine re, ItemStack itemstack) {
		String orig = new StringBuilder().append("/armor/").append(armorFilenamePrefix[this.renderIndex]).append("_").append(slot != 2 ? 1 : 2).append(".png").toString();
		int texture = 0;
		if(itemstack.hasNBTData()) {
			if(itemstack.nbtData.hasKey("Texture"))
				orig = itemstack.nbtData.getString("Texture");
			if(itemstack.nbtData.hasKey("TextureUrl")) {
				if(obtainedUrls.add(itemstack.nbtData.getString("TextureUrl")))
					re.obtainImageData(itemstack.nbtData.getString("TextureUrl"), new ImageBufferDownload());
				texture = re.getTextureForDownloadableImage(itemstack.nbtData.getString("TextureUrl"), orig);
			}
		}
		if(texture == 0)
			texture = re.getTexture(orig);
		re.bindTexture(texture);
	}

	public ModelBase getModel(ItemStack itemStack) {
		ModelBiped modelbiped = slot != 2 ? DistributedModels.modelArmorChestplate : DistributedModels.modelArmor;
		modelbiped.bipedHead.showModel = slot == 0;
		modelbiped.bipedHeadwear.showModel = slot == 0;
		modelbiped.bipedBody.showModel = slot == 1 || slot == 2;
		modelbiped.bipedRightArm.showModel = slot == 1;
		modelbiped.bipedLeftArm.showModel = slot == 1;
		modelbiped.bipedRightLeg.showModel = slot == 2 || slot == 3;
		modelbiped.bipedLeftLeg.showModel = slot == 2 || slot == 3;
		return modelbiped;
	}

	@Override
	public void updateInInventory(ItemStack item, EntityLiving entity, int slot) {
		if(item.hasNBTData() && item.nbtData.hasKey("Effects")) {
			NBTTagList effects = item.nbtData.getTagList("Effects");
			effs: for(int i = 0; i < effects.size(); ++i) {
				NBTTagCompound effect = (NBTTagCompound) effects.get(i);
				if(effect.getBoolean("F"))
					return;
				int intense = 2;
				if(effect.hasKey("I"))
					intense = effect.getInteger("I");
				if(rand.nextInt(intense) == 0) {
					int type = effect.getInteger("T");
					if(effect.hasKey("S")) {
						for(int s = 1; s < 5; ++s) {
							if(s == slot)
								continue;
							ItemStack setPart = entity.getEquipment(s);
							if(setPart == null || setPart.isBroken() || !(setPart.getItem() instanceof ItemWearable))
								return;
							ItemWearable part = (ItemWearable) setPart.getItem();
							if(part.renderIndex != this.renderIndex)
								return;
							if(!setPart.hasNBTData() || !setPart.nbtData.hasKey("Effects"))
								return;
							NBTTagList partEffects = setPart.nbtData.getTagList("Effects");
							boolean have = false;
							for(int i1 = 0; i1 < partEffects.size(); ++i1) {
								NBTTagCompound tag = (NBTTagCompound) partEffects.get(i1);
								if(tag.getInteger("T") == type && tag.getBoolean("S") && tag.getBoolean("F")) {
									have = true;
									break;
								}
							}
							if(!have)
								break effs;
						}
					}
					EntityFXGC fx = null;
					switch(type) {
					case 1:
						double x = entity.posX + rand.nextDouble() * 0.6D - 0.3D;
						double z = entity.posZ + rand.nextDouble() * 0.6D - 0.3D;
						double y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * wearablesHeight[slot] + rand.nextDouble() * 0.6D - 0.3D;
						fx = new EntityLightFXGC(entity.worldObj, x, y, z, entity, wearablesHeight[slot]);
						break;
					case 2:
						x = entity.posX + rand.nextDouble() * 2.4D - 1.2D;
						z = entity.posZ + rand.nextDouble() * 2.4D - 1.2D;
						y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * wearablesHeight[slot] + rand.nextDouble() * 2.4D - 1.2D;
						fx = new EntityDarknessFXGC(entity.worldObj, x, y, z, entity, wearablesHeight[slot]);
						break;
					default:
						return;
					}
					if(fx != null)
						Minecraft.theMinecraft.effectRenderer.addEffect(fx);
				}
			}
		}
	}

}