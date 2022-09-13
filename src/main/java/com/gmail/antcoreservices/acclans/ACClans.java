package com.gmail.antcoreservices.acclans;

import com.gmail.antcoreservices.acclans.clans.Clan;
import com.gmail.antcoreservices.acclans.clans.ClanMember;
import com.gmail.antcoreservices.acclans.clans.Group;
import com.gmail.antcoreservices.acclans.data.ConfigurationFile;
import com.gmail.antcoreservices.acclans.data.MessageConfig;
import com.gmail.antcoreservices.acclans.data.PlayerData;
import com.gmail.antcoreservices.acclans.data.ClanData;
import com.gmail.antcoreservices.acclans.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class ACClans extends JavaPlugin {
	// todo: quests, wars, alliance, claims, discord hook to send messages to discord (clan discord)
	private static ClanData clanData;
	private MessageConfig clanMessages;
	private static PlayerData playerData;


	@Override
	public void onEnable() {
		clanData = new ClanData(this);
		clanData.loadClans();
		playerData = new PlayerData(this);
		clanMessages = new MessageConfig(this, new File(this.getDataFolder().getPath(), "messages.yml"));
		clanMessages.load();
		List<ClanMember> clanMembers = new ArrayList<>();
		clanMembers.add(new ClanMember(UUID.fromString("016f7c6d-dcbc-3bb2-9f74-37096a3b0577"),"Antritus",null));
		clanData.getClans().add(new Clan(UUID.randomUUID(), "NAME", 0, clanMembers, null, null, null, null, this, null, null, null, null, null, null, null));
	}

	@Override
	public void onDisable() {
		if (clanData != null) clanData.saveClans(this, true);
		if (playerData != null) playerData.savePlayers();
	}

	public ClanData getClanData() {
		return clanData;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public MessageConfig getClanMessages() {
		return clanMessages;
	}


	public static void message(Player player, String message){
		player.sendMessage(StringUtils.translate(message));
	}
	public static void messageOffline(OfflinePlayer player, String string) {
		sendOffline(player.getPlayer(), string);
	}
	public static void messageOffline(Player player, String string) {
		sendOffline(player, string);
	}
	private static void sendOffline(Player player, String string){
		playerData.getPlayerFiles().get(player.getUniqueId()).addJoinMessage(string);

	}

	public static void sendClanMessage(UUID clanUUID, String string) {
		for (ClanMember clanMember : clanData.getClan(clanUUID).getPlayers()) {
			if (Bukkit.getPlayer(clanMember.getUUID()) == null || !Bukkit.getPlayer(clanMember.getUUID()).isOnline()){
				continue;
			}
			message(Bukkit.getPlayer(clanMember.getUUID()), string);
		}
	}

	private void addClanMessages(){
		String primaryPath = "clan-";
		{// BAN
			clanMessages.get().addDefault("clan-ban-player-try-ban-banned", "&cThe &f%ban_victim% &chas already been banned for &f%ban_reason%&c!");
			clanMessages.get().addDefault("clan-ban-no-permissions", "&cYou do not have enough permissions to use this command!");
			clanMessages.get().addDefault("clan-ban-player-try-ban-self", "&cYou cannot ban yourself!");
			clanMessages.get().addDefault("clan-ban-player-try-ban-higher-priority", "&cYou do not have permissions to ban this player!");
			clanMessages.get().addDefault("clan-ban-player-try-ban-same-priority", "&cYou do not have permissions to ban this player!");
			clanMessages.get().addDefault("clan-ban-player-victim-success-receiver-victim", "&7You have been banned from &f%clan_name%&7!");
			clanMessages.get().addDefault("clan-ban-player-victim-success-receiver-clan", "&f%player_punisher% &7has banned &f%player_victim%&7 due to &f%reason%&71");
		}
	}
}
