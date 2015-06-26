// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.greencubes.util.I18n;

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
		"ny2015beard", "bluenyhat2014beard", "rednyhat2013beard", "bone.armor", "bone.crown",
	};

	/**
	 * Hand, feet, pants, body, head
	 */
	protected static final double wearablesHeight[] = {0.7D, 0.1D, 0.25D, 0.6D, 0.85D, 0.7D};

	protected final Random rand = new Random();
	public final int renderIndex;
	public final int slot;
	protected BuffEffect[] defaultEffects;
	protected Buff[] defaultBuffs;

	public ItemWearable(int i, int texture, int slot) {
		super(i);
		this.slot = slot;
		this.renderIndex = texture;
		this.maxStackSize = 1;
	}
	
	protected ItemWearable setDefaultEffects(Buff[] buffs, BuffEffect[] effects) {
		this.defaultEffects = effects;
		this.defaultBuffs = buffs;
		return this;
	}
	
	public float getDefaultBuffMultiplier(BuffEffectType type) {
		if(defaultEffects != null)
			for(BuffEffect bf : defaultEffects)
				if(bf.type == type)
					return bf.multipler;
		return 0;
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
	
	public List<Buff> getBuffs(EntityLiving entity, ItemStack item) {
		List<Buff> buffs = new ArrayList<Buff>();
		if(item.nbtData != null) {
			if(item.nbtData.hasKey("RunSpeed") && item.nbtData.getFloat("RunSpeed") != 0.0f)
				buffs.add(Buff.FAST_RUN);
			if(item.nbtData.hasKey("SnowBoost") && item.nbtData.getInteger("SnowBoost") != 0)
				buffs.add(Buff.SNOW_BOOST);
			if(item.nbtData.hasKey("BuffCriticalChance") && item.nbtData.getFloat("BuffCriticalChance") != 0.0f)
				buffs.add(Buff.CRITICAL_CHANCE_ARMOR);
			if(item.nbtData.hasKey("BuffBowSpeed") && item.nbtData.getFloat("BuffBowSpeed") != 0.0f)
				buffs.add(Buff.BOW_SPEED_ARMOR);
		}
		if(defaultBuffs != null) {
			for(Buff b : defaultBuffs) {
				if(!buffs.contains(b)) {
					if(b == Buff.FAST_RUN) {
						if(item.nbtData != null && item.nbtData.hasKey("RunSpeed") && item.nbtData.getFloat("RunSpeed") == 0.0f)
							continue;
					} else if(b == Buff.SNOW_BOOST) {
						if(item.nbtData != null && item.nbtData.hasKey("SnowBoost") && item.nbtData.getInteger("SnowBoost") == 0)
							continue;
					} else if(b == Buff.CRITICAL_CHANCE_ARMOR) {
						if(item.nbtData != null && item.nbtData.hasKey("BuffCriticalChance") && item.nbtData.getFloat("BuffCriticalChance") == 0.0f)
							continue;
					} else if(b == Buff.BOW_SPEED_ARMOR) {
						if(item.nbtData != null && item.nbtData.hasKey("BuffBowSpeed") && item.nbtData.getFloat("BuffBowSpeed") == 0.0f)
							continue;
					}
					buffs.add(b);
				}
			}
		}
		return buffs;
	}
	
	@Override
	public void appendAttributes(ItemStack itemStack, List<String> target) {
		List<Buff> buffs = getBuffs(null, itemStack);
		for(int i = 0; i < buffs.size(); ++i) {
			Buff buff = buffs.get(i);
			if(buff == Buff.FAST_RUN) {
				float speed = 0;
				if(itemStack.getNBTData() != null && itemStack.getNBTData().hasKey("RunSpeed")) {
					speed = itemStack.getNBTData().getFloat("RunSpeed");
				} else {
					Item boots = itemStack.getItem();
					if(boots != null && boots instanceof ItemSpeedBoots)
						speed = ((ItemSpeedBoots) boots).speed;
				}
				if(speed > 0.0f)
					target.add(I18n.get("\247rffaaffff+%d%% к скорости бега", (int) (speed * 100))); // TODO translate
				else if(speed < 0.0f)
					target.add(I18n.get("\247rffff4444-%d%% к скорости бега", (int) (speed * -100))); // TODO translate
			} else if(buff == Buff.SNOW_BOOST) {
				int bonus = 0;
				if(itemStack.getNBTData() != null && itemStack.getNBTData().hasKey("SnowBoost")) {
					bonus = itemStack.getNBTData().getInteger("SnowBoost");
				} else {
					Item valenki = itemStack.getItem();
					if(valenki != null && valenki instanceof ItemValenki)
						bonus = ((ItemValenki) valenki).bonus;
				}
				if(bonus != 0)
					target.add(I18n.get("\247rffaaffff+%d к скорости передвижения по снегу", bonus)); // TODO translate
			} else if(buff == Buff.CRITICAL_CHANCE_ARMOR) {
				float bonus = 0f;
				if(itemStack.getNBTData() != null && itemStack.getNBTData().hasKey("BuffCriticalChance")) {
					bonus = itemStack.getNBTData().getFloat("BuffCriticalChance");
				} else {
					bonus = getDefaultBuffMultiplier(BuffEffectType.CRITICAL_CHANCE);
				}
				if(bonus != 1)
					target.add(I18n.get("\247rffaaffffКритический урон: x%.1f", bonus)); // TODO translate
			} else if(buff == Buff.BOW_SPEED_ARMOR) {
				float bonus = 0f;
				if(itemStack.getNBTData() != null && itemStack.getNBTData().hasKey("BuffBowSpeed")) {
					bonus = itemStack.getNBTData().getFloat("BuffBowSpeed");
				} else {
					bonus = getDefaultBuffMultiplier(BuffEffectType.BOW_SPEED);
				}
				if(bonus > 0) {
					target.add(I18n.get("\247rffaaffffСкорость натяжения титевы: +%d%%", (int) (bonus * 100))); // TODO translate
					target.add(I18n.get("\247rffaaffffЗамедление при натягивании титевы: -%d%%", (int) (bonus * 100))); // TODO translate
				}
			}
		}
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
								continue effs;
							ItemWearable part = (ItemWearable) setPart.getItem();
							if(part.renderIndex == 44 && this.renderIndex == 45 || part.renderIndex == 45 && this.renderIndex == 4) {
								// Nothing. I'm too stupit to negate this expression
							} else if(part.renderIndex != this.renderIndex)
								continue effs;
							if(!setPart.hasNBTData() || !setPart.nbtData.hasKey("Effects"))
								continue effs;
							NBTTagList partEffects = setPart.nbtData.getTagList("Effects");
							boolean have = false;
							for(int i1 = 0; i1 < partEffects.size(); ++i1) {
								NBTTagCompound tag = (NBTTagCompound) partEffects.get(i1);
								int t = tag.getInteger("T");
								if(t == type || (t == 3 && type == 4)) {
									have = true;
									break;
								}
							}
							if(!have)
								continue effs;
						}
					}
					EntityFXGC fx = null;
					switch(type) {
					case 1:
						if(slot == 0) // Not for item in hands effect
							break;
						double x = entity.posX + rand.nextDouble() * 0.6D - 0.3D;
						double z = entity.posZ + rand.nextDouble() * 0.6D - 0.3D;
						double y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * wearablesHeight[slot] + rand.nextDouble() * 0.6D - 0.3D;
						fx = new EntityLightFXGC(entity.worldObj, x, y, z, entity, wearablesHeight[slot]);
						break;
					case 2:
						if(slot == 0) // Not for item in hands effect
							break;
						x = entity.posX + rand.nextDouble() * 2.4D - 1.2D;
						z = entity.posZ + rand.nextDouble() * 2.4D - 1.2D;
						y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * wearablesHeight[slot] + rand.nextDouble() * 2.4D - 1.2D;
						fx = new EntityDarknessFXGC(entity.worldObj, x, y, z, entity, wearablesHeight[slot]);
						break;
					case 3:
						if(slot == 0) // Not for item in hands effect
							break;
						x = entity.posX + rand.nextDouble() * 2.0D - 1.0D;
						z = entity.posZ + rand.nextDouble() * 2.0D - 1.0D;
						y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5d + rand.nextDouble() * 2.4D - 1.2D;
						fx = new EntitySandFXGC(entity.worldObj, x, y, z, entity);
						break;
					case 4:
						if(slot == 0) // Not for item in hands effect
							break;
						x = entity.posX + rand.nextDouble() * 2.0D - 1.0D;
						z = entity.posZ + rand.nextDouble() * 2.0D - 1.0D;
						y = entity.boundingBox.minY + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5d + rand.nextDouble() * 2.4D - 1.2D;
						fx = new EntitySandUnusualFXGC(entity.worldObj, x, y, z, entity);
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
