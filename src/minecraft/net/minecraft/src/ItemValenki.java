package net.minecraft.src;

import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemValenki extends ItemCloth {

	protected final int bonus;

	public ItemValenki(int i, int j, int bonus) {
		super(i, j, 3);
		this.bonus = bonus;
		this.setMaxDamage(0);
	}
	
	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.IMPROOVED;
	}

	@Override
	public List<Buff> getBuffs(EntityLiving entity, ItemStack item) {
		List<Buff> buffs = super.getBuffs(entity, item);
		if(!buffs.contains(Buff.SNOW_BOOST))
			buffs.add(Buff.SNOW_BOOST);
		else if(item.getNBTData() != null && item.getNBTData().hasKey("SnowBoost") && item.getNBTData().getInteger("SnowBoost") == 0)
				buffs.remove(Buff.SNOW_BOOST);
		return buffs;
	}

}
