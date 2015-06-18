package org.greencubes.party;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;

public class Party {
	
	public final EntityPlayer player;
	public PartyMember leader;
	public int maxSize = 10;
	public List<PartyMember> members = new ArrayList<PartyMember>();
	public TIntObjectMap<PartyMember> membersMap = new TIntObjectHashMap<PartyMember>();
	
	public Party(EntityPlayer player) {
		this.player = player;
	}
	
	public boolean isPlayerLeader() {
		return leader == null || leader.playerId == player.playerId;
	}
}
