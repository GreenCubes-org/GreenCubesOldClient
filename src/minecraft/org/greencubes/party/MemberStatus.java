package org.greencubes.party;

public enum MemberStatus {
	
	//@formatter:off
	OK(0),
	DEAD(1),
	OFFLINE(2),
	;
	//@formatter:on
	
	private static final MemberStatus[] map = new MemberStatus[10];
	
	public final int id;
	
	private MemberStatus(int id) {
		this.id = id;
	}
	
	public static MemberStatus getById(int id) {
		return map[id];
	}
	
	static {
		for(MemberStatus ms : values()) {
			map[ms.id] = ms;
		}
	}
}
