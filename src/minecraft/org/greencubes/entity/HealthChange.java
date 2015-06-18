package org.greencubes.entity;

public class HealthChange {
	
	public static final HealthChange[] map = new HealthChange[10];
	
	public static final HealthChange NORMAL_DAMAGE = new HealthChange(0, true);
	public static final HealthChange CRITICAL_DAMAGE = new HealthChange(1, true);
	public static final HealthChange POISON = new HealthChange(2, true);
	public static final HealthChange HEAL = new HealthChange(3, false);
	
	public final int id;
	public final boolean isNegative;
	
	private HealthChange(int id, boolean isNegative) {
		this.id = id;
		this.isNegative = isNegative;
		map[id] = this;
	}
	
	public static HealthChange getById(int id) {
		return map[id];
	}
}
