package net.minecraft.src;

import org.greencubes.util.I18n;

public class BuffEffectType {

	public static final BuffEffectType[] byId = new BuffEffectType[256];

	public static final BuffEffectType MOVE_SPEED_UP = new BuffEffectType(1, "MoveSpeedUp", "\247aУскорение: %s"); // TODO translate
	public static final BuffEffectType MOVE_SPEED_DOWN = new BuffEffectType(2, "MoveSlowDown", "\247cЗамедление: -%s"); // TODO translate
	public static final BuffEffectType DIG_SPEED_UP = new BuffEffectType(3, "DigSpeedUp", "\247aСкорость копания: +%s"); // TODO translate
	public static final BuffEffectType DIG_SPEED_DOWN = new BuffEffectType(4, "DigSpeedDown", "\247cСкорость копания: -%s"); // TODO translate
	//public static final BuffEffectType DAMAGE_BOOST = new BuffEffectType(5, "DamageBoost");
	//public static final BuffEffectType HEAL = new BuffEffectType(6, "Heal");
	//public static final BuffEffectType HEAL_DAMAGE = new BuffEffectType(7, "HealDamage");
	//public static final BuffEffectType JUMP = new BuffEffectType(8, "Jump");
	//public static final BuffEffectType CONFUSION = new BuffEffectType(9, "Confusion");
	public static final BuffEffectType REGENERATION = new IntegerBuffEffectType(10, "Regeneration", "\247aРегенрация: %s здоровья в сек."); // TODO translate
	//public static final BuffEffectType RESISTANCE = new BuffEffectType(11, "Resistance");
	//public static final BuffEffectType FIRE_RESISTANCE = new BuffEffectType(12, "FireResistance");
	//public static final BuffEffectType WATER_BREATHING = new BuffEffectType(13, "WaterBreathing");
	//public static final BuffEffectType INVISIBILITY = new BuffEffectType(14, "Invisibility");
	//public static final BuffEffectType BLIDNESS = new BuffEffectType(15, "Blidness");
	//public static final BuffEffectType NIGHT_VISION = new BuffEffectType(16, "NightVision");
	//public static final BuffEffectType HUNGER_POISONING = new BuffEffectType(17,"HungerPoisoning");
	//public static final BuffEffectType WEAKNESS = new BuffEffectType(18, "Weakness");
	//public static final BuffEffectType HEALTH_POISONING = new BuffEffectType(19, "HealthPoisoning");
	public static final BuffEffectType FAST_RUN = new BuffEffectType(20, "FastRun", "\247aСкорость бега: +%s"); // TODO translate
	public static final BuffEffectType NO_RUN = new BuffEffectType(21, "NoRun", "\247cНельзя бегать"); // TODO translate
	public static final BuffEffectType HUNGER_SLOW = new BuffEffectType(22, "HungerSlow", "\247aЗамедление голода: %s"); // TODO translate
	public static final BuffEffectType SNOW_BOOST = new IntegerBuffEffectType(23, "SnowBoost", "\247aУскорение на снегу: +%s"); // TODO translate
	public static final BuffEffectType PVP = new BuffEffectType(24, "PVP", null);
	public static final BuffEffectType CRITICAL_CHANCE = new IntegerBuffEffectType(25, "CriticalChance", "\247aКритический урон x%s");
	public static final BuffEffectType BOW_SPEED = new BuffEffectType(26, "BowSpeed", "\247aУскорение натяжения титевы: +%1$s%%\n\247aЗамедление при натягивании титевы: -%1$s%%");

	public final int id;
	public final String name;
	public final String translate;

	protected BuffEffectType(int id, String name, String translate) {
		this.id = id;
		this.name = name;
		this.translate = translate;
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
	
	public String toStringValue(BuffEffect be) {
		return (int) (be.multipler * 100) + "%";
	}
	
	public String getValueAndName(BuffEffect be) {
		return I18n.get(translate, toStringValue(be));
	}
	
	protected static class PercentBuffEffectType extends BuffEffectType {

		protected PercentBuffEffectType(int id, String name, String translate) {
			super(id, name, translate);
		}
		
		@Override
		public String toStringValue(BuffEffect be) {
			return Integer.toString((int) (be.multipler * 100));
		}
	}
	
	protected static class IntegerBuffEffectType extends BuffEffectType {

		protected IntegerBuffEffectType(int id, String name, String translate) {
			super(id, name, translate);
		}
		
		@Override
		public String toStringValue(BuffEffect be) {
			return Integer.toString((int) be.multipler);
		}
	}
}