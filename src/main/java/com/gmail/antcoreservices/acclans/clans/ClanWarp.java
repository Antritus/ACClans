package com.gmail.antcoreservices.acclans.clans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClanWarp {
	private String name;
	private String world;
	private List<UUID> allowedGroups;
	private double x, y, z;

	public ClanWarp(String name, String world, List<UUID> allowedGroups, double x, double y, double z) {
		this.name = name;
		this.world = world;
		this.allowedGroups = allowedGroups;
		this.x = x;
		this.y = y;
		this.z = z;
		if (allowedGroups == null) {
			allowedGroups = new ArrayList<>();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public List<UUID> getAllowedGroups() {
		return allowedGroups;
	}

	public void setAllowedGroups(List<UUID> allowedGroups) {
		this.allowedGroups = allowedGroups;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	public boolean isAllowed(Group group){
		if (allowedGroups.isEmpty()) {
			return true;
		}
		return		(allowedGroups.contains(group.getId()));

	}
}
