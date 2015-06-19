package org.greencubes.party;

public class PartyMember {
	
	public final int playerId;
	public final String coloredName;
	public final String simpleName;
	public MemberStatus status;
	
	public PartyMember(int id, String coloredName, String simpleName) {
		this.playerId = id;
		this.coloredName = coloredName;
		this.simpleName = simpleName;
	}
	
}
