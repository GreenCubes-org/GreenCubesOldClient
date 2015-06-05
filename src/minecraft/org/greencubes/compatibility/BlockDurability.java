package org.greencubes.compatibility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import org.greencubes.nbt.NBTTagCompound;
import org.greencubes.nbt.NBTTagList;
import org.greencubes.util.Util;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

public class BlockDurability {
	
	private static final IItemMatcher[] iObj = new IItemMatcher[0];
	
	public static final byte DEFAULT_TYPE = 1;
	
	private final long baseDurability;
	private boolean isDropableByAllTools = true;
	private boolean isBreakableByAllTools = true;
	private TMap<IItemMatcher, ToolStat> tools = new THashMap<IItemMatcher, ToolStat>();
	private IItemMatcher[] matchers = iObj;
	
	public BlockDurability(long baseDurability) {
		this.baseDurability = baseDurability;
	}
	
	public void setDropable(boolean b) {
		this.isDropableByAllTools = b;
	}
	
	public void setBreakable(boolean b) {
		this.isBreakableByAllTools = b;
	}
	
	public void addTool(IItemMatcher matcher, ToolStat stat) {
		tools.put(matcher, stat);
		matchers = Util.arrayAppend(matchers, matcher);
	}
	
	public boolean haveTool(IItemMatcher matcher) {
		return tools.containsKey(matcher);
	}
	
	public long getBaseDurability() {
		return baseDurability;
	}
	
	public long getBlockDurability(EntityPlayer entity, ItemStack item, IBlockAccess blockRead, int x, int y, int z) {
		if(baseDurability < 1) // 0 or -1 returns as is
			return baseDurability;
		float mult2 = entity.getDigSpeedMultipler(null, 0); // We dont need block or block data here, because fuck u
		if(mult2 == 0)
			return -1;
		if(item != null && item.getItem() != null) {
			if(item.isInstabreak())
				return 0;
			if(item.isBroken())
				return -1;
			for(IItemMatcher m : matchers) {
				if(m.matches(item)) {
					if(item.nbtData != null && item.nbtData.hasKey("DigSpeed")) {
						float ds = 1.0f + item.nbtData.getFloat("DigSpeed");
						if(ds == 0)
							return -1;
						mult2 *= ds;
					}
					ToolStat stat = tools.get(m);
					return (long) (baseDurability / stat.multipler / mult2);
				}
			}
		}
		if(!isBreakableByAllTools)
			return -1;
		return (long) (baseDurability / mult2);
	}
	
	@Override
	public BlockDurability clone() {
		return clone(baseDurability);
	}
	
	public BlockDurability clone(long newDurability) {
		BlockDurability d = new BlockDurability(newDurability);
		d.isBreakableByAllTools = isBreakableByAllTools;
		d.isDropableByAllTools = isDropableByAllTools;
		d.matchers = Arrays.copyOf(matchers, matchers.length);
		d.tools.putAll(tools);
		return d;
	}
	
	public BlockDurability cloneWithMultipler(float mult) {
		return clone((long) (baseDurability * mult));
	}
	
	public NBTTagCompound toNBTTag(NBTTagCompound store) {
		if(store == null)
			store = new NBTTagCompound();
		store.setByte("bdt", DEFAULT_TYPE);
		store.setLong("bdd", baseDurability);
		store.setBoolean("bddr", isDropableByAllTools);
		store.setBoolean("bdbr", isBreakableByAllTools);
		if(baseDurability > 0) {
			NBTTagList list = new NBTTagList();
			Iterator<Entry<IItemMatcher, ToolStat>> iterator = tools.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<IItemMatcher, ToolStat> e = iterator.next();
				NBTTagCompound toolTag = e.getKey().toNBTTag(null);
				toolTag.setBoolean("drop", e.getValue().isDropable);
				toolTag.setFloat("mult", e.getValue().multipler);
				list.add(toolTag);
			}
			store.setNBTTag("bdtl", list);
		}
		return store;
	}
	
	public static BlockDurability fromNBTTag(NBTTagCompound tag) {
		BlockDurability d;
		switch(tag.getInt("bdt")) {
		case DEFAULT_TYPE:
			d = new BlockDurability(tag.getLong("bdd"));
			d.isBreakableByAllTools = tag.getBoolean("bdbr");
			d.isDropableByAllTools = tag.getBoolean("bddr");
			if(d.baseDurability > 0) {
				NBTTagList list = tag.getTagList("bdtl");
				for(int i = 0; i < list.size(); ++i) {
					NBTTagCompound toolTag = (NBTTagCompound) list.get(i);
					IItemMatcher matcher = CraftManager.getOrLoadMatcher(toolTag);
					d.addTool(matcher, new ToolStat(toolTag.getBoolean("drop"), toolTag.getFloat("mult")));
				}
			}
			return d;
		}
		return null;
	}
	
	public static class ToolStat {
		
		public boolean isDropable;
		public float multipler;
		
		public ToolStat(boolean isDropable, float multipler) {
			this.isDropable = isDropable;
			this.multipler = multipler;
		}
	}
}
