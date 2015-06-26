package net.minecraft.src;

import org.greencubes.util.ChatColor;
import org.greencubes.util.I18n;


public abstract class Buff {
	
	static final String COLOR_PREFIX_GOOD = ChatColor.GREEN.toString();
	static final String COLOR_PREFIX_BAD = ChatColor.RED.toString();
	
	public static final Buff[] byId = new Buff[Short.MAX_VALUE];
	
	public static final Buff HUNGER_1 = new SimpleBuff(1, "hunger.1.png", COLOR_PREFIX_BAD, I18n.get("Голод I"), I18n.get("Вы голодны и не можете нормально работать.\nВы так же медленно теряете здоровье.\n\n${EFFECTS}"));
	public static final Buff HUNGER_2 = new SimpleBuff(2, "hunger.2.png", COLOR_PREFIX_BAD, I18n.get("Голод II"), I18n.get("Вы голодны и не можете нормально работать.\nВы так же медленно теряете здоровье.\n\n${EFFECTS}"));
	public static final Buff HUNGER_3 = new SimpleBuff(3, "hunger.3.png", COLOR_PREFIX_BAD, I18n.get("Голод III"), I18n.get("Вы голодны и не можете нормально работать.\nВы так же медленно теряете здоровье.\n\n${EFFECTS}"));
	public static final Buff HEALTH_REGEN_FOOD = new SimpleBuff(4, "regen.png", COLOR_PREFIX_GOOD, I18n.get("Регенерация"), I18n.get("Вы восстанавливаете ${E.REGENERATION} здоровья в секунду"));
	public static final Buff FAST_RUN = new SimpleBuff(5, "fastrun.png", COLOR_PREFIX_GOOD, I18n.get("Быстрый бег"), I18n.get("Скорость вашего бега повышена.\n\n${EFFECTS}"));
	public static final Buff ENERGY_BAR = new SimpleBuff(6, "hunger.slow.png", COLOR_PREFIX_GOOD, I18n.get("Уменьшение голода"), I18n.get("Вы проголадываетесь на ${E.HUNGERSLOW} медленнее"));
	public static final Buff SNOW_BOOST = new SimpleBuff(7, "snowboost.png", COLOR_PREFIX_GOOD, I18n.get("Снегоходы"), I18n.get("Вы быстрее передвигаетесь по снегу.\n\n${EFFECTS}"));
	public static final Buff WALKED_COUNTER = new EmptyBuff(8);
	public static final Buff NY_MOOD = new SimpleBuff(9, "nymood.png", COLOR_PREFIX_GOOD, I18n.get("Новогоднее настроение"), null);
	public static final Buff PRESENT_CD = new SimpleBuff(10, "presentcd.png", COLOR_PREFIX_GOOD, I18n.get("Ожидание подарка"), null);
	public static final Buff SATYR = new SimpleBuff(11, "satyr.png", COLOR_PREFIX_GOOD, I18n.get("Сила сатиров"), I18n.get("\n\n${EFFECTS}"));
	public static final Buff PVP = new SimpleBuff(12, null, COLOR_PREFIX_BAD, I18n.get("PVP"), I18n.get("\n\nВаш статус PVP включён, другие игроки могут вас атаковать."));
	public static final Buff CRITICAL_CHANCE_ARMOR =  new EmptyBuff(13);
	public static final Buff BOW_SPEED_ARMOR =  new EmptyBuff(14);
	
	public final int id;
	/**
	 * <b>Can be null</b>
	 */
	public final String iconName;
	public final String colorPrefix;
	
	protected Buff(int id, String iconName, String colorPrefix) {
		this.id = id;
		this.iconName = iconName;
		this.colorPrefix = colorPrefix;
		if(byId[id] != null)
			throw new AssertionError("Slot " + id + " is already occupied by " + byId[id] + " when adding " + this);
		byId[id] = this;
	}
	
	public String getTextureFramed() {
		return iconName == null ? null : "/gc_images/buffs/" + iconName;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + id + "," + iconName + "}";
	}
	
	public abstract String getBuffDescription(BuffActive activeBuff);
	
	protected static class EmptyBuff extends Buff {

		protected EmptyBuff(int id) {
			super(id, null, "");
		}

		@Override
		public String getBuffDescription(BuffActive activeBuff) {
			return "";
		}
	}
	
	protected static class SimpleBuff extends Buff {
		
		protected String name;
		protected String description;

		protected SimpleBuff(int id, String iconName, String colorPrefix, String name, String description) {
			super(id, iconName, colorPrefix);
			this.name = name;
			this.description = description;
		}

		@Override
		public String getBuffDescription(BuffActive activeBuff) {
			StringBuilder sb = new StringBuilder();
			sb.append(colorPrefix).append(name).append(ChatColor.WHITE);
			if(description != null) {
				sb.append('\n').append(description);
			}
			String s = sb.toString();
			StringBuilder effects = null;
			if(s.contains("${EFFECTS}"))
				effects = new StringBuilder();
			for(BuffEffect e : activeBuff.effects) {
				if(effects != null) {
					if(effects.length() != 0)
						effects.append('\n');
					effects.append(e.type.getValueAndName(e)).append(ChatColor.WHITE);
				}
				if(s.contains("${E." + e.type.name.toUpperCase() + "}"))
					s = s.replace("${E." + e.type.name.toUpperCase() + "}", e.type.toStringValue(e));
			}
			if(effects != null)
				s = s.replace("${EFFECTS}", effects);
			return s;
		}
		
	}
}
