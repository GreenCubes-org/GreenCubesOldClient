package org.greencubes.entity;

public class HealthChange {
	
	public static final HealthChange[] map = new HealthChange[10];
	
	public static final HealthChange NORMAL_DAMAGE = new HealthChange(0);
	public static final HealthChange CRITICAL_DAMAGE = new HealthChange(1);
	public static final HealthChange POISON = new HealthChange(2);
	public static final HealthChange HEAL = new HealthChange(3);
	
	public final int id;
	
	private HealthChange(int id) {
		this.id = id;
	}
	
	public static HealthChange getById(int id) {
		return map[id];
	}
	
}
