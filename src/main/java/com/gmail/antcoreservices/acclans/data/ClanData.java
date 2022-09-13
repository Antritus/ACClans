package com.gmail.antcoreservices.acclans.data;

import com.gmail.antcoreservices.acclans.ACClans;
import com.gmail.antcoreservices.acclans.clans.Clan;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ClanData {

	private ArrayList<Clan> clans = new ArrayList<>();
	private ArrayList<String> bannedClans = new ArrayList<>();
	private HashMap<UUID, UUID> playerClans = new HashMap<>();

	private Plugin plugin;
	public ClanData(Plugin plugin) {
		this.plugin = plugin;
	}

	public Clan getClan(UUID uuid) {
		for (Clan clan : clans){
			if (clan.getId() == uuid){
				return clan;
			}
		}
		if (loadClan(uuid)){
			return getClan(uuid);
		}
		return null;
	}


	public void saveClans(ACClans acClans, boolean deletePersistentData) {

		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		File file = new File(plugin.getDataFolder().getPath() + File.separator + "clans" + File.separator );
		file.mkdirs();
		for (Clan clan : clans) {
			clan.deleteDupeCore();
			if (deletePersistentData){
				clan.deleteNonPersistentData();
			}
			String json = gson.toJson(clan);
			StringBuilder builder = new StringBuilder();
			for (String s : json.split("§")){
				if (builder.length() == json.length()) {
					continue;
				}
				builder.append(s).append("&");
			}
			json = builder.toString().replaceAll("}&", "}");
			System.out.println(json);
			try {
				file = new File(plugin.getDataFolder().getPath() + "/clans/", clan.getId() + ".json");
				if (!file.exists()) {
					file.createNewFile();
				}
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(json);
				out.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			clan.setDupeCore(acClans);
		}
	}

	public boolean loadClans() {
		final File file = new File(plugin.getDataFolder() + "/clans/");
		if (file.listFiles() == null){
			return true;
		}
		int filesFound = file.listFiles().length;
		int filesLoaded = 0;
		if (!file.exists()){
			file.mkdirs();
		}
		for (final File fileEntry : file.listFiles()){
			UUID uuid = UUID.fromString(fileEntry.getName().replaceAll(".json", ""));
			if (uuid == null) {
				System.out.println("Unknown file found while loading clans! (" + fileEntry.getName() + ")!");
				continue;
			}
			Gson gson = new Gson();
			try {
				JsonReader reader = new JsonReader(new FileReader(fileEntry));
				int clanNum = -999;
				for (int i = 0; i < clans.size(); i++) {
					if (clans.get(i).getId() == uuid){
						clanNum = i;
					}
				}
				if (clanNum != -999) {
					clans.remove(clanNum);
				}
				clans.add(gson.fromJson(reader, Clan.class));
				filesLoaded++;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Clans Found: " + filesFound + "\n" + "Clans Loaded: " + filesLoaded);
		return true;
	}

	public boolean loadClan(UUID uuid) {
		File file = new File(plugin.getDataFolder().getPath() + "/clans/", uuid + ".json");
		if (file.exists()) {
			Gson gson = new Gson();
			try {
				JsonReader reader = new JsonReader(new FileReader(file));
				int clanNum = -999;
				for (int i = 0; i < clans.size(); i++) {
					if (clans.get(i).getId() == uuid){
						clanNum = i;
					}
				}
				if (clanNum != -999) {
					clans.remove(clanNum);
				}
				clans.add(gson.fromJson(reader, Clan.class));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return file.exists();
	}

	public boolean deleteClan(UUID uuid) {
		int i = -999;
		for (Clan clan : clans) {
			if (clan.getId() == uuid) {
				i = clans.indexOf(clan);
			}
		}
		if (i != -999) {
			clans.remove(i);
		}
		File file = new File(plugin.getDataFolder().getPath() + "/clans/", uuid + ".json");
		if (file.exists()){
			file.delete();
		}
		return file.exists();
	}




	public ArrayList<Clan> getClans() {
		return clans;
	}

	public ArrayList<String> getBannedClans() {
		return bannedClans;
	}

	public HashMap<UUID, UUID> getPlayerClans() {
		return playerClans;
	}


}
