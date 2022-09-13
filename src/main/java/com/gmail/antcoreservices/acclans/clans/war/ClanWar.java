package com.gmail.antcoreservices.acclans.clans.war;

import com.gmail.antcoreservices.acclans.ACClans;
import com.gmail.antcoreservices.acclans.clans.Clan;
import com.gmail.antcoreservices.acclans.clans.ClanMember;
import com.gmail.antcoreservices.acclans.clans.Group;
import com.gmail.antcoreservices.acclans.clans.Permission;
import com.gmail.antcoreservices.acclans.utils.StringUtils;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.gmail.antcoreservices.acclans.ACClans.*;

public class ClanWar{

	ACClans core;
	public ClanWar(ACClans core){
		this.core = core;
	}

	public void start(UUID clan, UUID enemy, Player player, String reason){
		List<String> paths = new ArrayList<>();
		List<String> values = new ArrayList<>();
		values.add(player.getName());
		paths.add("%player%");
		Clan clanAllied = core.getClanData().getClan(clan);
		Clan clanEnemy = core.getClanData().getClan(enemy);
		paths.add("%ally%");
		paths.add("%enemy%");
		paths.add("%reason%");
		values.add(clanAllied.getName());
		values.add(clanEnemy.getName());
		values.add(reason);
		boolean permissions = false;
		for (Group group : clanAllied.getGroups()){
			for (UUID clanMember : group.getUUIDS()){
				if (player.getUniqueId() == clanMember){
					if (group.getPermissions().contains(Permission.WAR)){
						permissions = true;
					}
				}
			}
		}
		if (!permissions){
			message(player, core.getClanMessages().getFormattedMessage("clan-war-start-no-permissions", paths, values));
			return;
		}
		for (ClanEnemy enemyClans : clanAllied.getEnemyClans()){
			if (enemyClans.getUUID() == enemy){
				message(player, core.getClanMessages().getFormattedMessage("clan-war-start-enemy-already-enemy", paths, values));
				return;
			}
		}
		Random r = new Random();
		String code = StringUtils.randomisedCode("abcd1efg2hij3kl4mnp5qr7st8uv9wxyz", 4) + "-" + StringUtils.randomisedCode("abcd1efg2hij3kl4mnp5qr7st8uv9wxyz", 4);
		core.getClanData().getClan(clan).addClanAskWar(enemy);
		core.getClanData().getClan(clan).setClanAskCode(enemy, code);
		core.getClanData().getClan(enemy).addEnemyAskWar(enemy);
		core.getClanData().getClan(enemy).setEnemyAskCode(enemy, code);
		values.add(code);
		paths.add("%code%");
		sendClanMessage(enemy, core.getClanMessages().getFormattedMessage("clan-war-ask-enemy-agreement"));
		sendClanMessage(clan, core.getClanMessages().getFormattedMessage("clan-war-asked-enemy-for-war"));
	}
	public void accept(UUID clan, UUID enemy, Player player, String confirmationCode){
		List<String> paths = new ArrayList<>();
		List<String> values = new ArrayList<>();
		values.add(player.getName());
		paths.add("%player%");
		Clan clanAllied = core.getClanData().getClan(clan);
		Clan clanEnemy = core.getClanData().getClan(enemy);
		paths.add("%ally%");
		paths.add("%enemy%");
		values.add(clanAllied.getName());
		values.add(clanEnemy.getName());
		boolean permissions = false;
		for (Group group : clanAllied.getGroups()){
			for (UUID clanMember : group.getUUIDS()){
				if (player.getUniqueId() == clanMember){
					if (group.getPermissions().contains(Permission.WAR_ACCEPT)){
						permissions = true;
					}
				}
			}
		}
		if (!permissions) {
			message(player, core.getClanMessages().getFormattedMessage("clan-war-accept-no-permissions", paths, values));
			return;
		}
		boolean contEnemy = false;
		boolean contClan = false;
		for (UUID uuid : clanAllied.getEnemyAskWarList()){
			if (uuid == enemy) {
				contClan = true;
				break;
			}
		}
		for (UUID uuidEnemy : clanEnemy.getEnemyAskWarList()){
			if (uuidEnemy == clan) {
				contEnemy = true;
				break;
			}
		}
		if (!contClan && contEnemy){
			message(player, core.getClanMessages().getFormattedMessage("clan-war-accept-clan-not-found-ally", paths, values));
			return;
		}else if (contClan && !contEnemy){
			message(player, core.getClanMessages().getFormattedMessage("clan-war-accept-clan-not-found-enemy", paths, values));
			return;
		}
		else if (!contClan && !contEnemy){
			message(player, core.getClanMessages().getFormattedMessage("clan-war-accept-clan-not-found-both", paths, values));
			return;
		}
	}
}
