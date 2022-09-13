package com.gmail.antcoreservices.acclans.clans;

import java.util.UUID;

public class ClanMember {
	private UUID uuid;
	private String lastKnownName;
	private Group currentGroup;

	public ClanMember(UUID uuid, String lastKnownName, Group currentGroup) {
		this.uuid = uuid;
		this.lastKnownName = lastKnownName;
		this.currentGroup = currentGroup;
	}

	public Group getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(Group currentGroup) {
		this.currentGroup = currentGroup;
	}

	public String getLastKnownName() {
		return lastKnownName;
	}

	public void setLastKnownName(String lastKnownName) {
		this.lastKnownName = lastKnownName;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
}
