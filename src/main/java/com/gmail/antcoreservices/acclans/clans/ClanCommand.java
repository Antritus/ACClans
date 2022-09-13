package com.gmail.antcoreservices.acclans.clans;

import com.gmail.antcoreservices.acclans.ACClans;
import com.gmail.antcoreservices.acclans.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;


@SuppressWarnings("NullableProblems")
public class ClanCommand implements CommandExecutor {
	ACClans core;
	public ClanCommand(ACClans core) {
		this.core = core;
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
		return core.getClanData().getPlayerClans().get(player.getUniqueId()) != null;
	}

	private void createClan(Player player, String clanName){
		if (hasClan(player)) {
			message(player, "&cYou already have a clan!");
			return;
		}
		if (!clanName.toLowerCase().matches("[A-zZ-a1-9]")){
			message(player, "&cThe clan allows characters from a-z and 1-9!");
			return;
		}
		if (clanName.length() > 11) {
			message(player, "&cThe clan name must be under/or 12 characters long!");
			return;
		}
		if (clanName.length() <= 2) {
			message(player, "&cThe clan name must be over 2 letters long!");
			return;
		}
		for (String clan : core.getClanData().getBannedClans()){
			if (clan.equalsIgnoreCase(clanName)){
				message(player, "&cThe clan named &7" + clan + " &cis banned from our server!");
				return;
			}
		}
		for (Clan clan : core.getClanData().getClans()) {
			if (clan.getName().equalsIgnoreCase(clanName)) {
				message(player, "&cThe clan you have tried to create already exists!");
				return;
			}
		}
//		Clan clan = new Clan(core, UUID.randomUUID(), Collections.singletonList(new ClanMember(player.getUniqueId(), player.getName(), null)), null, clanName, 35, 1);
//		Bukkit.broadcastMessage(StringUtils.translate("&7A new clan has been created named " + clan + "&7 by " + player.getName()));
//		core.getClanData().getClans().add(clan);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(StringUtils.translate("&cYou cannot use this command!"));
			return true;
		}
		Player player = (Player) sender;
		if (args == null || args.length == 0) {
			if (args == null) {
				sendHelpMenu(player);
				return true;
			}
			else if (args[0] == "help") {
				sendHelpMenu(player);
				return true;
			}



			else {
				sendHelpMenu(player);
				return true;
			}
		} else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("create")) {
				if (args.length > 1) {
					sendCommandUsage(player, "/clan create <clan>");
					return true;
				}
				createClan(player, args[1]);
			}

		}

		return true;
	}
}
