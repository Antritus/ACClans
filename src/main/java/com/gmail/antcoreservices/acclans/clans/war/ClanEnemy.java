package com.gmail.antcoreservices.acclans.clans.war;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClanEnemy {
	final private UUID uuid;
	final private long warStart;
	private String reason;
	private List<ClanWarSettings> clanWarSettings;
	public ClanEnemy(UUID uuid, long warStart, List<ClanWarSettings> clanWarSettings){
		this.warStart = warStart;
		this.uuid = uuid;
		this.clanWarSettings = clanWarSettings;
		if (clanWarSettings == null){
			this.clanWarSettings = new ArrayList<>();
		}
	}

	public long getWarStart() {
		return warStart;
	}

	public UUID getUUID() {
		return uuid;
	}

	public List<ClanWarSettings> getClanWarSettings() {
		return clanWarSettings;
	}
}
