package net.minecraft.src;

public class BuffEffectType {

	public static final BuffEffectType[] byId = new BuffEffectType[256];

	public static final BuffEffectType MOVE_SPEED_UP = new BuffEffectType(1, "MoveSpeedUp");
	public static final BuffEffectType MOVE_SPEED_DOWN = new BuffEffectType(2, "MoveSlowDown");
	public static final BuffEffectType DIG_SPEED_UP = new BuffEffectType(3, "DigSpeedUp");
	public static final BuffEffectType DIG_SPEED_DOWN = new BuffEffectType(4, "DigSpeedDown");
	//public static final BuffEffectType DAMAGE_BOOST = new BuffEffectType(5, "DamageBoost");
	//public static final BuffEffectType HEAL = new BuffEffectType(6, "Heal");
	//public static final BuffEffectType HEAL_DAMAGE = new BuffEffectType(7, "HealDamage");
	//public static final BuffEffectType JUMP = new BuffEffectType(8, "Jump");
	//public static final BuffEffectType CONFUSION = new BuffEffectType(9, "Confusion");
	public static final BuffEffectType REGENERATION = new BuffEffectType(10, "Regeneration");
	//public static final BuffEffectType RESISTANCE = new BuffEffectType(11, "Resistance");
	//public static final BuffEffectType FIRE_RESISTANCE = new BuffEffectType(12, "FireResistance");
	//public static final BuffEffectType WATER_BREATHING = new BuffEffectType(13, "WaterBreathing");
	//public static final BuffEffectType INVISIBILITY = new BuffEffectType(14, "Invisibility");
	//public static final BuffEffectType BLIDNESS = new BuffEffectType(15, "Blidness");
	//public static final BuffEffectType NIGHT_VISION = new BuffEffectType(16, "NightVision");
	//public static final BuffEffectType HUNGER_POISONING = new BuffEffectType(17,"HungerPoisoning");
	//public static final BuffEffectType WEAKNESS = new BuffEffectType(18, "Weakness");
	//public static final BuffEffectType HEALTH_POISONING = new BuffEffectType(19, "HealthPoisoning");
	public static final BuffEffectType FAST_RUN = new BuffEffectType(20, "FastRun");
	public static final BuffEffectType NO_RUN = new BuffEffectType(21, "NoRun");
	public static final BuffEffectType HUNGER_SLOW = new BuffEffectType(22, "HungerSlow");
	public static final BuffEffectType SNOW_BOOST = new BuffEffectType(23, "SnowBoost");

	public final int id;
	public final String name;

	protected BuffEffectType(int id, String name) {
		this.id = id;
		this.name = name;
		if(byId[id] != null)
			throw new IllegalArgumentException("Buff effect type id " + id + " is occupied by " + byId[id] + " when adding " + this);
		byId[id] = this;
	}

	@Override
	public String toString() {
		return "BuffEffectType{" + id + "," + name + "}";
	}

	public String name() {
		return StringTranslate.getInstance().translateKey("buff.effect." + name);
	}
}