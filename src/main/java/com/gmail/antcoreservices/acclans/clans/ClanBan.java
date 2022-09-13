package com.gmail.antcoreservices.acclans.clans;

import com.gmail.antcoreservices.acclans.ACClans;

import java.util.UUID;

public class ClanBan {
	private UUID clanID;
	private UUID uuid;
	private long time;
	private long date;
	private String reason;
	private UUID punisher;

	ACClans core;

	public ClanBan(ACClans core, UUID clanID, UUID uuid, long time, long date, String reason, UUID punisher){
		this.core = core;
		this.clanID = clanID;
		this.uuid = uuid;
		this.time = time;
		this.date = date;
		this.reason = reason;
		this.punisher = punisher;
	}
	public void deleteACore(){
		this.core = null;
	}
	public void setACore(ACClans core){
		this.core =core;
	}

	public UUID getPunisher() {
		return punisher;
	}

	public String getReason() {
		return reason;
	}

	public long getDate() {
		return date;
	}

	public long getTime() {
		return time;
	}

	public UUID getUUID() {
		return uuid;
	}

	public UUID getClanID() {
		return clanID;
	}
}
