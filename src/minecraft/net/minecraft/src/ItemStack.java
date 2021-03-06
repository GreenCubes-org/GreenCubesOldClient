// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

import org.greencubes.items.unique.DecorItemQuality;
import org.greencubes.items.unique.DecorItemStatus;
import org.greencubes.items.unique.DecorItemUnique;

// Referenced classes of package net.minecraft.src:
//            Block, Item, NBTTagCompound, StatList, 
//            EntityPlayer, EnchantmentHelper, EntityLiving, World, 
//            NBTTagList, Enchantment, Entity, EnumAction, 
//            EnumRarity

public final class ItemStack {

	public int stackSize;
	public int animationsToGo;
	public int itemID;
	public NBTTagCompound nbtData;
	public int itemDamage;
	public byte particleTimer = 0;

	public ItemStack(Block block) {
		this(block, 1);
	}

	public ItemStack(Block block, int i) {
		this(block.blockID, i, 0);
	}

	public ItemStack(Block block, int i, int j) {
		this(block.blockID, i, j);
	}

	public ItemStack(Item item) {
		this(item.shiftedIndex, 1, 0);
	}

	public ItemStack(Item item, int i) {
		this(item.shiftedIndex, i, 0);
	}

	public ItemStack(Item item, int i, int j) {
		this(item.shiftedIndex, i, j);
	}

	public ItemStack(int id, int count, int damage) {
		stackSize = 0;
		itemID = id;
		stackSize = count;
		itemDamage = damage;
	}

	public ItemStack(int id, int count, int damage, NBTTagCompound tag) {
		this(id, count, damage);
		nbtData = tag;
	}

	public static ItemStack loadItemStackFromNBT(NBTTagCompound nbttagcompound) {
		ItemStack itemstack = new ItemStack();
		itemstack.readFromNBT(nbttagcompound);
		return itemstack.getItem() == null ? null : itemstack;
	}

	private ItemStack() {
		stackSize = 0;
	}

	public ItemStack splitStack(int i) {
		ItemStack itemstack = new ItemStack(itemID, i, itemDamage);
		if(nbtData != null) {
			itemstack.nbtData = (NBTTagCompound) nbtData.clone();
		}
		stackSize -= i;
		return itemstack;
	}

	public Item getItem() {
		Item i = Item.itemsList[itemID];
		if(i == null)
			System.err.println("No item for ID " + itemID);
		return i;
	}

	public int getIconIndex() {
		return getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		boolean flag = getItem().onItemUse(this, entityplayer, world, i, j, k, l);
		if(flag) {
			entityplayer.addStat(StatList.objectUseStats[itemID], 1);
		}
		return flag;
	}

	public boolean useItem2(EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		return getItem().onItemUse2(this, entityplayer, world, i, j, k, l);
	}

	public float getBlockDamageMultipler(Block block, EntityPlayer player, int data) {
		if(isBroken())
			return 0.0f;
		if(isInstabreak())
			return 50000000;
		return getItem().getBlockDamageMultipler(this, block, player, data);
	}

	public ItemStack useItemRightClick(World world, EntityPlayer entityplayer) {
		return getItem().onItemRightClick(this, world, entityplayer);
	}

	public ItemStack onFoodEaten(World world, EntityLiving entityLiving) {
		return getItem().onFoodEaten(this, world, entityLiving);
	}

	public boolean isUnbreakable() {
		return getItem().isUnbreakable() || nbtData != null && nbtData.getBoolean("Unbreakable");
	}

	public boolean isUndropable() {
		return nbtData != null && nbtData.getBoolean("Undropable");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("id", (short) itemID);
		nbttagcompound.setShort("Count", (short) stackSize);
		nbttagcompound.setShort("Damage", (short) itemDamage);
		if(nbtData != null)
			nbttagcompound.setTag("NBTdata", nbtData);
		return nbttagcompound;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		itemID = nbttagcompound.getShort("id");
		stackSize = nbttagcompound.getShort("Count");
		itemDamage = nbttagcompound.getShort("Damage");
		if(nbttagcompound.hasKey("tag"))
			nbtData = nbttagcompound.getCompoundTag("tag");
		if(nbttagcompound.hasKey("NBTdata"))
			nbtData = nbttagcompound.getCompoundTag("NBTdata");
	}

	public int getMaxStackSize() {
		return getItem().getItemStackLimit();
	}

	public boolean isStackable() {
		return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
	}

	public boolean isItemStackDamageable() {
		return getMaxDamage() > 0;
	}

	public boolean getHasSubtypes() {
		return Item.itemsList[itemID].getHasSubtypes();
	}

	public boolean isItemDamaged() {
		return isItemStackDamageable() && itemDamage > 0;
	}

	public int getItemDamageForDisplay() {
		return itemDamage;
	}

	public int getItemDamage() {
		return itemDamage;
	}

	public void setItemDamage(int i) {
		itemDamage = i;
	}

	public int getMaxDamage() {
		int durability = getItem().getMaxDamage();
		if(nbtData != null) {
			if(nbtData.hasKey("Durability"))
				durability += Math.ceil(durability * nbtData.getFloat("Durability"));
			if(nbtData.hasKey("DurAdd"))
				durability += nbtData.getInteger("DurAdd");
		}
		return durability;
	}

	public void damageItem(int i, EntityLiving entityliving) {
		if(!isItemStackDamageable() || isBroken()) {
			return;
		}
		if(nbtData != null && nbtData.getBoolean("Invulnerable"))
			return;
		if(i > 0 && (entityliving instanceof EntityPlayer)) {
			int j = EnchantmentHelper.getUnbreakingModifier(((EntityPlayer) entityliving).inventory);
			if(j > 0 && entityliving.worldObj.rand.nextInt(j + 1) > 0) {
				return;
			}
		}
		itemDamage += i;
		if(itemDamage > getMaxDamage()) {
			entityliving.renderBrokenItem(this);
			if(entityliving instanceof EntityPlayer) {
				((EntityPlayer) entityliving).addStat(StatList.objectBreakStats[itemID], 1);
			}
			if(!isUnbreakable()) {
				stackSize--;
				if(stackSize < 0)
					stackSize = 0;
				itemDamage = 0;
			}
		}
	}

	public void hitEntity(EntityLiving entityliving, EntityPlayer entityplayer) {
		boolean flag = Item.itemsList[itemID].hitEntity(this, entityliving, entityplayer);
		if(flag) {
			entityplayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}

	public void onDestroyBlock(int i, int j, int k, int l, EntityPlayer entityplayer) {
		boolean flag = Item.itemsList[itemID].onBlockDestroyed(this, i, j, k, l, entityplayer);
		if(flag) {
			entityplayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}

	public int getDamageVsEntity(Entity entity) {
		if(isBroken())
			return 0;
		return Item.itemsList[itemID].getDamageVsEntity(entity);
	}

	public boolean canHarvestBlock(Block block) {
		if(isBroken())
			return false;
		return Item.itemsList[itemID].canHarvestBlock(block);
	}

	public void onItemDestroyedByUse(EntityPlayer entityplayer) {
	}

	public void useItemOnEntity(EntityLiving entityliving) {
		Item.itemsList[itemID].useItemOnEntity(this, entityliving);
	}

	public ItemStack copy() {
		ItemStack itemstack = new ItemStack(itemID, stackSize, itemDamage);
		if(nbtData != null) {
			itemstack.nbtData = (NBTTagCompound) nbtData.clone();
			if(!itemstack.nbtData.equals(nbtData)) {
				return itemstack;
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack clone() {
		return copy();
	}

	public static boolean areItemStacksEqual(ItemStack itemstack, ItemStack itemstack1) {
		if(itemstack == null && itemstack1 == null) {
			return true;
		}
		if(itemstack == null || itemstack1 == null) {
			return false;
		} else {
			return itemstack.isItemStackEqual(itemstack1);
		}
	}

	public static boolean canStackItems(ItemStack i1, ItemStack i2) {
		if(!areItemStackContentEqual(i1, i2))
			return false;
		if(i1 != null) {
			if(i1.nbtData != null && i1.nbtData.hasKey("nostack"))
				return false;
			if(i2.nbtData != null && i2.nbtData.hasKey("nostack"))
				return false;
		}
		return true;
	}

	public static boolean areItemStackContentEqual(ItemStack i1, ItemStack i2) {
		if(i1 == null && i2 == null) {
			return true;
		}
		if(i1 == null || i2 == null) {
			return false;
		} else {
			return i1.isItemStackContentEqual(i2);
		}
	}

	private boolean isItemStackContentEqual(ItemStack itemstack) {
		if(itemID != itemstack.itemID) {
			return false;
		}
		if(itemDamage != itemstack.itemDamage) {
			return false;
		}
		if(nbtData == null && itemstack.nbtData != null) {
			return false;
		}
		return nbtData == null || nbtData.equals(itemstack.nbtData);
	}

	private boolean isItemStackEqual(ItemStack itemstack) {
		if(stackSize != itemstack.stackSize) {
			return false;
		}
		if(itemID != itemstack.itemID) {
			return false;
		}
		if(itemDamage != itemstack.itemDamage) {
			return false;
		}
		if(nbtData == null && itemstack.nbtData != null) {
			return false;
		}
		return nbtData == null || nbtData.equals(itemstack.nbtData);
	}

	public boolean isItemEqual(ItemStack itemstack) {
		return itemID == itemstack.itemID && itemDamage == itemstack.itemDamage;
	}

	public static ItemStack copyItemStack(ItemStack itemstack) {
		return itemstack != null ? itemstack.copy() : null;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append(stackSize).append("x").append(Item.itemsList[itemID].getItemName()).append("@").append(itemDamage).toString();
	}

	public void updateAnimation(World world, Entity entity, int i, boolean flag) {
		if(animationsToGo > 0) {
			animationsToGo--;
		}
		Item.itemsList[itemID].onUpdate(this, world, entity, i, flag);
	}

	public void onCrafting(World world, EntityPlayer entityplayer) {
		entityplayer.addStat(StatList.objectCraftStats[itemID], stackSize);
		Item.itemsList[itemID].onCreated(this, world, entityplayer);
	}

	public boolean isStackEqual(ItemStack itemstack) {
		return itemID == itemstack.itemID && stackSize == itemstack.stackSize && itemDamage == itemstack.itemDamage;
	}

	public int getMaxItemUseDuration() {
		return getItem().getMaxItemUseDuration(this);
	}

	public EnumAction getItemUseAction() {
		return getItem().getItemUseAction(this);
	}

	public void onPlayerStoppedUsing(World world, EntityPlayer entityplayer, int i) {
		getItem().onPlayerStoppedUsing(this, world, entityplayer, i);
	}

	public boolean noDrop() {
		return getItem().noDrop() || nbtData != null && nbtData.getBoolean("NoDrop");
	}

	/**
	 * ����������, ���� �� � ����� �������� NBT-data
	 * @return
	 */
	public boolean hasNBTData() {
		return nbtData != null;
	}

	public NBTTagCompound getNBTData() {
		return nbtData;
	}

	public NBTTagList getEnchantmentInfo() {
		if(nbtData == null) {
			return null;
		} else {
			return (NBTTagList) nbtData.getTag("ench");
		}
	}

	public void setNBTData(NBTTagCompound nbttagcompound) {
		if(Item.itemsList[itemID].getItemStackLimit() != 1) {
			throw new IllegalArgumentException("Cannot add tag data to a stackable item");
		} else {
			nbtData = nbttagcompound;
			return;
		}
	}

	/**
	 * GreenCubes name function
	 * @return item name for displaying
	 */
	private String getName() {
		StringBuilder sb = new StringBuilder();
		if(nbtData != null && nbtData.hasKey("NameColor"))
			sb.append(nbtData.getString("NameColor"));
		else {
			String color = getDecorColor();
			if(color != null)
				sb.append(color);
		}
		if(nbtData == null)
			return sb.append(getItem().getTranslatedName(this)).toString();
		if(nbtData.hasKey("UserName")) {
			sb.append("\"").append(nbtData.getString("UserName")).append("\"");
		} else {
			if(nbtData.hasKey("NamePrefix"))
				sb.append(nbtData.getString("NamePrefix")).append(' ');
			if(nbtData.hasKey("Name"))
				sb.append(nbtData.getString("Name"));
			else if(!nbtData.hasKey("NamePrefix"))
				sb.append(getItem().getTranslatedName(this));
			else
				sb.append(getItem().getTranslatedName(this).toLowerCase());
			if(nbtData.hasKey("NameSuffix"))
				sb.append(' ').append(nbtData.getString("NameSuffix"));
			if(nbtData.hasKey("Count")) {
				int[] cArr = nbtData.getIntArray("Count");
				if(cArr.length < 2 || cArr[0] <= cArr[1])
					sb.append(" #").append(cArr[0]);
			}
		}
		return sb.toString();
	}

	public String getDecorColor() {
		DecorItemQuality q = getDecorQuality();
		if(q == null)
			return null;
		DecorItemUnique u = getUnique();
		if(u == DecorItemUnique.UNIQUE_STRONG)
			return "\247rffff7518";
		if(u == null || q.overridesUnique || u == DecorItemUnique.UNIQUE_WEAK)
			return q.colorCode;
		return "\247rffff7518";
	}

	public boolean isInstabreak() {
		return nbtData != null && nbtData.getBoolean("Instabreak");
	}

	public boolean isBroken() {
		return isItemStackDamageable() && isUnbreakable() && itemDamage >= getMaxDamage();
	}

	public DecorItemUnique getUnique() {
		if(nbtData != null) {
			if(nbtData.hasKey("DU"))
				return DecorItemUnique.getById(nbtData.getByte("DU"));
			if(nbtData.hasKey("Count")) {
				int[] cArr = nbtData.getIntArray("Count");
				if(cArr.length < 2 || cArr[0] <= cArr[1])
					return DecorItemUnique.UNIQUE;
			}
		}
		return getItem().getDecorUniques();
	}

	public DecorItemStatus getDecorStatus() {
		if(nbtData != null && nbtData.hasKey("DS"))
			return DecorItemStatus.getById(nbtData.getByte("DS"));
		return getItem().getDecorStatus();
	}

	public DecorItemQuality getDecorQuality() {
		if(nbtData != null && nbtData.hasKey("DQ"))
			return DecorItemQuality.getById(nbtData.getByte("DQ"));
		return getItem().getDecorQuality();
	}

	public List<String> getDescription() {
		ArrayList<String> arraylist = new ArrayList<String>();
		Item item = Item.itemsList[itemID];
		arraylist.add(getName());
		DecorItemQuality q = getDecorQuality();
		if(q != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(q.colorCode).append(q.localizedName);
			DecorItemUnique u = getUnique();
			if(u != null)
				sb.append("\2477, \247rffff7518����������");
			DecorItemStatus s = getDecorStatus();
			if(s != null)
				sb.append("\2477, ").append(s.addName);
			arraylist.add(sb.toString());
		}
		if(hasNBTData() && nbtData.hasKey("UserDescription")) {
			NBTTagList list = (NBTTagList) nbtData.getTag("UserDescription");
			for(int i = 0; i < list.size(); ++i)
				arraylist.add("\247rffcccccc\247i" + ((NBTTagString) list.get(i)).stringValue);
		} else {
			item.appendDescription(this, arraylist);
			if(hasNBTData()) {
				if(nbtData.hasKey("title")) {
					StringBuilder title = new StringBuilder();
					title.append(nbtData.getString("title"));
					if(nbtData.hasKey("synchronized") && nbtData.getBoolean("synchronized"))
						title.append("\2477 (�������)");
					arraylist.add(title.toString());
				}	
				if(nbtData.hasKey("Subtitles")) {
					NBTTagList list = (NBTTagList) nbtData.getTag("Subtitles");
					for(int i = 0; i < list.size(); ++i)
						arraylist.add(((NBTTagString) list.get(i)).stringValue);
				}
			}
		}
		int	n = arraylist.size();
		
		item.appendAttributes(this, arraylist);
		if(hasNBTData() && nbtData.hasKey("TextAttrs")) {
			NBTTagList list = (NBTTagList) nbtData.getTag("TextAttrs");
			for(int i = 0; i < list.size(); ++i)
				arraylist.add(((NBTTagString) list.get(i)).stringValue);
		}
		if(hasNBTData()) {
			if(nbtData.getBoolean("Undropable"))
				arraylist.add("\247c������ ���������");
			if(nbtData.getBoolean("NoRepair"))
				arraylist.add("\247c������ ������");
		}
		if(getItem().isDamageable()) {
			StringBuilder sb = new StringBuilder().append("\2477���������: \247f");
			if(nbtData != null && nbtData.getBoolean("Invulnerable")) {
				sb.append("����������");
			} else {
				sb.append(getMaxDamage() - itemDamage).append(" \2477/\247f ").append(getMaxDamage());
				if(nbtData != null) {
					if(nbtData.hasKey("Durability") || nbtData.hasKey("DurAdd")) {
						sb.append(" \2477(");
						if(nbtData.hasKey("Durability")) {
							sb.append("+").append((int) (nbtData.getFloat("Durability") * 100)).append("%");
							if(nbtData.hasKey("DurAdd"))
								sb.append(", ");
						}
						if(nbtData.hasKey("DurAdd"))
							sb.append("+").append(nbtData.getInteger("DurAdd"));
						sb.append(")");
					}
				}
			}
			arraylist.add(sb.toString());
		}
		if(isUnbreakable() && (nbtData == null || !nbtData.getBoolean("Invulnerable")))
			arraylist.add("\2477�� ������������ �� �����������");
		if(noDrop())
			arraylist.add("\2477�� �������� ��� ������");
		if(hasNBTData()) {
			if(nbtData.getBoolean("Invulnerable"))
				arraylist.add("\247rffaaffff�� ������������");
			if(nbtData.getBoolean("Instabreak"))
				arraylist.add("\247rffaaffff���������� ������� ������");
			if(nbtData.getBoolean("NoAmmo"))
				arraylist.add("\247rffaaffff�� ������� ��������");
			if(nbtData.hasKey("DigSpeed")) {
				float ds = nbtData.getFloat("DigSpeed");
				if(ds > 0)
					arraylist.add("\247rffaaffff��������: +" + ((int) (ds * 100)) + "%");
				else
					arraylist.add("\247rffaaffff��������: " + ((int) (ds * 100)) + "%");
			}
			if(nbtData.hasKey("Efficiency")) {
				float ef = nbtData.getFloat("Efficiency");
				if(ef > 0)
					arraylist.add("\247rffaaffff�������������: +" + ((int) (ef * 100)) + "%");
				else
					arraylist.add("\247rffaaffff�������������: " + ((int) (ef * 100)) + "%");
			}
			if(nbtData.hasKey("Counters")) {
				NBTTagList counters = nbtData.getTagList("Counters");
				for(int i = 0; i < counters.size(); ++i) {
					NBTTagCompound counter = (NBTTagCompound) counters.get(i);
					switch(counter.getInteger("I")) {
					case 1:
						arraylist.add("\2476��������� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 2:
						arraylist.add("\2476������� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 3:
						arraylist.add("\2476������� ������ ��������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 4:
						arraylist.add("\2476��������� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 5:
						arraylist.add("\2476����������� ��������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 6:
						arraylist.add("\2476������ ��������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 7:
						arraylist.add("\2476������� ���� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 8:
						arraylist.add("\2476�������� ���� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 9:
						arraylist.add("\2476������� ���� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 10:
						arraylist.add("\2476�������� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 11:
						arraylist.add("\2476������ ��������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 12:
						arraylist.add("\2476���� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 13:
						arraylist.add("\2476����� ��������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 14:
						arraylist.add("\2476�������� ����������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 15:
						arraylist.add("\2476������ ����������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 16:
						arraylist.add("\2476������ ������ �����������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 17:
						arraylist.add("\2476���������� ������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 18:
						arraylist.add("\2476���� �������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 19:
						arraylist.add("\2476������� �������: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 20:
						arraylist.add("\2476�������� �����: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					case 21:
						arraylist.add("\2476�������� �����: " + counter.getInteger("C") + (counter.getBoolean("L") ? " \2477(������������)" : ""));
						break;
					}
				}
			}
			if(nbtData.hasKey("Dropped")) {
				NBTTagCompound dropped = nbtData.getCompoundTag("Dropped");
				arraylist.add("\247rff0ac80a������� ������� ������� " + dropped.getString("N"));
			}
			if(nbtData.hasKey("Found")) {
				NBTTagList founds = nbtData.getTagList("Found");
				for(int i = founds.size() - 1; i >= 0; --i) {
					NBTTagCompound found = (NBTTagCompound) founds.get(i);
					arraylist.add("\247rff0ac80a������ � ����������� �������: " + found.getString("N") + ", " + GCUtil.getLocalizedDate2(found.getLong("T")));
				}
			}
			if(nbtData.hasKey("Expires")) {
				arraylist.add("\247rff0ac80a������� ��������: " + GCUtil.getLocalizedDate2(nbtData.getLong("Expires")));
			}
			if(nbtData.hasKey("Glow") && itemID != Item.GLOWING_MOD.shiftedIndex) {
				if(nbtData.hasKey("DColor"))
					ItemGlowModifier.appendDescriptionToItem(this, arraylist);
				else
					arraylist.add("\247rffb428fa� �������� ��������");
			}
			if(nbtData.hasKey("Effects")) {
				NBTTagList effects = nbtData.getTagList("Effects");
				for(int i = 0; i < effects.size(); ++i) {
					NBTTagCompound effect = (NBTTagCompound) effects.get(i);
					if(effect.getInteger("T") == 1) {
						arraylist.add("\247rffb428fa� �������� \"������� �����\"" + (effect.getBoolean("S") ? " \2477(��������� ���� �����)" : ""));
					} else if(effect.getInteger("T") == 2) {
						arraylist.add("\247rffb428fa� �������� \"������� ����\"" + (effect.getBoolean("S") ? " \2477(��������� ���� �����)" : ""));
					} else if(effect.getInteger("T") == 3) {
						arraylist.add("\247rffb428fa� �������� \"�������� �����\"" + (effect.getBoolean("S") ? " \2477(��������� ���� �����)" : ""));
					} else if(effect.getInteger("T") == 4) {
						arraylist.add("\247rffb428fa� �������� \"�������� �����\"" + (effect.getBoolean("S") ? " \2477(��������� ���� �����)" : ""));
					}
				}
			}
			if(nbtData.hasKey("UserName") && nbtData.hasKey("Count")) {
				int[] cArr = nbtData.getIntArray("Count");
				if(cArr.length < 2 || cArr[0] <= cArr[1])
					arraylist.add("\2477������������ ����� ��������: #" + cArr[0]);
			}
		}
		if(arraylist.size() > n)
			arraylist.add(n, "");
		return arraylist;
	}

	public boolean isGlowing() {
		return (nbtData != null && nbtData.hasKey("Glow")) || getItem().isEnchanted(this);
	}

	public EnumRarity getRarity() {
		return getItem().getRarity(this);
	}

	public boolean func_40708_t() {
		if(!getItem().func_40401_i(this)) {
			return false;
		}
		return !hasEnchantment();
	}

	public void addEnchantment(Enchantment enchantment, int i) {
		if(nbtData == null) {
			setNBTData(new NBTTagCompound());
		}
		if(!nbtData.hasKey("ench")) {
			nbtData.setTag("ench", new NBTTagList("ench"));
		}
		NBTTagList nbttaglist = (NBTTagList) nbtData.getTag("ench");
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("id", (short) enchantment.effectId);
		nbttagcompound.setShort("lvl", (byte) i);
		nbttaglist.setTag(nbttagcompound);
	}

	public boolean hasEnchantment() {
		return nbtData != null && nbtData.hasKey("ench");
	}
}
