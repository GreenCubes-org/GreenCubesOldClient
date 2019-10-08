/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import org.greencubes.items.unique.Glowing;
import org.greencubes.items.unique.NamedColor;
import org.greencubes.util.HSVColor;
import org.greencubes.util.RGBUtils;

public class ItemGlowModifier extends Item {

	public static final float[] complexity = new float[]{60F, 30F, 15F, 7.5F, 3.75F, 1.875F, 0.9375F, 0.46875F, 0.234375F};

	public ItemGlowModifier(int i) {
		super(i);
	}

	public ItemStack generate(int level, NamedColor color) {
		return generate(level, color.h, color.s);
	}

	public ItemStack generate(int level, float h, int saturation) {
		ItemStack item = new ItemStack(shiftedIndex, 1, Glowing.colorModifier);
		Glowing glow = new Glowing(level, h, saturation);
		glow.applyToSource(item);
		return item;
	}

	@Override
	public boolean useMultirender(ItemStack source, ItemRenderType renderType) {
		return (source.itemDamage == 0 && source.nbtData != null && source.nbtData.hasKey("DColorData")) || (source.itemDamage == 3 && source.nbtData != null && source.nbtData.hasKey("DColor"));
	}

	@Override
	public List<ItemStack> getMultirender(ItemStack source, ItemRenderType renderType) {
		List<ItemStack> list = new ArrayList<ItemStack>(2);
		if(source.itemDamage == 0) {
			list.add(new ItemStack(this, 1, 251));
			ItemStack copy = source.copy();
			copy.setItemDamage(250);
			list.add(copy);
		} else if(source.itemDamage == 3) {
			list.add(new ItemStack(this, 1, 253));
			ItemStack copy = source.copy();
			copy.setItemDamage(252);
			list.add(copy);
		}
		return list;
	}

	@Override
	public boolean noPrerender() {
		return true;
	}

	@Override
	public int getIconIndex(ItemStack itemstack) {
		switch(itemstack.itemDamage) {
		case 0:
			return GreenTextures.glowmodcolor;
		case 1:
			return GreenTextures.glowmodpower;
		case 2:
		case 3:
			return GreenTextures.glowmodextract;
		case 4:
			return GreenTextures.glowmodclear;
		case 250:
			return GreenTextures.glowmodcolor2;
		case 251:
			return GreenTextures.glowmodcolor;
		case 252:
			return GreenTextures.glowmodextract2;
		case 253:
			return GreenTextures.glowmodextract;
		}
		return GreenTextures.glowmodcolor;
	}

	@Override
	public int getColorFromIS(ItemStack itemstack) {
		if(itemstack.getItemDamage() == 250)
			return itemstack.nbtData.getCompoundTag("DColorData").getInteger("Rc");
		if(itemstack.getItemDamage() == 252)
			return itemstack.nbtData.getCompoundTag("DColor").getInteger("Rc");
		return 0xFFFFFF;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		switch(itemstack.itemDamage) {
		case 0:
			return "item.gmod.color";
		case 1:
			return "item.gmod.power";
		case 2:
		case 3:
			return "item.gmod.extract";
		case 4:
			return "item.gmod.clear";
		}
		return super.getItemNameIS(itemstack);
	}

	public static void appendDescriptionToItem(ItemStack itemstack, List<String> list) {
		list.add("\247rffb428faС эффектом декоративного свечения:");
		NBTTagCompound colorTag = itemstack.nbtData.getCompoundTag("DColor");
		String colorName = Glowing.getFixedName(colorTag);
		HSVColor color = new HSVColor(colorTag.getFloat("Hp") / 360.0f, colorTag.getByte("Sp") / 100.0F, 1.0F);
		int c = color.getRGB();
		int[] cArr = RGBUtils.toIntArray(c);
		list.add("     \247rff" + RGBUtils.toHexString(c) + StringTranslate.getInstance().translateKey("item.gmod.color") + ": " + (colorName != null ? (colorName + " (") : "") + cArr[0] + ", " + cArr[1] + ", " + cArr[2] + (colorName != null ? ")" : ""));
		list.add("     \247fСложность оттенка: " + Glowing.getComplexity(colorTag.getFloat("Hp")) + (TMIConfig.getInstance().isEnabled() ? " \2477(Оттенок: " + colorTag.getFloat("Hp") + ")" : ""));
		list.add("     \247f" + StringTranslate.getInstance().translateKey("item.gmod.saturation") + ": " + colorTag.getByte("Sp") + "%");
		list.add("     \247f" + StringTranslate.getInstance().translateKey("item.gmod.power") + ": " + StringTranslate.getInstance().translateKey("item.gmod.level") + " " + colorTag.getByte("Lv"));
		if(colorTag.getBoolean("L"))
			list.add("     \2477(Цвет заблокирован)");
	}

	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		super.appendAttributes(itemstack, list);
		if(itemstack.getItemDamage() == 0) {
			if(itemstack.nbtData != null && itemstack.nbtData.hasKey("DColorData")) {
				NBTTagCompound colorTag = itemstack.nbtData.getCompoundTag("DColorData");
				int[] cArr = RGBUtils.toIntArray(colorTag.getInteger("Rc"));
				String colorName = Glowing.getFixedName(colorTag);
				list.add("\247rff" + RGBUtils.toHexString(colorTag.getInteger("Rc")) + StringTranslate.getInstance().translateKey("item.gmod.color") + ": " + (colorName != null ? (colorName + " (") : "") + cArr[0] + ", " + cArr[1] + ", " + cArr[2] + (colorName != null ? ")" : ""));
				list.add("\247fСложность оттенка: " + Glowing.getComplexity(colorTag.getFloat("Hp")) + (TMIConfig.getInstance().isEnabled() ? " \2477(Оттенок: " + colorTag.getFloat("Hp") + ")" : ""));
				list.add("\247f" + StringTranslate.getInstance().translateKey("item.gmod.saturation") + ": " + colorTag.getByte("Sp") + "%");
				list.add("\247f" + StringTranslate.getInstance().translateKey("item.gmod.power") + ": " + StringTranslate.getInstance().translateKey("item.gmod.level") + " " + colorTag.getByte("Lv"));
			}
		} else if(itemstack.getItemDamage() == 3) {
			list.add("\247rffb428fa" + StringTranslate.getInstance().translateKey("item.gmod.extract.filled"));
			NBTTagCompound colorTag = itemstack.nbtData.getCompoundTag("DColor");
			HSVColor color = new HSVColor(colorTag.getFloat("Hp") / 360.0f, colorTag.getByte("Sp") / 100.0F, 1.0F);
			int c = color.getRGB();
			int[] cArr = RGBUtils.toIntArray(c);
			String colorName = Glowing.getFixedName(colorTag);
			list.add("     \247rff" + RGBUtils.toHexString(c) + StringTranslate.getInstance().translateKey("item.gmod.color") + ": " + (colorName != null ? (colorName + " (") : "") + cArr[0] + ", " + cArr[1] + ", " + cArr[2] + (colorName != null ? ")" : ""));
			list.add("     \247fСложность оттенка: " + Glowing.getComplexity(colorTag.getFloat("Hp")) + (TMIConfig.getInstance().isEnabled() ? " \2477(Оттенок: " + colorTag.getFloat("Hp") + ")" : ""));
			list.add("     \247f" + StringTranslate.getInstance().translateKey("item.gmod.saturation") + ": " + colorTag.getByte("Sp") + "%");
			list.add("     \247f" + StringTranslate.getInstance().translateKey("item.gmod.power") + ": " + StringTranslate.getInstance().translateKey("item.gmod.level") + " " + colorTag.getByte("Lv"));
		}
	}
}
