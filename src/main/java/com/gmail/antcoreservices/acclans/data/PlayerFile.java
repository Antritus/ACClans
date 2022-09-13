package com.gmail.antcoreservices.acclans.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerFile {
	private final String lastName;

	private final UUID uuid;
	private UUID clanUUID;
	private UUID clanGroupUUID;

	private List<UUID> clanInvites;
	private List<String> joinMessages;

	private boolean isPrettyPrinting;
	private boolean hasClan;


	public PlayerFile(String lastName, UUID uuid, UUID clanUUID, List<UUID> clanInvites, List<String> knownIPs, List<String> knownNames, List<String> joinMessages, List<UUID> knownAccounts, List<Long> donationDates, HashMap<String, List<UUID>> knownAccountPerIP, long firstJoin, long lastSeen, double donatedAmount, boolean isOP, boolean isStaff, boolean isOnline, boolean isDonator, UUID clanGroupUUID, boolean isPrettyPrinting, boolean hasClan) {
		this.lastName = lastName;
		this.uuid = uuid;
		this.clanUUID = clanUUID;
		this.clanInvites = clanInvites;
		this.joinMessages = joinMessages;
		this.clanGroupUUID = clanGroupUUID;
		this.isPrettyPrinting = isPrettyPrinting;
		this.hasClan = hasClan;



		setup();
	}

	private void setup() {
		if (clanInvites == null)
			clanInvites = new ArrayList<>();
		if (joinMessages == null)
			joinMessages = new ArrayList<>();
		hasClan = clanUUID != null;
	}


	// default values

	public String getName() {
		return lastName;
	}

	public UUID getUUID() {
		return uuid;
	}


	// File formatting

	public boolean isPrettyPrinting() {
		return isPrettyPrinting;
	}

	public void setPrettyPrinting(boolean prettyPrinting) {
		isPrettyPrinting = prettyPrinting;
	}

	// Join stuff

	public List<String> getJoinMessages() {
		return joinMessages;
	}

	public void setJoinMessages(List<String> joinMessages) {
		this.joinMessages = joinMessages;
	}
	public void addJoinMessage(String string) {
		this.joinMessages.add(string);
	}
	public boolean hasJoinMessages() {
		return !this.joinMessages.isEmpty();
	}



	//Clan

	public List<UUID> getClanInvites() {
		return clanInvites;
	}
	public void setClanInvites(List<UUID> clanInvites) {
		this.clanInvites = clanInvites;
	}
	public void addClanInvite(UUID uuid) {
		this.clanInvites.add(uuid);
	}

	public UUID getClanUUID() {
		return clanUUID;
	}
	public void setClanUUID(UUID uuid) {
		this.clanUUID = uuid;
	}

	public boolean hasClan() {
		return hasClan;
	}

	private void setHasClan(boolean hasClan) {
		this.hasClan = hasClan;
	}


	public UUID getClanGroupUUID() {
		return clanGroupUUID;
	}
}