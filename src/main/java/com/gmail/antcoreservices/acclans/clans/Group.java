package com.gmail.antcoreservices.acclans.clans;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
	private String name;
	private UUID id;
	private List<UUID> players;
	private String prefix;
	private ChatColor nicknameColor;
	private List<Permission> permissions;
	private int priority; // higher
	public Group(String name, UUID id, List<UUID> players, String prefix, ChatColor nicknameColor, List<Permission> permissions, int priority) {
		this.name = name;
		this.id = id;
		this.players = players;
		this.priority = priority;
		if (players == null) {
			players = new ArrayList<>();
		}
		this.prefix = prefix;
		this.nicknameColor = nicknameColor;
		this.permissions = permissions;
		if (permissions == null) {
			permissions = new ArrayList<>();
		}
	}


	public String getName() {
		return name;
	}

	public List<UUID> getUUIDS() {
		return players;
	}

	public String getPrefix() {
		return prefix;
	}

	public ChatColor getNicknameColor() {
		return nicknameColor;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
