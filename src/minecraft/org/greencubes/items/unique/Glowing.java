package org.greencubes.items.unique;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.GCUtil;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemWearable;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemTool;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagDouble;
import net.minecraft.src.NBTTagFloat;

import org.greencubes.util.HSVColor;
import org.greencubes.util.collections.FastList;

public class Glowing {
	
	private static final ItemStack[] iObj = new ItemStack[0];
	
	public static final int colorModifier = 0;
	public static final int glowingLevelModifier = 1;
	public static final int extractModifier = 2;
	public static final int extractModifierWithColor = 3;
	public static final int clearModifier = 4;
	
	private static final int maxCombineLevel = 10;
	private static final int[] lightingLevels = new int[] {0, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 74, 78, 82, 86, 90, 92, 94, 96, 97, 98, 99, 100};
	private static final int[] saturationLevels = new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 85, 90, 94, 98, 100};
	public static final float[] complexity = new float[] {60F, 30F, 15F, 7.5F, 3.75F, 1.875F, 0.9375F, 0.46875F, 0.234375F};
	private static final TIntObjectMap<String> defaultColors = new TIntObjectHashMap<String>();
	
	public int lightingLevel = 0;
	public float hColorPart;
	public int sColorPart;
	
	public Glowing(int level, NamedColor color) {
		this(level, color.h, color.s);
	}
	
	public Glowing(int level, float h, int s) {
		this.lightingLevel = level;
		this.hColorPart = h;
		this.sColorPart = s;
	}
	
	public Glowing() {
	}
	
	public Glowing(NBTTagCompound colorTag) {
		this(colorTag.getByte("Lv"), colorTag.getFloat("Hp"), colorTag.getByte("Sp"));
	}
	
	public NamedColor getNamedColor() {
		return NamedColor.getByHS(hColorPart, sColorPart);
	}
	
	public void applyToSource(ItemStack item) {
		NBTTagCompound colorTag = new NBTTagCompound();
		HSVColor color = getHSVResult();
		HSVColor renderColor = new HSVColor(color.h, color.s, 1.0F);
		colorTag.setByte("Lv", (byte) lightingLevel);
		colorTag.setByte("Sp", (byte) sColorPart);
		colorTag.setFloat("Hp", hColorPart);
		colorTag.setInteger("Rc", renderColor.getRGB());
		NamedColor nc = getNamedColor();
		if(nc != null)
			colorTag.setString("Cn", nc.translatedName);
		else if(getComplexity() == 0)
			colorTag.setString("Cn", defaultColors.get((int) hColorPart) + ", " + sColorPart + "% насыщенности");
		if(item.nbtData == null)
			item.nbtData = new NBTTagCompound();
		item.nbtData.setTag("DColorData", colorTag);
	}
	
	public void applyToItem(ItemStack item) {
		HSVColor result = getHSVResult();
		NBTTagCompound colorTag = new NBTTagCompound();
		colorTag.setByte("Lv", (byte) lightingLevel);
		colorTag.setByte("Sp", (byte) sColorPart);
		colorTag.setFloat("Hp", hColorPart);
		NamedColor nc = getNamedColor();
		if(nc != null)
			colorTag.setString("Cn", nc.translatedName);
		else if(getComplexity() == 0)
			colorTag.setString("Cn", defaultColors.get((int) hColorPart) + ", " + sColorPart + "% насыщенности");
		if(item.nbtData == null)
			item.nbtData = new NBTTagCompound();
		item.nbtData.setTag("DColor", colorTag);
		item.nbtData.setInteger("Glow", result.getRGB());
	}
	
	public void applyToWithdrawalModifier(ItemStack item) {
		applyToItem(item);
		HSVColor color = getHSVResult();
		HSVColor renderColor = new HSVColor(color.h, color.s, 1.0F);
		NBTTagCompound colorTag = item.nbtData.getCompoundTag("DColor");
		colorTag.setInteger("Rc", renderColor.getRGB());
	}
	
	public HSVColor getHSVResult() {
		return new HSVColor((float) (hColorPart / 360.0D), sColorPart / 100.0F, lightingLevels[lightingLevel] / 100.0F);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp = Double.doubleToLongBits(hColorPart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + lightingLevel;
		result = prime * result + sColorPart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Glowing))
			return false;
		Glowing other = (Glowing) obj;
		if(Double.doubleToLongBits(hColorPart) != Double.doubleToLongBits(other.hColorPart))
			return false;
		if(lightingLevel != other.lightingLevel)
			return false;
		if(sColorPart != other.sColorPart)
			return false;
		return true;
	}
	
	public int getComplexity() {
		return getComplexity(hColorPart);
	}
	
	public static String getFixedName(NBTTagCompound colorTag) {
		Glowing glow = new Glowing(colorTag);
		NamedColor c = glow.getNamedColor();
		if(c != null)
			return c.translatedName;
		else if(glow.getComplexity() == 0)
			return defaultColors.get((int) glow.hColorPart) + ", " + glow.sColorPart + "% насыщенности";
		return null;
	}
	
	public static ItemStack getCrafting(InventoryCrafting grid) {
		ItemStack target = null;
		List<ItemStack> modifiers = new FastList<ItemStack>();
		for(int j = 0; j < grid.getSizeInventory(); j++) {
			ItemStack is = grid.getStackInSlot(j);
			if(is != null) {
				if(is.itemID == Item.GLOWING_MOD.shiftedIndex) 
					modifiers.add(is);
				else if(!canApplyColor(is) || target != null)
					return null;
				else
					target = is;
			}
		}
		if(modifiers.size() > 0) {
			if(target != null) {
				if(modifiers.size() == 1) {
					// Target and modifier
					ItemStack modifier = modifiers.get(0);
					switch(modifier.itemDamage) {
					case colorModifier:
						if(modifier.nbtData == null || !modifier.nbtData.hasKey("DColorData"))
							return null;
						if(!canApplyColor(target))
							return null;
						Glowing targetGlow = fromItem(target);
						Glowing newGlow = fromSource(modifier);
						if(targetGlow != null) {
							newGlow = combineAsSources(targetGlow, newGlow);
							newGlow.lightingLevel = Math.max(newGlow.lightingLevel, targetGlow.lightingLevel);
						}
						ItemStack result = target.clone();
						newGlow.applyToItem(result);
						return result;
					case glowingLevelModifier:
						targetGlow = fromItem(target);
						if(targetGlow != null) {
							if(targetGlow.lightingLevel < lightingLevels.length - 1) {
								if(targetGlow.lightingLevel == 1)
									targetGlow.lightingLevel = 4;
								else if(targetGlow.lightingLevel < 9)
									targetGlow.lightingLevel += 2;
								else
									targetGlow.lightingLevel++;
								result = target.clone();
								targetGlow.applyToItem(result);
								if(!canRemoveColor(target))
									lockColor(result);
								return result;
							}
						}
						break;
					/*case extractModifier:
						if(canRemoveColor(target)) {
							ItemStack newModifier = new ItemStack(Item.GLOWING_MOD, 1, extractModifierWithColor);
							ItemStack newTarget = target.clone();
							newGlow = removeFromItem(newTarget);
							newGlow.applyToWithdrawalModifier(newModifier);
							return new ExtractRecipe(target, newTarget, modifier, newModifier);
						}
						break;
					case extractModifierWithColor:
						if(modifier.nbtData == null || !modifier.nbtData.hasKey("DColor"))
							return null;
						if(!canApplyColor(target))
							return null;
						if(fromItem(target) != null)
							return null;
						newGlow = fromItem(modifier);
						result = target.clone();
						newGlow.applyToItem(result);
						return new GlowingRecipe(new ItemStack[] {modifier, target}, result, new ItemStack[] {modifier}, target);
					case clearModifier:
						if(fromItem(target) == null || !canRemoveColor(target))
							return null;
						result = target.clone();
						removeFromItem(result);
						return new GlowingRecipe(new ItemStack[] {modifier, target}, result, new ItemStack[] {modifier}, target);*/
					}
				}
			} else if(modifiers.size() > 1) {
				for(int i = 0; i < modifiers.size(); ++i)
					if(modifiers.get(i).itemDamage != colorModifier)
						return null;
				if(modifiers.size() == 3) {
					Glowing newGlow = fromSource(modifiers.get(0));
					if(newGlow == null)
						return null;
					for(int i = 1; i < modifiers.size(); ++i) {
						Glowing g = fromSource(modifiers.get(i));
						if(g == null)
							return null;
						newGlow = combineAsSources(newGlow, g);
					}
					newGlow.sColorPart = 0;
					for(int i = 1; i < modifiers.size(); ++i) {
						Glowing g = fromSource(modifiers.get(i));
						if(newGlow.equals(g))
							return null;
					}
					ItemStack result = new ItemStack(Item.GLOWING_MOD, 1, colorModifier);
					newGlow.applyToSource(result);
					return result;
				} else if(modifiers.size() == 2) {
					Glowing first = fromSource(modifiers.get(0));
					Glowing second = fromSource(modifiers.get(1));
					if(first == null || second == null)
						return null;
					Glowing newGlow = combineAsSources(first, second);
					if(newGlow.equals(first) || newGlow.equals(second))
						return null;
					ItemStack result = new ItemStack(Item.GLOWING_MOD, 1, colorModifier);
					newGlow.applyToSource(result);
					return result;
				}
			}
		}
		return null;
	}
	
	public static Glowing removeFromItem(ItemStack item) {
		Glowing r = fromItem(item);
		if(r != null) {
			item.nbtData.remove("DColor");
			item.nbtData.remove("Glow");
		}
		return r;
	}
	
	public static Glowing fromSource(ItemStack item) {
		if(item.nbtData != null && item.nbtData.hasKey("DColorData"))
			return new Glowing(item.nbtData.getCompoundTag("DColorData"));
		return null;
	}

	public static Glowing fromItem(ItemStack item) {
		if(item.nbtData != null && item.nbtData.hasKey("DColor"))
			return new Glowing(item.nbtData.getCompoundTag("DColor"));
		return null;
	}
	
	public static Glowing fromOldItem(ItemStack item) {
		if(item.nbtData != null && item.nbtData.hasKey("Glow")) {
			HSVColor hsvColor = HSVColor.fromRGB(item.nbtData.getInteger("Glow"));
			return new Glowing(getCurrentLightingLevel((int) (hsvColor.v * 100)), hsvColor.h * 360, (int) (hsvColor.s * 100));
		}
		return null;
	}
	
	public static Glowing combineAsSources(Glowing a, Glowing b) {
		Glowing result = new Glowing();
		result.lightingLevel = Math.min(maxCombineLevel, Math.max(a.lightingLevel + 1, b.lightingLevel + 1));
		if(a.sColorPart == 0 || b.sColorPart == 0) {
			result.hColorPart = a.sColorPart == 0 ? b.hColorPart : a.hColorPart;
			result.sColorPart = (a.sColorPart + b.sColorPart) / 2;
		} else if(a.hColorPart == b.hColorPart && a.sColorPart == b.sColorPart) {
			result.hColorPart = a.hColorPart;
			result.sColorPart = getNextSaturationLevel(a.sColorPart);
		} else if(GCUtil.degreeDiff(a.hColorPart, b.hColorPart) <= 120) {
			result.hColorPart = GCUtil.degreeMiddle(a.hColorPart, b.hColorPart);
			result.sColorPart = (a.sColorPart + b.sColorPart) / 2;
		}
		result.hColorPart = GCUtil.roundFloat(result.hColorPart, complexity[complexity.length - 1]);
		return result;
	}
	
	public static Glowing fromRGB(int rgb) {
		HSVColor color = HSVColor.fromRGB(rgb);
		return new Glowing(getCurrentLightingLevel((int) (color.v * 100)), color.h * 360, (int) (color.s * 100));
	}
	
	public static void lockColor(ItemStack item) {
		if(item.nbtData == null)
			item.nbtData = new NBTTagCompound();
		NBTTagCompound colorTag;
		if(item.nbtData.hasKey("DColor")) {
			colorTag = item.nbtData.getCompoundTag("DColor");
		} else {
			colorTag = new NBTTagCompound();
			item.nbtData.setTag("DColor", colorTag);
		}
		colorTag.setBoolean("L", true);
	}
	
	public static boolean canApplyColor(ItemStack item) {
		return item.itemID >= Block.blocksLength || Block.blocksList[item.itemID] == null;
	}
	
	public static boolean canRemoveColor(ItemStack item) {
		return item.nbtData != null && item.nbtData.hasKey("DColor") && !item.nbtData.getCompoundTag("DColor").getBoolean("L");
	}
	
	public static boolean hasItemColor(ItemStack item) {
		return item.nbtData != null && item.nbtData.hasKey("DColor");
	}
	
	public static int getNextSaturationLevel(int currentLevel) {
		int i = 0;
		for(; i < saturationLevels.length; ++i)
			if(currentLevel < saturationLevels[i])
				break;
		return saturationLevels[Math.min(i, saturationLevels.length - 1)];
	}
	
	public static int getCurrentLightingLevel(int v) {
		int i = 0;
		for(; i < lightingLevels.length; ++i)
			if(v < lightingLevels[i])
				break;
		return Math.max(0, --i);
	}
	
	public static int getComplexity(float h) {
		for(int i = 0; i < complexity.length; i++)
			if(h % complexity[i] == 0)
				return i;
		return complexity.length - 1;
	}
	
	static {
		defaultColors.put(0, "Красный");
		defaultColors.put(60, "Желтый");
		defaultColors.put(120, "Зелёный");
		defaultColors.put(180, "Бирюзовый");
		defaultColors.put(240, "Синий");
		defaultColors.put(300, "Пурпурный");
	}
}
