package com.gmail.antcoreservices.acclans.clans;

import com.gmail.antcoreservices.acclans.ACClans;
import com.gmail.antcoreservices.acclans.clans.war.ClanEnemy;
import com.gmail.antcoreservices.acclans.utils.StringUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.gmail.antcoreservices.acclans.ACClans.*;
import static com.gmail.antcoreservices.acclans.clans.Permission.ALL;
import static com.gmail.antcoreservices.acclans.clans.Permission.NONE;


@SuppressWarnings("unused")
public class Clan {
	String name;
	UUID id;
	private int maxPlayersInClan = 35;
	private int playersInClan;
	private List<ClanMember> players;
	private List<Group> groups;
	private List<UUID> sentOutInvitesList;
	private HashMap<UUID, Long> sentOutInvitesMap;
	private ACClans core;
	private List<ClanBan> clanBans;
	private List<ClanEnemy> enemyClans;
	private List<UUID> clanAskWarList;
	private List<UUID> enemyAskWarList;
	private HashMap<UUID, String> clanAskWarMap;
	private HashMap<UUID, String> enemyAskWarMap;
	private HashMap<UUID, Long> enemyAskWarTimeEnd;
	private HashMap<UUID, Long> clanAskWarTimeEnd;


	public Clan(UUID id, String name, int playersInClan, List<ClanMember> players, List<Group> groups, List<UUID> sentOutInvitesList, List<ClanBan> clanBans, HashMap<UUID, Long> sentOutInvitesMap, ACClans core, List<ClanEnemy> enemyClans, List<UUID> clanAskWarList, List<UUID> enemyAskWarList, HashMap<UUID, String> clanAskWarMap, HashMap<UUID, String> enemyAskWarMap, HashMap<UUID, Long> enemyAskWarTimeEnd, HashMap<UUID, Long> clanAskWarTimeEnd) {
		this.core = core;
		this.name = name;
		this.id = id;
		this.playersInClan = playersInClan;
		this.players = players;
		this.groups = groups;
		this.sentOutInvitesList = sentOutInvitesList;
		this.sentOutInvitesMap = sentOutInvitesMap;
		this.clanBans = clanBans;
		this.enemyClans = enemyClans;
		this.clanAskWarList = clanAskWarList;
		this.enemyAskWarList = enemyAskWarList;
		this.clanAskWarMap = clanAskWarMap;
		this.enemyAskWarMap = enemyAskWarMap;
		this.enemyAskWarTimeEnd = enemyAskWarTimeEnd;
		this.clanAskWarTimeEnd = clanAskWarTimeEnd;
		setup();
	}

	private void setup(){
		if (this.groups == null) {
			this.groups = new ArrayList<>();
			generateGroups();
		}
		if (this.sentOutInvitesMap == null){
			this.sentOutInvitesMap = new HashMap<>();
		}
		if (this.sentOutInvitesList == null) {
			this.sentOutInvitesList = new ArrayList<>();
		}
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		if (clanBans == null){
			this.clanBans = new ArrayList<>();
		}
		if (enemyClans == null){
			this.enemyClans = new ArrayList<>();
		}
	}

	public void deleteNonPersistentData(){
		clanAskWarList = null;
		clanAskWarMap = null;
		clanAskWarTimeEnd = null;
		enemyAskWarList = null;
		enemyAskWarMap = null;
		enemyAskWarTimeEnd = null;
		sentOutInvitesList = null;
		sentOutInvitesMap = null;
	}


	public void deleteDupeCore(){
		core = null;
	}
	public void setDupeCore(ACClans core) {
		this.core = core;
	}

	public UUID getId() {
		return id;
	}

	public List<ClanMember> getPlayers() {
		return players;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addPlayer(Player player) {
		this.players.add(new ClanMember(player.getUniqueId(),player.getName(),null));
	}
	public void removePlayer(Player player) {
		for (Group group : groups) {
			if (group.getUUIDS().isEmpty()){
				continue;
			}
			group.getUUIDS().removeIf(uuid -> uuid == player.getUniqueId());
		}
		players.removeIf(clanMember -> clanMember.getUUID() == player.getUniqueId());
	}
	public void removePlayer(UUID uuid) {
		for (Group group : groups) {
			if (group.getUUIDS().isEmpty()){
				continue;
			}
			group.getUUIDS().removeIf(uuidGroup -> uuidGroup == uuid);
		}
		players.removeIf(clanMember -> clanMember.getUUID() == uuid);
	}

	public void removeGroup(Group group){
		this.groups.remove(group);
	}
	public void addGroup(Group group) {
		this.groups.add(group);
	}

	public void promotePlayer(Player player) {
		//todo
	}
	public void demotePlayer(Player player) {
		//todo
	}
	public void setPlayerGroup(Player player, Group group) {
		for (ClanMember clanMember : players){
			clanMember.setCurrentGroup(group);
		}
	}
	public void removePlayerGroup(Player player) {
		for (ClanMember clanMember : players){
			clanMember.setCurrentGroup(null);
		}
	}


	public String getName() {
		return name;
	}

	public int getPlayersInClan() {
		return playersInClan;
	}

	public void setPlayersInClan(int playersInClan) {
		this.playersInClan = playersInClan;
	}

	public int getMaxPlayersInClan() {
		return maxPlayersInClan;
	}

	public void setMaxPlayersInClan(int maxPlayersInClan) {
		this.maxPlayersInClan = maxPlayersInClan;
	}

	public void generateGroups() {
		addGroup(new Group("owner", id, null,"&4[Owner]", ChatColor.WHITE, List.of(new Permission[]{
				ALL,
		}), 10));
		addGroup(new Group("admin", id, null,"&c[Admin]", ChatColor.WHITE, List.of(new Permission[]{
				NONE,

		}), 9));
		addGroup(new Group("mod", id, null,"&3[Mod]", ChatColor.WHITE, List.of(new Permission[]{
				NONE,
		}), 8));
		addGroup(new Group("vip", id, null,"&a[VIP]", ChatColor.WHITE, List.of(new Permission[]{
				NONE,
		}), 1));
		addGroup(new Group("default", id, null,"&7[Default]", ChatColor.WHITE, List.of(new Permission[]{
				NONE,
		}), 0));
	}
	public void sendClanMessage(Clan clan, String string) {
		for (ClanMember clanMember : clan.getPlayers()) {
			if (Bukkit.getPlayer(clanMember.getUUID()) == null || !Bukkit.getPlayer(clanMember.getUUID()).isOnline()){
				continue;
			}
			message(Bukkit.getPlayer(clanMember.getUUID()), string);
		}
	}
	public void sendClanOfflineMessage(Clan clan, String string) {
		for (ClanMember clanMember : clan.getPlayers()) {
			if (Bukkit.getPlayer(clanMember.getUUID()) == null || Bukkit.getPlayer(clanMember.getUUID()).isOnline()){
				continue;
			}
			if (!core.getPlayerData().getPlayerFiles().containsKey(clanMember.getUUID())) {
				core.getPlayerData().loadPlayer(clanMember.getUUID());
			}
			core.getPlayerData().getPlayerFiles().get(clanMember.getUUID()).addJoinMessage(string);
		}
	}
	private void sendClanMessage(String string) {
		for (ClanMember clanMember : this.getPlayers()) {
			if (Bukkit.getPlayer(clanMember.getUUID()) == null || !Bukkit.getPlayer(clanMember.getUUID()).isOnline()){
				continue;
			}
			message(Bukkit.getPlayer(clanMember.getUUID()), string);
		}
	}
	private void sendClanOfflineMessage(String string) {
		for (ClanMember clanMember : this.getPlayers()) {
			if (Bukkit.getPlayer(clanMember.getUUID()) == null || Bukkit.getPlayer(clanMember.getUUID()).isOnline()){
				continue;
			}
			if (!core.getPlayerData().getPlayerFiles().containsKey(clanMember.getUUID())) {
				core.getPlayerData().loadPlayer(clanMember.getUUID());
			}
			core.getPlayerData().getPlayerFiles().get(clanMember.getUUID()).addJoinMessage(string);
		}
	}
	private void sendOfflineMessage(Player player, String string) {
		core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).addJoinMessage(string);

	}
	private void message(Player player, String message){
		player.sendMessage(StringUtils.translate(message));
	}
	private void sendHelpMenu(Player player){
		player.sendMessage("Help Menu!!!");
	}
	private void sendCommandUsage(Player player, String usage) {
		message(player, "&cIncorrect Usage! &7" + usage);
	}
	private boolean hasClan(Player player) {
		return core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).hasClan();
	}



	private void deleteClan(String reason) {
		boolean found = false;
		for (Clan clanCheck : core.getClanData().getClans()){
			if (this.getId() == clanCheck.getId()) {
				found = true;
			}
		}
		if (!found) {
			if (!core.getClanData().loadClan(this.getId())) {
				System.out.println("Error loading clan!");
				;
				System.out.println("No clan found while attempting to delete a clan!");
				return;
			}
		}
		sendClanMessage(this, "&7Your clan has been disbanded!" + "\n" +
				"Reason: "+reason
		);
		sendClanOfflineMessage(this, "&7Your clan has been disbanded!" + "\n" +
				"Reason: "+reason);
	}
	private void leaveClan(@NotNull Player player) {
		if (core.getClanData().getPlayerClans().get(player.getUniqueId()) != null) {
			message(player, "&7You are currently not in a clan!");
			return;
		}
		sendClanMessage(this, "&e" + player.getName() + "&7 has left your clan!");
		message(player, "&7You have left &e" + this.getName());
		for (Clan clanI : core.getClanData().getClans()) {
			clanI.removePlayer(player);
		}
	}
	private void joinClan(@NotNull Player player) {
		if (core.getClanData().getPlayerClans().get(player.getUniqueId()) != null) {
			message(player, "&7You cannot join this clan due to you having a clan!");
			return;
		}
		sendClanMessage(this, "&e" + player.getName() + "&7 has joined your clan!");
		message(player, "&7You have joined &e" + this.getName());
		for (Clan clanI : core.getClanData().getClans()) {
			clanI.addPlayer(player);
		}
	}
	private void kickPlayer(Player player, Player kicker, String reason){
		int priorityToKick = -10;
		int priority = -10;
		boolean permsToKick = false;
		boolean isInClan = false;
		for (Group group : this.getGroups()) {
			for (UUID uuid : group.getUUIDS()) {
				if (uuid == player.getUniqueId()) {
					priorityToKick = group.getPriority();
					isInClan = true;
				}
				if (uuid == kicker.getUniqueId()) {
					priority = group.getPriority();
					if (group.getPermissions().contains(Permission.KICK)){
						permsToKick = true;
					}
				}
			}
		}
		if (!permsToKick) {
			this.message(kicker, "&cYou do not have enough permissions to kick a player from the clan!");
			return;
		}
		if (player.getUniqueId() == kicker.getUniqueId()) {
			this.message(player, "&cYou cannot kick yourself.");
			return;
		}
		if (!isInClan) {
			this.message(player, "&cYou cannot kick a player that is not in the same clan!");
			return;
		}
		if (priorityToKick == -10 && priority == -10) {
			this.message(kicker, "&cInternal error accured while attempting to sort priority. Contact administration!");
			System.out.println("Error Accorded, while trying to get clan priority of players (CLAN KICK (-10, -10))!");
			return;
		}
		if (priorityToKick == priority) {
			this.message(kicker, "&cYou do not have enough permissions to kick this player from the clan!");
		}
		if (priorityToKick > priority) {
			this.message(kicker, "&cYou do not have enough permissions to kick this player from the clan!");
		}
		if (priorityToKick < priority) {
			if (player.isOnline()) {
				this.message(player, "&7You have been kicked from the clan by " + kicker.getName() + "\n&7Reason: "+ reason);
			} else {
				this.sendOfflineMessage(player, "&7You have been kicked from the clan by " + kicker.getName() + "\n&7Reason: "+ reason);
			}
			this.sendClanMessage("&e" + player.getName() + "&7 has been from the clan due to &e" + reason + "&7 by &e" + kicker.getName());
			this.removePlayer(player);
		}
	}

	public void invitePlayer(Player sender, Player player){
		boolean hasPerm = false;
		for (Group group : this.getGroups()) {
			for (UUID uuid : group.getUUIDS()) {
				if (uuid == sender.getUniqueId()) {
					if (group.getPermissions().contains(Permission.INVITE)){
						hasPerm = true;
					}
				}
			}
		}
		if (!hasPerm) {
			message(sender, "&7You do not have permissions to send a message in your clan!");
			return;
		}
		if (core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).hasClan()){
			message(sender, "&7You cannot invite this player due to them having a clan!");
			return;
		}
		if (core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).hasJoinMessages()){
			if (core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).getClanInvites().contains(this.getId())){
				message(sender, "&7You cannot invite this player due to them having an invite already!");
				return;
			}
		}
		sentOutInvitesList.remove(player.getUniqueId());
		sentOutInvitesMap.remove(player.getUniqueId());

		sentOutInvitesList.add(player.getUniqueId());
		sentOutInvitesMap.put(player.getUniqueId(), System.currentTimeMillis());
		message(player, "&f%player%&7 has been invited to the clan by &f%sender%&7!".replaceAll("%player%", player.getName()).replaceAll("%sender%",sender.getName()));
		core.getPlayerData().getPlayerFiles().get(player.getUniqueId()).addClanInvite(this.getId());
		TextComponent comp = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&7You have been invited to join &f%clan%&7! Click or type /clan join %clan% to join it!".replaceAll("%clan%", getName())));
		comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan join %clan%".replaceAll("%clan%", this.getName())));
		comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&7Click here to join %clan%".replaceAll("%clan%", this.getName()))).create()));
		player.spigot().sendMessage(comp);
	}
	public void revokeInvite(UUID uuid) {
		if (sentOutInvitesList.contains(uuid)){
			sentOutInvitesList.remove(uuid);
			sentOutInvitesMap.remove(uuid);
		}
		if (!core.getPlayerData().getPlayerFiles().containsKey(uuid) && !core.getPlayerData().loadPlayer(uuid)){
			return;
		}
		core.getPlayerData().loadPlayer(uuid);
		core.getPlayerData().getPlayerFiles().get(uuid).getClanInvites().remove(this.getId());
	}
	public void sendClanInfo(@NotNull Player player) {
		player.sendMessage(						"&3&l%clan%" +
				"UUID" +
				"%nl%" +
				"&3Players: %players%" +
				"%nl%" +
				"&3Max Players: %maxplayers%" +
				"%nl%" +
				"Invites Sent: " +
				"%nl%" +
				"Clan Allies: " +
				"%nl%" +
				"Clan Enemies: " +
				"%nl%");
		for (Group group : groups){
			List<String> players = new ArrayList<>();
			for (UUID uuid : group.getUUIDS()) {
				if (Bukkit.getPlayer(uuid) == null) {
					players.add(Bukkit.getOfflinePlayer(uuid).getPlayer().getName());
				} else {
					players.add(Bukkit.getPlayer(uuid).getName());
				}
			}
			player.sendMessage(
					group.getName()
							+ "\n" +
							players.stream().toString()
			);
		}
	}


	public List<UUID> getSentOutInvitesList() {
		return sentOutInvitesList;
	}

	public void setSentOutInvitesList(List<UUID> sentOutInvitesList) {
		this.sentOutInvitesList = sentOutInvitesList;
	}

	public HashMap<UUID, Long> getSentOutInvitesMap() {
		return sentOutInvitesMap;
	}

	public void setSentOutInvitesMap(HashMap<UUID, Long> sentOutInvitesMap) {
		this.sentOutInvitesMap = sentOutInvitesMap;
	}

	public ClanBan getBans(){
		return (ClanBan) clanBans;
	}


	public void ban(Player banner, UUID uuid, String reason, double seconds) {
		final List<String> paths = new ArrayList<>();
		final List<String> values = new ArrayList<>();
		paths.add("%clan_id%");
		values.add(this.getId().toString());
		paths.add("%clan_name%");
		values.add(this.getName());
		Player bannable = Bukkit.getPlayer(uuid);
		if (bannable == null) {
			bannable = Bukkit.getOfflinePlayer(uuid).getPlayer();
		}
		if (bannable == null){
			return;
		}
		values.add(bannable.getName()); paths.add("%player_victim%");
		values.add(banner.getName()); paths.add("%player_punisher%");
		for (ClanBan clanBan : clanBans){
			if (clanBan.getUUID() == uuid){
				values.add(clanBan.getReason()); paths.add("%ban_reason%");
				values.add(String.valueOf(clanBan.getTime())); paths.add("%ban_time_left%");
				values.add(String.valueOf(clanBan.getDate())); paths.add("%ban_date%");
				message(banner, core.getClanMessages().getFormattedMessage("clan-ban-player-try-ban-banned", paths, values));
				return;
			}
		}
		int priorityToKick = -10;
		int priority = -10;
		boolean permsToPunish = false;
		boolean isInClan = false;
		for (Group group : this.getGroups()) {
			for (UUID uuidMember : group.getUUIDS()) {
				if (uuidMember == uuid) {
					priorityToKick = group.getPriority();
					isInClan = true;
					values.add(group.getName());
					paths.add("%player_victim_group%");
				}
				if (uuid == banner.getUniqueId()) {
					priority = group.getPriority();
					if (group.getPermissions().contains(Permission.BAN)){
						permsToPunish = true;
					}
					values.add(group.getName());
					paths.add("%player_punisher_group%");
				}
			}
		}
		if (!permsToPunish) {
			message(banner, core.getClanMessages().getFormattedMessage(
					"clan-ban-no-permission", paths, values));
			return;
		}
		seconds = seconds*1000;
		values.add(String.valueOf(seconds/1000));
		paths.add("%ban_time%");
		if (isInClan) {

			if (uuid == banner.getUniqueId()) {
				message(banner, core.getClanMessages().getFormattedMessage(
						"clan-ban-player-try-ban-self", paths, values));
				return;
			}
			values.add(String.valueOf(priority));
			paths.add("%player_punisher_priority%");
			values.add(String.valueOf(priorityToKick));
			paths.add("%player_victim_priority%");

			if (priorityToKick == -10 && priority == -10) {
				message(banner, "&cInternal error accursed while attempting to sort priority. Contact administration!");
				System.out.println("Error Accorded, while trying to get clan priority of players (CLAN KICK (-10, -10))!" + getName());
				return;
			}
			if (priorityToKick == priority) {
				message(banner, core.getClanMessages().getFormattedMessage(
						"clan-ban-player-try-ban-same-priority", paths, values));
				return;
			}
			if (priorityToKick > priority) {
				message(banner, core.getClanMessages().getFormattedMessage(
						"clan-ban-player-try-ban-higher-priority", paths, values));
				return;
			}
			core.getClanData().getClan(getId()).removePlayer(bannable.getUniqueId());
		}
		values.add(reason);
		paths.add("%ban_reason%");
		if (bannable.isOnline()) {
			message(banner, core.getClanMessages().getFormattedMessage(
					"clan-ban-player-ban-victim-success-receiver-victim", paths, values));
		} else {
			messageOffline(banner, core.getClanMessages().getFormattedMessage("clan-ban-player-ban-victim-success-receiver-victim", paths, values));
		}
		sendClanMessage(this, core.getClanMessages().getFormattedMessage("clan-ban-player-ban-victim-success-receiver-clan", paths, values));
		clanBans.add(new ClanBan(core,this.getId(), uuid, System.currentTimeMillis() + (long) seconds, System.currentTimeMillis(), reason, banner.getUniqueId()));
	}

	public void unban(Player player, UUID uuid, String reason){
		final List<String> paths = new ArrayList<>();
		final List<String> values = new ArrayList<>();
		paths.add("%clan_id%");
		values.add(this.getId().toString());
		paths.add("%clan_name%");
		values.add(this.getName());
		Player unbannable = Bukkit.getPlayer(uuid);
		if (unbannable == null) {
			unbannable = Bukkit.getOfflinePlayer(uuid).getPlayer();
		}
		if (unbannable == null){
			return;
		}
		values.add(unbannable.getName()); paths.add("%player_victim%");
		values.add(unbannable.getName()); paths.add("%player_punisher%");
		for (ClanBan clanBan : clanBans){
			if (clanBan.getUUID() == uuid){
				message(unbannable, core.getClanMessages().getFormattedMessage("clan-unban-player-not-banned", paths, values));
				return;
			}
		}
		boolean perm = false;
		boolean isInClan = false;
		for (Group group : this.getGroups()) {
			for (UUID uuidMember : group.getUUIDS()) {
				if (uuidMember == uuid) {
					message(unbannable, core.getClanMessages().getFormattedMessage("clan-unban-player-same-clan", paths, values));
					return;
				}
				if (uuid == player.getUniqueId()) {
					if (group.getPermissions().contains(Permission.BAN)){
						perm = true;
					}
					values.add(group.getName());
				}
			}
		}
		if (!perm){
			message(player, core.getClanMessages().getFormattedMessage(
					"clan-ban-no-permission", paths, values));
			return;
		}
		values.add(reason);
		paths.add("%unban_reason%");
		if (unbannable.isOnline()) {
			message(unbannable, core.getClanMessages().getFormattedMessage(
					"clan-player-unban-victim-success-receiver-victim", paths, values));
		} else {
			messageOffline(unbannable, core.getClanMessages().getFormattedMessage(
					"clan-player-unban-victim-success-receiver-victim", paths, values));
		}
		sendClanMessage(this, core.getClanMessages().getFormattedMessage("clan-ban-player-ban-victim-success-receiver-clan", paths, values));
		clanBans.removeIf(clanBan -> clanBan.getUUID() == uuid);
	}

	public void chat(@NotNull Player player, String string){
		//todo: add discord support
		//todo: clan chat event
		List<String> paths = new ArrayList<>();
		List<String> values = new ArrayList<>();
		paths.add("%player%");
		paths.add("%message%");
		paths.add("%prefix%");
		values.add(player.getName());
		values.add(string);
		String prefix = getGroup(player.getUniqueId()).getPrefix();
		if (prefix == null){
			prefix = "";
		}
		values.add(prefix);
		for (ClanMember clanMember : players){
			if (Bukkit.getOfflinePlayer(clanMember.getUUID()).isOnline()){
				if (prefix == ""){
					Bukkit.getOfflinePlayer(clanMember.getUUID()).getPlayer().sendMessage(
							core.getClanMessages().getFormattedMessage("clan-chat-format-no-prefix", paths, values)
					);
				} else {
					Bukkit.getOfflinePlayer(clanMember.getUUID()).getPlayer().sendMessage(
							core.getClanMessages().getFormattedMessage("clan-chat-format", paths, values)
					);
				}
			}
		}

	}
	public Group getGroup(UUID uuid){
		for (Group group : groups){
			if (group.getId() == uuid){
				return group;
			}
		}
		return null;
	}

	public List<ClanEnemy> getEnemyClans() {
		return enemyClans;
	}
	protected void setEnemyClans(List<ClanEnemy> clanEnemies){
		this.enemyClans = clanEnemies;
	}
	protected void addEnemyClan(ClanEnemy clanEnemy){
		this.enemyClans.add(clanEnemy);
	}

	public HashMap<UUID, String> getClanAskWarMap() {
		return clanAskWarMap;
	}
	public void setClanAskWarMap(HashMap<UUID, String> clanAskWarMap){
		this.clanAskWarMap = clanAskWarMap;
	}
	public List<UUID> getEnemyAskWarList() {
		return enemyAskWarList;
	}

	public List<UUID> getClanAskWarList() {
		return clanAskWarList;
	}

	public HashMap<UUID, String> getEnemyAskWarMap() {
		return enemyAskWarMap;
	}
	public void setEnemyAskWarMap(HashMap<UUID, String> enemyAskWarMap){
		this.enemyAskWarMap = enemyAskWarMap;
	}
	public void setEnemyAskWarList(List<UUID> enemyAskWarList){
		this.enemyAskWarList = enemyAskWarList;
	}
	public void setEnemyAskCode(UUID clan, String code){
		this.enemyAskWarMap.put(clan, code);
	}
	public void setClanAskCode(UUID clan, String code){
		this.clanAskWarMap.put(clan, code);
	}
	public void addEnemyAskWar(UUID uuid){
		this.enemyAskWarList.add(uuid);
	}
	public void addClanAskWar(UUID uuid){
		this.clanAskWarList.add(uuid);
	}

	public HashMap<UUID, Long> getEnemyAskWarTimeEnd() {
		return enemyAskWarTimeEnd;
	}

	public HashMap<UUID, Long> getClanAskWarTimeEnd() {
		return clanAskWarTimeEnd;
	}
}
