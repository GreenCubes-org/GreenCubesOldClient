package net.minecraft.src;

import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemSpeedBoots extends ItemCloth {

	protected final float speed;

	public ItemSpeedBoots(int i, int j, float speed) {
		super(i, j, 3);
		this.speed = speed;
		setMaxDamage(1200);
	}
	
	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public boolean isUnbreakable() {
		return true;
	}
	
	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.IMPROOVED;
	}

	@Override
	public List<Buff> getBuffs(EntityLiving entity, ItemStack item) {
		List<Buff> buffs = super.getBuffs(entity, item);
		if(!buffs.contains(Buff.FAST_RUN))
			buffs.add(Buff.FAST_RUN);
		else if(item.getNBTData() != null && item.getNBTData().hasKey("RunSpeed") && item.getNBTData().getFloat("RunSpeed") == 0.0f)
			buffs.remove(Buff.FAST_RUN);
		return buffs;
	}

}
